package cc.abing.abstart.biz.example.impl;

import cc.abing.abstart.biz.example.ExampleService;
import cc.abing.abstart.dao.example.ExampleMapper;
import cc.abing.abstart.model.example.ExampleDO;
import cc.abing.abstart.model.example.converter.ExampleConverter;
import cc.abing.abstart.model.example.request.ExampleRequest;
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
 * @author ABing
 * @since 2022-02-01
 */
@Service
public class ExampleServiceImpl implements ExampleService {

	private final ExampleMapper exampleMapper;

	@Autowired
	public ExampleServiceImpl(ExampleMapper exampleMapper) {
		this.exampleMapper = exampleMapper;
	}

	@Override
	public List<ExampleDO> listExampleDO(Long id, Date startCreateTime, Date endCreateTime, Integer pageIndex,
			Integer pageSize) {
		LambdaQueryWrapper<ExampleDO> listWrapper = Wrappers.<ExampleDO>lambdaQuery()
				.eq(id != null, ExampleDO::getId, id)
				.ge(startCreateTime != null, ExampleDO::getCreateTime, startCreateTime)
				.le(endCreateTime != null, ExampleDO::getCreateTime, endCreateTime)
				.orderByDesc(ExampleDO::getCreateTime);
		return exampleMapper.selectList(listWrapper);
	}

	@Override
	public Page<ExampleDO> pageExampleDO(Long id, Date startCreateTime, Date endCreateTime, Integer pageIndex,
			Integer pageSize) {
		return exampleMapper.selectPage(PageDTO.<ExampleDO>of(pageIndex, pageSize),
				Wrappers.<ExampleDO>lambdaQuery().eq(id != null, ExampleDO::getId, id)
						.ge(startCreateTime != null, ExampleDO::getCreateTime, startCreateTime)
						.le(endCreateTime != null, ExampleDO::getCreateTime, endCreateTime)
						.orderByDesc(ExampleDO::getCreateTime));
	}

	@Override
	public Page<ExampleDO> pageExampleDO(ExampleRequest request) {
		return exampleMapper.selectPage(PageDTO.<ExampleDO>of(request.getPageIndex(), request.getPageSize()),
				Wrappers.<ExampleDO>lambdaQuery().eq(request.getId() != null, ExampleDO::getId, request.getId())
						.ge(request.getStartCreateTime() != null, ExampleDO::getCreateTime,
								request.getStartCreateTime())
						.le(request.getEndCreateTime() != null, ExampleDO::getCreateTime, request.getEndCreateTime())
						.orderByDesc(ExampleDO::getCreateTime));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer createExampleDO(ExampleRequest request) {
		ExampleDO exampleDO = ExampleConverter.M.convert(request);
		return exampleMapper.insert(exampleDO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer modifyExampleDO(ExampleRequest request) {
		ExampleDO exampleDO = ExampleConverter.M.convert(request);
		return exampleMapper.update(exampleDO,
				Wrappers.<ExampleDO>lambdaUpdate().eq(ExampleDO::getId, exampleDO.getId()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updateExampleDO(ExampleRequest request) {
		ExampleDO exampleDO = ExampleConverter.M.convert(request);
		return exampleMapper.update(exampleDO,
				Wrappers.<ExampleDO>lambdaUpdate().eq(ExampleDO::getId, exampleDO.getId()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer deleteExampleDO(ExampleRequest request) {
		ExampleDO exampleDO = ExampleConverter.M.convert(request);
		return exampleMapper.delete(Wrappers.<ExampleDO>lambdaUpdate().eq(ExampleDO::getId, exampleDO.getId()));
	}

}
