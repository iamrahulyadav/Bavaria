package com.bavaria.group.retrofit.Model;

import java.util.ArrayList;

/**
 * Created by Archirayan on 05-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */


public class WaterBillPojo {

   String status, msg;
   ArrayList<WaterBillDataPojo> data;

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

    public ArrayList<WaterBillDataPojo> getData() {
        return data;
    }

    public void setData(ArrayList<WaterBillDataPojo> data) {
        this.data = data;
    }
}
