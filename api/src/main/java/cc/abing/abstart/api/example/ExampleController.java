package cc.abing.abstart.api.example;

import cc.abing.abstart.biz.example.ExampleService;
import cc.abing.abstart.model.example.ExampleDO;
import cc.abing.abstart.support.system.constant.SystemConstant;
import cc.abing.abstart.support.system.error.ABException;
import cc.abing.abstart.support.system.error.ABParamException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(SystemConstant.BASE_PATH + "/example")
public class ExampleController {

    private final ExampleService exampleService;

    @Autowired
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }


    @GetMapping(value = "")
    public ResponseEntity<Page<ExampleDO>> getExampleDOPage(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "start_create_time") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startCreateTime,
            @RequestParam(value = "end_create_time") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endCreateTime,
            @RequestParam(value = "page_index", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "page_size", required = false, defaultValue = "20") Integer pageSize) {
        if(pageSize > 20){
            throw new ABParamException("pageSize不能超过最大值20");
        }
        return ResponseEntity.ok(exampleService.getExampleDOPage(id, startCreateTime,endCreateTime, pageIndex, pageSize));
    }

}
