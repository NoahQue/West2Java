import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Test {
    public static void main(String[] args) {
        Analysis.analyseAll();
        Analysis.analysePart();
        clearTable();
    }
    public static void clearTable(){
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
                int count = stmt.executeUpdate(sqlStr);
                //System.out.println((count == 1) ? (temp + "清空成功") : (temp + "清空失败"));
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
}
