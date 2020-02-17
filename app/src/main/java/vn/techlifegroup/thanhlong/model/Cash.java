package vn.techlifegroup.thanhlong.model;

public class Cash {
    String userUid;
    String userPhone;
    String cashValue;

    public Cash() {
    }

    public Cash(String userUid, String userPhone, String cashValue) {
        this.userUid = userUid;
        this.userPhone = userPhone;
        this.cashValue = cashValue;
    }

    public String getUserUid() {
        return userUid;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getCashValue() {
        return cashValue;
    }
}
