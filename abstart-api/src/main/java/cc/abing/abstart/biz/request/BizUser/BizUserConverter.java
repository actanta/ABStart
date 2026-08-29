package cc.abing.abstart.biz.request.BizUser;

import cc.abing.abstart.model.BizUser.BizUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * <p>
 *  Converter 接口
 * </p>
 *
 * @author ABing
 * @since 2026-08-29
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

