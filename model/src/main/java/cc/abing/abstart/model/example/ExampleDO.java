package cc.abing.abstart.model.example;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author userz
 */
@Data
@TableName(value = "example", schema = "abstart")
public class ExampleDO implements Serializable {

	private static final long serialVersionUID = -4164424827871435176L;

	/**
	 * 序号
	 */
	@TableId
	private Long id;

	/**
	 * 字符串
	 */
	@Length(max = 20, message = "字符串限制20位")
	private String string;

	/**
	 * 布尔值
	 */
	private Boolean bool;

	/**
	 * IPv6
	 */
	private Byte[] ipv6;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * BigDecimal数
	 */
	private BigDecimal num1;

	/**
	 * 浮点数
	 */
	private Float num2;

//	@Size(max = 16,message = "用户名不能超过16个字符")
//	@NotNull(message = "用户名不能为空")
//	private String username;
//
//	@NotBlank(message = "密码不能为空")
//	@Length(min = 6, max = 16, message = "密码长度为6-16位")
//	private String password;
//
//	@Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "11位手机号格式不正确")
//	@NotNull(message = "mobile不能为空")
//	private String mobile;
//
//	@Email(message = "邮箱格式不正确")
//	@NotNull(message = "email不能为空")
//	private String email;
//
//
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	private LocalDateTime localDateTime;

}
