package cisco.services.xchange;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;

/**
 * Created by yogi on 14/03/16.
 */
public abstract class CiscoAsyncTask extends AsyncTask<HashMap<String, Object>, Void, Object>  {

    public Message mMessage;
    public Context mContext;

    protected CiscoAsyncTask(Context context, Message msg){
        this.mContext = context;
        this.mMessage = msg;
    }
}
