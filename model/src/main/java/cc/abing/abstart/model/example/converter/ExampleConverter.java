package cc.abing.abstart.model.example.converter;

import cc.abing.abstart.model.example.ExampleDO;
import cc.abing.abstart.model.example.request.ExampleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ABing
 * @since 2022/8/4
 */
@Mapper
public interface ExampleConverter {

	ExampleConverter M = Mappers.getMapper(ExampleConverter.class);

	/**
	 * ExampleRequest -> ExampleDO 转换器
	 * @param request
	 * @return
	 */
	ExampleDO convert(ExampleRequest request);

}
