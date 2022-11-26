package cc.abing.abstart.api.login;

import cc.abing.abstart.support.system.constant.SystemConstant;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ABing
 * @since 2022/11/22
 */
@Slf4j
@RestController
@Validated
@RequestMapping(SystemConstant.BASE_PATH + "/login")
public class LoginController {

	@GetMapping(value = "/")
	public String listExampleDO(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		// TODO待完善 暂时直接返回md5，用于前端接口测试
		return Hashing.md5().newHasher().putString(username + password, Charsets.UTF_8).hash().toString();
	}

}
