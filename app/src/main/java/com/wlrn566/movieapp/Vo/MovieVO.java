package com.wlrn566.movieapp.Vo;

import java.io.Serializable;

public class MovieVO implements Serializable {
    String rnum;
    String rank;
    String rankInten;
    String rankOldAndNew;
    String movieCd;
    String movieNm;
    String openDt;
    String salesAmt;
    String salesShare;
    String salesInten;
    String salesChange;
    String salesAcc;
    String audiCnt;
    String audiInten;
    String audiChange;
    String audiAcc;
    String scrnCnt;
    String showCnt;
    String pubDate;
    String image;
    String director;
    String actor;
    String userRating;

    public MovieVO() {
    }

    public MovieVO(String rank, String movieNm, String openDt, String audiAcc, String pubDate, String image, String director, String actor, String userRating) {
        this.rank = rank;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.audiAcc = audiAcc;
        this.pubDate = pubDate;
        this.image = image;
        this.director = director;
        this.actor = actor;
        this.userRating = userRating;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getRnum() {
        return rnum;
    }

    public void setRnum(String rnum) {
        this.rnum = rnum;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRankInten() {
        return rankInten;
    }

    public void setRankInten(String rankInten) {
        this.rankInten = rankInten;
    }

    public String getRankOldAndNew() {
        return rankOldAndNew;
    }

    public void setRankOldAndNew(String rankOldAndNew) {
        this.rankOldAndNew = rankOldAndNew;
    }

    public String getMovieCd() {
        return movieCd;
    }

    public void setMovieCd(String movieCd) {
        this.movieCd = movieCd;
    }

    public String getMovieNm() {
        return movieNm;
    }

    public void setMovieNm(String movieNm) {
        this.movieNm = movieNm;
    }

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public String getSalesAmt() {
        return salesAmt;
    }

    public void setSalesAmt(String salesAmt) {
        this.salesAmt = salesAmt;
    }

    public String getSalesShare() {
        return salesShare;
    }

    public void setSalesShare(String salesShare) {
        this.salesShare = salesShare;
    }

    public String getSalesInten() {
        return salesInten;
    }

    public void setSalesInten(String salesInten) {
        this.salesInten = salesInten;
    }

    public String getSalesChange() {
        return salesChange;
    }

    public void setSalesChange(String salesChange) {
        this.salesChange = salesChange;
    }

    public String getSalesAcc() {
        return salesAcc;
    }

    public void setSalesAcc(String salesAcc) {
        this.salesAcc = salesAcc;
    }

    public String getAudiCnt() {
        return audiCnt;
    }

    public void setAudiCnt(String audiCnt) {
        this.audiCnt = audiCnt;
    }

    public String getAudiInten() {
        return audiInten;
    }

    public void setAudiInten(String audiInten) {
        this.audiInten = audiInten;
    }

    public String getAudiChange() {
        return audiChange;
    }

    public void setAudiChange(String audiChange) {
        this.audiChange = audiChange;
    }

    public String getAudiAcc() {
        return audiAcc;
    }

    public void setAudiAcc(String audiAcc) {
        this.audiAcc = audiAcc;
    }

    public String getScrnCnt() {
        return scrnCnt;
    }

    public void setScrnCnt(String scrnCnt) {
        this.scrnCnt = scrnCnt;
    }

    public String getShowCnt() {
        return showCnt;
    }

    public void setShowCnt(String showCnt) {
        this.showCnt = showCnt;
    }


    @Override
    public String toString() {
        return "MovieVO{" +
                "rnum='" + rnum + '\'' +
                ", rank='" + rank + '\'' +
                ", rankInten='" + rankInten + '\'' +
                ", rankOldAndNew='" + rankOldAndNew + '\'' +
                ", movieCd='" + movieCd + '\'' +
                ", movieNm='" + movieNm + '\'' +
                ", openDt='" + openDt + '\'' +
                ", salesAmt='" + salesAmt + '\'' +
                ", salesShare='" + salesShare + '\'' +
                ", salesInten='" + salesInten + '\'' +
                ", salesChange='" + salesChange + '\'' +
                ", salesAcc='" + salesAcc + '\'' +
                ", audiCnt='" + audiCnt + '\'' +
                ", audiInten='" + audiInten + '\'' +
                ", audiChange='" + audiChange + '\'' +
                ", audiAcc='" + audiAcc + '\'' +
                ", scrnCnt='" + scrnCnt + '\'' +
                ", showCnt='" + showCnt + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", image='" + image + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", userRating='" + userRating + '\'' +
                '}';
    }
}
