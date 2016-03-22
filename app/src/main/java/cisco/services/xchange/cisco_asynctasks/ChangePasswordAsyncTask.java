package cisco.services.xchange.cisco_asynctasks;

import android.app.ProgressDialog;
import android.os.Message;
import android.util.Log;
import org.json.JSONObject;
import java.util.HashMap;
import cisco.services.xchange.AppDefine;
import cisco.services.xchange.CiscoActivity;
import cisco.services.xchange.CiscoAsyncTask;
import cisco.services.xchange.cisco_dao.Session;
import cisco.services.xchange.utils.ExceptionHandler;
import cisco.services.xchange.utils.NetworkUtil;

/**
 * Created by yogi on 18/03/16.
 */
public class ChangePasswordAsyncTask extends CiscoAsyncTask {

    private Session session;
    private final ProgressDialog mDialog;

    public ChangePasswordAsyncTask(CiscoActivity context, Message msg) {
        super(context, msg);
        session = context.getSession();
        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Change Password in progress !!!");
        mDialog.show();
    }

    @Override
    protected Object doInBackground(HashMap<String, Object>... params) {
        try {
            String authorization = session.getTokenType()+" "+session.getAccessToken();
            HashMap<String, Object> inputMap = params[0];
            NetworkUtil util = new NetworkUtil();
            String responseMsg = null;
            switch (session.getUserType()){
                case PARTNER:
                    responseMsg = util.getJsonResponse(AppDefine.GET_PARTNER_CHANGE_PASSWORD_URL, authorization, inputMap);
                    break;

                case NOMINEE:
                    responseMsg = util.getJsonResponse(AppDefine.GET_NOMINEE_CHANGE_PASSWORD_URL, authorization, inputMap);
                    break;
            }

            if (responseMsg != null) {
                JSONObject responseJson = new JSONObject(responseMsg);
                if (responseJson.getBoolean("IsSuccess")) {
                    return responseJson.getString("ResponseMessage");
                }else {
                    return responseJson;
                }
            }
        } catch (Exception ex){
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
            if (result instanceof JSONObject) {
                mMessage.arg2 = NetworkUtil.ERROR;
                mMessage.obj = result;
            }else {
                mMessage.arg2 = NetworkUtil.SUCCESS;
                mMessage.obj = result;
            }
        } else{
            mMessage.arg1 = NetworkUtil.RESULT_FAIL;
            mMessage.obj= result;
        }
        mMessage.sendToTarget();
    }
}
