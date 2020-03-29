package com.example.a86156.lunbo_2.gongdan;

public class GongdanEntity {
    private String color; //文本框颜色
    private String jhjhrq; //计划交货日期
    private String gdbh;  //工单编号
    private String cpbh; //产品编号
    private String cpmc; //产品名称
    private String jhsl; //计划数量
    private String yzj; //压铸机
    private String cpf; //除披峰
    private String jj; //机加
    private String pg; //抛光
    private String dd; //电镀
    private String bz; //包装


    public GongdanEntity(String color, String jhjhrq, String gdbh, String cpbh, String cpmc, String jhsl, String yzj, String cpf, String jj, String pg, String dd, String bz) {
        this.color = color;
        this.jhjhrq = jhjhrq;
        this.gdbh = gdbh;
        this.cpbh = cpbh;
        this.cpmc = cpmc;
        this.jhsl = jhsl;
        this.yzj = yzj;
        this.cpf = cpf;
        this.jj = jj;
        this.pg = pg;
        this.dd = dd;
        this.bz = bz;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getJhjhrq() {
        return jhjhrq;
    }

    public void setJhjhrq(String jhjhrq) {
        this.jhjhrq = jhjhrq;
    }

    public String getGdbh() {
        return gdbh;
    }

    public void setGdbh(String gdbh) {
        this.gdbh = gdbh;
    }

    public String getCpbh() {
        return cpbh;
    }

    public void setCpbh(String cpbh) {
        this.cpbh = cpbh;
    }

    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    public String getJhsl() {
        return jhsl;
    }

    public void setJhsl(String jhsl) {
        this.jhsl = jhsl;
    }

    public String getYzj() {
        return yzj;
    }

    public void setYzj(String yzj) {
        this.yzj = yzj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getJj() {
        return jj;
    }

    public void setJj(String jj) {
        this.jj = jj;
    }

    public String getPg() {
        return pg;
    }

    public void setPg(String pg) {
        this.pg = pg;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
