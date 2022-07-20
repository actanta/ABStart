package cc.abing.abstart.biz.example.impl;

import cc.abing.abstart.biz.example.ExampleService;
import cc.abing.abstart.dao.example.ExampleMapper;
import cc.abing.abstart.model.example.ExampleDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExampleServiceImpl implements ExampleService {

    private final ExampleMapper exampleMapper;

    @Autowired
    public ExampleServiceImpl(ExampleMapper exampleMapper) {
        this.exampleMapper = exampleMapper;
    }

    @Override
    public Page<ExampleDO> getExampleDOPage(Long id, Date createTime, Integer pageIndex, Integer pageSize) {
        return exampleMapper.selectPage(PageDTO.of(pageIndex,pageSize),Wrappers.<ExampleDO>lambdaQuery().eq(id != null,ExampleDO::getId,id).eq(createTime != null,ExampleDO::getCreateTime,createTime));
    }
}
