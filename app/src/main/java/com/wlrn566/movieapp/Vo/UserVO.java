package com.wlrn566.movieapp.Vo;

public class UserVO {
    String seq;
    String id;
    String pw;

    public UserVO() {
    }

    public UserVO(String seq, String id, String pw) {
        this.seq = seq;
        this.id = id;
        this.pw = pw;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "seq='" + seq + '\'' +
                ", id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                '}';
    }

}


