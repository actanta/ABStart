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

}
