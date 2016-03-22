package cisco.services.xchange.cisco_asynctasks;

import android.app.ProgressDialog;
import android.os.Message;

import org.json.JSONObject;

import java.util.HashMap;

import cisco.services.xchange.AppDefine;
import cisco.services.xchange.CiscoActivity;
import cisco.services.xchange.CiscoAsyncTask;
import cisco.services.xchange.cisco_dao.Session;
import cisco.services.xchange.utils.ExceptionHandler;
import cisco.services.xchange.utils.NetworkUtil;
import cisco.services.xchange.utils.Parser;

/**
 * Created by yogi on 19/03/16.
 */
public class AddNomineeAsycTask extends CiscoAsyncTask {
    private Session session;
    private final ProgressDialog mDialog;

    public AddNomineeAsycTask(CiscoActivity context, Message msg) {
        super(context, msg);
        session = context.getSession();
        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Please wait !!!");
        mDialog.show();
    }

    @Override
    protected Object doInBackground(HashMap<String, Object>... params) {
        try {
            HashMap<String, Object> inputMap = params[0];

            String authorization = session.getTokenType()+" "+session.getAccessToken();

            NetworkUtil util = new NetworkUtil();
            String responseMsg = util.getJsonResponse(AppDefine.ADD_NOMINEE_URL, authorization, inputMap);
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
        try {
            if (result != null) {
                mMessage.arg1 = NetworkUtil.RESULT_OK;
                if (result instanceof JSONObject) {
                    mMessage.arg2 = NetworkUtil.ERROR;
                    mMessage.obj = ((JSONObject) result).getString("ResponseMessage");
                } else {
                    mMessage.arg2 = NetworkUtil.SUCCESS;
                    mMessage.obj = result;
                }
            } else {
                mMessage.arg1 = NetworkUtil.RESULT_FAIL;
                mMessage.obj = result;
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        mMessage.sendToTarget();
    }
}
