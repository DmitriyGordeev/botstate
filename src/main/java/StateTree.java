import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public StateNode connect(String question, String answer) {
        StateNode node = new StateNode(question, answer, this);
        nodes.add(node);
        return node;
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

    public StateNode parseJsonObject(JSONObject object) throws JSONException {

        question = object.getString("userMessage");
        answer = object.getString("botAnswer");

        JSONArray nodes_array = object.getJSONArray("nodes");
        for(Object node : nodes_array) {
            StateNode sn = new StateNode("", "", null);
            connect(sn.parseJsonObject((JSONObject) node));
        }

        return this;
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

    public StateTree(String helloMessage) {
        root = new StateNode("", "", null);
        start = root.connect("/start", helloMessage);
    }

    public void parseTreeJson(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        start.parseJsonObject(json);
    }

    public StateNode root;
    public StateNode start;
}
