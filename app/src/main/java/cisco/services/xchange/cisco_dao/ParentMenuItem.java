package cisco.services.xchange.cisco_dao;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by yogi on 15/03/16.
 */
public class ParentMenuItem implements ParentObject {

    public int name;
    public boolean selected;
    public int imageId;
    private List<Object> childs;

    public ParentMenuItem(int name, boolean selected, int imageId, List<Object> childs) {
        this.name = name;
        this.selected = selected;
        this.imageId = imageId;
        this.childs = childs;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    @Override
    public List<Object> getChildObjectList() {
        return childs;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        childs = list;
    }
}
