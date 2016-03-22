package cisco.services.xchange.cisco_viewholder;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import cisco.services.xchange.R;


/**
 * Created by yogi on 15/03/16.
 */
public class MenuChildViewHolder extends ChildViewHolder {

    public View itemView;
    public TextView itemName;
    public SwitchCompat itemSwitch;

    public MenuChildViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        itemName = (TextView) itemView.findViewById(R.id.menu_item_name);
        itemSwitch = (SwitchCompat) itemView.findViewById(R.id.child_switch);

    }
}
