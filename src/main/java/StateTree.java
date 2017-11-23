import java.util.HashSet;

class StateNode {

    public StateNode() {
        nodes = new HashSet<StateNode>();
    }

    private HashSet<StateNode> nodes;
}

public class StateTree {

    public StateTree() {

    }

    public StateNode root;
}
