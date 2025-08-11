package cc.abing.abstart.biz.service;

import cc.abing.abstart.model.asset.AssetDO;
import cc.abing.abstart.model.asset.common.AssetRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资产记录表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2025-08-11
 */
public interface AssetService extends IService<AssetDO> {

/**
	 * 获取AssetDO列表
	 * @return
	 */
	List<AssetDO> listAssetDO(Long id, String name, Long ownerId, Integer categoryId, Long amount, Long balance, Long purchasePrice, Date purchaseTime, Date purchaseTimeLeft, Date purchaseTimeRight, Date expirationTime, Date expirationTimeLeft, Date expirationTimeRight, Integer purchaseChannel, String purchaseChannelDetail, String imagePath, Integer status, String remark, Date createTime, Date createTimeLeft, Date createTimeRight, Date updateTime, Date updateTimeLeft, Date updateTimeRight, Integer pageIndex, Integer pageSize);

	/**
	 * 获取AssetDO分页
	 */
	Page<AssetDO> pageAssetDO(Long id, String name, Long ownerId, Integer categoryId, Long amount, Long balance, Long purchasePrice, Date purchaseTime, Date purchaseTimeLeft, Date purchaseTimeRight, Date expirationTime, Date expirationTimeLeft, Date expirationTimeRight, Integer purchaseChannel, String purchaseChannelDetail, String imagePath, Integer status, String remark, Date createTime, Date createTimeLeft, Date createTimeRight, Date updateTime, Date updateTimeLeft, Date updateTimeRight, Integer pageIndex, Integer pageSize);

	/**
	 * 获取AssetDO分页
	 */
	Page<AssetDO> pageAssetDO(AssetRequest request);

	/**
	 * 创建AssetDO
	 */
	Integer createAssetDO(AssetRequest request);

	/**
	 * 修改AssetDO
	 */
	Integer modifyAssetDO(AssetRequest request);

	/**
	 * 更新AssetDO
	 */
	Integer updateAssetDO(AssetRequest request);

	/**
	 * 删除AssetDO
	 */
	Integer deleteAssetDO(AssetRequest request);

}
