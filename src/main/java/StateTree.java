import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

class StateNode implements Comparable<StateNode> {

    public StateNode(String question, String answer, StateNode parent) {
        nodes = new TreeSet<StateNode>();
        this.question = question;
        this.answer = answer;
        this.parent = parent;
    }

    public void connect(StateNode node) {
        nodes.add(node);
        node.parent = this;
    }

    public void connect(String question, String answer) {
        StateNode node = new StateNode(question, answer, this);
        nodes.add(node);
    }

    public StateNode find(String question) {
        Iterator<StateNode> iterator = this.nodes.iterator();
        while(iterator.hasNext()) {
            StateNode node = iterator.next();
            if(node.question.equalsIgnoreCase(question)) {
                return node;
            }
        }
        return null;
    }

    public String findAnswer(String question) {
        StateNode node = this.find(question);
        if(node == null) {
            return "";
        }
        return node.answer;
    }

    public int compareTo(StateNode other) {
        return this.question.compareToIgnoreCase(other.question);
    }

    public String question;
    public String answer;
    public TreeSet<StateNode> nodes;
    public StateNode parent;
}

public class StateTree {

    public StateTree() {
    }

    public StateNode root;
}
