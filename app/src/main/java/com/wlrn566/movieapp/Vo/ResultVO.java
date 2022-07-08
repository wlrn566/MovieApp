package com.wlrn566.movieapp.Vo;

public class ResultVO {
    String result;
    UserVO user = new UserVO();

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

}
