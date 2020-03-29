package com.example.a86156.lunbo_2.bolatu;

public class Entity {
    private String color; //文本框颜色
    private String data;  //日期
    private String ydg;   //应到岗人数
    private String sdg;   //实际到岗人数
    private String bzgs;  //标准工时
    private String zcsc;  // 正常生产
    private String jdl;   // 稼动率
    private String mbjdl; // 目标稼动率
    private String jhsl; //计划数量
    private String sjsl; //实际数量
    private String jhdcl; //计划达成率
    private String mbdcl; //目标达成率

    public Entity(String color, String data, String ydg, String sdg, String bzgs, String zcsc, String jdl, String mbjdl, String jhsl, String sjsl, String jhdcl, String mbdcl) {
        this.color = color;
        this.data = data;
        this.ydg = ydg;
        this.sdg = sdg;
        this.bzgs = bzgs;
        this.zcsc = zcsc;
        this.jdl = jdl;
        this.mbjdl = mbjdl;
        this.jhsl = jhsl;
        this.sjsl = sjsl;
        this.jhdcl = jhdcl;
        this.mbdcl = mbdcl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getYdg() {
        return ydg;
    }

    public void setYdg(String ydg) {
        this.ydg = ydg;
    }

    public String getSdg() {
        return sdg;
    }

    public void setSdg(String sdg) {
        this.sdg = sdg;
    }

    public String getBzgs() {
        return bzgs;
    }

    public void setBzgs(String bzgs) {
        this.bzgs = bzgs;
    }

    public String getZcsc() {
        return zcsc;
    }

    public void setZcsc(String zcsc) {
        this.zcsc = zcsc;
    }

    public String getJdl() {
        return jdl;
    }

    public void setJdl(String jdl) {
        this.jdl = jdl;
    }

    public String getMbjdl() {
        return mbjdl;
    }

    public void setMbjdl(String mbjdl) {
        this.mbjdl = mbjdl;
    }

    public String getJhsl() {
        return jhsl;
    }

    public void setJhsl(String jhsl) {
        this.jhsl = jhsl;
    }

    public String getSjsl() {
        return sjsl;
    }

    public void setSjsl(String sjsl) {
        this.sjsl = sjsl;
    }

    public String getJhdcl() {
        return jhdcl;
    }

    public void setJhdcl(String jhdcl) {
        this.jhdcl = jhdcl;
    }

    public String getMbdcl() {
        return mbdcl;
    }

    public void setMbdcl(String mbdcl) {
        this.mbdcl = mbdcl;
    }
}
