package com.example.livestockvendorapp.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Orderlist {

    private String phone;
    private String date;
    private String location;
    private String price;
    private String count;
    private List<String> order;
    private String status;
    private  String docid;
    private String sellerphone;


    public Orderlist() {
    }

    public Orderlist(String phone, String date, String location, String price, String count, List<String> order, String status) {
        this.phone = phone;
        this.date = date;
        this.location = location;
        this.price = price;
        this.count = count;
        this.order = order;
        this.status=status;
    }


    public Orderlist(String phone, String date, String location, String price, String count, String status) {
        this.phone = phone;
        this.date = date;
        this.location = location;
        this.price = price;
        this.count = count;

        this.status=status;
    }


    public List<String> getOrder() {
        return order;
    }

    public void setOrder(List<String> order) {
        this.order = order;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getSellerphone() {
        return sellerphone;
    }

    public void setSellerphone(String sellerphone) {
        this.sellerphone = sellerphone;
    }
}
