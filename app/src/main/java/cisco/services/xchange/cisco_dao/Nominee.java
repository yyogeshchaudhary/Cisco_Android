package cisco.services.xchange.cisco_dao;

import java.io.Serializable;

/**
 * Created by yogi on 16/03/16.
 */
public class Nominee implements Serializable{
    private int id;
    private String name;
    private String designation;
    private String mobile;
    private String email;
    private String password;
    private String partnerName;
    private String partnerEmail;
    private String status;
    private boolean firstLogin;
    private int oldPoints;
    private int points;
    private String creatdOn;
    private String modifiedOn;
    private boolean isDeleted;
    private String userName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }

    public void setPartnerEmail(String partnerEmail) {
        this.partnerEmail = partnerEmail;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public int getOldPoints() {
        return oldPoints;
    }

    public void setOldPoints(int oldPoints) {
        this.oldPoints = oldPoints;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getCreatdOn() {
        return creatdOn;
    }

    public void setCreatdOn(String creatdOn) {
        this.creatdOn = creatdOn;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }
}
