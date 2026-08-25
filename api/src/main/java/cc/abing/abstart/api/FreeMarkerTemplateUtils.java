package cc.abing.abstart.api;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FreeMarker字符串模板渲染通用工具类
 * 不依赖业务实体，全部使用Map作为数据模型演示
 */
public class FreeMarkerTemplateUtils {

    public static void main(String[] args) {
        // 1. 字符串模板内容，直接写代码内，无需文件
        String templateContent = "#### 分类标准\n" +
                "<#list data as category>\n" +
                "${category_index + 1}. ${category.name}（来自模板表中的1级分类）\n" +
                "- 定义：${category.categoryDesc}（来自模板表中的1级分类说明）\n" +
                "- 板块(从以下列表中选1个或多个最接近的)：\n" +
                "  <#if category.categoryInferenceRule == \"A\">\n" +
                "    <#list category.children as secondLevel>\n" +
                "      ${secondLevel.name}<#if secondLevel_has_next>, </#if>\n" +
                "    </#list>\n" +
                "  <#elseif category.categoryInferenceRule == \"B\">\n" +
                "    <#list category.children as secondLevel>\n" +
                "      <#list secondLevel.children as thirdLevel>\n" +
                "        ${thirdLevel.name}<#if thirdLevel_has_next>;</#if>\n" +
                "      </#list>\n" +
                "      <#if secondLevel_has_next>;</#if>\n" +
                "    </#list>\n" +
                "  </#if>\n" +
                "<#if category.name == \"客户信息\">\n" +
                "- 排除：内部员工、代理人、合作伙伴、内部机构等。\n" +
                "- 补充说明：客户信息需包含最新的实名认证数据。\n" +
                "</#if>\n" +
                "<#if category.name == \"业务信息\">\n" +
                "- 注意事项：业务信息需与交易记录保持一致性。\n" +
                "</#if>\n" +
                "</#list>";

        // 2. 构造纯Map嵌套数据模型，替代VO对象
        List<Map<String, Object>> dataList = new ArrayList<>();

        // 构造一级分类：客户信息 rule=A
        Map<String, Object> customerCategory = new HashMap<>();
        customerCategory.put("name", "客户信息");
        customerCategory.put("categoryDesc", "外部客户（自然人/企业）的自然属性、认证信息、标签等。");
        customerCategory.put("categoryInferenceRule", "A");

        List<Map<String, Object>> customerChildren = new ArrayList<>();
        customerChildren.add(createCategory("个人客户"));
        customerChildren.add(createCategory("企业客户"));
        customerCategory.put("children", customerChildren);
        dataList.add(customerCategory);

        // 构造一级分类：业务信息 rule=B，嵌套二级、三级
        Map<String, Object> businessCategory = new HashMap<>();
        businessCategory.put("name", "业务信息");
        businessCategory.put("categoryDesc", "银行在直接面向客户、开展金融服务过程中产生和使用的信息集合。");
        businessCategory.put("categoryInferenceRule", "B");

        List<Map<String, Object>> businessChildren = new ArrayList<>();

        Map<String, Object> level2Item1 = createCategory("账户管理");
        List<Map<String, Object>> level3List1 = new ArrayList<>();
        level3List1.add(createCategory("账户信息"));
        level3List1.add(createCategory("开户记录"));
        level2Item1.put("children", level3List1);
        businessChildren.add(level2Item1);

        Map<String, Object> level2Item2 = createCategory("数字货币");
        List<Map<String, Object>> level3List2 = new ArrayList<>();
        level3List2.add(createCategory("法定数字货币钱包信息"));
        level3List2.add(createCategory("交易流水"));
        level2Item2.put("children", level3List2);
        businessChildren.add(level2Item2);

        businessCategory.put("children", businessChildren);
        dataList.add(businessCategory);

        // 组装顶层model
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("data", dataList);

        // 3. 执行渲染
        String result = render(templateContent, dataModel);

        // 4. 输出结果
        System.out.println("====渲染输出====");
        System.out.println(result);
    }

    /**
     * 快速构造分类Map，模拟树节点
     */
    private static Map<String, Object> createCategory(String name) {
        Map<String, Object> category = new HashMap<>();
        category.put("name", name);
        return category;
    }

    /**
     * FreeMarker字符串模板渲染核心方法
     * @param templateContent 模板字符串
     * @param dataModel 数据模型，全部使用Map结构
     * @return 渲染完成后的文本
     */
    public static String render(String templateContent, Map<String, Object> dataModel) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            // 字符串模板加载器，不从磁盘读文件
            StringTemplateLoader loader = new StringTemplateLoader();
            loader.putTemplate("inline-template", templateContent);
            cfg.setTemplateLoader(loader);

            Template template = cfg.getTemplate("inline-template", "UTF-8");
            StringWriter writer = new StringWriter();
            template.process(dataModel, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("FreeMarker模板渲染失败", e);
        }
    }
}
