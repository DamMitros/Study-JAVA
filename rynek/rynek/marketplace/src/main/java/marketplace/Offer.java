package marketplace;

import marketplace.product.Product;

public class Offer {
  private final Product product;
  private double price;
  private final Seller seller;
  private int quantity;

  public Offer(Product product, double price, Seller seller, int quantity) {
    this.product = product;
    this.price = price;
    this.seller = seller;
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public Seller getSeller() {
    return seller;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getQuantity() {
    return quantity;
  }

  @Override
  public String toString() {
    return "Offer{" +
           "product=" + product.getName() +
           ", price=" + price +
           ", quantity=" + quantity +
           ", seller=" + seller.getName() +
           '}';
  }
}