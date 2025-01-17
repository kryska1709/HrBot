package dev.madela.hr_bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage; // ... существующий код ...
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class HrTelegramBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "HRBot"; // username
    }

    @Override
    public String getBotToken() {
        return "7698136675:AAFTCOocLIjn3YX8PgtcGIphKjoJETLHqtE"; // токен бота
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            switch (messageText) {
                case "/start":
                    sendResponse(update.getMessage().getChatId(), "Добро пожаловать! Я Ваш HR-бот.");
                    break;
                case "/help":
                    sendResponse(update.getMessage().getChatId(), "Команды: /start - начать, /help - помощь.");
                    break;
                default:
                    sendResponse(update.getMessage().getChatId(), "Вы написали: " + messageText);
                    break;
            }
        }
    }

    // Метод для отправки ответа
    private void sendResponse(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message); // Отправка сообщения
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
