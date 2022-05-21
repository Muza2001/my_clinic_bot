package academic.com.bot;

import academic.com.dto.request.TelegramContact;
import academic.com.model.TelegramUser;
import academic.com.repository.TelegramUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class BotService {

    private final TelegramUserRepository telegramUserRepository;


    public SendMessage startMenu(Message message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        String[] text = {"Tashxislarim", "Qabulga yozildim"};
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow keyboardRow;
        KeyboardButton keyboardButton;

            for (String s : text) {
                keyboardButton = new KeyboardButton();
                keyboardRow = new KeyboardRow();
                keyboardButton.setText(s);
                keyboardRow.add(keyboardButton);
                rows.add(keyboardRow);
            }
        replyKeyboardMarkup.setKeyboard(rows);
        TelegramUser contact = new TelegramUser(
                message.getFrom().getFirstName(),
                message.getFrom().getLastName(),
                message.getFrom().getUserName(),
                null,
                message.getChatId(),
                false);
        if (!telegramUserRepository.existsByChatId(message.getChatId()))
                 telegramUserRepository.save(contact);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setParseMode(ParseMode.MARKDOWN);
            sendMessage.setText("Asalomu alekum Clinicamizga hushkelibsiz !!!");
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            return sendMessage;
        }

    public SendMessage diagnose(Message message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setRequestContact(true);
        keyboardButton.setText("Telefon");
        keyboardRow.add(keyboardButton);
        rows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rows);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setText("Raqamni jo'nating");
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public SendDocument findByPhoneNumber(Message message) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setDocument(new InputFile(new File("http://localhost8080:/api/v1/file_upload/get/3")));
        sendDocument.setChatId(message.getChatId().toString());
        return sendDocument;
    }
}
