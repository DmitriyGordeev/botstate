import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class StateTreeTest {

    @Test
    public void stateNode_constructor() {
        StateNode node = new StateNode("Question", "Answer", null);
        Assert.assertEquals("Question", node.question);
        Assert.assertEquals(null, node.parent);
        Assert.assertTrue(node.nodes.isEmpty());
    }


    @Test
    public void stateNode_connect_ref() {
        StateNode node = new StateNode("Question", "Answer", null);
        StateNode otherNode = new StateNode("ChildQ", "ChildA", null);
        node.connect(otherNode);

        Assert.assertEquals(1, node.nodes.size());
        Assert.assertEquals(node, otherNode.parent);
    }


    @Test
    public void stateNode_connect_value() {

        StateNode node = new StateNode("Question", "Answer", null);
        node.connect("ChildQ", "ChildA");

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
    public void stateNode_find_notNull() {
        StateNode node = new StateNode("Question", "Answer", null);

        String test_value = "ChildQ";
        node.connect(test_value, "ChildA");

        StateNode found = node.find(test_value);
        Assert.assertTrue(found != null);
        Assert.assertEquals(test_value, found.question);
    }


    @Test
    public void stateNode_find_null() {
        StateNode node = new StateNode("Question", "Answer", null);

        String test_value = "ChildQ";
        node.connect(test_value, "ChildA");

        StateNode found = node.find("random");
        Assert.assertTrue(found == null);
    }

}
