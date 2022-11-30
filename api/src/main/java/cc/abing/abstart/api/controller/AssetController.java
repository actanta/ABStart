package cc.abing.abstart.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cc.abing.abstart.biz.service.AssetService;
import cc.abing.abstart.model.asset.AssetDO;
import cc.abing.abstart.support.system.constant.SystemConstant;
import cc.abing.abstart.model.asset.common.AssetRequest;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 采购记录表 资产分类表(树状primary_category secondary_category1衣物 2数码 3食物 4学习) 前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2022-12-10
 */
@RestController
@RequestMapping(SystemConstant.BASE_PATH + "/abstart/assetDO")
public class AssetController {

	private final AssetService assetService;

	@Autowired
	public AssetController(AssetService assetService) {
		this.assetService = assetService;
	}

	@GetMapping(value = "/list")
	public List<AssetDO> listAssetDO(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "owner_id", required = false) Long ownerId,
			@RequestParam(value = "category", required = false) Integer category,
			@RequestParam(value = "specific_category", required = false) Integer specificCategory,
			@RequestParam(value = "tag", required = false) String tag,
			@RequestParam(value = "amount", required = false) Long amount,
			@RequestParam(value = "balance", required = false) Long balance,
			@RequestParam(value = "purchase_price", required = false) Long purchasePrice,
			@RequestParam(value = "purchase_time", required = false) Date purchaseTime,
			@RequestParam(value = "purchase_time_left",
					required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date purchaseTimeLeft,
			@RequestParam(value = "purchase_time_right",
					required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date purchaseTimeRight,
			@RequestParam(value = "expiration_time", required = false) Date expirationTime,
			@RequestParam(value = "expiration_time_left",
					required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date expirationTimeLeft,
			@RequestParam(value = "expiration_time_right",
					required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date expirationTimeRight,
			@RequestParam(value = "purchase_channel", required = false) Integer purchaseChannel,
			@RequestParam(value = "purchase_channel_detail", required = false) String purchaseChannelDetail,
			@RequestParam(value = "image_path", required = false) String imagePath,
			@RequestParam(value = "remark", required = false) String remark,
			@RequestParam(value = "page_index", required = false, defaultValue = "1") Integer pageIndex,
			@Valid @Max(value = 50, message = "page_size超过最大值") @RequestParam(value = "page_size", required = false,
					defaultValue = "20") Integer pageSize) {
		return assetService.listAssetDO(id, name, ownerId, category, specificCategory, tag, amount, balance,
				purchasePrice, purchaseTime, purchaseTimeLeft, purchaseTimeRight, expirationTime, expirationTimeLeft,
				expirationTimeRight, purchaseChannel, purchaseChannelDetail, imagePath, remark, pageIndex, pageSize);
	}

	@GetMapping(value = "/page")
	public Page<AssetDO> pageAssetDO(@Valid AssetRequest request) {
		return assetService.pageAssetDO(request);
	}

	@PostMapping(value = "/")
	public Integer createAssetDO(@Valid @RequestBody AssetRequest request) {
		return assetService.createAssetDO(request);
	}

	@PatchMapping(value = "/")
	public Integer modifyAssetDO(@Valid @RequestBody AssetRequest request) {
		return assetService.modifyAssetDO(request);
	}

	@PutMapping(value = "/")
	public Integer updateAssetDO(@Valid @RequestBody AssetRequest request) {
		return assetService.updateAssetDO(request);
	}

	@DeleteMapping(value = "/")
	public Integer deleteAssetDO(@Valid @RequestBody AssetRequest request) {
		return assetService.deleteAssetDO(request);
	}

}
