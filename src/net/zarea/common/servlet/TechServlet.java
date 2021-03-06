package net.zarea.common.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zarea.common.log.Log;


/**
 * サーブレット共通クラス
 * @author zeku
 *
 */
@WebServlet("/TechServlet")
public class TechServlet extends HttpServlet {

    /** シリアライズID */
    private static final long serialVersionUID = 1L;

    /** アクションローダー */
    private static TechActionLoader ACTION_LOADER = new TechActionLoader();


    /**
     * doGet
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * doPost
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Log log = new Log();

        log.setThreadId(String.valueOf(Thread.currentThread().getId()));
        log.setSessionId(request.getSession().getId());
        log.setUserId("TESTUSR");
        log.setFuncId("TESTFUNC");

        log.writeLog("D0001", new String[]{"aaaa", "bbbb"});
        log.writeLog("D0001");

        // アクションIDを取得する。
        String actionId = request.getParameter("actionId");

        // アクションIDがNULLではない場合
        if (actionId != null) {

            // TODO 同名リクエストも保持できるようにする。
            // TODO ファイルStreamも保持できるようにする。
            // ロジック用のオブジェクトに詰め替える。(同名リクエストは最初のValue値を利用する。)
            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> techRequestFromJspMap = new HashMap<String, String>();
            Map<String, String> techRequestToJspMap = new HashMap<String, String>();
            for (String key : requestMap.keySet()) {
                techRequestFromJspMap.put(key, ((String[])requestMap.get(key))[0]);
            }

            // ロジックを取得する。
            String logic = ACTION_LOADER.getAction(actionId);
            try {
                // ロジックのインスタンスを生成する。
                TechLogic tl = (TechLogic)Class.forName(logic).newInstance();

                // DBの接続を行う。
//                tl.dbConnect();

                // ロジックを実行する。
                String nextJsp = tl.doProcess(techRequestFromJspMap, techRequestToJspMap);

                // ロジックの実行結果をJSPに返す。
                for (String key : techRequestToJspMap.keySet()) {
                    request.setAttribute(key, techRequestToJspMap.get(key).toString());
                }

                // DBの切断を行う。
//                tl.dbClose();

                // 遷移先を設定する。
                RequestDispatcher rd = request.getRequestDispatcher(nextJsp);
                rd.forward(request, response);

            } catch (Exception e) {
                // TODO エラーJSPへ遷移
                e.printStackTrace();
            }
        }
    }

}