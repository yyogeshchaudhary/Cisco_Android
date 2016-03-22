package cisco.services.xchange.cisco_fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cisco.services.xchange.CiscoFragment;
import cisco.services.xchange.R;

public class NomineeDashboardFragment extends CiscoFragment {

    public NomineeDashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nominee, container, false);
    }
}
