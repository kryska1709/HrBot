package dev.madela.hr_bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
            Long chatId = update.getMessage().getChatId(); // Получаем chatId

            switch (messageText) {
                case "/start":
                    sendResponse(chatId, "Добро пожаловать! Я Ваш HR-бот.");
                    break;
                case "/help":
                    sendResponse(chatId, "Команды: /start - начать, /help - помощь.");
                    break;
                default:
                    sendResponse(chatId, "Вы написали: " + messageText);
                    break;
            }
        }
    }

    public void sendResponse(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId)); // Преобразование Long в String
        message.setText(text);
        try {
            execute(message); // Отправка сообщения
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

// Новый контроллер для обработки запросов
@RestController
class BotController {

    private final HrTelegramBot hrTelegramBot;

    @Autowired
    public BotController(HrTelegramBot hrTelegramBot) {
        this.hrTelegramBot = hrTelegramBot;
    }

    @GetMapping("/sendMessage")
    public String sendMessage(Long chatId, String text) {
        hrTelegramBot.sendResponse(chatId, text);
        return "Сообщение отправлено!";
    }
}
