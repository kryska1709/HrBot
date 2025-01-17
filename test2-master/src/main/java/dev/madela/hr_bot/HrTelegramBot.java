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
            // Обработать сообщение
            switch (messageText) {
                case "/start":
                    sendResponse(update.getMessage().getChatId(), "Добро пожаловать в HRBot!");
                    break;
                case "/help":
                    sendResponse(update.getMessage().getChatId(), "Как я могу помочь вам?");
                    break;
                default:
                    sendResponse(update.getMessage().getChatId(), "Неизвестная команда. Попробуйте /help.");
                    break;
            }
        }
    }

    private void sendResponse(Long chatId, String text) {
        // Создаем объект SendMessage
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message); // Отправляем сообщение
        } catch (TelegramApiException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }
}
