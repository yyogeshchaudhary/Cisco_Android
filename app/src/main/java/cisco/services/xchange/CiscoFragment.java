package cisco.services.xchange;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import cisco.services.xchange.cisco_dao.Session;

/**
 * Created by yogi on 18/03/16.
 */
public class CiscoFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public Session getSession(){
        return ((CiscoActivity)getActivity()).getSession();
    }

    public void pushFragmentToStack(String name, CiscoFragment fragment){

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
