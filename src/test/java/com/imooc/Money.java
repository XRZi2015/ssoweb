package com.imooc;

/**
 * Created by Administrator on 2017/4/4.
 */
public class Money {
    //余额
    private Integer bank=100;
    //欠额
    private Integer Arrears=0;

    public Integer getBank() {
        return bank;
    }

    public void setBank(Integer bank) {
        this.bank = bank;
    }

    public Integer getArrears() {
        return Arrears;
    }

    public void setArrears(Integer arrears) {
        Arrears = arrears;
    }
}
