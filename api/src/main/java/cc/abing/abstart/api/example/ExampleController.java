package cc.abing.abstart.api.example;

import cc.abing.abstart.biz.example.ExampleService;
import cc.abing.abstart.model.example.ExampleDO;
import cc.abing.abstart.model.example.request.ExampleRequest;
import cc.abing.abstart.support.system.constant.SystemConstant;
import cc.abing.abstart.support.system.exception.ABParamException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(SystemConstant.BASE_PATH + "/example")
public class ExampleController {

    private final ExampleService exampleService;

    @Autowired
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }


    @GetMapping(value = "/list")
    public List<ExampleDO> listExampleDO(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "start_create_time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startCreateTime,
            @RequestParam(value = "end_create_time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endCreateTime,
            @RequestParam(value = "page_index", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "page_size", required = false, defaultValue = "20") Integer pageSize) {
        if (pageSize > SystemConstant.PAGE_SIZE) {
            throw new ABParamException("page_size超过最大值");
        }
        return exampleService.listExampleDO(id, startCreateTime, endCreateTime, pageIndex, pageSize);
    }

    @GetMapping(value = "/page")
    public Page<ExampleDO> pageExampleDO(ExampleRequest request) {
        return exampleService.pageExampleDO(request);
    }

    @PostMapping(value = "/")
    public Integer createExampleDO(@RequestBody ExampleRequest request) {
        return exampleService.createExampleDO(request);
    }

    @PatchMapping(value = "/")
    public Integer modifyExampleDO(@RequestBody ExampleRequest request) {
        return exampleService.modifyExampleDO(request);
    }

    @PutMapping(value = "/")
    public Integer updateExampleDO(@RequestBody ExampleRequest request) {
        return exampleService.updateExampleDO(request);
    }

    @DeleteMapping(value = "/")
    public Integer deleteExampleDO(@RequestBody ExampleRequest request) {
        return exampleService.deleteExampleDO(request);
    }

}
