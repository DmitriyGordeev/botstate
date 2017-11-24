import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class SimpleBot extends TelegramLongPollingBot {

    private StateTree stateTree;
    private StateNode currentState;

    public SimpleBot() {
        exp_createTree_2();
        currentState = stateTree.root;
    }

    public SimpleBot(String treeJsonString) throws JSONException {
        stateTree = new StateTree("");
        stateTree.parseTreeJson(treeJsonString);
        currentState = stateTree.root;
    }

    public void exp_createTree() {
        stateTree = new StateTree("Hello there. How are ya?)");

        StateNode node_A = stateTree.start.connect("qA", "aA");
        StateNode node_B = stateTree.start.connect("qB", "aB");
        StateNode node_C = stateTree.start.connect("qC", "aC");

        node_B.connect("qD", "aD");
        StateNode node_E =  node_C.connect("qE", "aE");
        node_C.connect("qF", "aF");
        node_E.connect("qG", "aG");
    }

    public void exp_createTree_2() {
        stateTree = new StateTree("This is test tree! Be careful)");

        StateNode node_A = stateTree.start.connect("Сколько времени", "Ваще хз");
        StateNode node_B = stateTree.start.connect("Как дела?", "Норм, у тебя?");
        StateNode node_C = stateTree.start.connect("qC", "aC");

        node_B.connect("Хорошо", "Зачет!");
        StateNode node_E =  node_C.connect("qE", "aE");
        node_C.connect("qF", "aF");
        node_E.connect("qG", "aG");
    }

    public String traversing(String user_message) {

        String answer = "";
        StateNode nextState = currentState.find(user_message);
        if(nextState != null) {
            currentState = nextState;
            answer = currentState.answer;
        }
        else {
            answer = currentState.answer;
        }

        // reset state if no children:
        if(currentState.nodes.isEmpty()) {
            currentState = stateTree.start;
        }

        return answer;
    }



    /* ----------------------------------------------------------------------------- */
    /* default telegram stuff: */

    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {

            String user_message = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();


            // define the answer due to the stateTree:
            String answer = traversing(user_message);


            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(answer);
            try {
                sendMessage(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    public String getBotUsername() {
        return "dimas_test_bot";
    }

    @Override
    public String getBotToken() {
        return "415598199:AAHFEgk3tjCeQmZ7YDBmH90XC0PYN0PyuSw";
    }

}