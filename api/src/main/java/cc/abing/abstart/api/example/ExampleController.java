package cc.abing.abstart.api.example;

import cc.abing.abstart.biz.example.ExampleService;
import cc.abing.abstart.model.example.ExampleDO;
import cc.abing.abstart.model.example.request.ExampleRequest;
import cc.abing.abstart.support.system.constant.SystemConstant;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping(SystemConstant.BASE_PATH + "/example")
public class ExampleController {

	private final ExampleService exampleService;

	@Autowired
	public ExampleController(ExampleService exampleService) {
		this.exampleService = exampleService;
	}

	/**
	 * 查询ExampleDO列表
	 * @param id 序号
	 * @param startCreateTime 创建时间开始
	 * @param endCreateTime 创建时间结束
	 * @param pageIndex 分页页码
	 * @param pageSize 分页大小
	 * @return
	 */
	@GetMapping(value = "/list")
	public List<ExampleDO> listExampleDO(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "start_create_time",
					required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startCreateTime,
			@RequestParam(value = "end_create_time",
					required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endCreateTime,
			@RequestParam(value = "page_index", required = false, defaultValue = "1") Integer pageIndex,
			@Valid @Max(value = 50, message = "page_size超过最大值") @RequestParam(value = "page_size", required = false,
					defaultValue = "20") Integer pageSize) {
		return exampleService.listExampleDO(id, startCreateTime, endCreateTime, pageIndex, pageSize);
	}

	@GetMapping(value = "/page")
	public Page<ExampleDO> pageExampleDO(@Valid ExampleRequest request) {
		return exampleService.pageExampleDO(request);
	}

	@PostMapping(value = "/")
	public Integer createExampleDO(@Valid @RequestBody ExampleRequest request) {
		return exampleService.createExampleDO(request);
	}

	@PatchMapping(value = "/")
	public Integer modifyExampleDO(@Valid @RequestBody ExampleRequest request) {
		return exampleService.modifyExampleDO(request);
	}

	@PutMapping(value = "/")
	public Integer updateExampleDO(@Valid @RequestBody ExampleRequest request) {
		return exampleService.updateExampleDO(request);
	}

	@DeleteMapping(value = "/")
	public Integer deleteExampleDO(@Valid @RequestBody ExampleRequest request) {
		return exampleService.deleteExampleDO(request);
	}

}
