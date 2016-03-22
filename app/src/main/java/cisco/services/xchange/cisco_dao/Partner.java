package cisco.services.xchange.cisco_dao;

import java.util.ArrayList;

/**
 * Created by yogi on 14/03/16.
 */
public class Partner {
    private int id;
    private String organisation;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String region;
    private String pincode;
    private String mobile;
    private String stdCode;
    private String phone;
    private String website;
    private String hqLocation;
    private String category;
    private boolean tnc;
    private int oldPoints;
    private int points;
    private int nominations;
    private String createdDate;
    private String modifiedDate;
    private boolean isDeleted;
    private ArrayList<Nominee> nominees;
    private String userName;
    private String salesAmount;

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStdCode() {
        return stdCode;
    }

    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getHqLocation() {
        return hqLocation;
    }

    public void setHqLocation(String hqLocation) {
        this.hqLocation = hqLocation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isTnc() {
        return tnc;
    }

    public void setTnc(boolean tnc) {
        this.tnc = tnc;
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

    public int getNominations() {
        return nominations;
    }

    public void setNominations(int nominations) {
        this.nominations = nominations;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setNominees(ArrayList<Nominee> nominees) {
        this.nominees = nominees;
    }

    public ArrayList<Nominee> getNominees(){
        return this.nominees;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSalesAmount(String salesAmount) {
        this.salesAmount = salesAmount;
    }

    public String getSalesAmount(){
        return salesAmount;
    }
}
