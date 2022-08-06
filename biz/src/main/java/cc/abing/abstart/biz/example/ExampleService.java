package cc.abing.abstart.biz.example;

import cc.abing.abstart.model.example.ExampleDO;
import cc.abing.abstart.model.example.request.ExampleRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Date;
import java.util.List;

public interface ExampleService {

    /**
     * 获取ExampleDO列表
     * @return
     */
    List<ExampleDO> listExampleDO(Long id, Date startCreateTime, Date endCreateTime, Integer pageIndex, Integer pageSize);

    /**
     * 获取ExampleDO分页
     */
    Page<ExampleDO> pageExampleDO(Long id, Date startCreateTime, Date endCreateTime, Integer pageIndex, Integer pageSize);

    /**
     * 获取ExampleDO分页
     */
    Page<ExampleDO> pageExampleDO(ExampleRequest request);

    /**
     * 创建ExampleDO
     */
    Integer createExampleDO(ExampleRequest request);

    /**
     * 修改ExampleDO
     */
    Integer modifyExampleDO(ExampleRequest request);

    /**
     * 更新ExampleDO
     */
    Integer updateExampleDO(ExampleRequest request);

    /**
     * 删除ExampleDO
     */
    Integer deleteExampleDO(ExampleRequest request);
}
