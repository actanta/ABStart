package ${package.Entity}.common;

import ${package.Entity}.${entity};
import javax.validation.constraints.Max;
import java.util.Date;
/**
 * <p>
 * ${table.comment!} Request模型
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public class ${entity?replace("DO", "Request")} extends ${entity} {
<#if entitySerialVersionUID>

    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger>
    @ApiModelProperty("${field.comment}")
        <#else>
    /**
     * ${field.comment}
     */
        </#if>
    </#if>
    <#if field.propertyType != "Date">
    private ${field.propertyType} ${field.propertyName};
    <#else>
    private ${field.propertyType} ${field.propertyName};

        <#if field.comment!?length gt 0>
            <#if swagger>
    @ApiModelProperty("${field.comment}左边界")
        <#else>
    /**
     * ${field.comment}左边界
     */
        </#if>
    </#if>
    private ${field.propertyType} ${field.propertyName}Left;

    <#if field.comment!?length gt 0>
        <#if swagger>
    @ApiModelProperty("${field.comment}右边界")
        <#else>
    /**
     * ${field.comment}右边界
     */
        </#if>
    </#if>
    private ${field.propertyType} ${field.propertyName}Right;
    </#if>
</#list>


	/**
	 * 分页页码
	 */
	protected Integer pageIndex = 1;

	/**
	 * 分页大小
	 */
	@Max(value = 50, message = "page_size超过最大值")
	protected Integer pageSize = 20;

	<#------------  END 字段循环遍历  ---------->

    <#if !entityLombokModel>
        <#list table.fields as field>
            <#if field.propertyType == "boolean">
                <#assign getprefix="is"/>
            <#else>
                <#assign getprefix="get"/>
            </#if>
<#------------  Date类型(if)：添加Left和Right字段的getter、setter方法-普通类型字段处理  ---------->
<#if field.propertyType != "Date">

    public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }

    <#if chainModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    <#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if chainModel>
        return this;
        </#if>
    }
<#else><#------------  Date类型(else)：添加Left和Right字段的getter、setter方法-Date类型字段处理  ---------->

    public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }

    <#if chainModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    <#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if chainModel>
        return this;
        </#if>
    }

    public ${field.propertyType} ${getprefix}${field.capitalName}Left() {
        return ${field.propertyName}Left;
    }

    <#if chainModel>
    public ${entity} set${field.capitalName}Left(${field.propertyType} ${field.propertyName}Left) {
    <#else>
    public void set${field.capitalName}Left(${field.propertyType} ${field.propertyName}Left) {
    </#if>
        this.${field.propertyName}Left = ${field.propertyName}Left;
        <#if chainModel>
        return this;
        </#if>
    }

    public ${field.propertyType} ${getprefix}${field.capitalName}Right() {
        return ${field.propertyName}Right;
    }

    <#if chainModel>
    public ${entity} set${field.capitalName}Right(${field.propertyType} ${field.propertyName}Right) {
    <#else>
    public void set${field.capitalName}Right(${field.propertyType} ${field.propertyName}Right) {
    </#if>
        this.${field.propertyName}Right = ${field.propertyName}Right;
        <#if chainModel>
        return this;
        </#if>
    }
</#if><#------------ Date类型(endif)： Date类型添加Left和Right字段的getter、setter方法-结束  ---------->

        </#list>


    </#if>

<#------------  END 字段循环遍历  ---------->
<#if !entityLombokModel>
    @Override
    public String toString() {
        return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
        "}";
    }
</#if>

	public Integer getPageIndex() {
		return pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageIndex(Integer pageIndex) {
		if (null == pageIndex || pageIndex <= 0) {
			pageIndex = 1;
		}
		this.pageIndex = pageIndex;
	}

	public void setPageSize(Integer pageSize) {
		if (null == pageSize || pageSize <= 0) {
			pageSize = 20;
		}
		this.pageSize = pageSize;
	}
}