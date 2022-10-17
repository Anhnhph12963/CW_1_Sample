package vn.edu.greenwich.cw_1_sample.models;

public class UploadResponse {
    private String uploadResponseCode;
    private String userid;
    private Integer number;
    private String names;
    private String message;

    public UploadResponse() {
    }

    public UploadResponse(String uploadResponseCode, String userid, Integer number, String names, String message) {
        this.uploadResponseCode = uploadResponseCode;
        this.userid = userid;
        this.number = number;
        this.names = names;
        this.message = message;
    }

    public String getUploadResponseCode() {
        return uploadResponseCode;
    }

    public void setUploadResponseCode(String uploadResponseCode) {
        this.uploadResponseCode = uploadResponseCode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
