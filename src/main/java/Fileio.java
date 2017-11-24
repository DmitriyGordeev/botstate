import java.io.*;

enum FileEncoding { CP1251, UTF8 }

public class Fileio {

    public static FileEncoding encoding = FileEncoding.UTF8;

    public static String setEncoding(FileEncoding encoding) {
        switch(encoding) {
            case CP1251:
                return "cp1251";
            default:
                return "UTF-8";
        }
    }

    public static String readfile(String filename) throws IOException {

        String out = "";
        File f = new File(filename);

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        String line;
        while((line = br.readLine()) != null) {
            out += line + "\n";
        }

        br.close();
        fr.close();

        return out;
    }

    public static void writefile(String filename, String content) throws IOException {

        PrintWriter writer = new PrintWriter(filename, setEncoding(encoding));
        writer.print(content);
        writer.close();
    }

}