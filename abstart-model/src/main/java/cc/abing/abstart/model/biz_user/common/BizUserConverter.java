package cc.abing.abstart.model.biz_user.common;

import cc.abing.abstart.model.biz_user.BizUserDO;
import cc.abing.abstart.model.biz_user.common.BizUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * <p>
 *  Converter 接口
 * </p>
 *
 * @author ABing
 * @since 2026-08-25
 */
@Mapper
public interface BizUserConverter {

	BizUserConverter M = Mappers.getMapper(BizUserConverter.class);

	/**
	 * BizUserRequest -> BizUserDO 转换器
	 * @param request
	 * @return
	 */
	BizUserDO convert(BizUserRequest request);
}

