package cisco.services.xchange.cisco_asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;

import org.json.JSONObject;

import java.util.HashMap;

import cisco.services.xchange.AppDefine;
import cisco.services.xchange.CiscoAsyncTask;
import cisco.services.xchange.cisco_dao.Session;
import cisco.services.xchange.utils.ExceptionHandler;
import cisco.services.xchange.utils.NetworkUtil;
import cisco.services.xchange.utils.Parser;

/**
 * Created by yogi on 18/03/16.
 */
public class ForgotPasswordAsyncTask extends CiscoAsyncTask {

    private final ProgressDialog mDialog;

    public ForgotPasswordAsyncTask(Context context, Message msg) {
        super(context, msg);
        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Please wait!!!");
        mDialog.show();
    }

    @Override
    protected Object doInBackground(HashMap<String, Object>... params) {
        try {
            HashMap<String, Object> inputMap = params[0];


            NetworkUtil util = new NetworkUtil();
            String responseMsg = util.getJsonResponse(AppDefine.GET_FORGOT_PASSWORD_URL, null, inputMap);
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
