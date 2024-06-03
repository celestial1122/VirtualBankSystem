import java.io.*;

public class readgoal {
    public static void main(String[] args) {
        // 输入和输出文件路径
        String inputFilePath = "saving_goals.txt";
        String outputFilePath = "goal.txt";

        try (InputStream inputStream = new FileInputStream(inputFilePath);
             OutputStream outputStream = new FileOutputStream(outputFilePath);
             PrintWriter writer = new PrintWriter(outputStream)) {

            // 读取输入文件中的字节并转换为十六进制字符串写入输出文件
            int data;
            while ((data = inputStream.read()) != -1) {
                String hexString = Integer.toHexString(data);
                writer.print(hexString);
            }

            System.out.println("Binary file converted to text successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
