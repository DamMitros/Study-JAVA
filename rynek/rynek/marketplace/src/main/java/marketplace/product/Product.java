package marketplace.product;

import java.util.Objects;

public class Product {
  private final String name;
  private final double baseCost;
  private final ProductType type;
  
  public Product(String name, double baseCost, ProductType type) {
    this.name = name;
    this.baseCost = baseCost;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public double getBaseCost() {
    return baseCost;
  }

  public ProductType getType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (this==o) return true; 
    if (o==null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Double.compare(product.baseCost, baseCost) == 0 && 
           Objects.equals(name, product.name) && 
           type == product.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, baseCost, type);
  }

  @Override
  public String toString() {
    return "Product{" +
           "name='" + name + '\'' +
           ", baseCost=" + baseCost +
           ", type=" + type +
           '}';
  }
}
