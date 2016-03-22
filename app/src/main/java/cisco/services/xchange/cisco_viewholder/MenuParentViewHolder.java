package cisco.services.xchange.cisco_viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import cisco.services.xchange.R;

/**
 * Created by yogi on 15/03/16.
 */
public class MenuParentViewHolder extends ParentViewHolder {

    public View selected;
    public ImageView itemImage;
    public TextView itemName;
    public ImageView arrow;
    public View itemView;

    public MenuParentViewHolder(View itemView) {
        super(itemView);
        selected = itemView.findViewById(R.id.menu_item_selected);
        itemImage = (ImageView) itemView.findViewById(R.id.menu_item_image);
        itemName = (TextView) itemView.findViewById(R.id.menu_item_name);
        arrow = (ImageView) itemView.findViewById(R.id.menu_item_arrow);
        this.itemView = itemView;
    }
}
