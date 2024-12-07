package com.core.java.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class DemoApp {

    public static void main(String[] args) throws IOException {

        List<Country> countries = Arrays.asList(new Country("Pune", "Maharashtra"),
                new Country("Solapur", "Maharshtra"));
        List<Country> countries2 = Arrays.asList(new Country("Pune1", "Maharashtra1"));
        List<Address> addresses = Arrays.asList(new Address("Pune1", countries));
        List<Address> addresses2 = Arrays.asList(new Address("Pune2", countries2));


        Employee employee1 = new Employee(1L, "abc", "AI dev", addresses);
        Employee employee2 = new Employee(2L, "pqr", "AI dev", addresses2);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode actualObj1 = mapper.readTree(employee1.toString());
        JsonNode actualObj2 = mapper.readTree(employee2.toString());

        Map<String, String> map = new HashMap<>();
        addKeys("", actualObj1, map, new ArrayList<>());

        Map<String, String> map2 = new HashMap<>();
        addKeys("", actualObj2, map2, new ArrayList<>());

        Map<String, String> diffMap =null;

        System.out.println(map);
        System.out.println(map2);

        Properties properties = loadProperties();

        diffMap = differenceMap(map, map2, properties);

        //System.out.println(diffMap);
        diffMap.entrySet()
          .forEach(System.out::println);
    }

    public static Properties loadProperties() throws IOException {
        Properties prop=new Properties();
        FileInputStream ip= new FileInputStream(new ClassPathResource("label.properties").getFile());
        prop.load(ip);
        return prop;
    }

    public static Map<String,String> differenceMap(Map<String, String> map1, Map<String, String> map2, Properties prop){
        Map<String,String> diff = new HashMap<>();

        if(!map1.isEmpty() && !map2.isEmpty()){
            if(map1.size() > map2.size()){
                map1.forEach((k,v)->{
                    String str = "employee."+k.toString().replaceAll("-",".");
                    int i = str.contains("1") ? str.indexOf('1')-1: str.length();
                    String s1 = str.substring(0, i);
                    String label = (String) prop.get(s1);
                    System.out.println(k+"="+"value1:"+v+"\t value2:"+map2.get(k));
                    if(map2.containsKey(k) && !v.equals(map2.get(k))){
                        if(diff.containsKey(label)){
                            diff.put(label+"_1", diff.get(label));
                        } else {
                            diff.put(label, "value1:"+v+"\t value2:"+map2.get(k));
                        }
                    }else{
                        diff.put(label, "value1:"+v+"\t value2:"+map2.get(k));
                    }
                });
            } else{
                map2.forEach((k,v)->{
                    if(map1.containsKey(k) && !v.equals(map1.get(k))){
                        diff.put(k, "value1:"+map1.get(k)+"\t value2:"+v);
                    }
                });
            }
        }
        return diff;
    }



    /*public static boolean comapreJson(String currentPath,  JsonNode o1, JsonNode o2,  Map<String, String> map, List<Integer>  suffix){

        if(o1 == null || o2 == null) return false;

        if(o1 != null && o2 != null){
            if (o1.isValueNode() && o2.isValueNode()) {
                System.out.println(o1);
                System.out.println(o2);
            }
            else if (o1.isArray() && o2.isArray()) {

                ArrayNode arrayNode = (ArrayNode) o1;

                for (int i = 0; i < arrayNode.size(); i++) {
                    suffix.add(i + 1);
                    addKeys(currentPath, arrayNode.get(i), map, suffix);

                    if (i + 1 <arrayNode.size()){
                        suffix.remove(arrayNode.size() - 1);
                    }
                }
                for (JsonNode arrayItem : o1) {
                    System.out.println(arrayItem);
                }

                for (JsonNode arrayItem : o1) {
                    System.out.println(arrayItem);
                }
            }
            else if (o1.isObject() && o2.isObject()) {
                System.out.println(o1);
                System.out.println(o2);
                Map<String, String> map = new HashMap<>();
                ObjectNode objectNode = (ObjectNode) o1;
                Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
                String pathPrefix = currentPath.isEmpty() ? "" : currentPath + "-";

                while (iter.hasNext()) {
                    Map.Entry<String, JsonNode> entry = iter.next();
                    addKeys(pathPrefix + entry.getKey(), entry.getValue(), map, new ArrayList<>());
                }
            }
        }

        return  false;
    }*/



    private static void addKeys(String currentPath, JsonNode jsonNode, Map<String, String> map, List<String> suffix) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
            String pathPrefix = currentPath.isEmpty() ? "" : currentPath + "-";

            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();
                addKeys(pathPrefix + entry.getKey(), entry.getValue(), map, suffix);
            }
        } else if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;

            for (int i = 0; i < arrayNode.size(); i++) {
                int n = i + 1;
                suffix.add(i+1+"");
                addKeys(currentPath, arrayNode.get(i), map, suffix);

                if (i + 1 < arrayNode.size()) {
                    suffix.remove(arrayNode.size() - 1);
                }
            }

        } else if (jsonNode.isValueNode()) {
            if (currentPath.contains("-")) {
                for (int i = 0; i < suffix.size(); i++) {
                    currentPath += "-" +  suffix.get(i);
                }

                suffix = new ArrayList<>();
            }

            ValueNode valueNode = (ValueNode) jsonNode;
            map.put(currentPath, valueNode.asText());
        }

    }

}
