import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class utest_StateNode {

    @Test
    public void constructor() {
        StateNode node = new StateNode("Question", "Answer", null);
        Assert.assertEquals("Question", node.question);
        Assert.assertEquals(null, node.parent);
        Assert.assertTrue(node.nodes.isEmpty());
    }


    @Test
    public void connect_ref() {
        StateNode node = new StateNode("Question", "Answer", null);
        StateNode otherNode = new StateNode("ChildQ", "ChildA", null);
        node.connect(otherNode);

        Assert.assertEquals(1, node.nodes.size());
        Assert.assertEquals(node, otherNode.parent);
    }


    @Test
    public void connect_value() {

        StateNode node = new StateNode("Question", "Answer", null);
        StateNode childNode = node.connect("ChildQ", "ChildA");

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
        StateNode node = new StateNode("Question", "Answer", null);

        String test_value = "ChildQ";
        node.connect(test_value, "ChildA");

        StateNode found = node.find(test_value);
        Assert.assertTrue(found != null);
        Assert.assertEquals(test_value, found.question);
    }


    @Test
    public void find_null() {
        StateNode node = new StateNode("Question", "Answer", null);

        String test_value = "ChildQ";
        node.connect(test_value, "ChildA");

        StateNode found = node.find("random");
        Assert.assertTrue(found == null);
    }


    @Test
    public void findAnswer_notEmpty() {
        StateNode node = new StateNode("Question", "Answer", null);

        String test_question = "ChildQ";
        node.connect(test_question, "ChildA");

        String answer = node.findAnswer(test_question);
        Assert.assertEquals("ChildA", answer);
    }


    @Test
    public void findAnswer_empty() {
        StateNode node = new StateNode("Question", "Answer", null);

        String test_question = "ChildQ";
        node.connect(test_question, "ChildA");

        String answer = node.findAnswer("Random Question");
        Assert.assertEquals("", answer);
    }


    @Test
    public void parseJsonObject_exceptionOnNonValidJSON() {
        // TODO
    }
    
    @Test
    public void parseJsonObject_correctRecursivity() {
        
        String jsonString =
                "{\n" +
                "  \"userMessage\":\"/start\",\n" +
                "  \"botAnswer\":\"Greetings from JSON!\",\n" +
                "  \"nodes\":[\n" +
                "    {\n" +
                "      \"userMessage\":\"A\",\n" +
                "      \"botAnswer\":\"JSON Answer: A\",\n" +
                "      \"nodes\":[]\n" +
                "    },\n" +
                "    {\n" +
                "      \"userMessage\":\"B\",\n" +
                "      \"botAnswer\":\"JSON Answer: B\",\n" +
                "      \"nodes\":[]\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        JSONObject json = new JSONObject(jsonString);
        StateNode start = new StateNode("", "", null);
        start.parseJsonObject(json);

        Assert.assertEquals("/start", start.question);
        Assert.assertEquals("Greetings from JSON!", start.answer);
        Assert.assertEquals(2, start.nodes.size());

        StateNode node_A = start.find("A");
        Assert.assertFalse(node_A == null);
        Assert.assertEquals("A", node_A.question);
        Assert.assertEquals("JSON Answer: A", node_A.answer);
        Assert.assertEquals(0, node_A.nodes.size());

        StateNode node_B = start.find("B");
        Assert.assertFalse(node_B == null);
        Assert.assertEquals("B", node_B.question);
        Assert.assertEquals("JSON Answer: B", node_B.answer);
        Assert.assertEquals(0, node_B.nodes.size());

    }

}
