import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class utest_StateTree {

    StateTree createTree() {

        StateTree tree = new StateTree("Hello there. How are ya?)");

        StateNode node_A = tree.start.connect("qA", "aA");
        StateNode node_B = tree.start.connect("qB", "aB");
        StateNode node_C = tree.start.connect("qC", "aC");

        node_B.connect("qD", "aD");
        StateNode node_E =  node_C.connect("qE", "aE");
        node_C.connect("qF", "aF");
        node_E.connect("qG", "aG");

        return tree;
    }


    @Test
    public void deep_correctAnswerState() {

        StateTree tree = createTree();
        String user_question = "qC";

        StateNode answer_state = tree.root.find(user_question);
        Assert.assertFalse(answer_state == null);
        Assert.assertEquals("aC", answer_state.answer);

        String another_question = "qF";
        StateNode another_answer = answer_state.find(another_question);
        Assert.assertFalse(another_answer == null);
        Assert.assertEquals("aF", another_answer.answer);


    }




}
