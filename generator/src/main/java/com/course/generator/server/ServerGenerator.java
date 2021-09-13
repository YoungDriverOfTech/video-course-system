package com.course.generator.server;

import com.course.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerGenerator {

    static String toServicePath = "server\\src\\main\\java\\com\\course\\server\\service\\";

    public static void main(String[] args) throws IOException, TemplateException {
        String domainUpperCase = "Section";
        String domainLowerCase = "section";
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", domainUpperCase);
        map.put("domain", domainLowerCase);


        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + domainUpperCase + "Service.java", map);
    }
}
