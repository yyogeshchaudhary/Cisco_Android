package cisco.services.xchange.cisco_activities;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import cisco.services.xchange.CiscoActivity;
import cisco.services.xchange.R;
import cisco.services.xchange.cisco_dao.Session;

public class ProfileActivity extends CiscoActivity implements View.OnClickListener {

    private TextView website;
    private TextView partnerName;
    private TextView emailid;
    private TextView mobile;
    private TextView phone;
    private TextView company;
    private TextView name;
    private ImageView back;
    private ImageView editButton;
    private EditText website_edit;
    private EditText partnerName_edit;
    private EditText emailid_edit;
    private EditText mobile_edit;
    private EditText phone_edit;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();
        initListner();

        init();
    }

    private void init() {
        Session session = getSession();
        if (session != null && session.getPartner() != null) {
            company.setText(session.getPartner().getOrganisation());
            name.setText(session.getPartner().getFirstName());
            website.setText(session.getPartner().getWebsite());
            emailid.setText(session.getPartner().getEmail());
            mobile.setText(session.getPartner().getMobile());
            phone.setText(session.getPartner().getPhone());
        }
    }

    private void initListner() {
        back.setOnClickListener(this);
        editButton.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    private void initView() {
        company = (TextView) findViewById(R.id.TV_user_company);
        name = (TextView) findViewById(R.id.TV_user_name);
        website = (TextView) findViewById(R.id.website);
        partnerName = (TextView) findViewById(R.id.partner_owner_name);
        emailid = (TextView) findViewById(R.id.emailid);
        mobile = (TextView) findViewById(R.id.mobile);
        phone = (TextView) findViewById(R.id.phone);
        back = (ImageView) findViewById(R.id.back);
        editButton = (ImageView) findViewById(R.id.edit);
        submit = (Button) findViewById(R.id.submit);

        website_edit = (EditText) findViewById(R.id.website_edit);
        partnerName_edit = (EditText) findViewById(R.id.partner_owner_name_edit);
        emailid_edit = (EditText) findViewById(R.id.emailid_edit);
        mobile_edit = (EditText) findViewById(R.id.mobile_edit);
        phone_edit = (EditText) findViewById(R.id.phone_edit);

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
                turnOnEditView(false);
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
        website.setVisibility(text);
        website_edit.setVisibility(edit);
        if (website_edit.getVisibility() == View.VISIBLE)
            website_edit.setText(website.getText().toString().trim());

        partnerName.setVisibility(text);
        partnerName_edit.setVisibility(edit);
        if (partnerName_edit.getVisibility() == View.VISIBLE)
            partnerName_edit.setText(partnerName.getText().toString().trim());

        emailid.setVisibility(text);
        emailid_edit.setVisibility(edit);
        if (emailid_edit.getVisibility() == View.VISIBLE)
            emailid_edit.setText(emailid.getText().toString().trim());

        mobile.setVisibility(text);
        mobile_edit.setVisibility(edit);
        if (mobile_edit.getVisibility() == View.VISIBLE)
            mobile_edit.setText(mobile.getText().toString().trim());

        phone.setVisibility(text);
        phone_edit.setVisibility(edit);
        if (phone_edit.getVisibility() == View.VISIBLE)
            phone_edit.setText(phone.getText().toString().trim());

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
}
