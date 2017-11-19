package net.zarea.common.servlet;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * アクション共通クラス
 * @author zeku
 *
 */
public class TechActionLoader {

    private Properties actionProp;

    public TechActionLoader() {

        if (actionProp == null) {
            actionProp = new Properties();
            try (FileInputStream fis = new FileInputStream("D:/action.properties");) {
                actionProp.load(fis);
            } catch (Exception e) {
                // TODO エラー処理
                e.printStackTrace();
            }
        }
    }

    String getAction(String key) {
        return actionProp.get(key).toString();
    }
}
