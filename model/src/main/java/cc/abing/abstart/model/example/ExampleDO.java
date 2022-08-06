package cc.abing.abstart.model.example;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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

    @TableId
    private Long id;

    private String string;

    private Boolean bool;

    private Byte[] ipv6;

    private Date createTime;

    private Date modifyTime;

    private BigDecimal num1;

    private Float num2;
}
