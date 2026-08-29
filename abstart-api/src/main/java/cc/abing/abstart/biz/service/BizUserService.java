package cc.abing.abstart.biz.service;

import cc.abing.abstart.model.BizUser.BizUserDO;
import cc.abing.abstart.biz.request.BizUser.BizUserRequest;
import com.baomidou.mybatisplus.spring.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ABing
 * @since 2026-08-29
 */
public interface BizUserService extends IService<BizUserDO> {

/**
	 * 获取BizUserDO列表
	 * @return
	 */
	List<BizUserDO> listBizUserDO(Integer id, String username, String password, String slat, String sessionId, String nickname, String avatar, String mobile, String email, Byte status, Date lastLoginTime, Date lastLoginTimeLeft, Date lastLoginTimeRight, String lastLoginIp, Date createdTime, Date createdTimeLeft, Date createdTimeRight, Date updatedTime, Date updatedTimeLeft, Date updatedTimeRight, Byte isDeleted, Integer pageIndex, Integer pageSize);

	/**
	 * 获取BizUserDO分页
	 */
	Page<BizUserDO> pageBizUserDO(Integer id, String username, String password, String slat, String sessionId, String nickname, String avatar, String mobile, String email, Byte status, Date lastLoginTime, Date lastLoginTimeLeft, Date lastLoginTimeRight, String lastLoginIp, Date createdTime, Date createdTimeLeft, Date createdTimeRight, Date updatedTime, Date updatedTimeLeft, Date updatedTimeRight, Byte isDeleted, Integer pageIndex, Integer pageSize);

	/**
	 * 获取BizUserDO分页
	 */
	Page<BizUserDO> pageBizUserDO(BizUserRequest request);

	/**
	 * 创建BizUserDO
	 */
	Integer createBizUserDO(BizUserRequest request);

	/**
	 * 修改BizUserDO
	 */
	Integer patchBizUserDO(BizUserRequest request);

	/**
	 * 更新BizUserDO
	 */
	Integer updateBizUserDO(BizUserRequest request);

	/**
	 * 删除BizUserDO
	 */
	Integer deleteBizUserDO(BizUserRequest request);

}
