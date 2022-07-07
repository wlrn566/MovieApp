package com.wlrn566.movieapp.Vo;

public class ReviewVO {
    String seq;
    String id;
    String movieNm;
    String content;
    String crt_dt;

    public ReviewVO(String seq, String id, String movieNm, String content, String crt_dt) {
        this.seq = seq;
        this.id = id;
        this.movieNm = movieNm;
        this.content = content;
        this.crt_dt = crt_dt;
    }

    @Override
    public String toString() {
        return "ReviewVO{" +
                "seq='" + seq + '\'' +
                ", id='" + id + '\'' +
                ", movieNm='" + movieNm + '\'' +
                ", content='" + content + '\'' +
                ", crt_dt='" + crt_dt + '\'' +
                '}';
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

    public String getMovieNm() {
        return movieNm;
    }

    public void setMovieNm(String movieNm) {
        this.movieNm = movieNm;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCrt_dt() {
        return crt_dt;
    }

    public void setCrt_dt(String crt_dt) {
        this.crt_dt = crt_dt;
    }
}
