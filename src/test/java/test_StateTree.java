import org.junit.Before;
import org.junit.Test;

public class test_StateTree {

    StateTree createTree() {

        StateTree tree = new StateTree("Hello there. How are ya?)");
        StateNode node_A = new StateNode("qA", "aA", tree.root);
        StateNode node_B = new StateNode("qB", "aB", tree.root);
        StateNode node_C = new StateNode("qC", "aC", tree.root);

        node_B.connect("qD", "aD");
        StateNode node_E =  new StateNode("qE", "aE", node_C);
        node_C.connect("qF", "aF");
        node_E.connect("qG", "aG");

        return tree;
    }


    @Test
    public void deep_correctAnswer() {

        StateTree tree = createTree();


    }




}
