import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

enum FileEncoding { CP1251, UTF8 }

public class utest_Fileio {

    public static FileEncoding encoding = FileEncoding.UTF8;

    public static String setEncoding() {
        switch(encoding) {
            case CP1251:
                return "cp1251";
            default:
                return "UTF-8";
        }
    }

    @Test
    public void test_write_read_file() {
        String filename = "fileio-test.txt";
        String fileContent = "Message!\n";

        try {
            Fileio.writefile(filename, fileContent);
        }
        catch(IOException e) { e.printStackTrace(); }

        String content = "";
        try {
            content = Fileio.readfile(filename);
        }
        catch(IOException e) { e.printStackTrace(); }


        System.out.println("expected: " + fileContent);
        System.out.println("-----------------------------------");
        System.out.println("actual: " + content);

        Assert.assertEquals(fileContent, content);
    }

    @Test
    public void correctEncoding() throws FileNotFoundException,
            UnsupportedEncodingException {

        String filename = "encoding-test.txt";

        PrintWriter writer = new PrintWriter(filename, setEncoding());
        writer.print("русские символы здесь!");
        writer.close();
    }

}