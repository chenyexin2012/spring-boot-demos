package com.holmes.springboot.mybatisplus.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成代码工具
 */
public class CodeGenerator {

    public static void main(String[] args) {

        CodeGenerator g = new CodeGenerator();
        boolean startWithI = true;
        String projectPath = System.getProperty("user.dir") + "/spring-boot-mybatis-plus";
        String packageName = "com.holmes.springboot.mybatisplus";
        String[] tableNames = new String[]{
                "t_user", "t_order"
        };

        g.generateByTables(startWithI, projectPath, packageName, tableNames);
    }

    /**
     * 根据表自动生成
     *
     * @param serviceNameStartWithI 默认为false
     * @param packageName           包名
     * @param tableNames            表名
     */
    private void generateByTables(boolean serviceNameStartWithI, String projectPath, String packageName, String... tableNames) {
        //配置数据源
        DataSourceConfig dataSourceConfig = getDataSourceConfig();
        // 加载默认配置
        InjectionConfig injectionConfig = getInjectionConfig(projectPath);
        // 策略配置
        StrategyConfig strategyConfig = getStrategyConfig(tableNames);
        //全局变量配置
        GlobalConfig globalConfig = getGlobalConfig(serviceNameStartWithI, projectPath);
        // 模板配置
        TemplateConfig templateConfig = getTemplateConfig();
        //包名配置
        PackageConfig packageConfig = getPackageConfig(packageName);
        //自动生成
        atuoGenerator(dataSourceConfig, strategyConfig, templateConfig, injectionConfig, globalConfig, packageConfig);
    }

    /**
     * 集成
     *
     * @param dataSourceConfig
     * @param strategyConfig
     * @param templateConfig
     * @param injectionConfig
     * @param config
     * @param packageConfig
     */
    private void atuoGenerator(DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig,
                               TemplateConfig templateConfig, InjectionConfig injectionConfig,
                               GlobalConfig config, PackageConfig packageConfig) {
        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setTemplate(templateConfig)
                .setCfg(injectionConfig)
                .setPackageInfo(packageConfig)
                .setTemplateEngine(new VelocityTemplateEngine())
                .execute();
    }

    /**
     * 全局配置
     *
     * @param serviceNameStartWithI false
     * @return GlobalConfig
     */
    private GlobalConfig getGlobalConfig(boolean serviceNameStartWithI, String projectPath) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig
                .setBaseColumnList(true)
                .setBaseResultMap(true)
                .setActiveRecord(false)
                .setAuthor("holmes")
                //设置输出路径
                .setOutputDir(projectPath + "/src/main/java/")
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            //设置service名
            globalConfig.setServiceName("%sService");
        }
        return globalConfig;
    }

    /**
     * 设置包名
     *
     * @param packageName 父路径包名
     * @return PackageConfig 包名配置
     */
    private PackageConfig getPackageConfig(String packageName) {

        PackageConfig pc = new PackageConfig()
                .setParent(packageName)
//                .setXml("mapper")
                .setMapper("mapper")
                .setController("controller")
                .setService("service")
                .setServiceImpl("service.impl")
                .setEntity("entity");

        return pc;
    }

    /**
     * 配置模板
     *
     * @return
     */
    private TemplateConfig getTemplateConfig() {
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //不使用默认类型模板，由自定义配置生成
        templateConfig.setController(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setXml(null);
        // 使用默认配置
        templateConfig.setMapper(ConstVal.TEMPLATE_MAPPER);
        templateConfig.setEntity(ConstVal.TEMPLATE_ENTITY_JAVA);
        templateConfig.setService(ConstVal.TEMPLATE_SERVICE);
        templateConfig.setServiceImpl(ConstVal.TEMPLATE_SERVICE_IMPL);
        return templateConfig;
    }

    /**
     * 自定义配置，自定义配置优先于默认配置生效
     *
     * @param projectPath
     * @return
     */
    private InjectionConfig getInjectionConfig(String projectPath) {

        String mapperXmlPath = "/templates/mapper.xml.vm";
//        String mapperJavaPath = "/templates/mapper.java.vm";
//        String entityPath = "/templates/entity.java.vm";

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

//        focList.add(new FileOutConfig(entityPath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义Entity文件名跟生成路径
//                return projectPath + "/src/main/java/com/holmes/springboot/mybatisplus/entity/"
//                        + tableInfo.getEntityName() + StringPool.DOT_JAVA;
//            }
//        });

        focList.add(new FileOutConfig(mapperXmlPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义xml 文件名和生成路径
                return projectPath + "/src/main/resources/mybatis/mapper/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

//        focList.add(new FileOutConfig(mapperJavaPath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义Mapper类文件名和生成路径
//                return projectPath + "/src/main/java/com/holmes/springboot/mybatisplus/mapper/"
//                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
//            }
//        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }


    /**
     * 策略配置
     *
     * @param tableNames 表名
     * @return StrategyConfig
     */
    private StrategyConfig getStrategyConfig(String... tableNames) {
        return new StrategyConfig()
                // 全局大写命名 ORACLE 注意
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setRestControllerStyle(false)
                //从数据库表到文件的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("t_")
                .setColumnNaming(NamingStrategy.underline_to_camel)
                //需要生成的的表名，多个表名传数组
                .setInclude(tableNames);
    }

    /**
     * 配置数据源
     *
     * @return 数据源配置 DataSourceConfig
     */
    private DataSourceConfig getDataSourceConfig() {
        return new DataSourceConfig().setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://peer1:3306/mybatis-demo?useUnicode=true&characterEncoding=utf-8")
                .setUsername("root")
                .setPassword("123456")
                .setDriverName("com.mysql.cj.jdbc.Driver");
    }


}