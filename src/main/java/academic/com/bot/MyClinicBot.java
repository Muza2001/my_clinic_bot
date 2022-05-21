package academic.com.bot;

import academic.com.model.Employee;
import academic.com.repository.EmployeeRepository;
import academic.com.service.AcceptanceService;
import academic.com.service.AttachmentService;
import academic.com.service.Impl.AcceptanceServiceImpl;
import academic.com.service.Impl.AttachmentServiceImpl;
import academic.com.service.Impl.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyClinicBot extends TelegramLongPollingBot {


    private BotService botService;

    @Autowired
    public MyClinicBot(BotService botService) {
        this.botService = botService;
    }

    @Override
    public void onUpdateReceived(Update update) {
            if(update.hasMessage()){

                Message message = update.getMessage();

                if (message.hasText()) {
                    String text = message.getText();

                    if (text.equals("/start")){
                        try {
                            execute(botService.startMenu(message));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (text.equals("Tashxislarim")) {
                        try {
                            execute(botService.diagnose(message));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (text.equals("Qabulga yozildim")){

                    }
                }
                if (message.hasContact()){
                    try {
                        execute(botService.findByPhoneNumber(message));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }

            }
    }


    @Override
    public String getBotUsername() {
        return "myclinicbotuz_bot";
    }

    @Override
    public String getBotToken() {
        return "5353514671:AAHBVtMMTEsJO8gfIZODu7XdeAl9gZjGh64";
    }
}
