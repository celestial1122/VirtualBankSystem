import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class readdata {
    public static void main(String[] args) {
        String filePath = "D:\\JavaCode\\Year3\\bankAPP0507\\children.txt"; // 替换为实际的文本文件路径
        BufferedReader reader = null;
        try {
            // 使用 FileInputStream 配合 InputStreamReader 和 BufferedReader 读取文本文件
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            String line;
            // 逐行读取文本内容并输出
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}