package net.zarea.common.log;

import java.text.MessageFormat;

import net.zarea.common.message.MessageLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ログ出力クラス
 * @author zeku
 *
 */
public class Log {

    /** ロガー */
    private Logger logger = null;
    /** メソッドID */
    private String funcId = null;
    /** ユーザID */
    private String userId = null;
    /** スレッドID */
    private String threadId = null;
    /** セッションID */
    private String sessionId = null;

    /** パイプ */
    private static final String VERTICAL_BAR = " | ";
    /** 左括弧 */
    private static final String BRACKETS_LEFT = "[";
    /** 右括弧 */
    private static final String BRACKETS_RIGHT = "]";
    /** メッセージローダー */
    private static MessageLoader MESSAGE_LOADER = new MessageLoader();

    /**
     * ロガーを設定する。
     * @param logger
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * メソッドIDを設定する。
     * @param funcId
     */
    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    /**
     * ユーザIDを設定する。
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * スレッドIDを設定する。
     * @param threadId
     */
    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    /**
     * セッションIDを設定する。
     * @param sessionId
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * コンストラクタ
     */
    public Log() {
        logger = LogManager.getLogger("WEB");
    }

    /**
     * ログレベルを取得する。
     * @param messageId
     * @return
     */
    private String getLogLevel(String messageId) {

        // メッセージIDがnullの場合、またはメッセージIDが空欄の場合
        if (messageId == null || "".equals(messageId)) {
            return "DEBU";
        }

        // メッセージ種別(メッセージIDの先頭1桁目)を取得する。
        String messageType = messageId.substring(0, 1);

        // メッセージ種別が「I」の場合
        if ("I".equals(messageType)) {
            return "INFO";
        // メッセージ種別が「W」の場合
        } else if ("W".equals(messageType)) {
            return "WARN";
        // メッセージ種別が「E」の場合
        } else if ("E".equals(messageType)) {
            return "ERRO";
        // 上記以外の場合
        } else {
            return "DEBU";
        }
    }

    /**
     * メッセージを取得する。
     * @param messageId
     * @param param
     * @return
     */
    private String getLogMsg(String messageId, Object[] param) {

        // メッセージを取得する。
        String msg = MESSAGE_LOADER.getMessage(messageId);

        // パラメータがnullではない場合、かつパラメータの長さが0ではない場合
        if (param != null && param.length != 0) {

            // {n}(nは半角数字)の置換項目を置換する。
            msg = MessageFormat.format(msg, param);
        }

        // メッセージを生成する。
        String ret = BRACKETS_LEFT + threadId + BRACKETS_RIGHT + VERTICAL_BAR
                + BRACKETS_LEFT + sessionId + BRACKETS_RIGHT + VERTICAL_BAR
                + BRACKETS_LEFT + funcId + BRACKETS_RIGHT + VERTICAL_BAR
                + BRACKETS_LEFT + userId + BRACKETS_RIGHT + VERTICAL_BAR
                + messageId + VERTICAL_BAR
                + msg;

        return ret;
    }

    /**
     * ログを出力する。
     * @param messageId
     */
    public void writeLog(String messageId) {
        writeLog(messageId, null);
    }

    /**
     * ログを出力する。
     * @param messageId
     * @param param
     */
    public void writeLog(String messageId, Object[] param) {

        // ログ出力内容を取得する。
        String logMsg = getLogMsg(messageId, param);

        // ログレベルを取得する。
        String logLevel = getLogLevel(messageId);

        // ログレベルが「INFO」の場合
        if ("INFO".equals(logLevel)) {
            logger.info(logMsg);
        // ログレベルが「WARN」の場合
        } else if ("WARN".equals(logLevel)) {
            logger.warn(logMsg);
        // ログレベルが「ERROR」の場合
        } else if ("ERROR".equals(logLevel)) {
            logger.error(logMsg);
        // 上記以外の場合
        } else {
            logger.debug(logMsg);
        }
    }
}
