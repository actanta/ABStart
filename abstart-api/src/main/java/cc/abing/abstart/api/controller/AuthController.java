package cc.abing.abstart.api.controller;

import cc.abing.abstart.biz.business.AuthService;
import cc.abing.abstart.biz.service.BizUserService;
import cc.abing.abstart.biz.request.BizUser.BizUserRequest;
import cc.abing.abstart.suite.system.constant.SystemConstant;
import cc.abing.abstart.suite.system.exception.BizException;
import cc.abing.abstart.suite.system.response.CodeMsg;
import cc.abing.abstart.suite.system.result.Result;
import cc.abing.abstart.suite.system.util.ParamUtil;
import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author ABing
 * @since 2026-08-25
 */
@Slf4j
@RestController
@Validated
@RequestMapping(SystemConstant.BASE_PATH + "/auth")
public class AuthController {

	private final BizUserService bizUserService;

	private final AuthService authService;

	@Autowired
	public AuthController(BizUserService bizUserService, AuthService authService) {
		this.bizUserService = bizUserService;
        this.authService = authService;
    }

	@PostMapping(value = "/login")
	public Result login(@RequestBody BizUserRequest bizUserRequest) {
		ParamUtil.notBlank(bizUserRequest, BizUserRequest::getUsername);
		ParamUtil.notBlank(bizUserRequest, BizUserRequest::getPassword);
		return Result.success("登录成功", authService.login(bizUserRequest));
	}

	@PostMapping(value = "/register")
	public Result register(@RequestBody BizUserRequest bizUserRequest) {
		return Result.success("注册成功", authService.register(bizUserRequest));
	}

}
