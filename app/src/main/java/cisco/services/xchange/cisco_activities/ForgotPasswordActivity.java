package cisco.services.xchange.cisco_activities;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import cisco.services.xchange.AppDefine;
import cisco.services.xchange.R;
import cisco.services.xchange.cisco_asynctasks.ForgotPasswordAsyncTask;
import cisco.services.xchange.utils.NetworkUtil;


public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener, Handler.Callback {

    private static final int FORGOT_PASSWORD = 1000;
    private EditText mUsername;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.support.design.R.drawable.abc_ic_ab_back_material);

        mUsername = (EditText) findViewById(R.id.username);
        ((Button) findViewById(R.id.send)).setOnClickListener(this);

        mHandler = new Handler(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        String username = mUsername.getText().toString().trim();
        if (username.equalsIgnoreCase("")) {
            mUsername.setError(getResources().getString(R.string.error_field_required));
            mUsername.requestFocus();
            return;
        }
        HashMap<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put(AppDefine.USERNAME, username);
        inputMap.put(AppDefine.JSONDATA, "");

        Message msg = mHandler.obtainMessage(FORGOT_PASSWORD);
        ForgotPasswordAsyncTask task = new ForgotPasswordAsyncTask(this, msg);
        task.execute(inputMap);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1){
            case NetworkUtil.RESULT_OK:
                switch (msg.arg2){
                    case NetworkUtil.SUCCESS:
                        switch (msg.what){
                            case FORGOT_PASSWORD:
                                Toast.makeText(ForgotPasswordActivity.this,(String)msg.obj,Toast.LENGTH_LONG).show();
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
                Snackbar.make(findViewById(R.id.rootView), "Please try again.", Snackbar.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
