package com.yf.generator.tools;


import com.yf.generator.domain.BaseDataRow;
import com.yf.generator.domain.DataTable;
import com.yf.generator.utils.Contants;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;


/**
 * @author sunyifu
 * @ClassName: GeneratorProfileHelper
 * @Description: 生成器助手类
 * @date 2017年4月7日 下午5:55:02
 */
public class VelocityHelper {
    private String rootPath = VelocityHelper.class.getClassLoader()
            .getResource("").getFile()
            + "../../src/main";
    private String rootPath_java = rootPath + "/java";
    private String genPath = "";
    //表名
    private String tableName;
    //去掉第一个下划线之前的
    private String tableNameI;
    //驼峰首字母大写
    private String tableNameII;
    //驼峰首字母小写
    private String tableNameIII;
    //生成文件名
    private String fileName;
    //生成文件路径
    private String filePath;
    //业务包路径
    private String projectName;
    //所在包路径
    private String packageName;
    private DataTable dataTable;

    private VelocityEngine velocityEngine = null;
    private VelocityContext velocityContext = null;


    public VelocityHelper(String tableName, DataTable dataTable) {
        this.tableName = tableName;
        //去掉t_user  t_
        this.tableNameI = StringHelper.toRequestMappingName(tableName);
        //首字母大写
        this.tableNameII = StringHelper.toFirstCharUpperCase(StringHelper.underlineToCamel(tableName));
        //首字母小写
        this.tableNameIII = StringHelper.underlineToCamel(tableName);
        //文件生成路径
        this.genPath = rootPath_java + Contants.GENERATE_CARBON + "/" + Contants.GENERATE_PACKAGENAME + "/" + StringHelper.underlineToCamel(tableName) + "/";


        this.projectName = Contants.GENERATE_PROJECTNAME;
        this.packageName = projectName + ".modules";
        this.dataTable = dataTable;

        this.init();
    }

    /**
     * 生成Service服务类文件
     */
    public VelocityHelper GenerateService() {
        return GenerateFile(
                Contants.GENERATE_SERVICE_PATH,
                Contants.GENERATE_SUFFIX_SERVICE,
                "/vm/service.vm");
    }

    /**
     * 生成Controller控制类文件
     */
    public VelocityHelper GenerateController() {
        return GenerateFile(
                Contants.GENERATE_CONTROLLER_PATH,
                Contants.GENERATE_SUFFIX_CONTROLLER,
                "/vm/controller.vm");
    }

    /**
     * 生成Controller控制类文件
     */
    public VelocityHelper GenerateJpa() {
        return GenerateFile(
                Contants.GENERATE_JPA_PATH,
                Contants.GENERATE_SUFFIX_JPA,
                "/vm/jpa.vm");
    }

    /**
     * 生成实体类Java文件
     */
    public VelocityHelper GenerateEntity() {
        return GenerateFile(
                Contants.GENERATE_ENTITY_PATH,
                Contants.GENERATE_SUFFIX_ENTITY,
                "/vm/entity.vm");
    }

    /**
     * 生成文件
     *
     * @param suffix       生成文件后缀 比如".java"
     * @param templatePath 模板路径
     */
    private VelocityHelper GenerateFile(String path, String suffix, String templatePath) {
        //文件路径
        this.filePath = this.genPath + path;
        //文件全路径 无后缀
        this.fileName = this.filePath + tableNameII;
        Template t = velocityEngine.getTemplate(templatePath, Contants.GENERATE_FILE_ENCODING);
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        PrintWriter pw = null;
        try {
            System.out.println(fileName + suffix);
            pw = new PrintWriter(fileName + suffix);
            t.merge(velocityContext, pw);
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            pw.close();
        }
        return this;

    }

    /**
     * 参数初始化
     */
    private void init() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class",
                ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        velocityContext = new VelocityContext();
        velocityContext.put("table_name", tableName);
        velocityContext.put("name", tableNameI);
        velocityContext.put("tableName", tableNameIII);
        velocityContext.put("TableName", tableNameII);
        velocityContext.put("projectName", projectName);
        velocityContext.put("packageName", packageName);
        List<BaseDataRow> columns = dataTable.ConvertDataTableToList(dataTable);
        for (BaseDataRow data : columns) {
            if (!data.getId_name().equals("")) {
                //因为主键名称不统一 所以动态传入
                velocityContext.put("id_name", data.getId_name());
                if (!data.getId_name().equals(""))
                    velocityContext.put("table_name", data.getTable_name());
                if (!data.getId_name().equals(""))
                    velocityContext.put("db_name", data.getDb_name());
            }
        }
        velocityContext.put("columns", columns);
    }
}
