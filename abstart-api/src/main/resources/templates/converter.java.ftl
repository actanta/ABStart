package ${package.Parent}.biz.request.${TABLE_NAME_UPPER_CAMEL};

import ${package.Entity}.${entity};
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * <p>
 * ${table.comment!} Converter 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Mapper
public interface ${entity?replace("DO", "Converter")} {

	${entity?replace("DO", "Converter")} M = Mappers.getMapper(${entity?replace("DO", "Converter")}.class);

	/**
	 * ${entity?replace("DO", "Request")} -> ${entity} 转换器
	 * @param request
	 * @return
	 */
	${entity} convert(${entity?replace("DO", "Request")} request);
}

