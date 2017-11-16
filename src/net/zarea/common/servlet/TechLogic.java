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

    protected Connection con = null;

    public void dbConnect() throws Exception {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/wagbydb?useUnicode=true&relaxautoCommit=true";
            String user = "";
            String password = "";
            this.con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dbClose() throws Exception {

        try {
            if (this.con != null) {
                this.con.close();
                this.con = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet execute(String sql) throws Exception {

        Statement stm = null;
        ResultSet rs = null;

        try {
            stm = this.con.createStatement();
            rs = stm.executeQuery(sql);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // TODO ここでstmはクローズしてしまいたいが、クローズするとResultSetの中身が消える、はず。
            // TODO ResultSet を作成する時に使用していた Statement に別の問い合わせを作ると、その時点で開いていた ResultSet インスタンスは自動的に閉じられます。
            // TODO なのでDB結果は別のオブジェクトに保持しておきたい。
        }
        return rs;
    }

    abstract String doProcess(Map<String, String> techRequestFromJspMap, Map<String, String> techRequestToJspMap) throws Exception;

}
