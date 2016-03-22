package cisco.services.xchange.cisco_activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import cisco.services.xchange.CiscoActivity;
import cisco.services.xchange.CiscoFragment;
import cisco.services.xchange.R;
import cisco.services.xchange.cisco_adapters.MenuAdapter;
import cisco.services.xchange.cisco_fragments.ChangePasswordFragment;
import cisco.services.xchange.cisco_fragments.NomineeDashboardFragment;
import cisco.services.xchange.cisco_fragments.PartnerDashboardFragment;
import cisco.services.xchange.custom.CircularImageView;
import cisco.services.xchange.custom.Menu;
import cisco.services.xchange.custom.MenuItemListner;

public class DashboardActivity extends CiscoActivity implements MenuItemListner, View.OnClickListener {

    private DrawerLayout mDrawer;
    private CircularImageView mUserPicture;
    private TextView mUserName;
    private TextView mUserCompany;
    private ImageView mMenu;
    private ImageView mNotificationBell;
    private List<ParentObject> mMenuItemList;
    private RecyclerView mMenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initMenu();

        initViews();

        setListners();

        setValues();
    }

    private void initMenu() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mMenuList = (RecyclerView) findViewById(R.id.RV_menu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMenuList.setLayoutManager(linearLayoutManager);

        setAdapter(R.string.program_csx);

        onMenuItemClick(R.string.program_csx);
    }

    private void setAdapter(int itemId) {
        mMenuItemList = Menu.genrateMenu(this,itemId);
        MenuAdapter menuAdapter = new MenuAdapter(this, mMenuItemList, this);
        menuAdapter.setCustomParentAnimationViewId(R.id.menu_item_arrow);
        menuAdapter.setParentClickableViewAnimationDefaultDuration();
        menuAdapter.setParentAndIconExpandOnClick(true);
        mMenuList.setAdapter(menuAdapter);
    }

    private void initViews() {
        mUserPicture = (CircularImageView) findViewById(R.id.IV_user_picture);
        mUserName = (TextView) findViewById(R.id.TV_user_name);
        mUserCompany = (TextView) findViewById(R.id.TV_user_company);
        mMenu = (ImageView) findViewById(R.id.menu);
        mNotificationBell = (ImageView) findViewById(R.id.notification_bell);
    }

    private void setListners(){
        mMenu.setOnClickListener(this);
        mNotificationBell.setOnClickListener(this);
    }

    private void setValues() {
        if (getSession() != null) {
            switch (getSession().getUserType()) {
                case PARTNER:
                    mUserName.setText(getSession().getPartner().getFirstName());
                    mUserCompany.setText(getSession().getPartner().getWebsite());
                    break;
                case NOMINEE:
                    mUserName.setText(getSession().getNominee().getName());
                    mUserCompany.setText(getSession().getNominee().getDesignation());
                    break;
            }
        }else {
            logout();
        }
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.logout);
        builder.setMessage("Do you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMenuItemClick(int itemName) {
        setAdapter(itemName);
        switch (itemName){
            case R.string.profile:
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
                break;
            case R.string.program_csx:
                if (getSession() != null){
                    switch (getSession().getUserType()){
                        case PARTNER:
                            pushFragmentToStack(getString(R.string.program_csx), new PartnerDashboardFragment());
                            break;
                        case NOMINEE:
                            pushFragmentToStack(getString(R.string.program_csx), new NomineeDashboardFragment());
                            break;

                    }
                }
                break;
            case R.string.tnc:

                break;
            case R.string.faqs:

                break;
            case R.string.changepassword:
                pushFragmentToStack(getString(R.string.changepassword), new ChangePasswordFragment());
                break;
            case R.string.sound:

                break;
            case R.string.contact:

                break;
            case R.string.logout:
                logout();
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu:
                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                } else {
                    mDrawer.openDrawer(GravityCompat.START);
                }
                break;

            case R.id.notification_bell:

                break;
        }
    }
}
