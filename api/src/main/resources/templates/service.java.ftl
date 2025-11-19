package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Entity}.common.${entity?replace("DO", "Request")};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

/**
	 * 获取${entity}列表
	 * @return
	 */
	List<${entity}> list${entity}(<#list table.fields as field>${field.propertyType} ${field.propertyName}, <#if field.propertyType == "Date">${field.propertyType} ${field.propertyName}Left, ${field.propertyType} ${field.propertyName}Right, </#if></#list>Integer pageIndex, Integer pageSize);

	/**
	 * 获取${entity}分页
	 */
	Page<${entity}> page${entity}(<#list table.fields as field>${field.propertyType} ${field.propertyName}, <#if field.propertyType == "Date">${field.propertyType} ${field.propertyName}Left, ${field.propertyType} ${field.propertyName}Right, </#if></#list>Integer pageIndex, Integer pageSize);

	/**
	 * 获取${entity}分页
	 */
	Page<${entity}> page${entity}(${entity?replace("DO", "Request")} request);

	/**
	 * 创建${entity}
	 */
	Integer create${entity}(${entity?replace("DO", "Request")} request);

	/**
	 * 修改${entity}
	 */
	Integer patch${entity}(${entity?replace("DO", "Request")} request);

	/**
	 * 更新${entity}
	 */
	Integer update${entity}(${entity?replace("DO", "Request")} request);

	/**
	 * 删除${entity}
	 */
	Integer delete${entity}(${entity?replace("DO", "Request")} request);

}
</#if>
