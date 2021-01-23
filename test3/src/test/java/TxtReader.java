import java.io.*;

public class TxtReader {
    public static String txtToString(File file) {
        String result = "";
        try {
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(new FileReader(file));
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    result = result + "\n" + lineTxt;
                }
                br.close();
            } else System.out.println("文件不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
