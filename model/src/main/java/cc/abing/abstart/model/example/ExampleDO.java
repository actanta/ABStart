package cc.abing.abstart.model.example;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName(value = "example", schema = "abstart")
public class ExampleDO implements Serializable {

    private static final long serialVersionUID = -4164424827871435176L;
    private Long id;

    private String string;

    private Boolean bool;

    private Byte[] ipv6;

    private Date createTime;

    private Date modifyTime;

    private BigDecimal num1;

    private Float num2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Boolean getBool() {
        return bool;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public Byte[] getIpv6() {
        return ipv6;
    }

    public void setIpv6(Byte[] ipv6) {
        this.ipv6 = ipv6;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public BigDecimal getNum1() {
        return num1;
    }

    public void setNum1(BigDecimal num1) {
        this.num1 = num1;
    }

    public Float getNum2() {
        return num2;
    }

    public void setNum2(Float num2) {
        this.num2 = num2;
    }
}
