import org.json.JSONException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Vector;


public class SimpleBot extends TelegramLongPollingBot {

    private StateTree stateTree;
    private StateNode currentState;

    public SimpleBot() {
        setupStateTree();
        currentState = stateTree.root;
    }

    public SimpleBot(String treeJsonString) throws JSONException {
        stateTree = new StateTree("");
        stateTree.parseTreeJson(treeJsonString);
        currentState = stateTree.root;
    }

    public void setupStateTree() {
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

        return answer;
    }

    private ReplyKeyboardMarkup setupKeyboard() {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // get keyboard info from current answer state:
        ArrayList<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        if(currentState != null) {
            keyboard = createKeyboard(currentState.keyboard);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public ArrayList<KeyboardRow> createKeyboard(Vector<Vector<String>> keyboardContent) {

        ArrayList<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        for(Vector<String> row : keyboardContent) {
            KeyboardRow keyboardRow = new KeyboardRow();
            for(String button : row) {
                keyboardRow.add(button);
            }
            keyboard.add(keyboardRow);
        }

        return keyboard;
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

            SendMessage message = new SendMessage();
            message.setChatId(chat_id);
            message.enableMarkdown(true);

            ReplyKeyboardMarkup keyboard = setupKeyboard();


            message.setReplyMarkup(keyboard);
            message.setText(answer);
            try {
                sendMessage(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }



            // some bullshit for testing
            stateTree.start.answer = "Для просомотра всех команд, наберите /help";



            // TODO: think about this state returning
            // if no subnodes found return to start state:
            if(currentState.nodes.isEmpty()) {
                currentState = stateTree.start;
                message = new SendMessage();
                message.setChatId(chat_id);
                message.enableMarkdown(true);

                keyboard = setupKeyboard();
                message.setReplyMarkup(keyboard);
                message.setText(currentState.answer);
                try {
                    sendMessage(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
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