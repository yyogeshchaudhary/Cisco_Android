package cisco.services.xchange.cisco_activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cisco.services.xchange.AppDefine;
import cisco.services.xchange.CiscoActivity;
import cisco.services.xchange.R;
import cisco.services.xchange.cisco_asynctasks.ChangePasswordAsyncTask;
import cisco.services.xchange.cisco_asynctasks.UpdateNomineeAsyncTask;
import cisco.services.xchange.cisco_dao.Nominee;
import cisco.services.xchange.utils.NetworkUtil;

public class NomineeDetailActivity extends CiscoActivity implements View.OnClickListener, Handler.Callback {

    private static final int UPDATE_NOMINEE = 1000;
    private TextView designation;
    private TextView emailid;
    private TextView mobile;
    private TextView status;
    private TextView name;
    private ImageView back;
    private ImageView editButton;
    private EditText name_edit;
    private EditText designation_edit;
    private EditText mobile_edit;
    private Spinner status_edit;
    private Button submit;
    private Handler mHandler;
    private View mRootView;
    private Nominee nominee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominee_detail);

        initView();
        initListner();
        init();

    }

    private void init() {
        back.setImageResource(android.support.design.R.drawable.abc_ic_ab_back_material);
        mHandler = new Handler(this);
        nominee = (Nominee)getIntent().getSerializableExtra("nominee");
        if (nominee != null){
            name.setText(nominee.getName());
            designation.setText(nominee.getDesignation());
            emailid.setText(nominee.getEmail());
            mobile.setText(nominee.getMobile());
            if (nominee.getStatus() != null && !nominee.getStatus().equalsIgnoreCase("null"))
                status.setText(nominee.getStatus());
        }
    }

    private void initListner() {
        back.setOnClickListener(this);
        editButton.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    private void initView() {
        name = (TextView) findViewById(R.id.name);
        designation = (TextView) findViewById(R.id.designation);
        emailid = (TextView) findViewById(R.id.emailid);
        mobile = (TextView) findViewById(R.id.mobile);
        status = (TextView) findViewById(R.id.status);
        back = (ImageView) findViewById(R.id.back);
        editButton = (ImageView) findViewById(R.id.edit);
        submit = (Button) findViewById(R.id.submit);

        name_edit = (EditText) findViewById(R.id.name_edit);
        designation_edit = (EditText) findViewById(R.id.designation_edit);
        mobile_edit = (EditText) findViewById(R.id.mobile_edit);
        status_edit = (Spinner) findViewById(R.id.status_spinner);
        mRootView = findViewById(R.id.rootView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit:
                if (submit.getVisibility() == View.VISIBLE){
                    turnOnEditView(false);
                } else {
                    turnOnEditView(true);
                }
                break;

            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if(name_edit.getText().toString().equalsIgnoreCase("")){
                    name_edit.setError(getString(R.string.error_field_required));
                    return;
                }
                if(designation_edit.getText().toString().equalsIgnoreCase("")){
                    designation_edit.setError(getString(R.string.error_field_required));
                    return;
                }
                if(mobile_edit.getText().toString().equalsIgnoreCase("")){
                    mobile_edit.setError(getString(R.string.error_field_required));
                    return;
                }

                turnOnEditView(false);
                name.setText(name_edit.getText().toString().trim());
                designation.setText(designation_edit.getText().toString().trim());
                mobile.setText(mobile_edit.getText().toString().trim());
                status.setText((String)status_edit.getSelectedItem());

                HashMap<String, Object> inputMap = new HashMap<String, Object>();
                inputMap.put(AppDefine.USERNAME, nominee.getEmail());
                HashMap<String, Object> jsonData = new HashMap<String, Object>();
                jsonData.put(AppDefine.ID,nominee.getId());
                jsonData.put(AppDefine.NAME,name_edit.getText().toString().trim());
                jsonData.put(AppDefine.DESIGNATION,designation_edit.getText().toString().trim());
                jsonData.put(AppDefine.MOBILE,mobile_edit.getText().toString().trim());
                jsonData.put(AppDefine.EMAIL,nominee.getEmail());
                jsonData.put(AppDefine.PASSWORD,nominee.getPassword());
                jsonData.put(AppDefine.PARTERNAME,nominee.getPartnerName());
                jsonData.put(AppDefine.PARTEREMAIL,nominee.getPartnerEmail());
                jsonData.put(AppDefine.STATUS,(String)status_edit.getSelectedItem());
                jsonData.put(AppDefine.FIRSTLOGIN,nominee.isFirstLogin());
                jsonData.put(AppDefine.OLDPOINTS,nominee.getOldPoints());
                jsonData.put(AppDefine.POINTS,nominee.getPoints());
                jsonData.put(AppDefine.CREATEDON,nominee.getCreatdOn());
                jsonData.put(AppDefine.MODIFIEDON,nominee.getModifiedOn());
                jsonData.put(AppDefine.ISDELETED,nominee.isDeleted());
                inputMap.put(AppDefine.JSONDATA, jsonData);

                Message msg = mHandler.obtainMessage(UPDATE_NOMINEE);
                UpdateNomineeAsyncTask task = new UpdateNomineeAsyncTask(this, msg);
                task.execute(inputMap);
                break;
        }
    }

    private void turnOnEditView(boolean flag) {
        int text = View.GONE;
        int edit = View.VISIBLE;
        if (!flag){
            text = View.VISIBLE;
            edit = View.GONE;
        }
        name.setVisibility(text);
        name_edit.setVisibility(edit);
        if (name_edit.getVisibility() == View.VISIBLE)
            name_edit.setText(name.getText().toString().trim());

        designation.setVisibility(text);
        designation_edit.setVisibility(edit);
        if (designation_edit.getVisibility() == View.VISIBLE)
            designation_edit.setText(designation.getText().toString().trim());


        mobile.setVisibility(text);
        mobile_edit.setVisibility(edit);
        if (mobile_edit.getVisibility() == View.VISIBLE)
            mobile_edit.setText(mobile.getText().toString().trim());

        status.setVisibility(text);
        status_edit.setVisibility(edit);
        if (status_edit.getVisibility() == View.VISIBLE) {
            List<String> list = new ArrayList<String>();
            if (status.getText().toString().equalsIgnoreCase("")){
                list.add("");
                list.add("Disable");
            } else {
                list.add("Inactive");
                list.add("Disable");
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            status_edit.setAdapter(dataAdapter);
            if (status.getText().toString().equalsIgnoreCase("Inactive")) {
                status_edit.setSelection(0);
            } else if (status.getText().toString().equalsIgnoreCase("Disable")) {
                status_edit.setSelection(1);
            }
        }

        submit.setVisibility(edit);
    }

    @Override
    public void onBackPressed() {
        if (submit.getVisibility() == View.VISIBLE){
            turnOnEditView(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1){
            case NetworkUtil.RESULT_OK:
                switch (msg.arg2){
                    case NetworkUtil.SUCCESS:
                        switch (msg.what){
                            case UPDATE_NOMINEE:
                                Toast.makeText(this, (String) msg.obj, Toast.LENGTH_LONG).show();
                                break;
                        }
                        break;

                    case NetworkUtil.ERROR:
                        JSONObject errorJson = (JSONObject) msg.obj;
                        break;
                }
                break;

            case NetworkUtil.RESULT_FAIL:
                Snackbar.make(mRootView, "Please try again.", Snackbar.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
