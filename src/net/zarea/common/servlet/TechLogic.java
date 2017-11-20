package net.zarea.common.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

/**
 * ロジック共通クラス
 * @author zeku
 *
 */
public abstract class TechLogic {

    /** コネクション */
    protected Connection con = null;

    /**
     * DBに接続する。
     * @throws Exception
     */
    public void dbConnect() throws Exception {

        try {
            // ドライバをロードする。
            Class.forName("com.mysql.jdbc.Driver");

            // DB接続用設定を行う。
            String url = "jdbc:mysql://localhost:3306/wagbydb?useUnicode=true&relaxautoCommit=true";
            String user = "";
            String password = "";

            // DB接続を行う。
            this.con = DriverManager.getConnection(url, user, password);

        // 予期せぬ例外が発生した場合
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * DBから切断する。
     * @throws Exception
     */
    public void dbClose() throws Exception {

        try {
            // コネクションがnullではない場合
            if (this.con != null) {
                // コネクションをクローズする。
                this.con.close();
                this.con = null;
            }

        // 予期せぬ例外が発生した場合
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * DBから取得する。
     * @param sql
     * @return
     * @throws Exception
     */
    public ResultSet execute(String sql) throws Exception {

        // ステートメントを宣言する。
        Statement stm = null;
        // リザルトセットを宣言する。
        ResultSet rs = null;

        try {
            // ステートメントを取得する。
            stm = this.con.createStatement();

            // SQLを実行する。
            rs = stm.executeQuery(sql);
            return rs;

        // 予期せぬ例外が発生した場合
        } catch (Exception e) {
            throw e;
        } finally {
            // TODO ここでstmはクローズしてしまいたいが、クローズするとResultSetの中身が消える、はず。
            // TODO ResultSet を作成する時に使用していた Statement に別の問い合わせを作ると、その時点で開いていた ResultSet インスタンスは自動的に閉じられます。
            // TODO なのでDB結果は別のオブジェクトに保持しておきたい。
        }
    }

    /**
     * 業務ロジックを実行する。
     * @param techRequestFromJspMap
     * @param techRequestToJspMap
     * @return
     * @throws Exception
     */
    abstract String doProcess(Map<String, String> techRequestFromJspMap, Map<String, String> techRequestToJspMap) throws Exception;
}
