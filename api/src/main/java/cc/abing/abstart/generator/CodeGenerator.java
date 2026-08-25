package cc.abing.abstart.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.base.CaseFormat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author ABing
 * @since 2026-08-25
 */
public class CodeGenerator {

	public static final String DB_NAME = "abstart";

	public static String TABLE_NAME = "biz_user";

	public static final String PACKAGE_NAME = "cc.abing";

	public static final String MODULE_NAME = "abstart";

	public static final String TABLE_PREFIX = "";

	public static final String PACKAGE_PATH = (PACKAGE_NAME.concat(".").concat(MODULE_NAME)).replaceAll("\\.", "/");

	public static final String USER_DIR = System.getProperty("user.dir");

	public static String DB_URL;

	public static String DB_USERNAME;

	public static String DB_PASSWORD;

	public static String AUTHOR = "ABing";

	public static void main(String[] args) {
		System.out.println("FastAutoGenerator开始执行");
		Properties properties = new Properties();
		try {
			properties.load(CodeGenerator.class.getClassLoader().getResourceAsStream("application-local.properties"));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		DB_URL = properties.getProperty("spring.datasource.dynamic.datasource.mysql.url");
		DB_USERNAME = properties.getProperty("spring.datasource.dynamic.datasource.mysql.username");
		DB_PASSWORD = properties.getProperty("spring.datasource.dynamic.datasource.mysql.password");
		System.out.printf("读取数据库配置：%s\n账号密码：%s@%s\n表：%s.%s%n", DB_URL, DB_USERNAME, DB_PASSWORD, DB_NAME, TABLE_NAME);

		generateCode();
		System.out.println("FastAutoGenerator执行结束");
	}

	public static void generateCode() {
		// 数据源配置 字段类型映射配置
		DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig.Builder(DB_URL, DB_USERNAME,
				DB_PASSWORD).schema(DB_NAME).typeConvert(new MySqlTypeConvert() {
					@Override
					public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
						DbColumnType dbColumnType = null;
						// 将数据库中无符号int转换成Long
						if (fieldType.toLowerCase().startsWith("int") && fieldType.toLowerCase().contains("unsigned")) {
							dbColumnType = DbColumnType.LONG;
						}
						if (dbColumnType == null) {
							dbColumnType = (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
						}
						System.out.println("转换类型：" + fieldType + "==>" + dbColumnType.getType());
						return dbColumnType;
					}
				});

		// 自定义输出路径
		final Map<OutputFile, String> pathInfoMap = new HashMap<OutputFile, String>() {
			private static final long serialVersionUID = -3741977801579160080L;

			{
				put(OutputFile.controller, USER_DIR + "/api/src/main/java/" + PACKAGE_PATH + "/api/controller");
				put(OutputFile.service, USER_DIR + "/api/src/main/java/" + PACKAGE_PATH + "/biz/service");
				put(OutputFile.serviceImpl, USER_DIR + "/api/src/main/java/" + PACKAGE_PATH + "/biz/service/impl");
				put(OutputFile.xml, USER_DIR + "/api/src/main/resources/mapper");
				put(OutputFile.entity, USER_DIR + "/model/src/main/java/" + PACKAGE_PATH + "/model/" + TABLE_NAME);
				// other:
				// /USER_DIR/model/src/main/java/PACKAGE_PATH/model/TABLE_NAME/common/${entity}/${injectionConfig.customFileMap.key}
				put(OutputFile.other,
						USER_DIR + "/model/src/main/java/" + PACKAGE_PATH + "/model/" + TABLE_NAME + "/common");
			}
		};
		// 打印输出路径
		String outputDir = USER_DIR + "/api/src/main/java";
		System.out.println("基础输出路径：" + outputDir);
		System.out.println("自定义输出路径：");
		pathInfoMap.forEach((key, value) -> System.out.println(key.name() + ":" + value));

		FastAutoGenerator.create(dataSourceConfigBuilder).globalConfig(builder -> {
			builder.author(AUTHOR).outputDir(outputDir).disableOpenDir().dateType(DateType.ONLY_DATE);
		})
				// 包名配置
				.packageConfig(builder -> {
					builder.parent(PACKAGE_NAME).moduleName(MODULE_NAME).controller("api.controller")
							.service("biz.service").serviceImpl("biz.service.impl").mapper("dao.mapper")
							.xml("mapper.xml").entity("model." + TABLE_NAME).pathInfo(pathInfoMap);
				})
				// 自定义策略配置 .enableChainModel().addTableFills(new Column("create_time",
				// FieldFill.INSERT))
				.strategyConfig(builder -> {
					builder.addInclude(TABLE_NAME).addTablePrefix(TABLE_PREFIX).enableSchema()
							// controller
							.controllerBuilder().enableRestStyle()
							// service
							.serviceBuilder().formatServiceFileName("%sService")
							// mapper
							.mapperBuilder().enableMapperAnnotation()
							// entity
							.entityBuilder().idType(IdType.AUTO)/*.enableLombok()*/.formatFileName("%sDO");
				})

				// 注入自定义变量
				.injectionConfig(builder -> {
					builder.customFile(new HashMap<String, String>() {
						{
							// 指定自定义模板文件路径<输出文件名，模板路径>
							put("../" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, TABLE_NAME)
									+ "Converter.java", "/templates/converter.java.ftl");
							put("../" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, TABLE_NAME)
									+ "Request.java", "/templates/request.java.ftl");
						}
					}).customMap(new HashMap<String, Object>() {
						{
							put("MyComment", "自定义变量值");
							put("commonPackage", PACKAGE_NAME + "." + MODULE_NAME);
						}
					}).beforeOutputFile((tableInfo, map) -> {
						System.out.println("【CodeGenerator变量map】");
						map.forEach((key, value) -> System.out.println(key + '=' + value));
					})
					// .fileOverride()
					;
				})

				// 指定模板文件路径
				.templateConfig(builder -> {
					builder.controller("/templates/controller.java").service("/templates/service.java")
							.serviceImpl("/templates/serviceImpl.java").mapper("/templates/mapper.java")
							.xml("/templates/mapper.xml").entity("/templates/entity.java");
				})
				// 指定模板引擎
				.templateEngine(new FreemarkerTemplateEngine()).execute();
	}

}
