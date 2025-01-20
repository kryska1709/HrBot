package dev.madela.hr_bot.Telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RestController
public class NotificationController {

    @Autowired
    private HrTelegramBot hrTelegramBot;

    @GetMapping("/sendNotification")
    public String sendNotification(@RequestParam String chatId, @RequestParam String message) {
        try {
            hrTelegramBot.sendNotification(chatId, message);
            return "Notification sent successfully!";
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return "Failed to send notification: " + e.getMessage();
        }
    }
}

