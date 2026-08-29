package cc.abing.abstart.api.controller;

import cc.abing.abstart.suite.system.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cc.abing.abstart.biz.service.BizUserService;
import cc.abing.abstart.model.BizUser.BizUserDO;
import cc.abing.abstart.suite.system.constant.SystemConstant;
import cc.abing.abstart.biz.request.BizUser.BizUserRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import java.util.Date;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ABing
 * @since 2026-08-29
 */
@RestController
@RequestMapping(SystemConstant.BASE_PATH + "/abstart/bizUserDO")
public class BizUserController {

	private final BizUserService bizUserService;

	@Autowired
	public BizUserController(BizUserService bizUserService) {
		this.bizUserService = bizUserService;
	}
	
	@GetMapping(value = "")
    	public List<BizUserDO> listBizUserDO(
                @RequestParam(value = "id", required = false) Integer id,
                @RequestParam(value = "username", required = false) String username,
                @RequestParam(value = "password", required = false) String password,
                @RequestParam(value = "slat", required = false) String slat,
                @RequestParam(value = "sessionId", required = false) String sessionId,
                @RequestParam(value = "nickname", required = false) String nickname,
                @RequestParam(value = "avatar", required = false) String avatar,
                @RequestParam(value = "mobile", required = false) String mobile,
                @RequestParam(value = "email", required = false) String email,
                @RequestParam(value = "status", required = false) Byte status,
                @RequestParam(value = "lastLoginTime", required = false) Date lastLoginTime,
                @RequestParam(value = "lastLoginTimeLeft", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date lastLoginTimeLeft,
                @RequestParam(value = "lastLoginTimeRight", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date lastLoginTimeRight,
                @RequestParam(value = "lastLoginIp", required = false) String lastLoginIp,
                @RequestParam(value = "createdTime", required = false) Date createdTime,
                @RequestParam(value = "createdTimeLeft", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createdTimeLeft,
                @RequestParam(value = "createdTimeRight", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createdTimeRight,
                @RequestParam(value = "updatedTime", required = false) Date updatedTime,
                @RequestParam(value = "updatedTimeLeft", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date updatedTimeLeft,
                @RequestParam(value = "updatedTimeRight", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date updatedTimeRight,
                @RequestParam(value = "isDeleted", required = false) Byte isDeleted,
    			@RequestParam(value = "page_index", required = false, defaultValue = "1") Integer pageIndex,
    			@Valid @Max(value = 50, message = "page_size超过最大值") @RequestParam(value = "page_size", required = false,defaultValue = "20") Integer pageSize) {
    		return bizUserService.listBizUserDO(id, username, password, slat, sessionId, nickname, avatar, mobile, email, status, lastLoginTime, lastLoginTimeLeft, lastLoginTimeRight, lastLoginIp, createdTime, createdTimeLeft, createdTimeRight, updatedTime, updatedTimeLeft, updatedTimeRight, isDeleted,  pageIndex, pageSize);
    	}
    
    	@GetMapping(value = "/page")
    	public Page<BizUserDO> pageBizUserDO(@Validated(Query.class) BizUserRequest request) {
    		return bizUserService.pageBizUserDO(request);
    	}
    
    	@PostMapping(value = "")
    	public Integer createBizUserDO(@Validated(Create.class) @RequestBody BizUserRequest request) {
    		return bizUserService.createBizUserDO(request);
    	}
    
    	@PatchMapping(value = "")
    	public Integer patchBizUserDO(@Validated(Patch.class) @RequestBody BizUserRequest request) {
    		return bizUserService.patchBizUserDO(request);
    	}
    
    	@PutMapping(value = "")
    	public Integer updateBizUserDO(@Validated(Put.class) @RequestBody BizUserRequest request) {
    		return bizUserService.updateBizUserDO(request);
    	}
    
    	@DeleteMapping(value = "")
    	public Integer deleteBizUserDO(@Validated(Delete.class) @RequestBody BizUserRequest request) {
    		return bizUserService.deleteBizUserDO(request);
    	}

}
