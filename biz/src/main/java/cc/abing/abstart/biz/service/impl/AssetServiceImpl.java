package cc.abing.abstart.biz.service.impl;

import cc.abing.abstart.model.asset.AssetDO;
import cc.abing.abstart.model.asset.common.AssetRequest;
import cc.abing.abstart.model.asset.common.AssetConverter;
import cc.abing.abstart.dao.mapper.AssetMapper;
import cc.abing.abstart.biz.service.AssetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资产记录表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2025-08-11
 */
@Service
public class AssetServiceImpl extends ServiceImpl<AssetMapper, AssetDO> implements AssetService {

    private final AssetMapper assetMapper;

	@Autowired
	public AssetServiceImpl(AssetMapper assetMapper) {
		this.assetMapper = assetMapper;
	}

	@Override
	public List<AssetDO> listAssetDO(Long id, String name, Long ownerId, Integer categoryId, Long amount, Long balance, Long purchasePrice, Date purchaseTime, Date purchaseTimeLeft, Date purchaseTimeRight, Date expirationTime, Date expirationTimeLeft, Date expirationTimeRight, Integer purchaseChannel, String purchaseChannelDetail, String imagePath, Integer status, String remark, Date createTime, Date createTimeLeft, Date createTimeRight, Date updateTime, Date updateTimeLeft, Date updateTimeRight, Integer pageIndex, Integer pageSize) {
		LambdaQueryWrapper<AssetDO> wrapper = Wrappers.<AssetDO>lambdaQuery()
                .eq(id != null, AssetDO::getId, id)
                .eq(name != null, AssetDO::getName, name)
                .eq(ownerId != null, AssetDO::getOwnerId, ownerId)
                .eq(categoryId != null, AssetDO::getCategoryId, categoryId)
                .eq(amount != null, AssetDO::getAmount, amount)
                .eq(balance != null, AssetDO::getBalance, balance)
                .eq(purchasePrice != null, AssetDO::getPurchasePrice, purchasePrice)
                .ge(purchaseTimeLeft != null, AssetDO::getPurchaseTime, purchaseTimeLeft)
                .le(purchaseTimeRight != null, AssetDO::getPurchaseTime, purchaseTimeRight)
                .ge(expirationTimeLeft != null, AssetDO::getExpirationTime, expirationTimeLeft)
                .le(expirationTimeRight != null, AssetDO::getExpirationTime, expirationTimeRight)
                .eq(purchaseChannel != null, AssetDO::getPurchaseChannel, purchaseChannel)
                .eq(purchaseChannelDetail != null, AssetDO::getPurchaseChannelDetail, purchaseChannelDetail)
                .eq(imagePath != null, AssetDO::getImagePath, imagePath)
                .eq(status != null, AssetDO::getStatus, status)
                .eq(remark != null, AssetDO::getRemark, remark)
                .ge(createTimeLeft != null, AssetDO::getCreateTime, createTimeLeft)
                .le(createTimeRight != null, AssetDO::getCreateTime, createTimeRight)
                .ge(updateTimeLeft != null, AssetDO::getUpdateTime, updateTimeLeft)
                .le(updateTimeRight != null, AssetDO::getUpdateTime, updateTimeRight)
                .orderByDesc(AssetDO::getId);
                
		return assetMapper.selectList(wrapper);
	}

	@Override
	public Page<AssetDO> pageAssetDO(Long id, String name, Long ownerId, Integer categoryId, Long amount, Long balance, Long purchasePrice, Date purchaseTime, Date purchaseTimeLeft, Date purchaseTimeRight, Date expirationTime, Date expirationTimeLeft, Date expirationTimeRight, Integer purchaseChannel, String purchaseChannelDetail, String imagePath, Integer status, String remark, Date createTime, Date createTimeLeft, Date createTimeRight, Date updateTime, Date updateTimeLeft, Date updateTimeRight, Integer pageIndex, Integer pageSize) {
            LambdaQueryWrapper<AssetDO> wrapper = Wrappers.<AssetDO>lambdaQuery()
                .eq(id != null, AssetDO::getId, id)
                .eq(name != null, AssetDO::getName, name)
                .eq(ownerId != null, AssetDO::getOwnerId, ownerId)
                .eq(categoryId != null, AssetDO::getCategoryId, categoryId)
                .eq(amount != null, AssetDO::getAmount, amount)
                .eq(balance != null, AssetDO::getBalance, balance)
                .eq(purchasePrice != null, AssetDO::getPurchasePrice, purchasePrice)
                .ge(purchaseTimeLeft != null, AssetDO::getPurchaseTime, purchaseTimeLeft)
                .le(purchaseTimeRight != null, AssetDO::getPurchaseTime, purchaseTimeRight)
                .ge(expirationTimeLeft != null, AssetDO::getExpirationTime, expirationTimeLeft)
                .le(expirationTimeRight != null, AssetDO::getExpirationTime, expirationTimeRight)
                .eq(purchaseChannel != null, AssetDO::getPurchaseChannel, purchaseChannel)
                .eq(purchaseChannelDetail != null, AssetDO::getPurchaseChannelDetail, purchaseChannelDetail)
                .eq(imagePath != null, AssetDO::getImagePath, imagePath)
                .eq(status != null, AssetDO::getStatus, status)
                .eq(remark != null, AssetDO::getRemark, remark)
                .ge(createTimeLeft != null, AssetDO::getCreateTime, createTimeLeft)
                .le(createTimeRight != null, AssetDO::getCreateTime, createTimeRight)
                .ge(updateTimeLeft != null, AssetDO::getUpdateTime, updateTimeLeft)
                .le(updateTimeRight != null, AssetDO::getUpdateTime, updateTimeRight)
                .orderByDesc(AssetDO::getId);
                
        return assetMapper.selectPage(PageDTO.<AssetDO>of(pageIndex, pageSize), wrapper);
	}

	@Override
	public Page<AssetDO> pageAssetDO(AssetRequest request) {
	    LambdaQueryWrapper<AssetDO> wrapper = Wrappers.<AssetDO>lambdaQuery()
                .eq(request.getId() != null, AssetDO::getId, request.getId())
                .eq(request.getName() != null, AssetDO::getName, request.getName())
                .eq(request.getOwnerId() != null, AssetDO::getOwnerId, request.getOwnerId())
                .eq(request.getCategoryId() != null, AssetDO::getCategoryId, request.getCategoryId())
                .eq(request.getAmount() != null, AssetDO::getAmount, request.getAmount())
                .eq(request.getBalance() != null, AssetDO::getBalance, request.getBalance())
                .eq(request.getPurchasePrice() != null, AssetDO::getPurchasePrice, request.getPurchasePrice())
                .ge(request.getPurchaseTimeLeft() != null, AssetDO::getPurchaseTime, request.getPurchaseTimeLeft())
                .le(request.getPurchaseTimeRight() != null, AssetDO::getPurchaseTime, request.getPurchaseTimeRight())
                .ge(request.getExpirationTimeLeft() != null, AssetDO::getExpirationTime, request.getExpirationTimeLeft())
                .le(request.getExpirationTimeRight() != null, AssetDO::getExpirationTime, request.getExpirationTimeRight())
                .eq(request.getPurchaseChannel() != null, AssetDO::getPurchaseChannel, request.getPurchaseChannel())
                .eq(request.getPurchaseChannelDetail() != null, AssetDO::getPurchaseChannelDetail, request.getPurchaseChannelDetail())
                .eq(request.getImagePath() != null, AssetDO::getImagePath, request.getImagePath())
                .eq(request.getStatus() != null, AssetDO::getStatus, request.getStatus())
                .eq(request.getRemark() != null, AssetDO::getRemark, request.getRemark())
                .ge(request.getCreateTimeLeft() != null, AssetDO::getCreateTime, request.getCreateTimeLeft())
                .le(request.getCreateTimeRight() != null, AssetDO::getCreateTime, request.getCreateTimeRight())
                .ge(request.getUpdateTimeLeft() != null, AssetDO::getUpdateTime, request.getUpdateTimeLeft())
                .le(request.getUpdateTimeRight() != null, AssetDO::getUpdateTime, request.getUpdateTimeRight())
                .orderByDesc(AssetDO::getId);
                
		return assetMapper.selectPage(PageDTO.<AssetDO>of(request.getPageIndex(), request.getPageSize()),wrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer createAssetDO(AssetRequest request) {
		AssetDO assetDO = AssetConverter.M.convert(request);
		return assetMapper.insert(assetDO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer modifyAssetDO(AssetRequest request) {
		AssetDO assetDO = AssetConverter.M.convert(request);
		return assetMapper.update(assetDO,
				Wrappers.<AssetDO>lambdaUpdate().eq(AssetDO::getId, assetDO.getId()).last("limit 1"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updateAssetDO(AssetRequest request) {
		AssetDO assetDO = AssetConverter.M.convert(request);
		return assetMapper.update( null,
				Wrappers.<AssetDO>lambdaUpdate().eq(AssetDO::getId, assetDO.getId())
				    .set(AssetDO::getId,assetDO.getId())
				    .set(AssetDO::getName,assetDO.getName())
				    .set(AssetDO::getOwnerId,assetDO.getOwnerId())
				    .set(AssetDO::getCategoryId,assetDO.getCategoryId())
				    .set(AssetDO::getAmount,assetDO.getAmount())
				    .set(AssetDO::getBalance,assetDO.getBalance())
				    .set(AssetDO::getPurchasePrice,assetDO.getPurchasePrice())
				    .set(AssetDO::getPurchaseTime,assetDO.getPurchaseTime())
				    .set(AssetDO::getExpirationTime,assetDO.getExpirationTime())
				    .set(AssetDO::getPurchaseChannel,assetDO.getPurchaseChannel())
				    .set(AssetDO::getPurchaseChannelDetail,assetDO.getPurchaseChannelDetail())
				    .set(AssetDO::getImagePath,assetDO.getImagePath())
				    .set(AssetDO::getStatus,assetDO.getStatus())
				    .set(AssetDO::getRemark,assetDO.getRemark())
				    .set(AssetDO::getCreateTime,assetDO.getCreateTime())
				    .set(AssetDO::getUpdateTime,assetDO.getUpdateTime())
				.last("limit 1"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer deleteAssetDO(AssetRequest request) {
		AssetDO assetDO = AssetConverter.M.convert(request);
		return assetMapper.delete(Wrappers.<AssetDO>lambdaUpdate().eq(AssetDO::getId, assetDO.getId()).last("limit 1"));
	}

}
