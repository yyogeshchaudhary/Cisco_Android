package cisco.services.xchange.cisco_fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import cisco.services.xchange.AppDefine;
import cisco.services.xchange.CiscoActivity;
import cisco.services.xchange.CiscoFragment;
import cisco.services.xchange.R;
import cisco.services.xchange.cisco_asynctasks.AddNomineeAsycTask;
import cisco.services.xchange.cisco_asynctasks.UpdateNomineeAsyncTask;
import cisco.services.xchange.utils.NetworkUtil;
import cisco.services.xchange.utils.Util;


public class AddNomineeFragment extends CiscoFragment implements View.OnClickListener,Handler.Callback {

    private static final int ADD_NOMINEE = 1000;
    private EditText name;
    private EditText designation;
    private EditText email;
    private EditText mobile;
    private Button submit;
    private Button cancel;
    private Handler mHandler;
    private View mRootView;


    public AddNomineeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_nominee, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = (EditText) view.findViewById(R.id.name);
        designation = (EditText) view.findViewById(R.id.designation);
        mobile = (EditText) view.findViewById(R.id.mobile);
        email = (EditText) view.findViewById(R.id.emailid);
        submit = (Button) view.findViewById(R.id.submit);
        cancel = (Button) view.findViewById(R.id.cancel);
        mRootView = view.findViewById(R.id.rootView);

        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);

        mHandler = new Handler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                if(name.getText().toString().equalsIgnoreCase("")){
                    name.setError(getString(R.string.error_field_required));
                    return;
                }
                if(designation.getText().toString().equalsIgnoreCase("")){
                    designation.setError(getString(R.string.error_field_required));
                    return;
                }
                if(mobile.getText().toString().equalsIgnoreCase("")){
                    mobile.setError(getString(R.string.error_field_required));
                    return;
                }

                if(email.getText().toString().equalsIgnoreCase("")){
                    email.setError(getString(R.string.error_field_required));
                    return;
                } else if(!Util.isValidEmail(email.getText().toString().trim())){
                    email.setError(getString(R.string.error_invalid_emailid));
                    return;
                }

                HashMap<String, Object> inputMap = new HashMap<String, Object>();
                inputMap.put(AppDefine.USERNAME, email.getText().toString().trim());
                HashMap<String, Object> jsonData = new HashMap<String, Object>();
                jsonData.put(AppDefine.ID,0);
                jsonData.put(AppDefine.NAME,name.getText().toString().trim());
                jsonData.put(AppDefine.DESIGNATION,designation.getText().toString().trim());
                jsonData.put(AppDefine.MOBILE,mobile.getText().toString().trim());
                jsonData.put(AppDefine.EMAIL,email.getText().toString().trim());
                jsonData.put(AppDefine.PASSWORD,"");
                jsonData.put(AppDefine.PARTERNAME,getSession().getPartner().getFirstName()+" "+getSession().getPartner().getLastName());
                jsonData.put(AppDefine.PARTEREMAIL,getSession().getPartner().getEmail());
                jsonData.put(AppDefine.STATUS,"");
                jsonData.put(AppDefine.FIRSTLOGIN,true);
                jsonData.put(AppDefine.OLDPOINTS,0);
                jsonData.put(AppDefine.POINTS,0);
                jsonData.put(AppDefine.CREATEDON,"");
                jsonData.put(AppDefine.MODIFIEDON,"");
                jsonData.put(AppDefine.ISDELETED,false);
                inputMap.put(AppDefine.JSONDATA, jsonData);

                Message msg = mHandler.obtainMessage(ADD_NOMINEE);
                AddNomineeAsycTask task = new AddNomineeAsycTask((CiscoActivity)this.getActivity(), msg);
                task.execute(inputMap);
                break;

            case R.id.cancel:

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
                            case ADD_NOMINEE:
                                Toast.makeText(this.getActivity(), (String) msg.obj, Toast.LENGTH_LONG).show();
                                name.setText("");
                                designation.setText("");
                                email.setText("");
                                mobile.setText("");
                                break;
                        }
                        break;

                    case NetworkUtil.ERROR:
                        Toast.makeText(this.getActivity(), (String) msg.obj, Toast.LENGTH_LONG).show();
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
