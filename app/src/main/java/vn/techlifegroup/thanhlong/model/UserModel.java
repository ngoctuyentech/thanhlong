package vn.techlifegroup.thanhlong.model;

public class UserModel {

    String userName;
    String userCmnd;
    String userPhone;
    String userAddress;


    public UserModel(){

    }

    public UserModel(String userName, String userCmnd, String userPhone, String userAddress) {
        this.userName = userName;
        this.userCmnd = userCmnd;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
    }

    public String getNameUser() {
        return userName;
    }

    public String getUserCmnd() {
        return userCmnd;
    }

    public String getPhoneUser() {
        return userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }
}
