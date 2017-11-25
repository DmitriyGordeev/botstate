import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;
import java.util.Vector;

public class utest_SimpleBot {

    private Vector<Vector<String>> generateKeyboardContent() {
        Vector<Vector<String>> keyboardContent = new Vector<Vector<String>>();
        Vector<String> row_1 = new Vector<String>();
        row_1.add("A"); row_1.add("B");

        Vector<String> row_2 = new Vector<String>();
        row_2.add("C"); row_2.add("D");

        keyboardContent.add(row_1);
        keyboardContent.add(row_2);

        return keyboardContent;
    }

    @Test
    public void parseJsonExample() {

        String jsonString = "{\"value\":10}";
        try {
            JSONObject json = new JSONObject(jsonString);
            System.out.println(json.getInt("value"));
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createKeyboard_correctValues() {

        SimpleBot bot = new SimpleBot();
        ArrayList<KeyboardRow> rows =  bot.createKeyboard(generateKeyboardContent());
        Assert.assertEquals(2, rows.size());

        KeyboardRow r = rows.get(0);
        Assert.assertEquals(2, r.size());
        Assert.assertEquals("A", r.get(0).getText());
        Assert.assertEquals("B", r.get(1).getText());

        r = rows.get(1);
        Assert.assertEquals(2, r.size());
        Assert.assertEquals("C", r.get(0).getText());
        Assert.assertEquals("D", r.get(1).getText());
    }


}
