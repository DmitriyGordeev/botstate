import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Vector;

public class utest_StateNode {

    @Test
    public void constructor() {
        StateNode node = new StateNode("Question", "Answer", null, new Vector<String>());
        Assert.assertEquals("Question", node.question);
        Assert.assertEquals(null, node.parent);
        Assert.assertTrue(node.actions.isEmpty());
        Assert.assertTrue(node.nodes.isEmpty());
    }


    @Test
    public void connect_ref() {
        StateNode node = new StateNode("Question", "Answer", null, new Vector<String>());
        StateNode otherNode = new StateNode("ChildQ", "ChildA", null, new Vector<String>());
        node.connect(otherNode);

        Assert.assertEquals(1, node.nodes.size());
        Assert.assertEquals(node, otherNode.parent);
    }


    @Test
    public void connect_value() {

        StateNode node = new StateNode("Question", "Answer", null, new Vector<String>());
        StateNode childNode = node.connect("ChildQ", "ChildA", new Vector<String>());

        Assert.assertFalse(childNode == null);
        Assert.assertEquals("ChildQ", childNode.question);
        Assert.assertEquals("ChildA", childNode.answer);
        Assert.assertEquals(node, childNode.parent);

        Assert.assertEquals(1, node.nodes.size());
        StateNode child = null;
        Iterator<StateNode> iterator = node.nodes.iterator();
        while(iterator.hasNext()) {
            child = iterator.next();
            if(child.question.equals("ChildQ"))
                break;
        }

         Assert.assertEquals(node, child.parent);
    }


    @Test
    public void find_notNull() {
        StateNode node = new StateNode("Question", "Answer", null, new Vector<String>());

        String test_value = "ChildQ";
        node.connect(test_value, "ChildA", new Vector<String>());

        StateNode found = node.find(test_value);
        Assert.assertTrue(found != null);
        Assert.assertEquals(test_value, found.question);
    }


    @Test
    public void find_null() {
        StateNode node = new StateNode("Question", "Answer", null, new Vector<String>());

        String test_value = "ChildQ";
        node.connect(test_value, "ChildA", new Vector<String>());

        StateNode found = node.find("random");
        Assert.assertTrue(found == null);
    }


    @Test
    public void findAnswer_notEmpty() {
        StateNode node = new StateNode("Question", "Answer", null, new Vector<String>());

        String test_question = "ChildQ";
        node.connect(test_question, "ChildA", new Vector<String>());

        String answer = node.findAnswer(test_question);
        Assert.assertEquals("ChildA", answer);
    }


    @Test
    public void findAnswer_empty() {
        StateNode node = new StateNode("Question", "Answer", null, new Vector<String>());

        String test_question = "ChildQ";
        node.connect(test_question, "ChildA", new Vector<String>());

        String answer = node.findAnswer("Random Question");
        Assert.assertEquals("", answer);
    }


    @Test
    public void parseJsonObject_exceptionOnNonValidJSON() {
        // TODO
    }


    @Test
    public void parseJsonObject_correctRecurse() {

        String jsonString =
                "{\n" +
                "  \"userMessage\":\"/start\",\n" +
                "  \"botAnswer\":\"Greetings from JSON!\",\n" +
                "  \"keyboard\":[\n" +
                "    [\"A\", \"B\"],\n" +
                "    [\"C\", \"D\"]\n" +
                "  ],\n" +
                "  \"actions\": [\"One\", \"Two\", \"Three\"],\n" +
                "  \"nodes\":[\n" +
                "    {\n" +
                "      \"userMessage\":\"A\",\n" +
                "      \"botAnswer\":\"JSON Answer: A\",\n" +
                "      \"keyboard\":[],\n" +
                "      \"actions\": [\"One\", \"Two\", \"Three\"],\n" +
                "      \"nodes\":[\n" +
                "        {\n" +
                "          \"userMessage\":\"C\",\n" +
                "          \"botAnswer\":\"JSON Answer: C\",\n" +
                "          \"keyboard\":[],\n" +
                "          \"actions\": [\"One\", \"Two\", \"Three\"],\n" +
                "          \"nodes\":[]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"userMessage\":\"B\",\n" +
                "      \"botAnswer\":\"JSON Answer: B\",\n" +
                "      \"keyboard\":[\n" +
                "        [\"E\", \"F\"]\n" +
                "      ],\n" +
                "      \"actions\": [\"One\", \"Two\", \"Three\"],\n" +
                "      \"nodes\":[]\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        JSONObject json = new JSONObject(jsonString);
        StateNode start = new StateNode("", "", null, new Vector<String>());
        start.parseJsonObject(json);

        Assert.assertEquals("/start", start.question);
        Assert.assertEquals("Greetings from JSON!", start.answer);
        Assert.assertEquals(2, start.nodes.size());
        Assert.assertEquals("A", start.keyboard.get(0).get(0));
        Assert.assertEquals("B", start.keyboard.get(0).get(1));
        Assert.assertEquals("C", start.keyboard.get(1).get(0));
        Assert.assertEquals("D", start.keyboard.get(1).get(1));

        Assert.assertEquals(3, start.actions.size());
        Assert.assertEquals("One", start.actions.get(0));
        Assert.assertEquals("Two", start.actions.get(1));
        Assert.assertEquals("Three", start.actions.get(2));



        StateNode node_A = start.find("A");
        Assert.assertFalse(node_A == null);
        Assert.assertEquals("A", node_A.question);
        Assert.assertEquals("JSON Answer: A", node_A.answer);
        Assert.assertEquals(1, node_A.nodes.size());
        Assert.assertTrue(node_A.keyboard.isEmpty());



        StateNode node_C = node_A.find("C");
        Assert.assertFalse(node_C == null);
        Assert.assertEquals("C", node_C.question);
        Assert.assertEquals("JSON Answer: C", node_C.answer);
        Assert.assertEquals(0, node_C.nodes.size());
        Assert.assertTrue(node_C.keyboard.isEmpty());



        StateNode node_B = start.find("B");
        Assert.assertFalse(node_B == null);
        Assert.assertEquals("B", node_B.question);
        Assert.assertEquals("JSON Answer: B", node_B.answer);
        Assert.assertEquals(0, node_B.nodes.size());
        Assert.assertEquals(1, node_B.keyboard.size());
        Assert.assertEquals("E", node_B.keyboard.get(0).get(0));
        Assert.assertEquals("F", node_B.keyboard.get(0).get(1));
    }


    @Test
    public void test_DoubleArray() {

        String jsonString =
                "{" +
                "\"keyboard\":[\n" +
                "    [\"A\", \"B\"],\n" +
                "    [\"C\", \"D\"]\n" +
                "  ]" +
                "}";

        JSONObject json = new JSONObject(jsonString);
        JSONArray arr = json.getJSONArray("keyboard");

        Vector<Vector<String>> rows = new Vector<Vector<String>>();
        for(Object r : arr)
        {
            JSONArray buttons = (JSONArray)r;
            Vector<String> buttons_vector = new Vector<String>();
            for(Object b : buttons) {
                buttons_vector.add((String)b);
            }
            rows.add(buttons_vector);
        }

        Assert.assertEquals("A", rows.get(0).get(0));
        Assert.assertEquals("B", rows.get(0).get(1));
        Assert.assertEquals("C", rows.get(1).get(0));
        Assert.assertEquals("D", rows.get(1).get(1));
    }

}
