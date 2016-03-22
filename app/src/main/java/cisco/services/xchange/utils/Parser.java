package cisco.services.xchange.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cisco.services.xchange.cisco_dao.Nominee;
import cisco.services.xchange.cisco_dao.Partner;

/**
 * Created by yogi on 16/03/16.
 */
public class Parser {

    public static Partner getPartner(JSONObject userJson) throws Exception{
        Partner partner = new Partner();
        partner.setId(Integer.parseInt(userJson.getString("Id")));
        partner.setOrganisation(userJson.getString("OrgName"));
        partner.setEmail(userJson.getString("Email"));
        partner.setPassword(userJson.getString("Password"));
        partner.setFirstName(userJson.getString("FName"));
        partner.setLastName(userJson.getString("LName"));
        partner.setAddressLine1(userJson.getString("Add1"));
        partner.setAddressLine2(userJson.getString("Add2"));
        partner.setCity(userJson.getString("City"));
        partner.setRegion(userJson.getString("Region"));
        partner.setPincode(userJson.getString("Pincode"));
        partner.setMobile(userJson.getString("Mobile"));
        partner.setStdCode(userJson.getString("STD"));
        partner.setPhone(userJson.getString("Phone"));
        partner.setWebsite(userJson.getString("Website"));
        partner.setHqLocation(userJson.getString("HOLocation"));
        partner.setCategory(userJson.getString("Category"));
        partner.setTnc(userJson.getBoolean("AgreeTC"));
        partner.setOldPoints(Integer.parseInt(userJson.getString("OldPoints")));
        partner.setPoints(Integer.parseInt(userJson.getString("Points")));
        partner.setNominations(Integer.parseInt(userJson.getString("Nomination")));
        partner.setCreatedDate(userJson.getString("CreatedOn"));
        partner.setModifiedDate(userJson.getString("ModifiedOn"));
        partner.setIsDeleted(userJson.getBoolean("IsDeleted"));
        return partner;
    }

    public static Nominee getNominee(JSONObject userJson) throws Exception {
        Nominee nominee = new Nominee();
        nominee.setId(Integer.parseInt(userJson.getString("Id")));
        nominee.setName(userJson.getString("Name"));
        nominee.setDesignation(userJson.getString("Designation"));
        nominee.setMobile(userJson.getString("Mobile"));
        nominee.setEmail(userJson.getString("Email"));
        nominee.setPassword(userJson.getString("Password"));
        nominee.setPartnerName(userJson.getString("PartnerName"));
        nominee.setPartnerEmail(userJson.getString("PartnerEmail"));
        nominee.setStatus(userJson.getString("Status"));
        nominee.setFirstLogin(userJson.getBoolean("FirstLogin"));
        nominee.setOldPoints(Integer.parseInt(userJson.getString("OldPoints")));
        nominee.setPoints(Integer.parseInt(userJson.getString("Points")));
        nominee.setCreatdOn(userJson.getString("CreatedOn"));
        nominee.setModifiedOn(userJson.getString("ModifiedOn"));
        nominee.setIsDeleted(userJson.getBoolean("IsDeleted"));
        return nominee;
    }

    public static ArrayList<Nominee> getNomineeList(JSONArray nominees) throws Exception {
        ArrayList<Nominee> nomineeList = new ArrayList<>();
        for (int index =0; index < nominees.length(); index++){
            nomineeList.add(getNominee(nominees.getJSONObject(index)));
        }

        return nomineeList;
    }
}
