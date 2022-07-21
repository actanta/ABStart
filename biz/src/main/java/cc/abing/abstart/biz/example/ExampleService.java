package cc.abing.abstart.biz.example;

import cc.abing.abstart.model.example.ExampleDO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Date;

public interface ExampleService {
    Page<ExampleDO> getExampleDOPage(Long id, Date startCreateTime, Date endCreateTime, Integer pageIndex, Integer pageSize);
}
