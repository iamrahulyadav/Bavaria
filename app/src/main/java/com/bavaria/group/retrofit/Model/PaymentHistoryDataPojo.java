package com.bavaria.group.retrofit.Model;

/**
 * Created by Archirayan on 14-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */

public class PaymentHistoryDataPojo {

    String project_name, Payment_towards, Transaction_Id, Transaction_Date, Transaction_Time, Amount_Paid;

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getPayment_towards() {
        return Payment_towards;
    }

    public void setPayment_towards(String payment_towards) {
        Payment_towards = payment_towards;
    }

    public String getTransaction_Id() {
        return Transaction_Id;
    }

    public void setTransaction_Id(String transaction_Id) {
        Transaction_Id = transaction_Id;
    }

    public String getTransaction_Date() {
        return Transaction_Date;
    }

    public void setTransaction_Date(String transaction_Date) {
        Transaction_Date = transaction_Date;
    }

    public String getTransaction_Time() {
        return Transaction_Time;
    }

    public void setTransaction_Time(String transaction_Time) {
        Transaction_Time = transaction_Time;
    }

    public String getAmount_Paid() {
        return Amount_Paid;
    }

    public void setAmount_Paid(String amount_Paid) {
        Amount_Paid = amount_Paid;
    }
}
