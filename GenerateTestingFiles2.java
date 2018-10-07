import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class GenerateTestingFiles2 {

    public static void main(String[] args) {
        PrintWriter writer;
        try {
            writer = new PrintWriter("input/oneSparseFalse2.txt", "UTF-8");
            writer.println(10000);
            for(int i=0;i < 100; i++){
                for(int j=0; j<i; j++){
                    if(i==70 || i==50 || i==30){
                        writer.println(i + " " + (StdRandom.uniform(100)+1));
                    }
                    else{
                        writer.println(i+ " 0");
                    }
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
