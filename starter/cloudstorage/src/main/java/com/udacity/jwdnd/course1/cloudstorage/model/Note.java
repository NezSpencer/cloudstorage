package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteid;
    private String notetitle;
    private Integer userid;
    private String notedescription;

    public Note(String notetitle, String notedescription) {
        this.noteid = null;
        this.notetitle = notetitle;
        this.userid = null;
        this.notedescription = notedescription;
    }

    public Note() {
    }

    public Integer getNoteid() {
        return noteid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }
}
