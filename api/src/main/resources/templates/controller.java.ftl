package ${package.Controller};

<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${commonPackage}.support.system.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import constant.cc.abing.abstart.suite.system.SystemConstant;
import ${package.Entity}.common.${entity?replace("DO", "Request")};

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.Date;
import java.util.List;

<#function camelToDashed(s)>
  <#return s
      <#-- "fooBar" to "foo_bar": -->
      ?replace('([a-z])([A-Z])', '$1_$2', 'r')
      <#-- "FOOBar" to "FOO_Bar": -->
      ?replace('([A-Z])([A-Z][a-z])', '$1_$2', 'r')
      <#-- All of those to "FOO_BAR": -->
      ?lower_case
  >
</#function>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping(SystemConstant.BASE_PATH + "<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

	private final ${table.serviceName} ${table.serviceName?uncap_first};

	@Autowired
	public ${table.controllerName}(${table.serviceName} ${table.serviceName?uncap_first}) {
		this.${table.serviceName?uncap_first} = ${table.serviceName?uncap_first};
	}
	
	@GetMapping(value = "/")
    	public List<${entity}> list${entity}(
    	<#list table.fields as field>
                @RequestParam(value = "${field.propertyName}", required = false) ${field.propertyType} ${field.propertyName},
    	    <#if field.propertyType == "Date">
                @RequestParam(value = "${field.propertyName}Left", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") ${field.propertyType} ${field.propertyName}Left,
                @RequestParam(value = "${field.propertyName}Right", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") ${field.propertyType} ${field.propertyName}Right,
            </#if>
    	</#list>
    			@RequestParam(value = "page_index", required = false, defaultValue = "1") Integer pageIndex,
    			@Valid @Max(value = 50, message = "page_size超过最大值") @RequestParam(value = "page_size", required = false,defaultValue = "20") Integer pageSize) {
    		return ${table.serviceName?uncap_first}.list${entity}(<#list table.fields as field>${field.propertyName}, <#if field.propertyType == "Date">${field.propertyName}Left, ${field.propertyName}Right, </#if></#list> pageIndex, pageSize);
    	}
    
    	@GetMapping(value = "/page")
    	public Page<${entity}> page${entity}(@Validated(Query.class) ${entity?replace("DO", "Request")} request) {
    		return ${table.serviceName?uncap_first}.page${entity}(request);
    	}
    
    	@PostMapping(value = "/")
    	public Integer create${entity}(@Validated(Create.class) @RequestBody ${entity?replace("DO", "Request")} request) {
    		return ${table.serviceName?uncap_first}.create${entity}(request);
    	}
    
    	@PatchMapping(value = "/")
    	public Integer patch${entity}(@Validated(Patch.class) @RequestBody ${entity?replace("DO", "Request")} request) {
    		return ${table.serviceName?uncap_first}.patch${entity}(request);
    	}
    
    	@PutMapping(value = "/")
    	public Integer update${entity}(@Validated(Put.class) @RequestBody ${entity?replace("DO", "Request")} request) {
    		return ${table.serviceName?uncap_first}.update${entity}(request);
    	}
    
    	@DeleteMapping(value = "/")
    	public Integer delete${entity}(@Validated(Delete.class) @RequestBody ${entity?replace("DO", "Request")} request) {
    		return ${table.serviceName?uncap_first}.delete${entity}(request);
    	}

}
</#if>
