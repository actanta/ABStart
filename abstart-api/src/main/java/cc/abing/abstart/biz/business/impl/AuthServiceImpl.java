package cc.abing.abstart.biz.business.impl;


import cc.abing.abstart.biz.business.AuthService;
import cc.abing.abstart.biz.request.BizUser.BizUserRequest;
import cc.abing.abstart.biz.service.BizUserService;
import cc.abing.abstart.model.BizUser.BizUserDO;
import cc.abing.abstart.suite.system.exception.BizException;
import cc.abing.abstart.suite.system.response.CodeMsg;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final BizUserService bizUserService;

    @Autowired
    public AuthServiceImpl(BizUserService bizUserService){
        this.bizUserService = bizUserService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object login(BizUserRequest bizUserRequest) {
        BizUserDO bizUserDO = bizUserService.getOne(new LambdaQueryWrapper<BizUserDO>()
                .eq(BizUserDO::getUsername, bizUserRequest.getUsername())
        );
        if (Objects.isNull(bizUserDO)){
            throw new BizException(CodeMsg.BAD_REQUEST, "用户名或密码错误");
        }

        if (Objects.equals(bizUserDO.getStatus(), (byte)1) &&
                BCrypt.checkpw(bizUserRequest.getPassword(), bizUserDO.getPassword())){
            StpUtil.login(bizUserDO.getId());
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            bizUserDO.setSessionId(tokenInfo.getTokenValue());
            bizUserDO.setLastLoginTime(new Date());
            bizUserService.updateById(bizUserDO);
            return bizUserDO;
        }
        throw new BizException(CodeMsg.BAD_REQUEST, "用户名或密码错误");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object register(BizUserRequest bizUserRequest) {
        String gensalt = BCrypt.gensalt();
        log.info("salt:{}", gensalt);
        log.info("BCrypt:{}", BCrypt.hashpw(bizUserRequest.getPassword(), gensalt));
        throw new BizException(CodeMsg.SYSTEM_ERR, "注册功能未开放");
    }
}
