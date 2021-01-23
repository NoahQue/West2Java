import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

public class Analysis {
    public static void analyseAll() {
        File file = new File("D:/javaLearning/WestTwo/test3/src/test/java/testResult.text");
        String result = TxtReader.txtToString(file);
        Map<String, Object> jsonMap = JSONObject.parseObject(result);
        String str = String.valueOf(jsonMap.get("All"));
        //System.out.println(str);
        Map<String, Object> jsonSecondMap = JSONObject.parseObject(str);
        String country = String.valueOf(jsonSecondMap.get("country"));
        String confirmed = String.valueOf(jsonSecondMap.get("confirmed"));
        String recovered = String.valueOf(jsonSecondMap.get("recovered"));
        String deaths = String.valueOf(jsonSecondMap.get("deaths"));
        String sqlStr = "INSERT INTO epidemic_situation VALUES ('" + country + "'," + confirmed + "," + recovered + "," + deaths + ")";
        System.out.println(sqlStr);
    }

    public static void analysePart() {
        File file = new File("D:/javaLearning/WestTwo/test3/src/test/java/testResult.text");
        String result = TxtReader.txtToString(file);
        Map<String, Object> jsonMap = JSONObject.parseObject(result);

        Iterator<Map.Entry<String, Object>> it = jsonMap.entrySet().iterator();
        String temp = String.valueOf(jsonMap.get("All"));
        Map<String, Object> otherJson = JSONObject.parseObject(temp);
        String country = String.valueOf(otherJson.get("country"));
        System.out.println(country);
        //int key = 1;
        System.out.println(country);
        if(country.equals("United Kingdom")) country = "United_Kingdom";
        //System.out.println(key);
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (entry.getKey().equals("All")) entry = it.next();
            //System.out.println(entry.getKey());
            String str = String.valueOf(jsonMap.get(entry.getKey()));
            Map<String, Object> jsonSecondMap = JSONObject.parseObject(str);
            String province_name = entry.getKey();
            String confirmed = String.valueOf(jsonSecondMap.get("confirmed"));
            String recovered = String.valueOf(jsonSecondMap.get("recovered"));
            String deaths = String.valueOf(jsonSecondMap.get("deaths"));
            String updated = String.valueOf(jsonSecondMap.get("updated"));
            String sqlStr = "INSERT INTO " + country + " VALUES ('" + province_name + "'," + confirmed + "," + recovered + "," + deaths + ",'" + updated + "')";
            System.out.println(sqlStr);

        }
    }

        //return sqlStr;

}
