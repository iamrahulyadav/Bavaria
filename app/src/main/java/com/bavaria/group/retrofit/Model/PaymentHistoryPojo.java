package com.bavaria.group.retrofit.Model;

import java.util.ArrayList;

/**
 * Created by Archirayan on 07-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */


public class PaymentHistoryPojo {

    String status, msg;
    ArrayList<PaymentHistoryDataPojo> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<PaymentHistoryDataPojo> getData() {
        return data;
    }

    public void setData(ArrayList<PaymentHistoryDataPojo> data) {
        this.data = data;
    }
}
