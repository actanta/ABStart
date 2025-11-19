package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Entity}.common.${entity?replace("DO", "Request")};
import ${package.Entity}.common.${entity?replace("DO", "Converter")};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
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
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    private final ${table.mapperName} ${table.mapperName?uncap_first};

	@Autowired
	public ${table.serviceImplName}(${table.mapperName} ${table.mapperName?uncap_first}) {
		this.${table.mapperName?uncap_first} = ${table.mapperName?uncap_first};
	}

	@Override
	public List<${entity}> list${entity}(<#list table.fields as field>${field.propertyType} ${field.propertyName}, <#if field.propertyType == "Date">${field.propertyType} ${field.propertyName}Left, ${field.propertyType} ${field.propertyName}Right, </#if></#list>Integer pageIndex, Integer pageSize) {
		LambdaQueryWrapper<${entity}> wrapper = Wrappers.<${entity}>lambdaQuery()
                <#list table.fields as field>
                    <#if field.keyFlag>
                        <#assign keyPropertyCapitalName="${field.capitalName}"/>
                        <#if field.propertyType == "boolean">
                            <#assign getKeyprefix="is"/>
                        <#else>
                            <#assign getKeyprefix="get"/>
                        </#if>
                    </#if>
                    <#if field.propertyType == "boolean">
                        <#assign getprefix="is"/>
                    <#else>
                        <#assign getprefix="get"/>
                    </#if>
                    <#if field.propertyType == "Date">
                .ge(${field.propertyName}Left != null, ${entity}::${getprefix}${field.capitalName}, ${field.propertyName}Left)
                .le(${field.propertyName}Right != null, ${entity}::${getprefix}${field.capitalName}, ${field.propertyName}Right)
                    <#else>
                .eq(${field.propertyName} != null, ${entity}::${getprefix}${field.capitalName}, ${field.propertyName})
                    </#if>
                </#list>
                <#if keyPropertyCapitalName??>
                .orderByDesc(${entity}::${getKeyprefix}${keyPropertyCapitalName});
                <#else>;
                </#if>

		return ${table.mapperName?uncap_first?uncap_first}.selectList(wrapper);
	}

	@Override
	public Page<${entity}> page${entity}(<#list table.fields as field>${field.propertyType} ${field.propertyName}, <#if field.propertyType == "Date">${field.propertyType} ${field.propertyName}Left, ${field.propertyType} ${field.propertyName}Right, </#if></#list>Integer pageIndex, Integer pageSize) {
            LambdaQueryWrapper<${entity}> wrapper = Wrappers.<${entity}>lambdaQuery()
                <#list table.fields as field>
                    <#if field.keyFlag>
                        <#assign keyPropertyCapitalName="${field.capitalName}"/>
                        <#if field.propertyType == "boolean">
                            <#assign getKeyprefix="is"/>
                        <#else>
                            <#assign getKeyprefix="get"/>
                        </#if>
                    </#if>
                    <#if field.propertyType == "boolean">
                        <#assign getprefix="is"/>
                    <#else>
                        <#assign getprefix="get"/>
                    </#if>
                    <#if field.propertyType == "Date">
                .ge(${field.propertyName}Left != null, ${entity}::${getprefix}${field.capitalName}, ${field.propertyName}Left)
                .le(${field.propertyName}Right != null, ${entity}::${getprefix}${field.capitalName}, ${field.propertyName}Right)
                    <#else>
                .eq(${field.propertyName} != null, ${entity}::${getprefix}${field.capitalName}, ${field.propertyName})
                    </#if>
                </#list>
                <#if keyPropertyCapitalName??>
                .orderByDesc(${entity}::${getKeyprefix}${keyPropertyCapitalName});
                <#else>;
                </#if>

        return ${table.mapperName?uncap_first?uncap_first}.selectPage(PageDTO.<${entity}>of(pageIndex, pageSize), wrapper);
	}

	@Override
	public Page<${entity}> page${entity}(${entity?replace("DO", "Request")} request) {
	    LambdaQueryWrapper<${entity}> wrapper = Wrappers.<${entity}>lambdaQuery()
                <#list table.fields as field>
                    <#if field.keyFlag>
                        <#assign keyPropertyCapitalName="${field.capitalName}"/>
                        <#if field.propertyType == "boolean">
                            <#assign getKeyprefix="is"/>
                        <#else>
                            <#assign getKeyprefix="get"/>
                        </#if>
                    </#if>
                    <#if field.propertyType == "boolean">
                        <#assign getprefix="is"/>
                    <#else>
                        <#assign getprefix="get"/>
                    </#if>
                    <#if field.propertyType == "Date">
                .ge(request.${getprefix}${field.capitalName}Left() != null, ${entity}::${getprefix}${field.capitalName}, request.${getprefix}${field.capitalName}Left())
                .le(request.${getprefix}${field.capitalName}Right() != null, ${entity}::${getprefix}${field.capitalName}, request.${getprefix}${field.capitalName}Right())
                    <#else>
                .eq(request.${getprefix}${field.capitalName}() != null, ${entity}::${getprefix}${field.capitalName}, request.${getprefix}${field.capitalName}())
                    </#if>
                </#list>
                <#if keyPropertyCapitalName??>
                .orderByDesc(${entity}::${getKeyprefix}${keyPropertyCapitalName});
                <#else>;
                </#if>

		return ${table.mapperName?uncap_first?uncap_first}.selectPage(PageDTO.<${entity}>of(request.getPageIndex(), request.getPageSize()),wrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer create${entity}(${entity?replace("DO", "Request")} request) {
		${entity} ${entity?uncap_first} = ${entity?replace("DO", "Converter")}.M.convert(request);
		return ${table.mapperName?uncap_first?uncap_first}.insert(${entity?uncap_first});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer patch${entity}(${entity?replace("DO", "Request")} request) {
		${entity} ${entity?uncap_first} = ${entity?replace("DO", "Converter")}.M.convert(request);
		return ${table.mapperName?uncap_first?uncap_first}.update(${entity?uncap_first},
				Wrappers.<${entity}>lambdaUpdate().eq(${entity}::getId, ${entity?uncap_first}.getId()).last("limit 1"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer update${entity}(${entity?replace("DO", "Request")} request) {
		${entity} ${entity?uncap_first} = ${entity?replace("DO", "Converter")}.M.convert(request);
		return ${table.mapperName?uncap_first?uncap_first}.update( null,
				Wrappers.<${entity}>lambdaUpdate().eq(${entity}::getId, ${entity?uncap_first}.getId())
				<#list table.fields as field>
                    <#if field.propertyType == "boolean">
                        <#assign getprefix="is"/>
                    <#else>
                        <#assign getprefix="get"/>
                    </#if>
				    .set(${entity}::${getprefix}${field.capitalName},${entity?uncap_first}.${getprefix}${field.capitalName}())
				</#list>
				.last("limit 1"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer delete${entity}(${entity?replace("DO", "Request")} request) {
		${entity} ${entity?uncap_first} = ${entity?replace("DO", "Converter")}.M.convert(request);
		return ${table.mapperName?uncap_first?uncap_first}.delete(Wrappers.<${entity}>lambdaUpdate().eq(${entity}::getId, ${entity?uncap_first}.getId()).last("limit 1"));
	}

}
</#if>
