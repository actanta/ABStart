package cc.abing.abstart.schedule;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ScheduledStockMonitor {

    // 飞书Webhook URL（需要替换为你自己的）
    private static final String FEISHU_WEBHOOK_URL = "https://open.feishu.cn/open-apis/bot/v2/hook/4ab912bb-2fdb-4b67-9d61-c12cfff131c6";

    // 股票代码映射（前缀：沪市6开头，深市0和3开头）
    private static final Map<String, List<String>> STOCK_PREFIXES = new HashMap<>();
    static {
        STOCK_PREFIXES.put("sh", Arrays.asList("6"));     // 沪市
        STOCK_PREFIXES.put("sz", Arrays.asList("0", "3")); // 深市主板和创业板
    }

    // 监控规则存储
    private static final Map<String, List<MonitorRule>> stockRules = new HashMap<>();

    // 定时任务执行器
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    // 日期格式化器
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) {
        // 示例：添加监控规则
        addMonitorRule("000877", new MonitorRule(5.55, 5.95, "自定义监控规则"));

        // 启动监控任务（每30秒检查一次）
        scheduler.scheduleAtFixedRate(ScheduledStockMonitor::checkStockPrices, 0, 30, TimeUnit.SECONDS);
    }

    /**
     * 添加监控规则
     * @param stockCode 股票代码（不带前缀）
     * @param rule 监控规则
     */
    public static void addMonitorRule(String stockCode, MonitorRule rule) {
        String fullCode = getFullStockCode(stockCode);
        stockRules.computeIfAbsent(fullCode, k -> new ArrayList<>()).add(rule);
        System.out.println("已添加监控规则: " + fullCode + " - " + rule);
    }
    
    /**
     * 根据股票代码获取带市场前缀的完整代码
     */
    private static String getFullStockCode(String stockCode) {
        for (Map.Entry<String, List<String>> entry : STOCK_PREFIXES.entrySet()) {
            String prefix = entry.getKey();
            for (String startDigit : entry.getValue()) {
                if (stockCode.startsWith(startDigit)) {
                    return prefix + stockCode;
                }
            }
        }
        throw new IllegalArgumentException("无效的股票代码: " + stockCode);
    }

    /**
     * 判断是否为工作日（周一至周五）
     * @return 是否为工作日
     */
    private static boolean isWorkingDay() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    private static boolean isInTradingTime() {
        //9点30分到11点30分，13点30分到15点00分
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        LocalTime now = LocalTime.now(zoneId);
        return now.isAfter(LocalTime.of(9, 30)) && now.isBefore(LocalTime.of(11, 30)) ||
                now.isAfter(LocalTime.of(13, 30)) && now.isBefore(LocalTime.of(15, 0));
    }
    /**
     * 检查股票价格并触发告警
     */
    public static void checkStockPrices() {
        //如果是非工作日且不在时间段，则不检查
        if (!isWorkingDay() || !isInTradingTime()) {
            return;
        }
        for (Map.Entry<String, List<MonitorRule>> entry : stockRules.entrySet()) {
            String stockCode = entry.getKey();
            List<MonitorRule> rules = entry.getValue();

            try {
                // 获取股票实时数据
                StockData stockData = getStockData(stockCode);
                if (stockData == null) {
                    System.err.println("无法获取股票数据: " + stockCode);
                    continue;
                }

                System.out.println(DATE_FORMAT.format(new Date()) + " 检查数据: " + stockCode + " 当前价: " + stockData.currentPrice);

                // 检查每条规则
                for (MonitorRule rule : rules) {
                    if (shouldTriggerAlert(stockData.currentPrice, rule)) {
                        String message = createAlertMessage(stockCode, stockData, rule);
                        sendFeishuAlert(message);
                    }
                }
            } catch (Exception e) {
                System.err.println("检查股票 " + stockCode + " 时出错: " + e.getMessage());
            }
        }
    }

    /**
     * 判断是否应该触发告警
     */
    private static boolean shouldTriggerAlert(double currentPrice, MonitorRule rule) {
        return (rule.getLowerThreshold() != null && currentPrice <= rule.getLowerThreshold()) ||
                (rule.getUpperThreshold() != null && currentPrice >= rule.getUpperThreshold());
    }

    /**
     * 创建告警消息
     */
    private static String createAlertMessage(String stockCode, StockData stockData, MonitorRule rule) {
        String direction = (rule.getUpperThreshold() != null && stockData.currentPrice >= rule.getUpperThreshold()) ? "上涨" : "下跌";

        return String.format(
            "**股票价格警报**\\n" +
            "- 股票代码: %s\\n" +
            "- 股票名称: %s\\n" +
            "- 当前价格: %.2f\\n" +
            "- 涨跌幅: %s%%\\n" +
            "- 触发规则: %s达到 %s\\n" +
            "- 监控时间: %s\\n",
            stockCode,
            stockData.name,
            stockData.currentPrice,
            stockData.changePercent,
            direction,
            rule.getDescription(),
            DATE_FORMAT.format(stockData.timestamp)
        );
    }

    /**
     * 发送飞书告警
     */
    public static void sendFeishuAlert(String message) {
        try {
            // 使用Hutool发送POST请求
            String body = "{\"msg_type\":\"text\",\"content\":{\"text\":\"" + message + "\"}}";
            System.out.println(body);
            HttpResponse execute = HttpRequest.post(FEISHU_WEBHOOK_URL)
                    .header("Content-Type", "application/json")
                    .body(body)
                    .execute();
            int status = execute.getStatus();
            if (status != 200) {
                System.out.println("飞书告警发送失败，状态码：" + status + " body: " + execute.body());
            } else {
                System.out.println("飞书告警发送成功");
            }
        } catch (Exception e) {
            System.err.println("发送飞书告警失败: " + e.getMessage());
        }
    }

    private static final String WEBHOOK_URL = "https://oapi.dingtalk.com/robot/send?access_token=1e8f3e6a2dfe791bbbea2aad10b6072a48d58518c3b9882d7fddb8c013fd9054";

    private static final String SECRET = "SEC2b52c139179e4d141b1bd573cde214965dbcac160c36b1466c1953209edcef32";
    public static void sendDingTalkMessage(String message) {
        try {
            // 如果设置了加签，需要计算签名
            String timestamp = String.valueOf(System.currentTimeMillis());
            String stringToSign = timestamp + "\n" + SECRET;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            String sign = URLEncoder.encode(Base64.getEncoder().encodeToString(signData), "UTF-8");

            // 构建最终的URL
            String url = WEBHOOK_URL + "&timestamp=" + timestamp + "&sign=" + sign;

            // 构建消息体
            JSONObject json = new JSONObject();
            json.set("msgtype", "text");
            JSONObject text = new JSONObject();
            text.set("content", message);
            json.set("text", text);

            // 发送POST请求
            HttpResponse response = HttpRequest.post(url)
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonStr(json))
                    .execute();

            // 处理响应
            if (response.isOk()) {
                System.out.println("消息发送成功: " + response.body());
            } else {
                System.out.println("消息发送失败: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("发送钉钉消息异常:" + e.getMessage());
        }
    }

    /**
     * 从新浪财经API获取股票数据
     */
    public static StockData getStockData(String stockCode) {
        try {
            // 构建新浪财经API URL
            String url = "http://hq.sinajs.cn/list=" + stockCode;

            // 使用Hutool发送GET请求
            HttpResponse response = HttpRequest.get(url)
                    .header("Referer", "https://finance.sina.com.cn/")
                    .execute();

            if (response.getStatus() == 200) {
                return StockData.parseSinaStockData(response.body(), stockCode);
            }
        } catch (Exception e) {
            System.err.println("获取股票数据失败: " + stockCode + " - " + e.getMessage());
        }
        return null;
    }

    /**
     * 股票数据类
     */
    public static class StockData {
        public String name;
        public String code;
        public double currentPrice;
        public String changePercent;
        public Date timestamp;

        public static StockData parseSinaStockData(String responseBody, String stockCode) {
            if (responseBody != null && responseBody.contains("\"")) {
                String data = responseBody.split("\"")[1];
                String[] parts = data.split(",");

                if (parts.length > 3) {
                    StockData stockData = new StockData();
                    stockData.name = parts[0];
                    stockData.code = stockCode;
                    stockData.currentPrice = Double.parseDouble(parts[3]);
                    stockData.changePercent = parts.length > 32 ? parts[32] : "0";
                    stockData.timestamp = new Date();
                    return stockData;
                }
            }
            return null;
        }
    }

    /**
     * 监控规则类
     */
    @Getter
    @RequiredArgsConstructor
    public static class MonitorRule {
        private final Double lowerThreshold;  // 下跌阈值（低于此值触发）
        private final Double upperThreshold;  // 上涨阈值（高于此值触发）
        private final String description;     // 规则描述

        @Override
        public String toString() {
            return String.format("低于%.2f或高于%.2f时提醒(%s)",
                    lowerThreshold != null ? lowerThreshold : 0,
                    upperThreshold != null ? upperThreshold : 0,
                    description);
        }
    }
}