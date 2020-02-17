package vn.techlifegroup.thanhlong.model;

public class QrCodeModel {

    String codeType;
    String productName;
    String disCountRate;
    String cashValue;
    String codeValue;

    public QrCodeModel() {
    }

    public QrCodeModel(String codeType, String disCountRate, String cashValue) {
        this.codeType = codeType;
        this.disCountRate = disCountRate;
        this.cashValue = cashValue;
    }

    public QrCodeModel(String codeType, String productName, String disCountRate, String cashValue) {
        this.codeType = codeType;
        this.productName = productName;
        this.disCountRate = disCountRate;
        this.cashValue = cashValue;
    }

    public QrCodeModel(String codeType, String codeValue) {
        this.codeType = codeType;
        this.codeValue = codeValue;
    }

    public String getCodeType() {
        return codeType;
    }

    public String getDisCountRate() {
        return disCountRate;
    }

    public String getCashValue() {
        return cashValue;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public String getProductName() {
        return productName;
    }
}
