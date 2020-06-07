package com.example.livestockvendorapp.Model;

public class Animal {


    double Cost;
    String Weight;
    String Image;
    String Name;
    String Seller_id;
    String Type;
    String Status;
    String Description;
    String Docid;

    public Animal() {
    }

    public Animal(double cost, String weight, String description, String image, String name, String seller_id, String type, String status) {
        Cost = cost;
        Weight = weight;
        Description = description;
        Image = image;
        Name = name;
        Seller_id = seller_id;
        Type = type;
        this.Status = status;
    }


    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSeller_id() {
        return Seller_id;
    }

    public void setSeller_id(String seller_id) {
        Seller_id = seller_id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    public String getDocid() {
        return Docid;
    }

    public void setDocid(String docid) {
        Docid = docid;
    }
}
