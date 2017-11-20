package net.zarea.common.servlet;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * アクション共通クラス
 * @author zeku
 *
 */
public class TechActionLoader {

    /** アクションプロパティ */
    private Properties actionProp = null;

    /**
     * コンストラクタ
     */
    public TechActionLoader() {

        // アクションプロパティがnullの場合
        if (actionProp == null) {

            // アクションプロパティを生成する。
            actionProp = new Properties();

            // プロパティファイルをStreamに読み込む。
            try (FileInputStream fis = new FileInputStream("D:/action.properties");) {

                // アクションプロパティにロードする。
                actionProp.load(fis);

            // 予期せぬ例外が発生した場合
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * アクションを取得する。
     * @param key
     * @return
     */
    String getAction(String key) {
        return actionProp.get(key).toString();
    }
}
