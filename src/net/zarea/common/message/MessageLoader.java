package net.zarea.common.message;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * メッセージローダークラス
 * @author zeku
 *
 */
public class MessageLoader {

    /** メッセージプロパティ */
    private Properties msgPorp = null;

    /**
     * コンストラクタ
     */
    public MessageLoader() {

        // メッセージプロパティがnullの場合
        if (msgPorp == null) {

            // メッセージプロパティを生成する。
            msgPorp = new Properties();

            // プロパティファイルをStreamに読み込む。
            try (FileInputStream fis = new FileInputStream("D:/message.properties");) {

                // メッセージプロパティにロードする。
                msgPorp.load(fis);

            // 予期せぬ例外が発生した場合
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * メッセージを取得する。
     * @param messageId
     * @return
     */
    public String getMessage(String messageId) {
        return msgPorp.get(messageId).toString();
    }
}
