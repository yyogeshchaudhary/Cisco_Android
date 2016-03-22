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

import java.util.HashMap;

import cisco.services.xchange.AppDefine;
import cisco.services.xchange.CiscoActivity;
import cisco.services.xchange.CiscoFragment;
import cisco.services.xchange.R;
import cisco.services.xchange.cisco_asynctasks.ChangePasswordAsyncTask;
import cisco.services.xchange.utils.NetworkUtil;

public class ChangePasswordFragment extends CiscoFragment implements View.OnClickListener, Handler.Callback {

    private static final int CHANGE_PASSWORD = 1000;
    private EditText mOldPassword;
    private EditText mNewPassword;
    private EditText mConfirmPassword;
    private Handler mHandler;
    private View mRootView;

    public ChangePasswordFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOldPassword = (EditText) view.findViewById(R.id.oldPassword);
        mNewPassword = (EditText) view.findViewById(R.id.newPassword);
        mConfirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        mRootView = view.findViewById(R.id.rootView);

        ((Button) view.findViewById(R.id.submit)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.cancel)).setOnClickListener(this);

        mHandler = new Handler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                String oldPassword = mOldPassword.getText().toString().trim();
                if (oldPassword.equalsIgnoreCase("")) {
                    mOldPassword.setError(getResources().getString(R.string.error_field_required));
                    mOldPassword.requestFocus();
                    return;
                }

                String newPassword = mNewPassword.getText().toString().trim();
                if (newPassword.equalsIgnoreCase("")) {
                    mNewPassword.setError(getResources().getString(R.string.error_field_required));
                    mNewPassword.requestFocus();
                    return;
                }

                String confirmPassword = mConfirmPassword.getText().toString().trim();
                if (confirmPassword.equalsIgnoreCase("")) {
                    mConfirmPassword.setError(getResources().getString(R.string.error_field_required));
                    mConfirmPassword.requestFocus();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(getActivity(), R.string.new_confirm_password_not_match, Toast.LENGTH_LONG).show();
                    return;
                }

                String username = null;
                String email = null;
                switch (getSession().getUserType()) {
                    case PARTNER:
                        username = getSession().getPartner().getUserName();
                        email = getSession().getPartner().getEmail();
                        break;

                    case NOMINEE:
                        username = getSession().getNominee().getUserName();
                        email = getSession().getNominee().getEmail();
                        break;
                }
                HashMap<String, Object> inputMap = new HashMap<String, Object>();
                inputMap.put(AppDefine.USERNAME, username);
                HashMap<String, Object> jsonData = new HashMap<String, Object>();
                jsonData.put(AppDefine.EMAIL, email);
                jsonData.put(AppDefine.OLD_PASSWORD, oldPassword);
                jsonData.put(AppDefine.NEW_PASSWORD, newPassword);
                inputMap.put(AppDefine.JSONDATA, jsonData);

                Message msg = mHandler.obtainMessage(CHANGE_PASSWORD);
                ChangePasswordAsyncTask task = new ChangePasswordAsyncTask((CiscoActivity) ChangePasswordFragment.this.getActivity(), msg);
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
                            case CHANGE_PASSWORD:
                                Toast.makeText(ChangePasswordFragment.this.getActivity(),(String)msg.obj,Toast.LENGTH_LONG).show();
                                mOldPassword.setText("");
                                mNewPassword.setText("");
                                mConfirmPassword.setText("");
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
