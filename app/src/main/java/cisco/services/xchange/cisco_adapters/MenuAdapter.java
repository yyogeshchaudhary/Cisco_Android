package cisco.services.xchange.cisco_adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;
import java.util.zip.Inflater;

import cisco.services.xchange.R;
import cisco.services.xchange.cisco_dao.ChildMenuItem;
import cisco.services.xchange.cisco_dao.ParentMenuItem;
import cisco.services.xchange.cisco_viewholder.MenuChildViewHolder;
import cisco.services.xchange.cisco_viewholder.MenuParentViewHolder;
import cisco.services.xchange.custom.MenuItemListner;

/**
 * Created by yogi on 15/03/16.
 */
public class MenuAdapter extends ExpandableRecyclerAdapter<MenuParentViewHolder, MenuChildViewHolder>{


    private final MenuItemListner mListner;
    private List<ParentObject> menuItemList;
    public MenuAdapter(Context context, List<ParentObject> parentItemList, MenuItemListner listner) {
        super(context, parentItemList);
        this.menuItemList = parentItemList;
        this.mContext = context;
        this.mListner = listner;
    }

    @Override
    public MenuParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = View.inflate(mContext, R.layout.list_item_menu_parent, null);
        return new MenuParentViewHolder(view);
    }

    @Override
    public MenuChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = View.inflate(mContext, R.layout.list_item_menu_child, null);
        return new MenuChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(MenuParentViewHolder menuParentViewHolder, final int parentIndex, Object parentObject) {
        final ParentMenuItem item = (ParentMenuItem) parentObject;
        menuParentViewHolder.itemName.setText(item.name);
        menuParentViewHolder.itemImage.setImageResource(item.imageId);
        if (item.selected)
            menuParentViewHolder.selected.setBackgroundResource(R.color.menu_selected);
        else
            menuParentViewHolder.selected.setBackgroundColor(Color.TRANSPARENT);

        if (item.getChildObjectList() != null)
            menuParentViewHolder.arrow.setVisibility(View.VISIBLE);
        else
            menuParentViewHolder.arrow.setVisibility(View.GONE);

        menuParentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListner.onMenuItemClick(item.name);
            }
        });
    }

    @Override
    public void onBindChildViewHolder(MenuChildViewHolder menuChildViewHolder, final int childIndex, Object childObject) {
        final ChildMenuItem item = (ChildMenuItem) childObject;
        menuChildViewHolder.itemName.setText(item.name);
        if (item.isSelected()){
            menuChildViewHolder.itemSwitch.setVisibility(View.VISIBLE);
        } else {
            menuChildViewHolder.itemSwitch.setVisibility(View.GONE);
        }
        menuChildViewHolder.itemSwitch.setSelected(item.isOn);
        menuChildViewHolder.itemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        menuChildViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListner.onMenuItemClick(item.name);
            }
        });
    }
}
