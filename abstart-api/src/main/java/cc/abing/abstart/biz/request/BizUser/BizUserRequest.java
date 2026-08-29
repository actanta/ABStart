package cc.abing.abstart.biz.request.BizUser;

import cc.abing.abstart.model.BizUser.BizUserDO;
import jakarta.validation.constraints.Max;
import java.util.Date;
/**
 * <p>
 *  Request模型
 * </p>
 *
 * @author ABing
 * @since 2026-08-29
 */
public class BizUserRequest extends BizUserDO {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String slat;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态（0:禁用 1:启用）
     */
    private Byte status;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录时间左边界
     */
    private Date lastLoginTimeLeft;

    /**
     * 最后登录时间右边界
     */
    private Date lastLoginTimeRight;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 创建时间左边界
     */
    private Date createdTimeLeft;

    /**
     * 创建时间右边界
     */
    private Date createdTimeRight;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 更新时间左边界
     */
    private Date updatedTimeLeft;

    /**
     * 更新时间右边界
     */
    private Date updatedTimeRight;

    /**
     * 逻辑删除标记
     */
    private Byte isDeleted;


	/**
	 * 分页页码
	 */
	protected Integer pageIndex = 1;

	/**
	 * 分页大小
	 */
	@Max(value = 50, message = "page_size超过最大值")
	protected Integer pageSize = 20;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getSlat() {
        return slat;
    }

    public void setSlat(String slat) {
        this.slat = slat;
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }


    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastLoginTimeLeft() {
        return lastLoginTimeLeft;
    }

    public void setLastLoginTimeLeft(Date lastLoginTimeLeft) {
        this.lastLoginTimeLeft = lastLoginTimeLeft;
    }

    public Date getLastLoginTimeRight() {
        return lastLoginTimeRight;
    }

    public void setLastLoginTimeRight(Date lastLoginTimeRight) {
        this.lastLoginTimeRight = lastLoginTimeRight;
    }


    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }


    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getCreatedTimeLeft() {
        return createdTimeLeft;
    }

    public void setCreatedTimeLeft(Date createdTimeLeft) {
        this.createdTimeLeft = createdTimeLeft;
    }

    public Date getCreatedTimeRight() {
        return createdTimeRight;
    }

    public void setCreatedTimeRight(Date createdTimeRight) {
        this.createdTimeRight = createdTimeRight;
    }


    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTimeLeft() {
        return updatedTimeLeft;
    }

    public void setUpdatedTimeLeft(Date updatedTimeLeft) {
        this.updatedTimeLeft = updatedTimeLeft;
    }

    public Date getUpdatedTimeRight() {
        return updatedTimeRight;
    }

    public void setUpdatedTimeRight(Date updatedTimeRight) {
        this.updatedTimeRight = updatedTimeRight;
    }


    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }




    @Override
    public String toString() {
        return "BizUserDO{" +
            "id=" + id +
            ", username=" + username +
            ", password=" + password +
            ", slat=" + slat +
            ", sessionId=" + sessionId +
            ", nickname=" + nickname +
            ", avatar=" + avatar +
            ", mobile=" + mobile +
            ", email=" + email +
            ", status=" + status +
            ", lastLoginTime=" + lastLoginTime +
            ", lastLoginIp=" + lastLoginIp +
            ", createdTime=" + createdTime +
            ", updatedTime=" + updatedTime +
            ", isDeleted=" + isDeleted +
        "}";
    }

	public Integer getPageIndex() {
		return pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageIndex(Integer pageIndex) {
		if (null == pageIndex || pageIndex <= 0) {
			pageIndex = 1;
		}
		this.pageIndex = pageIndex;
	}

	public void setPageSize(Integer pageSize) {
		if (null == pageSize || pageSize <= 0) {
			pageSize = 20;
		}
		this.pageSize = pageSize;
	}
}