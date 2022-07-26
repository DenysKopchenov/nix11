package com.alevel.lesson10.shop;

import com.alevel.lesson10.shop.model.phone.Manufacturer;
import com.alevel.lesson10.shop.model.phone.OperatingSystem;
import com.alevel.lesson10.shop.model.phone.Phone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStreamXML = classLoader.getResourceAsStream("phone.xml");
        InputStream inputStreamJSON = classLoader.getResourceAsStream("phone.json");
        List<String> lines = readAllLinesXML(inputStreamXML);
        Map<String, String> fields = new HashMap<>();
        parseLinesFromXMLToMap(lines, fields);
        Phone phone = mapPhone(fields);
        System.out.println("XML phone : " + phone);

        lines = readAllLinesJSON(inputStreamJSON);
        fields = new HashMap<>();

        parseLinesFromJSONToMap(lines, fields);
        Phone phoneJSON = mapPhone(fields);
        System.out.println("JSON phone : " + phoneJSON);
    }

    private static Phone mapPhone(Map<String, String> map) {
        OperatingSystem operatingSystem = new OperatingSystem();
        operatingSystem.setDesignation(map.get("designation"));
        operatingSystem.setVersion(Integer.parseInt(map.get("version")));
        return new Phone(map.get("title"),
                Integer.parseInt(map.get("count")),
                Long.parseLong(map.get("price")),
                map.get("model"),
                Manufacturer.valueOf(map.get("manufacturer")),
                LocalDateTime.parse(map.get("created"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")),
                map.get("currency"),
                operatingSystem);
    }

    private static void parseLinesFromXMLToMap(List<String> lines, Map<String, String> fields) {
        lines.stream()
                .map(String::trim)
                .forEach(line -> {
                    if (line.contains("<") && line.contains("</") && !line.startsWith("</")) {
                        if (line.contains("currency")) {
                            String substring = line.substring(line.indexOf('<') + 1, line.indexOf(" "));
                            String price = line.substring(line.indexOf('>') + 1, line.indexOf("</"));
                            fields.put(substring, price);
                            String currency = line.substring(line.indexOf(" ") + 1, line.indexOf("="));
                            String value = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                            fields.put(currency, value);
                        } else {
                            String substring = line.substring(line.indexOf('<') + 1, line.indexOf('>'));
                            String value = line.substring(line.indexOf('>') + 1, line.indexOf("</"));
                            fields.put(substring, value);
                        }
                    }
                });
    }

    private static List<String> readAllLinesXML(InputStream inputStream) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    private static void parseLinesFromJSONToMap(List<String> lines, Map<String, String> fields) {
        Pattern pattern = Pattern.compile("\"\\w+\": \".*\"");
        lines.stream()
                .map(String::trim)
                .forEach(line -> {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String field = matcher.group().substring(1, line.indexOf(":") - 1);
                        String value = matcher.group().substring(line.indexOf(":") + 3, line.lastIndexOf("\""));
                        fields.put(field, value);
                    }
                });
    }

    private static List<String> readAllLinesJSON(InputStream inputStreamJSON) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamJSON))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}