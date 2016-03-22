package cisco.services.xchange;

import android.support.v7.app.AppCompatActivity;

import cisco.services.xchange.cisco_dao.Session;

/**
 * Created by yogi on 13/03/16.
 */
public abstract class CiscoActivity extends AppCompatActivity {

    public void storeSession(Session session){
        ((CiscoApplication)getApplication()).storeSession(session);
    }

    public Session getSession(){
        return ((CiscoApplication)getApplication()).getSession();
    }

    public void pushFragmentToStack(String name, CiscoFragment fragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
