package cc.abing.abstart.model.asset.common;

import cc.abing.abstart.model.asset.AssetDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * <p>
 * 采购记录表 资产分类表(树状primary_category secondary_category1衣物 2数码 3食物 4学习) Converter 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2022-12-10
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

