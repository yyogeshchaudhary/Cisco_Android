package cisco.services.xchange;

import android.app.Application;

import cisco.services.xchange.cisco_dao.Session;

/**
 * Created by yogi on 15/03/16.
 */
public class CiscoApplication extends Application {

    private Session mSession;

    public void storeSession(Session session){
        this.mSession = session;
    }

    public Session getSession(){
        return mSession;
    }

    public void clearSession(){
        this.mSession = null;
    }

}
