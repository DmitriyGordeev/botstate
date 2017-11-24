import org.json.JSONObject;
import org.junit.Test;

public class utest_SimpleBot {

    @Test
    public void parseJsonExample() {

        String jsonString = "{\"value\":10}";
        JSONObject json = new JSONObject(jsonString);
        System.out.println(json.getInt("value"));
    }


}
