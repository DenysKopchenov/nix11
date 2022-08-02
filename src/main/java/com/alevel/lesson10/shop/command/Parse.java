package com.alevel.lesson10.shop.command;

import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.service.ParserUtil;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parse implements Command {
    @Override
    public void execute() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStreamXML = classLoader.getResourceAsStream("phone.xml");
        InputStream inputStreamJSON = classLoader.getResourceAsStream("phone.json");

        List<String> lines = ParserUtil.readAllLinesXML(inputStreamXML);
        Map<String, String> fields = new HashMap<>();
        ParserUtil.parseLinesFromXMLToMap(lines, fields);
        Phone phone = ParserUtil.phoneMapper(fields);
        System.out.println("XML phone : " + phone);

        lines = ParserUtil.readAllLinesJSON(inputStreamJSON);

        ParserUtil.parseLinesFromJSONToMap(lines, fields);
        Phone phoneJSON = ParserUtil.phoneMapper(fields);
        System.out.println("JSON phone : " + phoneJSON);
    }
}
