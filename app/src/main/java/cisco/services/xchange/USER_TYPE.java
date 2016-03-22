package cisco.services.xchange;

/**
 * Created by yogi on 16/03/16.
 */
public enum USER_TYPE {
    PARTNER("P"),NOMINEE("N");
    private String userType;
    USER_TYPE(String userType){
        this.userType = userType;
    }
}
