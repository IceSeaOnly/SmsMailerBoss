package com.qdxiaogutou.eye;


/**
 * Created by Administrator on 2016/12/18.
 */
public class MsgEntity {
    private int id;
    private Long time;
    private String jdata;

    public MsgEntity(Long time, String jdata) {
        this.time = time;
        this.jdata = jdata;
    }

    public MsgEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getJdata() {
        return jdata;
    }

    public void setJdata(String jdata) {
        this.jdata = jdata;
    }
}
