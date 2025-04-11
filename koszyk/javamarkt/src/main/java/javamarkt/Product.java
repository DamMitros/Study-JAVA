package javamarkt;

public class Product {
    private String code;
    private String name;
    private Double price;
    private Double discountPrice;

    public Product(String code, String name, double price){
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = price;
    }

    public String getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    public Double getPrice(){
        return price;
    }

    public Double getDiscountPrice(){
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice){
        this.discountPrice = discountPrice;
    }

    public void resetDiscountPrice(){
        this.discountPrice = price;
    }
}