import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

class StateNode implements Comparable<StateNode> {

    public StateNode(String question, String answer, StateNode parent, Vector<String> actions) {
        nodes = new TreeSet<StateNode>();
        keyboard = new Vector<Vector<String>>();
        this.question = question;
        this.answer = answer;
        this.parent = parent;
        this.actions = actions;
    }

    public void connect(StateNode node) {
        nodes.add(node);
        node.parent = this;
    }

    public StateNode connect(String question, String answer, Vector<String> actions) {
        StateNode node = new StateNode(question, answer, this, actions);
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

        JSONArray arr = object.getJSONArray("keyboard");
        for(Object r : arr)
        {
            JSONArray buttons = (JSONArray)r;
            Vector<String> buttons_vector = new Vector<String>();
            for(Object b : buttons) {
                buttons_vector.add((String)b);
            }
            keyboard.add(buttons_vector);
        }

        JSONArray actions_array = object.getJSONArray("actions");
        for(Object a : actions_array) {
            actions.add((String)a);
        }

        JSONArray nodes_array = object.getJSONArray("nodes");
        for(Object node : nodes_array) {
            StateNode sn = new StateNode("", "", null, new Vector<String>());
            connect(sn.parseJsonObject((JSONObject) node));
        }

        return this;
    }

    public int compareTo(StateNode other) {
        return this.question.compareToIgnoreCase(other.question);
    }

    public String question;
    public String answer;
    public Vector<Vector<String>> keyboard;
    public TreeSet<StateNode> nodes;
    public StateNode parent;

    public Vector<String>  actions;
}

public class StateTree {

    public StateTree(String helloMessage) {
        root = new StateNode("", "", null, new Vector<String>());
        start = root.connect("/start", helloMessage, new Vector<String>());
    }

    public void parseTreeJson(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        start.parseJsonObject(json);
    }

    public StateNode root;
    public StateNode start;
}
