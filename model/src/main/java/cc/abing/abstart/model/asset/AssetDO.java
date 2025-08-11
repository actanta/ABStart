package cc.abing.abstart.model.asset;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资产记录表
 * </p>
 *
 * @author CodeGenerator
 * @since 2025-08-11
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
     * 资产分类ID（关联asset_category表）
     */
    private Integer categoryId;

    /**
     * 资产数量
     */
    private Long amount;

    /**
     * 资产余量
     */
    private Long balance;

    /**
     * 采购价格（单位：分）
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
     * 图片路径（多图用JSON数组存储）
     */
    private String imagePath;

    /**
     * 资产状态：0删除 1正常 2维修中 3已丢失
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

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
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AssetDO{" +
            "id=" + id +
            ", name=" + name +
            ", ownerId=" + ownerId +
            ", categoryId=" + categoryId +
            ", amount=" + amount +
            ", balance=" + balance +
            ", purchasePrice=" + purchasePrice +
            ", purchaseTime=" + purchaseTime +
            ", expirationTime=" + expirationTime +
            ", purchaseChannel=" + purchaseChannel +
            ", purchaseChannelDetail=" + purchaseChannelDetail +
            ", imagePath=" + imagePath +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
