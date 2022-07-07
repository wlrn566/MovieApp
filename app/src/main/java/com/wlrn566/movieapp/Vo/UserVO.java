package com.wlrn566.movieapp.Vo;

import com.google.gson.JsonObject;

public class UserVO {

    String result;
    JsonObject user;  // google 의 JsonObject 로 받아야함 (JSONObject X)  --> "user"가 Json 으로 되어있음.

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public JsonObject getUser() {
        return user;
    }

    public void setUser(JsonObject user) {
        this.user = user;
    }

    class user{
        String id;
        String pw;

        @Override
        public String toString() {
            return "user{" +
                    "id='" + id + '\'' +
                    ", pw='" + pw + '\'' +
                    '}';
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
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "result='" + result + '\'' +
                ", user=" + user +
                '}';
    }
}


