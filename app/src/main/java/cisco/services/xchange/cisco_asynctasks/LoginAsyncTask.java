package cisco.services.xchange.cisco_asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;
import org.json.JSONObject;
import java.util.HashMap;
import cisco.services.xchange.AppDefine;
import cisco.services.xchange.CiscoAsyncTask;
import cisco.services.xchange.USER_TYPE;
import cisco.services.xchange.cisco_dao.Partner;
import cisco.services.xchange.cisco_dao.Session;
import cisco.services.xchange.utils.ExceptionHandler;
import cisco.services.xchange.utils.NetworkUtil;

/**
 * Created by yogi on 14/03/16.
 */
public class LoginAsyncTask extends CiscoAsyncTask{

    private final ProgressDialog mDialog;

    public LoginAsyncTask(Context context, Message msg) {
        super(context, msg);
        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Please wait !!!");
        mDialog.show();
    }

    @Override
    protected Session doInBackground(HashMap<String, Object>... params) {
        try {
            HashMap<String, Object> inputMap = params[0];
            inputMap.put(AppDefine.GRANT_TYPE, "password");
            NetworkUtil util = new NetworkUtil();
            String responseMsg = util.getPostResponse(AppDefine.LOGIN_URL, inputMap);
            if (responseMsg != null) {
                JSONObject responseJson = new JSONObject(responseMsg);
                Session session = new Session();
                session.setAccessToken(responseJson.getString("access_token"));
                session.setTokenType(responseJson.getString("token_type"));
                session.setExpiresIn(responseJson.getInt("expires_in"));
                session.setRefreshToken(responseJson.getString("refresh_token"));
                session.setEmail(responseJson.getString("Email"));
                if (responseJson.getString("UserType").equals("P")) {
                    session.setUserType(USER_TYPE.PARTNER);
                } else {
                    session.setUserType(USER_TYPE.NOMINEE);
                }
                session.setIssueDate(responseJson.getString(".issued"));
                session.setExpiryDate(responseJson.getString(".expires"));

                return session;
            }
        } catch (Exception ex) {
            ExceptionHandler.handle(ex);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        mDialog.dismiss();
        if (result != null){
            mMessage.arg1 = NetworkUtil.RESULT_OK;
            mMessage.arg2 = NetworkUtil.SUCCESS;
            mMessage.obj= result;
        } else{
            mMessage.arg1 = NetworkUtil.RESULT_FAIL;
            mMessage.obj= result;
        }
        mMessage.sendToTarget();
    }
}
