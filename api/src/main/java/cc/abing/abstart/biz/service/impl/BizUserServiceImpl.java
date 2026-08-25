package cc.abing.abstart.biz.service.impl;

import cc.abing.abstart.model.biz_user.BizUserDO;
import cc.abing.abstart.model.biz_user.common.BizUserRequest;
import cc.abing.abstart.model.biz_user.common.BizUserConverter;
import cc.abing.abstart.dao.mapper.BizUserMapper;
import cc.abing.abstart.biz.service.BizUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ABing
 * @since 2026-08-25
 */
@Service
public class BizUserServiceImpl extends ServiceImpl<BizUserMapper, BizUserDO> implements BizUserService {

    private final BizUserMapper bizUserMapper;

	@Autowired
	public BizUserServiceImpl(BizUserMapper bizUserMapper) {
		this.bizUserMapper = bizUserMapper;
	}

	@Override
	public List<BizUserDO> listBizUserDO(Integer id, String username, String password, String slat, String sessionId, String nickname, String avatar, String mobile, String email, Integer status, Date lastLoginTime, Date lastLoginTimeLeft, Date lastLoginTimeRight, String lastLoginIp, Date createdTime, Date createdTimeLeft, Date createdTimeRight, Date updatedTime, Date updatedTimeLeft, Date updatedTimeRight, Integer isDeleted, Integer pageIndex, Integer pageSize) {
		LambdaQueryWrapper<BizUserDO> wrapper = Wrappers.<BizUserDO>lambdaQuery()
                .eq(id != null, BizUserDO::getId, id)
                .eq(username != null, BizUserDO::getUsername, username)
                .eq(password != null, BizUserDO::getPassword, password)
                .eq(slat != null, BizUserDO::getSlat, slat)
                .eq(sessionId != null, BizUserDO::getSessionId, sessionId)
                .eq(nickname != null, BizUserDO::getNickname, nickname)
                .eq(avatar != null, BizUserDO::getAvatar, avatar)
                .eq(mobile != null, BizUserDO::getMobile, mobile)
                .eq(email != null, BizUserDO::getEmail, email)
                .eq(status != null, BizUserDO::getStatus, status)
                .ge(lastLoginTimeLeft != null, BizUserDO::getLastLoginTime, lastLoginTimeLeft)
                .le(lastLoginTimeRight != null, BizUserDO::getLastLoginTime, lastLoginTimeRight)
                .eq(lastLoginIp != null, BizUserDO::getLastLoginIp, lastLoginIp)
                .ge(createdTimeLeft != null, BizUserDO::getCreatedTime, createdTimeLeft)
                .le(createdTimeRight != null, BizUserDO::getCreatedTime, createdTimeRight)
                .ge(updatedTimeLeft != null, BizUserDO::getUpdatedTime, updatedTimeLeft)
                .le(updatedTimeRight != null, BizUserDO::getUpdatedTime, updatedTimeRight)
                .eq(isDeleted != null, BizUserDO::getIsDeleted, isDeleted)
                .orderByDesc(BizUserDO::getId);
                
		return bizUserMapper.selectList(wrapper);
	}

	@Override
	public Page<BizUserDO> pageBizUserDO(Integer id, String username, String password, String slat, String sessionId, String nickname, String avatar, String mobile, String email, Integer status, Date lastLoginTime, Date lastLoginTimeLeft, Date lastLoginTimeRight, String lastLoginIp, Date createdTime, Date createdTimeLeft, Date createdTimeRight, Date updatedTime, Date updatedTimeLeft, Date updatedTimeRight, Integer isDeleted, Integer pageIndex, Integer pageSize) {
            LambdaQueryWrapper<BizUserDO> wrapper = Wrappers.<BizUserDO>lambdaQuery()
                .eq(id != null, BizUserDO::getId, id)
                .eq(username != null, BizUserDO::getUsername, username)
                .eq(password != null, BizUserDO::getPassword, password)
                .eq(slat != null, BizUserDO::getSlat, slat)
                .eq(sessionId != null, BizUserDO::getSessionId, sessionId)
                .eq(nickname != null, BizUserDO::getNickname, nickname)
                .eq(avatar != null, BizUserDO::getAvatar, avatar)
                .eq(mobile != null, BizUserDO::getMobile, mobile)
                .eq(email != null, BizUserDO::getEmail, email)
                .eq(status != null, BizUserDO::getStatus, status)
                .ge(lastLoginTimeLeft != null, BizUserDO::getLastLoginTime, lastLoginTimeLeft)
                .le(lastLoginTimeRight != null, BizUserDO::getLastLoginTime, lastLoginTimeRight)
                .eq(lastLoginIp != null, BizUserDO::getLastLoginIp, lastLoginIp)
                .ge(createdTimeLeft != null, BizUserDO::getCreatedTime, createdTimeLeft)
                .le(createdTimeRight != null, BizUserDO::getCreatedTime, createdTimeRight)
                .ge(updatedTimeLeft != null, BizUserDO::getUpdatedTime, updatedTimeLeft)
                .le(updatedTimeRight != null, BizUserDO::getUpdatedTime, updatedTimeRight)
                .eq(isDeleted != null, BizUserDO::getIsDeleted, isDeleted)
                .orderByDesc(BizUserDO::getId);
                
        return bizUserMapper.selectPage(PageDTO.<BizUserDO>of(pageIndex, pageSize), wrapper);
	}

	@Override
	public Page<BizUserDO> pageBizUserDO(BizUserRequest request) {
	    LambdaQueryWrapper<BizUserDO> wrapper = Wrappers.<BizUserDO>lambdaQuery()
                .eq(request.getId() != null, BizUserDO::getId, request.getId())
                .eq(request.getUsername() != null, BizUserDO::getUsername, request.getUsername())
                .eq(request.getPassword() != null, BizUserDO::getPassword, request.getPassword())
                .eq(request.getSlat() != null, BizUserDO::getSlat, request.getSlat())
                .eq(request.getSessionId() != null, BizUserDO::getSessionId, request.getSessionId())
                .eq(request.getNickname() != null, BizUserDO::getNickname, request.getNickname())
                .eq(request.getAvatar() != null, BizUserDO::getAvatar, request.getAvatar())
                .eq(request.getMobile() != null, BizUserDO::getMobile, request.getMobile())
                .eq(request.getEmail() != null, BizUserDO::getEmail, request.getEmail())
                .eq(request.getStatus() != null, BizUserDO::getStatus, request.getStatus())
                .ge(request.getLastLoginTimeLeft() != null, BizUserDO::getLastLoginTime, request.getLastLoginTimeLeft())
                .le(request.getLastLoginTimeRight() != null, BizUserDO::getLastLoginTime, request.getLastLoginTimeRight())
                .eq(request.getLastLoginIp() != null, BizUserDO::getLastLoginIp, request.getLastLoginIp())
                .ge(request.getCreatedTimeLeft() != null, BizUserDO::getCreatedTime, request.getCreatedTimeLeft())
                .le(request.getCreatedTimeRight() != null, BizUserDO::getCreatedTime, request.getCreatedTimeRight())
                .ge(request.getUpdatedTimeLeft() != null, BizUserDO::getUpdatedTime, request.getUpdatedTimeLeft())
                .le(request.getUpdatedTimeRight() != null, BizUserDO::getUpdatedTime, request.getUpdatedTimeRight())
                .eq(request.getIsDeleted() != null, BizUserDO::getIsDeleted, request.getIsDeleted())
                .orderByDesc(BizUserDO::getId);
                
		return bizUserMapper.selectPage(PageDTO.<BizUserDO>of(request.getPageIndex(), request.getPageSize()),wrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer createBizUserDO(BizUserRequest request) {
		BizUserDO bizUserDO = BizUserConverter.M.convert(request);
		return bizUserMapper.insert(bizUserDO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer patchBizUserDO(BizUserRequest request) {
		BizUserDO bizUserDO = BizUserConverter.M.convert(request);
        BizUserDO updatebizUserDO = new BizUserDO();
            updatebizUserDO.setId(bizUserDO.getId());
            updatebizUserDO.setUsername(bizUserDO.getUsername());
            updatebizUserDO.setPassword(bizUserDO.getPassword());
            updatebizUserDO.setSlat(bizUserDO.getSlat());
            updatebizUserDO.setSessionId(bizUserDO.getSessionId());
            updatebizUserDO.setNickname(bizUserDO.getNickname());
            updatebizUserDO.setAvatar(bizUserDO.getAvatar());
            updatebizUserDO.setMobile(bizUserDO.getMobile());
            updatebizUserDO.setEmail(bizUserDO.getEmail());
            updatebizUserDO.setStatus(bizUserDO.getStatus());
            updatebizUserDO.setLastLoginTime(bizUserDO.getLastLoginTime());
            updatebizUserDO.setLastLoginIp(bizUserDO.getLastLoginIp());
            updatebizUserDO.setCreatedTime(bizUserDO.getCreatedTime());
            updatebizUserDO.setUpdatedTime(bizUserDO.getUpdatedTime());
            updatebizUserDO.setIsDeleted(bizUserDO.getIsDeleted());
		return bizUserMapper.update(updatebizUserDO,
				Wrappers.<BizUserDO>lambdaUpdate().eq(BizUserDO::getId, bizUserDO.getId()).last("limit 1"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updateBizUserDO(BizUserRequest request) {
		BizUserDO bizUserDO = BizUserConverter.M.convert(request);
		return bizUserMapper.update( null,
				Wrappers.<BizUserDO>lambdaUpdate().eq(BizUserDO::getId, bizUserDO.getId())
				    .set(bizUserDO.getId()!=null, BizUserDO::getId, bizUserDO.getId())
				    .set(bizUserDO.getUsername()!=null, BizUserDO::getUsername, bizUserDO.getUsername())
				    .set(bizUserDO.getPassword()!=null, BizUserDO::getPassword, bizUserDO.getPassword())
				    .set(bizUserDO.getSlat()!=null, BizUserDO::getSlat, bizUserDO.getSlat())
				    .set(bizUserDO.getSessionId()!=null, BizUserDO::getSessionId, bizUserDO.getSessionId())
				    .set(bizUserDO.getNickname()!=null, BizUserDO::getNickname, bizUserDO.getNickname())
				    .set(bizUserDO.getAvatar()!=null, BizUserDO::getAvatar, bizUserDO.getAvatar())
				    .set(bizUserDO.getMobile()!=null, BizUserDO::getMobile, bizUserDO.getMobile())
				    .set(bizUserDO.getEmail()!=null, BizUserDO::getEmail, bizUserDO.getEmail())
				    .set(bizUserDO.getStatus()!=null, BizUserDO::getStatus, bizUserDO.getStatus())
				    .set(bizUserDO.getLastLoginTime()!=null, BizUserDO::getLastLoginTime, bizUserDO.getLastLoginTime())
				    .set(bizUserDO.getLastLoginIp()!=null, BizUserDO::getLastLoginIp, bizUserDO.getLastLoginIp())
				    .set(bizUserDO.getCreatedTime()!=null, BizUserDO::getCreatedTime, bizUserDO.getCreatedTime())
				    .set(bizUserDO.getUpdatedTime()!=null, BizUserDO::getUpdatedTime, bizUserDO.getUpdatedTime())
				    .set(bizUserDO.getIsDeleted()!=null, BizUserDO::getIsDeleted, bizUserDO.getIsDeleted())
				.last("limit 1"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer deleteBizUserDO(BizUserRequest request) {
		BizUserDO bizUserDO = BizUserConverter.M.convert(request);
		return bizUserMapper.delete(Wrappers.<BizUserDO>lambdaUpdate().eq(BizUserDO::getId, bizUserDO.getId()).last("limit 1"));
	}

}
