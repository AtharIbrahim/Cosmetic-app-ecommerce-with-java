package com.example.shoesecommerceapp.fetchBestsellerRecyclerview;

public class responsemodel {

    private String productid, name, price, description, image, quantity;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public responsemodel(String productid, String name, String price, String description, String image, String quantity) {
        this.productid = productid;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
    }

    public responsemodel(){

    }
}
