import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class GenerateTestingFiles {

    public static void main(String[] args) {
        PrintWriter writer;
        try {
            writer = new PrintWriter("input/dinner-2.txt", "UTF-8");
            writer.println(10000);
            for(int i=0;i < 100; i++){
                for(int j=0; j<i; j++){
                    writer.println((i+ " " +(StdRandom.uniform(100)+1)));
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

}
