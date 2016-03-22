package cisco.services.xchange.cisco_fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cisco.services.xchange.CiscoActivity;
import cisco.services.xchange.CiscoFragment;
import cisco.services.xchange.R;

public class PartnerDashboardFragment extends CiscoFragment implements View.OnClickListener{

    private TextView mCompanyName;
    private TextView mSaleAmount;
    private TextView nomineeList;

    public PartnerDashboardFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_partner, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCompanyName = (TextView) view.findViewById(R.id.company_name);
        mSaleAmount = (TextView) view.findViewById(R.id.sale_amount);
        nomineeList = (TextView) view.findViewById(R.id.nomineeList);
        nomineeList.setOnClickListener(this);
        setData();
    }

    private void setData() {
        if (getSession() != null && getSession().getPartner() != null){
            mCompanyName.setText(getSession().getPartner().getOrganisation());
            mSaleAmount.setText("USD "+getSession().getPartner().getSalesAmount());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nomineeList:
                ((CiscoActivity)getActivity()).pushFragmentToStack("Nominee List", new NomineeListFragment());
                break;
        }
    }
}
