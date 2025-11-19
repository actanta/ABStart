package cc.abing.abstart.api;

import com.sailing.dcs.common.vo.TreeTemplateCategoryVo;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeMarkerTemplateUtils {

    public static void main(String[] args) {
        // 1. 准备模板内容 (优化后的最终版本)
        String templateContent = "#### 分类标准\n" +
                "<#list firstLevelCategories as category>\n" +
                "${category_index + 1}. ${category.name}（来自模板表中的1级分类）\n" +
                "- 定义：${category.definition}（来自模板表中的1级分类说明）\n" +
                "- 板块(从以下列表中选1个或多个最接近的)：" +
                "<#if category.branch == \"A\">\n" +
                "    <#list category.children as secondLevel>\n" +
                "      ${secondLevel.name}<#if secondLevel_has_next>, </#if>\n" +
                "    </#list>\n" +
                "<#elseif category.branch == \"B\">" +
                "<#list category.children as secondLevel>" +
                "<#list secondLevel.children as thirdLevel>${thirdLevel.name}<#if thirdLevel_has_next>;</#if></#list>" +
                "<#if secondLevel_has_next>;</#if>" +
                "</#list>\n" +
                "</#if>\n" +
                "<#if category.name == \"客户信息\">\n" +
                "- 排除：内部员工、代理人、合作伙伴、内部机构等。\n" +
                "- 补充说明：客户信息需包含最新的实名认证数据。\n" +
                "</#if>\n" +
                "<#if category.name == \"业务信息\">\n" +
                "- 注意事项：业务信息需与交易记录保持一致性。\n" +
                "</#if>\n" +
                "</#list>";
        templateContent="#### 分类标准\n" +
                "<#list data as category>\n" +
                "${category_index + 1}. ${category.name}（来自模板表中的1级分类）\n" +
                "- 定义：${category.categoryDesc}（来自模板表中的1级分类说明）\n" +
                "- 板块(从以下列表中选1个或多个最接近的)：\n" +
                "  <#if category.categoryInferenceRule == \"A\">\n" +
                "    <#list category.children as secondLevel>\n" +
                "      ${secondLevel.name}<#if secondLevel_has_next>, </#if>\n" +
                "    </#list>\n" +
                "  <#elseif category.categoryInferenceRule == \"B\">\n" +
                "    <#list category.children as secondLevel>\n" +
                "      <#list secondLevel.children as thirdLevel>\n" +
                "        ${thirdLevel.name}<#if thirdLevel_has_next>;</#if>\n" +
                "      </#list>\n" +
                "      <#if secondLevel_has_next>;</#if>\n" +
                "    </#list>\n" +
                "  </#if>\n" +
                "<#if category.name == \"客户信息\">\n" +
                "- 排除：内部员工、代理人、合作伙伴、内部机构等。\n" +
                "- 补充说明：客户信息需包含最新的实名认证数据。\n" +
                "</#if>\n" +
                "<#if category.name == \"业务信息\">\n" +
                "- 注意事项：业务信息需与交易记录保持一致性。\n" +
                "</#if>\n" +
                "</#list>";
        System.out.println(templateContent);

//        // 2. 准备测试数据模型 (branch = "A")
//        Map<String, Object> dataModel = new HashMap<>();
//        List<Map<String, Object>> firstLevelCategories = new ArrayList<>();
//
////        Map<String, Object> customerCategory = new HashMap<>();
////        customerCategory.put("name", "客户信息");
////        customerCategory.put("definition", "外部客户（自然人/企业）的自然属性、认证信息、标签等。");
////        customerCategory.put("branch", "A"); // 关键：分支为 A
////
////        List<Map<String, Object>> secondLevelCategories = new ArrayList<>();
////        Map<String, Object> personalCustomer = new HashMap<>();
////        personalCustomer.put("name", "个人客户");
////        secondLevelCategories.add(personalCustomer);
////
////        Map<String, Object> enterpriseCustomer = new HashMap<>();
////        enterpriseCustomer.put("name", "企业客户");
////        secondLevelCategories.add(enterpriseCustomer);
////
////        customerCategory.put("children", secondLevelCategories);
////        firstLevelCategories.add(customerCategory);
////
////        dataModel.put("firstLevelCategories", firstLevelCategories);
//        Map<String, Object> businessCategory = new HashMap<>();
//        businessCategory.put("name", "业务信息");
//        businessCategory.put("definition", "银行在直接面向客户、开展金融服务过程中产生和使用的信息集合。");
//        businessCategory.put("branch", "B"); // 关键：分支为 B
//
//        List<Map<String, Object>> secondLevelCategories = new ArrayList<>();
//
//        Map<String, Object> accountManagement = new HashMap<>();
//        accountManagement.put("name", "账户管理");
//        List<Map<String, Object>> thirdLevelUnderAccount = new ArrayList<>();
//        thirdLevelUnderAccount.add(createCategory("账户信息"));
//        thirdLevelUnderAccount.add(createCategory("开户记录"));
//        accountManagement.put("children", thirdLevelUnderAccount);
//        secondLevelCategories.add(accountManagement);
//
//        Map<String, Object> digitalCurrency = new HashMap<>();
//        digitalCurrency.put("name", "数字货币");
//        List<Map<String, Object>> thirdLevelUnderDigital = new ArrayList<>();
//        thirdLevelUnderDigital.add(createCategory("法定数字货币钱包信息"));
//        thirdLevelUnderDigital.add(createCategory("交易流水"));
//        digitalCurrency.put("children", thirdLevelUnderDigital);
//        secondLevelCategories.add(digitalCurrency);
//
//        businessCategory.put("children", secondLevelCategories);
//        firstLevelCategories.add(businessCategory);
//
//        dataModel.put("firstLevelCategories", firstLevelCategories);
//
//
//        // 3. 执行渲染
//        String result = render(templateContent, dataModel);

        // 假设你构建的数据模型如下
        List<TreeTemplateCategoryVo> templateCategoryVoList = new ArrayList<>();

// 1. 构建一个 branch 为 "A" 的分类
        TreeTemplateCategoryVo customerCategory = new TreeTemplateCategoryVo();
        customerCategory.setName("客户信息");
        customerCategory.setCategoryDesc("外部客户（自然人/企业）的自然属性、认证信息、标签等。");
        customerCategory.setCategoryInferenceRule("A"); // 对应 branch = "A"

        List<TreeTemplateCategoryVo> customerChildren = new ArrayList<>();
        TreeTemplateCategoryVo personalCustomer = new TreeTemplateCategoryVo();
        personalCustomer.setName("个人客户");
        customerChildren.add(personalCustomer);

        TreeTemplateCategoryVo enterpriseCustomer = new TreeTemplateCategoryVo();
        enterpriseCustomer.setName("企业客户");
        customerChildren.add(enterpriseCustomer);

        customerCategory.setChildren(customerChildren);
        templateCategoryVoList.add(customerCategory);

// 2. 构建一个 branch 为 "B" 的分类
        TreeTemplateCategoryVo businessCategory = new TreeTemplateCategoryVo();
        businessCategory.setName("业务信息");
        businessCategory.setCategoryDesc("银行在直接面向客户、开展金融服务过程中产生和使用的信息集合。");
        businessCategory.setCategoryInferenceRule("B"); // 对应 branch = "B"


        List<TreeTemplateCategoryVo> businessChildren = new ArrayList<>();
        TreeTemplateCategoryVo business1 = new TreeTemplateCategoryVo();
        business1.setName("10");
        business1.setCategoryDesc("10x");
        businessChildren.add(business1);

        TreeTemplateCategoryVo business2 = new TreeTemplateCategoryVo();
        business2.setName("11");
        businessChildren.add(business2);
        businessCategory.setChildren(businessChildren);

        List<TreeTemplateCategoryVo> children = new ArrayList<>();
        TreeTemplateCategoryVo business11 = new TreeTemplateCategoryVo();
        business11.setName("111");
        business11.setCategoryDesc("111x");
        children.add(business11);
        business1.setChildren(children);

        List<TreeTemplateCategoryVo> children2 = new ArrayList<>();
        TreeTemplateCategoryVo business12 = new TreeTemplateCategoryVo();
        business12.setName("112");
        business12.setCategoryDesc("112");
        children2.add(business12);
        business2.setChildren(children2);

// ... 此处省略为 businessCategory 设置嵌套 children 的代码 ...
// 其结构应为 businessCategory -> children (二级分类) -> children (三级分类)

        templateCategoryVoList.add(businessCategory);

// 将构建好的列表放入 Map
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("data", templateCategoryVoList);
        String result = render(templateContent, stringObjectHashMap);

        // 4. 验证结果
        System.out.println("测试 branch A 的渲染结果：");
        System.out.println(result);

//            // 验证关键内容是否存在
//            assertTrue(result.contains("1. 客户信息（来自模板表中的1级分类）"));
//            assertTrue(result.contains("- 定义：外部客户（自然人/企业）的自然属性、认证信息、标签等。（来自模板表中的1级分类说明）"));
//            assertTrue(result.contains("- 板块(从以下列表中选一个最接近的)："));
//            assertTrue(result.contains("个人客户, 企业客户")); // 验证2级分类被正确渲染
//            assertTrue(result.contains("- 排除：内部员工、代理人、合作伙伴、内部机构等。")); // 验证硬编码内容
//            assertFalse(result.contains("法定数字货币钱包信息")); // 确保没有渲染3级分类
    }

    /**
     * 辅助方法，用于快速创建分类 Map。
     */
    private static Map<String, Object> createCategory(String name) {
        Map<String, Object> category = new HashMap<>();
        category.put("name", name);
        return category;
    }

    public static String render(String templateContent, Map<String, Object> dataModel) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            StringTemplateLoader loader = new StringTemplateLoader();
            loader.putTemplate("data", templateContent);
            cfg.setTemplateLoader(loader);

            Template template = cfg.getTemplate("data", "UTF-8");
            StringWriter writer = new StringWriter();
            template.process(dataModel, writer);
            return writer.toString();

        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Template rendering failed", e);
        }
    }
}