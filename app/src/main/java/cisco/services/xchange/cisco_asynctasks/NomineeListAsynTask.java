package cisco.services.xchange.cisco_asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;

import org.json.JSONArray;
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
 * Created by yogi on 18/03/16.
 */
public class NomineeListAsynTask extends CiscoAsyncTask {

    private Session session;
    private final ProgressDialog mDialog;

    public NomineeListAsynTask(CiscoActivity context, Message msg) {
        super(context, msg);
        session = context.getSession();
        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Please wait for Nominee List !!!");
        mDialog.show();
    }

    @Override
    protected Object doInBackground(HashMap<String, Object>... params) {
        try {
            HashMap<String, Object> inputMap = params[0];

            String authorization = session.getTokenType()+" "+session.getAccessToken();

            NetworkUtil util = new NetworkUtil();
            String responseMsg = util.getJsonResponse(AppDefine.GET_NOMINEE_LIST_URL, authorization, inputMap);
            if (responseMsg != null) {
                JSONObject responseJson = new JSONObject(responseMsg);
                if (responseJson.getBoolean("IsSuccess")) {
                    JSONArray nominees = new JSONArray(responseJson.getString("ResponseMessage"));
                    return Parser.getNomineeList(nominees);
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
