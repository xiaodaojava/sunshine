package red.lixiang.tools.jdk.domain;

import red.lixiang.tools.base.CommonModel;
import red.lixiang.tools.base.annotation.EnhanceTool;
import red.lixiang.tools.jdk.reflect.ReflectTools;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author lixiang
 * @date 2020/4/28
 **/
public class DomainDesc implements CommonModel {
    /** 实体类的名字: Passport */
    @EnhanceTool(skipToMap = true)
    private String modelName;
    /** xxxDO */
    @EnhanceTool(mapKey = "DO.java")
    private String dbModel;
    /** xxxQC */
    @EnhanceTool(mapKey = "QC.java")
    private String qc;
    @EnhanceTool(mapKey = "Mapper.java")
    private String javaMapper;
    @EnhanceTool(mapKey = "Provider.java")
    private String provider;
    @EnhanceTool(mapKey = "Manager.java")
    private String manager;
    @EnhanceTool(mapKey = "ManagerImpl.java")
    private String managerImpl;
    @EnhanceTool(mapKey = "Controller.java")
    private String controller;
    @EnhanceTool(mapKey = "-Mapper.xml")
    private String xmlMapper;

    public String getModelName() {
        return modelName;
    }

    public DomainDesc setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public String getDbModel() {
        return dbModel;
    }

    public DomainDesc setDbModel(String dbModel) {
        this.dbModel = dbModel;
        return this;
    }

    public String getQc() {
        return qc;
    }

    public DomainDesc setQc(String qc) {
        this.qc = qc;
        return this;
    }

    public String getJavaMapper() {
        return javaMapper;
    }

    public DomainDesc setJavaMapper(String javaMapper) {
        this.javaMapper = javaMapper;
        return this;
    }

    public String getXmlMapper() {
        return xmlMapper;
    }

    public DomainDesc setXmlMapper(String xmlMapper) {
        this.xmlMapper = xmlMapper;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public DomainDesc setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getManager() {
        return manager;
    }

    public DomainDesc setManager(String manager) {
        this.manager = manager;
        return this;
    }

    public String getManagerImpl() {
        return managerImpl;
    }

    public DomainDesc setManagerImpl(String managerImpl) {
        this.managerImpl = managerImpl;
        return this;
    }

    public String getController() {
        return controller;
    }

    public DomainDesc setController(String controller) {
        this.controller = controller;
        return this;
    }

    public void toFile(String dir){
        //把当前内容输出成文件
        Map<String, Object> map = toMap();
        String targetDir = dir+"/"+modelName;
        if(Files.notExists(Paths.get(targetDir))){
            try {
                Files.createDirectories(Paths.get(targetDir));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map.forEach((key,objValue)->{
            String path = targetDir+"/"+modelName+key;
            try {
                Files.write(Paths.get(path), objValue.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
