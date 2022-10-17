package vn.edu.greenwich.cw_1_sample.models;

import java.io.Serializable;

public class Request implements Serializable {
    protected long _id;
    protected int _money;
    protected String _content;
    protected String _date;
    protected String _time;
    protected String _type;
    protected long _residentId;

    public Request() {
        _id = -1;
        _content = null;
        _money = 0;
        _date = null;
        _time = null;
        _type = null;
        _residentId = -1;
    }

    public Request(long id,int money, String content, String date, String time, String type, long residentId) {
        _id = id;
        _money = money;
        _content = content;
        _date = date;
        _time = time;
        _type = type;
        _residentId = residentId;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public int getMoney() {
        return _money;
    }

    public void setMoney(int money) {
        _money = money;
    }

    public String getContent() {
        return _content;
    }

    public void setContent(String content) {
        _content = content;
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String date) {
        _date = date;
    }

    public String getTime() {
        return _time;
    }

    public void setTime(String time) {
        _time = time;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

    public long getResidentId() {
        return _residentId;
    }

    public void setResidentId(long residentId) {
        _residentId = residentId;
    }
}