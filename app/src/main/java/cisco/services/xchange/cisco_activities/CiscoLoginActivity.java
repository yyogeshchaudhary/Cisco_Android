package cisco.services.xchange.cisco_activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cisco.services.xchange.AppDefine;
import cisco.services.xchange.CiscoActivity;
import cisco.services.xchange.R;
import cisco.services.xchange.cisco_asynctasks.LoginAsyncTask;
import cisco.services.xchange.cisco_asynctasks.NomineeAsyncTask;
import cisco.services.xchange.cisco_asynctasks.NomineeListAsynTask;
import cisco.services.xchange.cisco_asynctasks.PartnerAsyncTask;
import cisco.services.xchange.cisco_asynctasks.PartnerSaleAmountAsyncTask;
import cisco.services.xchange.cisco_dao.Nominee;
import cisco.services.xchange.cisco_dao.Partner;
import cisco.services.xchange.cisco_dao.Session;
import cisco.services.xchange.utils.NetworkUtil;


public class CiscoLoginActivity extends CiscoActivity implements OnClickListener, Handler.Callback {


    private static final int SIGN_IN = 1000;
    private static final int GET_PARTNER_INFO = 2000;
    private static final int GET_NOMINEE_INFO = 3000;
    private static final int GET_NOMINEE_LIST = 4000;
    private static final int GET_PARTNER_SALE_AMOUNT = 5000;
    private EditText mUsername;
    private EditText mPassword;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cisco_login);

        initViews();

        mHandler = new Handler(this);

    }

    private void initViews() {
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);

        ((TextView) findViewById(R.id.forgot_password)).setOnClickListener(this);
        ((Button) findViewById(R.id.sign_in_button)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                // Partner
                mUsername.setText("ravikantgaud@gmail.com");
                mPassword.setText("ravikant");

                // sales
//                mUsername.setText("yyogesh.chaudhary@gmail.com");
//                mPassword.setText("37bfc66");
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (username.equalsIgnoreCase("")) {
                    mUsername.setError(getResources().getString(R.string.error_field_required));
                    mUsername.requestFocus();
                    return;
                }

                if (password.equalsIgnoreCase("")) {
                    mPassword.setError(getResources().getString(R.string.error_field_required));
                    mPassword.requestFocus();
                    return;
                }

                HashMap<String, Object> inputMap = new HashMap<String, Object>();
                inputMap.put(AppDefine.USERNAME_KEY, username);
                inputMap.put(AppDefine.PASSWORD_KEY, password);

                Message msg = mHandler.obtainMessage(SIGN_IN);
                LoginAsyncTask task = new LoginAsyncTask(this, msg);
                task.execute(inputMap);
                break;
            case R.id.forgot_password:
                Intent intent = new Intent(CiscoLoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1){
            case NetworkUtil.RESULT_OK:
                switch (msg.arg2){
                    case NetworkUtil.SUCCESS:
                        switch (msg.what){
                            case SIGN_IN:
                                Session session = (Session) msg.obj;
                                storeSession(session);
                                HashMap<String, Object> inputMap = new HashMap<String, Object>();
                                inputMap.put(AppDefine.USERNAME, session.getEmail());
                                inputMap.put(AppDefine.JSONDATA, "");

                                switch (session.getUserType()){
                                    case PARTNER:
                                        Message partnerMsg = mHandler.obtainMessage(GET_PARTNER_INFO);
                                        PartnerAsyncTask task = new PartnerAsyncTask(CiscoLoginActivity.this, partnerMsg);
                                        task.execute(inputMap);
                                        break;
                                    case NOMINEE:
                                        Message nomineeMsg = mHandler.obtainMessage(GET_NOMINEE_INFO);
                                        NomineeAsyncTask nomineeAsyncTask = new NomineeAsyncTask(CiscoLoginActivity.this, nomineeMsg);
                                        nomineeAsyncTask.execute(inputMap);
                                        break;
                                }
                                break;

                            case GET_PARTNER_INFO:
                                getSession().setPartner((Partner) msg.obj);
                                getSession().getPartner().setUserName(mUsername.getText().toString().trim());
                                HashMap<String, Object> inputMap1 = new HashMap<String, Object>();
                                inputMap1.put(AppDefine.USERNAME, getSession().getPartner().getUserName());
                                inputMap1.put(AppDefine.JSONDATA, "");

                                Message nomineeMsg = mHandler.obtainMessage(GET_NOMINEE_LIST);
                                NomineeListAsynTask nlAsyncTask = new NomineeListAsynTask(CiscoLoginActivity.this, nomineeMsg);
                                nlAsyncTask.execute(inputMap1);
                                break;


                            case GET_NOMINEE_LIST:
                                if (getSession() != null && getSession().getPartner() != null) {
                                    getSession().getPartner().setNominees((ArrayList<Nominee>) msg.obj);

                                    HashMap<String, Object> partnerSaleAmountReq = new HashMap<String, Object>();
                                    partnerSaleAmountReq.put(AppDefine.USERNAME, getSession().getPartner().getUserName());
                                    partnerSaleAmountReq.put(AppDefine.JSONDATA, "");

                                    Message partnerSaleAmoutMsg = mHandler.obtainMessage(GET_PARTNER_SALE_AMOUNT);
                                    PartnerSaleAmountAsyncTask gpsaAsyncTask = new PartnerSaleAmountAsyncTask(CiscoLoginActivity.this, partnerSaleAmoutMsg);
                                    gpsaAsyncTask.execute(partnerSaleAmountReq);
                                }
                                break;

                            case GET_PARTNER_SALE_AMOUNT:
                                if (getSession() != null && getSession().getPartner() != null) {
                                    getSession().getPartner().setSalesAmount((String)msg.obj);
                                    Intent intent = new Intent(CiscoLoginActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                break;

                            case GET_NOMINEE_INFO:
                                getSession().setNominee((Nominee) msg.obj);
                                getSession().getNominee().setUserName(mUsername.getText().toString().trim());
                                Intent intent1 = new Intent(CiscoLoginActivity.this, DashboardActivity.class);
                                startActivity(intent1);
                                finish();
                                break;
                        }
                        break;

                    case NetworkUtil.ERROR:
                        Toast.makeText(this, (String) msg.obj, Toast.LENGTH_LONG).show();
                        break;
                }
                break;

            case NetworkUtil.RESULT_FAIL:
                Snackbar.make(findViewById(R.id.rootView),"Please try again.", Snackbar.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

//    @Override
//    public boolean handleMessage(Message msg) {
//        switch (msg.arg1){
//            case NetworkUtil.RESULT_OK:
//                switch (msg.what){
//
//                }
//                break;
//
//            case NetworkUtil.RESULT_FAIL:
//                break;
//        }
//        return false;
//    }
}

