package cisco.services.xchange.cisco_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import cisco.services.xchange.AppDefine;
import cisco.services.xchange.CiscoActivity;
import cisco.services.xchange.R;

public class CiscoLandingActivity extends CiscoActivity {

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cisco_landinng);

        mTimer = new Timer();
        mTimer.schedule(mTimerTask, AppDefine.SPLASH_TIME);
    }

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            Intent intent = new Intent(CiscoLandingActivity.this, CiscoLoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        mTimer.cancel();
        finish();
    }
}
