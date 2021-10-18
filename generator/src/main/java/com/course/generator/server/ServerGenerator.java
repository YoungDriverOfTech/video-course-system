package com.course.generator.server;

import com.course.generator.util.DbUtil;
import com.course.generator.util.Field;
import com.course.generator.util.FreemarkerUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

public class ServerGenerator {

    static String MODULE = "system";
//    static String MODULE = "file";
    static String toDtoPath = "server\\src\\main\\java\\com\\course\\server\\dto\\";
    static String toServicePath = "server\\src\\main\\java\\com\\course\\server\\service\\";
    static String toControllerPath = MODULE + "\\src\\main\\java\\com\\course\\" + MODULE + "\\controller\\admin\\";
    static String generatorConfigPath = "server\\src\\main\\resources\\generator\\generatorConfig.xml";

    public static void main(String[] args) throws Exception {
        String module = MODULE;

        // only generate first table node in xml file
        File file = new File(generatorConfigPath);
        SAXReader reader = new SAXReader();
        // read xml file into Document obejct
        Document doc = reader.read(file);
        // get root element
        Element rootElement = doc.getRootElement();
        // get context node
        Element contextElement = rootElement.element("context");
        // define a Element obj for loop
        Element tableElement;
        // get first table node
        tableElement = contextElement.elementIterator("table").next();
        String Domain = tableElement.attributeValue("domainObjectName");
        String tableName = tableElement.attributeValue("tableName");
        String tableNameCn = DbUtil.getTableComment(tableName);
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        System.out.println("Table："+tableElement.attributeValue("tableName"));
        System.out.println("Domain："+tableElement.attributeValue("domainObjectName"));

        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);
        Set<String> typeSet = getJavaTypes(fieldList);
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableNameCn", tableNameCn);
        map.put("module", module);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);

        // generate Dto
        FreemarkerUtil.initConfig("dto.ftl");
        FreemarkerUtil.generator(toDtoPath + Domain + "Dto.java", map);

        // generate service layer
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);

        // generate controller layer
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + Domain + "Controller.java", map);

    }

    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (Field field : fieldList) {
            set.add(field.getJavaType());
        }
        return set;
    }
}
