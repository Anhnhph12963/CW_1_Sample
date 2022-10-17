package vn.edu.greenwich.cw_1_sample.models;

import java.util.List;

public class UploadModels {
    private String userId;
    private List<detailListChild> detailList;

    public UploadModels() {
    }

    public UploadModels(String userId, List<detailListChild> detailList) {
        this.userId = userId;
        this.detailList = detailList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<detailListChild> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<detailListChild> detailList) {
        this.detailList = detailList;
    }

    @Override
    public String toString() {
        String a="";
        for (int i =0 ;i <detailList.size();i++) {
            if (i==0){
                a += detailList.get(i).toString();
            }else {
                a += ","+detailList.get(i).toString();
            }


        }
        return "{" +
                "\"userId\":" +"\""+ userId + "\",\"detailList\":" + "[" +a+"]"+
                '}';
    }
}
