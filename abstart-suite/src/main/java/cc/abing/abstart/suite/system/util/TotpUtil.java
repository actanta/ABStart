package cc.abing.abstart.suite.system.util;
import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

/**
 * RFC6238 TOTP 工具
 * 兼容Google Authenticator
 */
public class TotpUtil {

    public static final int DEFAULT_STEP_SECONDS = 30;
    public static final int DEFAULT_DIGITS = 6;
    public static final String DEFAULT_ALGORITHM = "HmacSHA1";

    /**
     * 根据base32密钥生成TOTP验证码
     * @param base32Secret Google Authenticator给出的Base32密钥
     * @return 6位数字验证码字符串，带前导零
     */
    public static String generateTotp(String base32Secret) {
        return generateTotp(base32Secret, Instant.now().getEpochSecond(), DEFAULT_STEP_SECONDS, DEFAULT_DIGITS, DEFAULT_ALGORITHM);
    }

    /**
     * 校验TOTP验证码
     * @param base32Secret base32密钥
     * @param inputCode 用户输入的6位验证码
     * @param window 时间窗口，前后各允许偏移多少个step，谷歌一般 window=1 （允许上一个、当前、下一个时间片）
     * @return true=校验通过
     */
    public static boolean verifyTotp(String base32Secret, String inputCode, int window) {
        long now = Instant.now().getEpochSecond();
        for (int i = -window; i <= window; i++) {
            long checkTime = now + (long) i * DEFAULT_STEP_SECONDS;
            String code = generateTotp(base32Secret, checkTime, DEFAULT_STEP_SECONDS, DEFAULT_DIGITS, DEFAULT_ALGORITHM);
            if (code.equals(inputCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 底层生成TOTP
     * @param base32Secret base32编码密钥
     * @param epochSeconds unix时间戳秒
     * @param step 步长，单位秒
     * @param digits 输出位数
     * @param algorithm HmacSHA1 / HmacSHA256 / HmacSHA512
     * @return totp码
     */
    public static String generateTotp(String base32Secret, long epochSeconds, int step, int digits, String algorithm) {
        Base32 base32 = new Base32();
        byte[] keyBytes = base32.decode(base32Secret);

        // T = floor(now / step)
        long timeCounter = epochSeconds / step;

        // T转8字节 大端字节数组
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putLong(timeCounter);
        byte[] timeBytes = buffer.array();

        byte[] hmacResult;
        try {
            Mac mac = Mac.getInstance(algorithm);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, algorithm);
            mac.init(keySpec);
            hmacResult = mac.doFinal(timeBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        // dynamic truncation RFC4226 HOTP
        int offset = hmacResult[hmacResult.length - 1] & 0x0F;
        int binary = ((hmacResult[offset] & 0x7F) << 24)
                | ((hmacResult[offset + 1] & 0xFF) << 16)
                | ((hmacResult[offset + 2] & 0xFF) << 8)
                | (hmacResult[offset + 3] & 0xFF);

        int mod = (int) Math.pow(10, digits);
        int otp = binary % mod;

        // 补前导0
        return String.format("%0" + digits + "d", otp);
    }

    // 测试
    public static void main(String[] args) {
        // 替换为账号绑定身份验证器给出的Base32密钥，全部大写，去掉空格
        String secret = "JBSWY3DPEHPK3PXP";

        String code = generateTotp(secret);
        System.out.println("当前TOTP验证码：" + code);

        boolean ok = verifyTotp(secret, code, 1);
        System.out.println("校验结果：" + ok);
    }
}

