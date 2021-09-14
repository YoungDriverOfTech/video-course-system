package com.course.generator.server;

import com.course.generator.util.DbUtil;
import com.course.generator.util.Field;
import com.course.generator.util.FreemarkerUtil;

import java.util.*;

public class ServerGenerator {

    static String MODULE = "business";
    static String toDtoPath = "server\\src\\main\\java\\com\\course\\server\\dto\\";
    static String toServicePath = "server\\src\\main\\java\\com\\course\\server\\service\\";
    static String toControllerPath = "business\\src\\main\\java\\com\\course\\business\\controller\\admin\\";

    public static void main(String[] args) throws Exception {
        String domainUpperCase = "Section";
        String domainLowerCase = "section";
        String tableNameCn = "Section";
        String module = MODULE;
        List<Field> fieldList = DbUtil.getColumnByTableName(domainLowerCase);
        Set<String> typeSet = getJavaTypes(fieldList);
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", domainUpperCase);
        map.put("domain", domainLowerCase);
        map.put("tableNameCn", tableNameCn);
        map.put("module", module);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);

        // generate Dto
        FreemarkerUtil.initConfig("dto.ftl");
        FreemarkerUtil.generator(toDtoPath + domainUpperCase + "Dto.java", map);

        // generate service layer
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + domainUpperCase + "Service.java", map);

        // generate controller layer
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + domainUpperCase + "Controller.java", map);

    }

    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
