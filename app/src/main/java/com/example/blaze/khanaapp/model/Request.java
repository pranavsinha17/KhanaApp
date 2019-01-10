package com.example.blaze.khanaapp.model;

import android.widget.EditText;

import java.util.List;

public class Request {
    private String phone;
    private String address;
    private String total;
    private List<Order> foods; //list of order
    private String status;

    public Request()
    {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Request(String aCommon, String name, String address, String total, List<Order> foods) {
        this.phone = aCommon;
        this.address = address;
        this.total = total;
        this.foods = foods;
        this.status="0";//by default                //0 for placed
                                                    //1 for shipping
                                                    //2 for shipped

    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }



}
