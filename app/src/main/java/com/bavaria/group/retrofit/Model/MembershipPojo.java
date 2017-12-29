package com.bavaria.group.retrofit.Model;

import java.util.ArrayList;

/**
 * Created by Archirayan on 05-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */


public class MembershipPojo {

    String status, msg;
    ArrayList<MembershipDataPojo> data;

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

    public ArrayList<MembershipDataPojo> getData() {
        return data;
    }

    public void setData(ArrayList<MembershipDataPojo> data) {
        this.data = data;
    }
}
