package cc.abing.abstart.biz.business;

import cc.abing.abstart.biz.request.BizUser.BizUserRequest;

/**
 * <p>
 *  认证服务类
 * </p>
 *
 * @author ABing
 * @since 2026-08-25
 */
public interface AuthService {

    Object login(BizUserRequest bizUserRequest);

    Object register(BizUserRequest bizUserRequest);
}
