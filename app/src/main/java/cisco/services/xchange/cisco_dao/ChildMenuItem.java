package cisco.services.xchange.cisco_dao;

/**
 * Created by yogi on 15/03/16.
 */
public class ChildMenuItem{
    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public boolean isOn() {
        return isOn;
    }

    public boolean isSelected() {
        return selector;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public void setIsSelected(boolean selected) {
        this.selector = selected;
    }

    public ChildMenuItem(int name, boolean isOn, boolean selector) {
        this.name = name;
        this.isOn = isOn;
        this.selector = selector;
    }

    public int name;
    public boolean isOn;
    public boolean selector;
}
