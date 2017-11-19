package net.zarea.common.message;

import java.io.FileInputStream;
import java.util.Properties;

public class MessageLoader {

    private Properties msgPorp;

    public MessageLoader() {

        if (msgPorp == null) {
            msgPorp = new Properties();
            try (FileInputStream fis = new FileInputStream("D:/message.properties");) {
                msgPorp.load(fis);
            } catch (Exception e) {
                // TODO エラー処理
                e.printStackTrace();
            }
        }
    }

    public String getMessage(String messageId) {
        return msgPorp.get(messageId).toString();
    }
}
