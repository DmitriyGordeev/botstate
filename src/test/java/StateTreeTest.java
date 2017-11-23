import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class StateTreeTest {

    @Test
    public void stateNode_constructor() {
        StateNode node = new StateNode("Value", null);
        Assert.assertEquals("Value", node.value);
        Assert.assertEquals(null, node.parent);
        Assert.assertTrue(node.nodes.isEmpty());
    }


    @Test
    public void stateNode_connect_ref_correctReferences() {
        StateNode node = new StateNode("Value", null);
        StateNode otherNode = new StateNode("child", null);
        node.connect(otherNode);

        Assert.assertEquals(1, node.nodes.size());
        Assert.assertEquals(node, otherNode.parent);
    }


    @Test
    public void stateNode_connect_value_correctReferences() {

        StateNode node = new StateNode("Value", null);
        node.connect("child");

        Assert.assertEquals(1, node.nodes.size());
        StateNode child = null;
        Iterator<StateNode> iterator = node.nodes.iterator();
        while(iterator.hasNext()) {
            child = iterator.next();
            if(child.value.equals("child"))
                break;
        }

         Assert.assertEquals(node, child.parent);
    }


    @Test
    public void stateNode_find_notNull() {
        StateNode node = new StateNode("Value", null);

        String test_value = "child";
        node.connect(test_value);

        StateNode found = node.find(test_value);
        Assert.assertTrue(found != null);
        Assert.assertEquals(test_value, found.value);
    }

    @Test
    public void stateNode_find_null() {
        StateNode node = new StateNode("Value", null);

        String test_value = "child";
        node.connect(test_value);

        StateNode found = node.find("random");
        Assert.assertTrue(found == null);
    }

}
