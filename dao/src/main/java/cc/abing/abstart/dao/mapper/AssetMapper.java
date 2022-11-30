package cc.abing.abstart.dao.mapper;

import cc.abing.abstart.model.asset.AssetDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 采购记录表 资产分类表(树状primary_category secondary_category1衣物 2数码 3食物 4学习) Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2022-12-10
 */
@Mapper
public interface AssetMapper extends BaseMapper<AssetDO> {

}
