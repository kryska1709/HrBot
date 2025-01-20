package dev.madela.hr_bot.Telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class HrTelegramBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "Madela_HRBot"; // username
    }

    @Override
    public String getBotToken() {
        return "7087269656:AAH7h68xN7lNOR4Q8DTpq6tVQE7GC6BxFjg"; // токен бота
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            // Логирование chatId
            System.out.println("Chat ID: " + chatId);

            switch (messageText) {
                case "/start":
                    StartCommandRecieved(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    HelpCommandRecieved(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(chatId, "такой команды нет");
            }
        }
    }


    private void StartCommandRecieved(long chatId, String name) {
        String answer = "Здравствуйте, " + name + ", Я HRBot!";
        sendMessage(chatId, answer);
    }

    private void HelpCommandRecieved(long chatId, String name) {
        String answer = "Команды: /start - начать, /help - помощь.";
        sendMessage(chatId, answer);
    }

    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(String chatId, String message) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        execute(sendMessage);
    }
}
