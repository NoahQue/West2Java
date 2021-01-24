import com.alibaba.fastjson.JSONObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class JDBCTest {
    public static void main(String[] args) {
        HttpConnect.init();
        HttpConnect.getInformation();
        storeData();
        Scanner input=new Scanner(System.in);
        while(true) {  //数据更新
            System.out.println("是否进行数据更新？(是/否)");
            String str = input.next();
            if(str.equals("是")){
                HttpConnect.init();
                HttpConnect.getInformation();
                upgrateData();
                System.out.println("数据更新成功");
                System.out.println("建议下一次更新间隔不小于十分钟");
            }
            else{
                System.out.println("使用结束");
                break;
            }
        }
    }
    public static void storeData(){ //将请求的数据储存
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        //String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            for (int i = 0; i < 4; i++) {   //向表epidemic_situation填入数据

                Object temp = HttpConnect.objectList.get(i);
                String str1 = String.valueOf(temp);
                Map<String, Object> jsonMap = JSONObject.parseObject(str1);
                String str2 = String.valueOf(jsonMap.get("All"));
                Map<String, Object> jsonSecondMap = JSONObject.parseObject(str2);

                String country = String.valueOf(jsonSecondMap.get("country"));
                String confirmed = String.valueOf(jsonSecondMap.get("confirmed"));
                String recovered = String.valueOf(jsonSecondMap.get("recovered"));
                String deaths = String.valueOf(jsonSecondMap.get("deaths"));

                String sqlStr = String.format("INSERT INTO epidemic_situation() VALUES ('%s',%s,%s,%s)", country, confirmed, recovered, deaths);
                int count = stmt.executeUpdate(sqlStr);
                System.out.println(count == 1 ? "数据保存成功" : "数据保存失败");
            }
            for (int i = 0; i < 4; i++) {   //分别向四个国家的表中填入数据

                Object temp = HttpConnect.objectList.get(i);
                String str1 = String.valueOf(temp);
                Map<String, Object> jsonMap = JSONObject.parseObject(str1);
                String str2 = String.valueOf(jsonMap.get("All"));
                Map<String, Object> otherJson = JSONObject.parseObject(str2);
                String country = String.valueOf(otherJson.get("country"));
                if(country.equals("United Kingdom")) country = "United_Kingdom";//直接用原名不符合sql语法 先改变名称
                Iterator<Map.Entry<String, Object>> it = jsonMap.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry<String, Object> entry = it.next();
                    if(entry.getKey().equals("All")) entry = it.next();
                    //System.out.println(entry.getKey());
                    String str = String.valueOf(jsonMap.get(entry.getKey()));
                    Map<String, Object> jsonSecondMap = JSONObject.parseObject(str);
                    String province_name = entry.getKey();
                    String confirmed = String.valueOf(jsonSecondMap.get("confirmed"));
                    String recovered = String.valueOf(jsonSecondMap.get("recovered"));
                    String deaths = String.valueOf(jsonSecondMap.get("deaths"));
                    String updated = String.valueOf(jsonSecondMap.get("updated"));
                    String sqlStr = String.format("INSERT INTO %s VALUES ('%s',%s,%s,%s,'%s')", country, province_name, confirmed, recovered, deaths, updated);
                    int count = stmt.executeUpdate(sqlStr);
                    System.out.println(count == 1 ? "数据保存成功" : "数据保存失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void clearTable(){ //清空所有表中的数据
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            ArrayList<String> List = new ArrayList<>();
            List.add("epidemic_situation");
            List.add("China");
            List.add("US");
            List.add("United_Kingdom");
            List.add("Japan");
            for(int i = 0; i < 4; i++) {
                String temp = List.get(i);
                String sqlStr = String.format("TRUNCATE TABLE %s",temp);
                stmt.executeUpdate(sqlStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void upgrateData(){ //更新所有表中数据
        clearTable();
        storeData();
    }


}
