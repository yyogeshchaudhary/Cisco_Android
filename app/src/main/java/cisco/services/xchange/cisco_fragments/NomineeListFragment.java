package cisco.services.xchange.cisco_fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cisco.services.xchange.CiscoFragment;
import cisco.services.xchange.R;
import cisco.services.xchange.cisco_adapters.NomineeListAdapter;

public class NomineeListFragment extends CiscoFragment implements View.OnClickListener {

    private RecyclerView mNomineeList;
    private NomineeListAdapter mNomineeAdaper;

    public NomineeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nominee_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNomineeList = (RecyclerView) view.findViewById(R.id.nomineeList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mNomineeList.setLayoutManager(linearLayoutManager);

        mNomineeAdaper = new NomineeListAdapter(this.getActivity(), getSession().getPartner().getNominees());
        mNomineeList.setHasFixedSize(true);
        mNomineeList.setAdapter(mNomineeAdaper);

        ((ImageView) view.findViewById(R.id.addNominee)).setOnClickListener(this);
        ((ImageView) view.findViewById(R.id.disableNominee)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addNominee:
                pushFragmentToStack(getActivity().getResources().getString(R.string.add_nominee), new AddNomineeFragment());
                break;

            case R.id.disableNominee:

                break;
        }
    }
}
