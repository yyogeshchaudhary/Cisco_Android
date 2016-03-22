package cisco.services.xchange.cisco_adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import cisco.services.xchange.R;
import cisco.services.xchange.cisco_activities.NomineeDetailActivity;
import cisco.services.xchange.cisco_dao.Nominee;

/**
 * Created by yogi on 19/03/16.
 */
public class NomineeListAdapter extends RecyclerView.Adapter{

    private final Context mContext;
    private ArrayList<Nominee> nominees;

    public NomineeListAdapter(Context context, ArrayList<Nominee> nominees){
        this.nominees = nominees;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NomineeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_nominee, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NomineeViewHolder nomineeViewHolder = (NomineeViewHolder) holder;
        Nominee nominee = nominees.get(position);

        nomineeViewHolder.name.setText(nominee.getName());
        nomineeViewHolder.designation.setText(nominee.getDesignation());
        nomineeViewHolder.setNominee(nominee);
    }

    @Override
    public int getItemCount() {
        return nominees.size();
    }

    public class NomineeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView name;
        private final TextView designation;
        private final ImageView userImage;
        private Nominee nominee;

        public NomineeViewHolder(View itemView) {
            super(itemView);
            userImage = (ImageView) itemView.findViewById(R.id.userImage);
            name = (TextView) itemView.findViewById(R.id.name);
            designation = (TextView) itemView.findViewById(R.id.designation);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, NomineeDetailActivity.class);
            intent.putExtra("nominee", nominee);
            mContext.startActivity(intent);
        }

        public void setNominee(Nominee nominee) {
            this.nominee = nominee;
        }
    }
}
