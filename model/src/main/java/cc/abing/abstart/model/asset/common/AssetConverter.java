package cc.abing.abstart.model.asset.common;

import cc.abing.abstart.model.asset.AssetDO;
import cc.abing.abstart.model.asset.common.AssetRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * <p>
 * 资产记录表 Converter 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2025-08-11
 */
@Mapper
public interface AssetConverter {

	AssetConverter M = Mappers.getMapper(AssetConverter.class);

	/**
	 * AssetRequest -> AssetDO 转换器
	 * @param request
	 * @return
	 */
	AssetDO convert(AssetRequest request);
}

