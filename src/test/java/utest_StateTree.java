import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

public class utest_StateTree {

    StateTree createTree() {

        StateTree tree = new StateTree("Hello there. How are ya?)");

        StateNode node_A = tree.start.connect("qA", "aA", new Vector<>());
        StateNode node_B = tree.start.connect("qB", "aB", new Vector<>());
        StateNode node_C = tree.start.connect("qC", "aC", new Vector<>());

        node_B.connect("qD", "aD", new Vector<>());
        StateNode node_E =  node_C.connect("qE", "aE", new Vector<>());
        node_C.connect("qF", "aF", new Vector<>());
        node_E.connect("qG", "aG", new Vector<>());

        return tree;
    }


    @Test
    public void deep_correctAnswerState() {

        StateTree tree = createTree();
        String user_question = "qC";

        StateNode answer_state = tree.start.find(user_question);
        Assert.assertFalse(answer_state == null);
        Assert.assertEquals("aC", answer_state.answer);

        String another_question = "qF";
        StateNode another_answer = answer_state.find(another_question);
        Assert.assertFalse(another_answer == null);
        Assert.assertEquals("aF", another_answer.answer);

    }

    @Test
    public void parseTreeJson_valid() {

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


        StateTree tree = new StateTree("start message");
        tree.parseTreeJson(jsonString);

        Assert.assertEquals("Greetings from JSON!", tree.start.answer);
        Assert.assertEquals(2, tree.start.nodes.size());


        StateNode node_A = tree.start.find("A");
        Assert.assertFalse(node_A == null);
        Assert.assertEquals("A", node_A.question);
        Assert.assertEquals("JSON Answer: A", node_A.answer);
        Assert.assertEquals(1, node_A.nodes.size());

        StateNode node_C = node_A.find("C");
        Assert.assertFalse(node_C == null);
        Assert.assertEquals("C", node_C.question);
        Assert.assertEquals("JSON Answer: C", node_C.answer);
        Assert.assertEquals(0, node_C.nodes.size());
        Assert.assertEquals(3, node_C.actions.size());
        Assert.assertEquals("One", node_C.actions.get(0));
        Assert.assertEquals("Two", node_C.actions.get(1));
        Assert.assertEquals("Three", node_C.actions.get(2));

        StateNode node_B = tree.start.find("B");
        Assert.assertFalse(node_B == null);
        Assert.assertEquals("B", node_B.question);
        Assert.assertEquals("JSON Answer: B", node_B.answer);
        Assert.assertEquals(0, node_B.nodes.size());

    }
}
