package cisco.services.xchange.custom;

import android.content.Context;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import cisco.services.xchange.R;
import cisco.services.xchange.cisco_dao.ChildMenuItem;
import cisco.services.xchange.cisco_dao.ParentMenuItem;

/**
 * Created by yogi on 17/03/16.
 */
public class Menu {

    public static List<ParentObject> genrateMenu(Context context, int itemId) {
        ArrayList<ParentObject> list = new ArrayList<>();
        if (itemId == R.string.profile)
            list.add(new ParentMenuItem(R.string.profile, true,R.mipmap.profile, null));
        else
            list.add(new ParentMenuItem(R.string.profile, false,R.mipmap.profile, null));

        if (itemId == R.string.program_csx)
            list.add(new ParentMenuItem(R.string.program_csx, true,R.mipmap.program, null));
        else
            list.add(new ParentMenuItem(R.string.program_csx, false,R.mipmap.program, null));

        if (itemId == R.string.tnc)
            list.add(new ParentMenuItem(R.string.tnc, true,R.mipmap.tnc, null));
        else
            list.add(new ParentMenuItem(R.string.tnc, false,R.mipmap.tnc, null));

        if (itemId == R.string.faqs)
            list.add(new ParentMenuItem(R.string.faqs, true,R.mipmap.faq, null));
        else
            list.add(new ParentMenuItem(R.string.faqs, false,R.mipmap.faq, null));

        ArrayList<Object> childs = new ArrayList<>();
        childs.add(new ChildMenuItem(R.string.notification, false, true));
        childs.add(new ChildMenuItem(R.string.sound, false, true));
        childs.add(new ChildMenuItem(R.string.changepassword, false, false));
        if (itemId == R.string.settings)
            list.add(new ParentMenuItem(R.string.settings, true,R.mipmap.setting, childs));
        else
            list.add(new ParentMenuItem(R.string.settings, false,R.mipmap.setting, childs));

        if (itemId == R.string.contact)
            list.add(new ParentMenuItem(R.string.contact, true,R.mipmap.contact, null));
        else
            list.add(new ParentMenuItem(R.string.contact, false,R.mipmap.contact, null));

        list.add(new ParentMenuItem(R.string.logout, false,R.mipmap.logout, null));

        return list;
    }
}
