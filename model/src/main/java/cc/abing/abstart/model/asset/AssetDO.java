package cc.abing.abstart.model.asset;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.Date;

/**
 * <p>
 * 采购记录表 资产分类表(树状primary_category secondary_category1衣物 2数码 3食物 4学习)
 * </p>
 *
 * @author CodeGenerator
 * @since 2022-12-10
 */
@TableName("abstart.asset")
public class AssetDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资产编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 资产名称
     */
    private String name;

    /**
     * 所有者编号
     */
    private Long ownerId;

    /**
     * 资产分类
     */
    private Integer category;

    /**
     * 资产具体分类
     */
    private Integer specificCategory;

    /**
     * 资产标签 必需品、双十一、生日
     */
    private String tag;

    /**
     * 资产数量
     */
    private Long amount;

    /**
     * 资产余量
     */
    private Long balance;

    /**
     * 采购价格
     */
    private Long purchasePrice;

    /**
     * 采购时间
     */
    private Date purchaseTime;

    /**
     * 过期时间
     */
    private Date expirationTime;

    /**
     * 采购渠道：0未分类 1线下 2淘宝 3京东 4拼多多
     */
    private Integer purchaseChannel;

    /**
     * 采购渠道具体信息
     */
    private String purchaseChannelDetail;

    /**
     * 图片路径
     */
    private String imagePath;

    /**
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
    public Integer getSpecificCategory() {
        return specificCategory;
    }

    public void setSpecificCategory(Integer specificCategory) {
        this.specificCategory = specificCategory;
    }
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
    public Long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }
    public Integer getPurchaseChannel() {
        return purchaseChannel;
    }

    public void setPurchaseChannel(Integer purchaseChannel) {
        this.purchaseChannel = purchaseChannel;
    }
    public String getPurchaseChannelDetail() {
        return purchaseChannelDetail;
    }

    public void setPurchaseChannelDetail(String purchaseChannelDetail) {
        this.purchaseChannelDetail = purchaseChannelDetail;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AssetDO{" +
            "id=" + id +
            ", name=" + name +
            ", ownerId=" + ownerId +
            ", category=" + category +
            ", specificCategory=" + specificCategory +
            ", tag=" + tag +
            ", amount=" + amount +
            ", balance=" + balance +
            ", purchasePrice=" + purchasePrice +
            ", purchaseTime=" + purchaseTime +
            ", expirationTime=" + expirationTime +
            ", purchaseChannel=" + purchaseChannel +
            ", purchaseChannelDetail=" + purchaseChannelDetail +
            ", imagePath=" + imagePath +
            ", remark=" + remark +
        "}";
    }
}
