import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

class StateNode implements Comparable<StateNode> {

    public StateNode(String value, StateNode parent) {
        nodes = new TreeSet<StateNode>();
        this.value = value;
        this.parent = parent;
    }

    public void connect(StateNode node) {
        nodes.add(node);
        node.parent = this;
    }

    public void connect(String value) {
        StateNode node = new StateNode(value, this);
        nodes.add(node);
    }

    public StateNode find(String value) {
        Iterator<StateNode> iterator = this.nodes.iterator();
        while(iterator.hasNext()) {
            StateNode node = iterator.next();
            if(node.value.equalsIgnoreCase(value)) {
                return node;
            }
        }
        return null;
    }

    public int compareTo(StateNode other) {
        return this.value.compareToIgnoreCase(other.value);
    }

    public String value;
    public TreeSet<StateNode> nodes;
    public StateNode parent;
}

public class StateTree {

    public StateTree() {

    }

    public StateNode root;
}
