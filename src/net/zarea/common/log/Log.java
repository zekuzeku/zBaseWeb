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

    private Logger logger = null;
    private String funcId = null;
    private String userId = null;
    private String threadId = null;
    private String sessionId = null;
    private static final String VERTICAL_BAR = " | ";
    private static final String BRACKETS_LEFT = "[";
    private static final String BRACKETS_RIGHT = "]";

    private MessageLoader messageLoader = new MessageLoader();

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Log() {
        logger = LogManager.getLogger("WEB");
    }

    private String getLogLevel(String messageId) {
        if (messageId == null || "".equals(messageId)) {
            return "DEBU";
        }

        String messageType = messageId.substring(0, 1);

        if ("I".equals(messageType)) {
            return "INFO";
        } else if ("W".equals(messageType)) {
            return "WARN";
        } else if ("E".equals(messageType)) {
            return "ERRO";
        } else {
            return "DEBU";
        }
    }

    private String getLogMsg(String messageId, Object[] param) {

        String msg = messageLoader.getMessage(messageId);

        if (param != null && param.length != 0) {
            msg = MessageFormat.format(msg, param);
        }

        String ret = BRACKETS_LEFT + threadId + BRACKETS_RIGHT + VERTICAL_BAR
                + BRACKETS_LEFT + sessionId + BRACKETS_RIGHT + VERTICAL_BAR
                + BRACKETS_LEFT + funcId + BRACKETS_RIGHT + VERTICAL_BAR
                + BRACKETS_LEFT + userId + BRACKETS_RIGHT + VERTICAL_BAR
                + messageId + VERTICAL_BAR
                + msg;

        return ret;
    }

    public void writeLog(String messageId) {
        writeLog(messageId, null);
    }

    public void writeLog(String messageId, Object[] param) {

        String logMsg = getLogMsg(messageId, param);

        String logLevel = getLogLevel(messageId);

        if ("INFO".equals(logLevel)) {
            logger.info(logMsg);
        } else if ("WARN".equals(logLevel)) {
            logger.warn(logMsg);
        } else if ("ERROR".equals(logLevel)) {
            logger.error(logMsg);
        } else {
            logger.debug(logMsg);
        }
    }
}
