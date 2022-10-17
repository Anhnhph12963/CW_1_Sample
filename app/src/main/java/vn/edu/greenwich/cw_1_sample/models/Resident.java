package vn.edu.greenwich.cw_1_sample.models;

import java.io.Serializable;

public class Resident implements Serializable {
    protected long _id;
    protected String _name;
    protected String _destination;
    protected String _startDate;
    protected String _note;
    protected String _comment;
    protected int _owner;

    public Resident() {
        _id = -1;
        _name = null;
        _destination = null;
        _startDate = null;
        _owner = -1;
        _note = null;
        _comment =null;
    }

    public Resident(long _id, String _name, String _destination, String _startDate, String _note, String _comment, int _owner) {
        this._id = _id;
        this._name = _name;
        this._destination = _destination;
        this._startDate = _startDate;
        this._note = _note;
        this._comment = _comment;
        this._owner = _owner;
    }

    public String get_destination() { return _destination; }
    public void set_destination(String _destination) { this._destination = _destination; }

    public String get_note() { return _note; }
    public void set_note(String _note) { this._note = _note; }

    public String get_comment() { return _comment; }
    public void set_comment(String _comment) { this._comment = _comment; }

    public long getId() { return _id; }
    public void setId(long id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }

    public String getStartDate() {
        return _startDate;
    }
    public void setStartDate(String startDate) {
        _startDate = startDate;
    }

    public int getOwner() {
        return _owner;
    }
    public void setOwner(int owner) {
        _owner = owner;
    }
}