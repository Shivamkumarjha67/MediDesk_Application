/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MediDeskApp.Pojo;

/**
 *
 * @author shivam kumar jha
 */
public class ReceptionistPojo {

    public String getRecpId() {
        return recpId;
    }

    public void setRecpId(String recpId) {
        this.recpId = recpId;
    }

    public String getRecpName() {
        return recpName;
    }

    public void setRecpName(String recpName) {
        this.recpName = recpName;
    }

    public String getRecpGender() {
        return recpGender;
    }

    public void setRecpGender(String recpGender) {
        this.recpGender = recpGender;
    }
    private String recpId;
    private String recpName;
    private String recpGender;
    
}
