import org.json.JSONException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;


public class Main {

    public static void main(String[] args) {

        if(args.length != 1) {
            System.out.println("usage: needed single argument <path to tree json>");
            return;
        }

        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        String treeJson = "";
        try {
            treeJson = Fileio.readfile(args[0]);
        }
        catch(IOException e) {
            e.printStackTrace();
            return;
        }

        // Register our bot
        try {
            botsApi.registerBot(new SimpleBot(treeJson));
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

    }

}
