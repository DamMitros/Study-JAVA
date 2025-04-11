package javamarkt.cartoperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javamarkt.Product;
import javamarkt.cartoperation.comparison.*;
import javamarkt.cartoperation.promotion.Promotion;


public class ShoppingCart {
  private Product[] products;
  private List<Promotion> promotions;
  private CompareProduct comparator;

  private static final int INITIAL_CAPACITY = 10;
  private int size = 0;

  public ShoppingCart(){
    this.products = new Product[10];
    this.promotions = new ArrayList<>();
    this.comparator = new CompositeComparator(
      new PriceCompare(false),
      new NameCompare(true)
    );
  }

  public void addProduct(Product product){
    if (size == products.length) {
      int newCapacity = products.length + INITIAL_CAPACITY;
      products = Arrays.copyOf(products, newCapacity);
    }
    products[size++] = product;
    sortProducts();
  }

  public void removeProduct(Product product){
    int index = findIndex(product);
    if (index != -1) {
      for (int i = index; i < size - 1; i++) {
        products[i] = products[i + 1];
      }
      products[size -1] = null;
      size--;
    }
  }

  public void addPromotion(Promotion promotion){
    this.promotions.add(promotion);
  }

  public void removePromotion(Promotion promotion){
    this.promotions.remove(promotion);
  }

  public List<Promotion> showPromotions(){
    return this.promotions;
  }

  private int findIndex(Product product){
    for (int i=0; i<size; i++){
      if (products[i]==product){
        return i;
      }
    }
    return -1;
  }

  private void sortProducts(){
    Product[] nonNullProducts = new Product[size];
    System.arraycopy(products, 0, nonNullProducts, 0, size);
    Arrays.sort(nonNullProducts, comparator::compare);
    System.arraycopy(nonNullProducts, 0, products, 0, size);
  }

  public Product[] getProducts(){
    Product[] result = new Product[size];
    System.arraycopy(products, 0, result, 0, size);
    return result;
  }

  public void setComparator(CompareProduct comparator){
    this.comparator = comparator;
    sortProducts();
  }
  
  public Product FindExtremeProduct(boolean findCheapest){
    if (size == 0){
      return null;
    }

    CompareProduct priceComparator = new PriceCompare(findCheapest);
    Product extremeProduct=products[0];
    for (int i=0; i<size; i++){
      if (priceComparator.compare(products[i], extremeProduct) < 0){
        extremeProduct = products[i];
      }
    }
    return extremeProduct;
  }

  public Product[] FindExtremeProducts(int n, boolean ascending){
    if (n<=0 || size == 0){
      return new Product[0];
    }

    Product[] sortedProducts = new Product[size];
    System.arraycopy(products, 0, sortedProducts, 0, size);
    CompareProduct extremeCompator = new CompositeComparator(
      new PriceCompare(ascending),
      new NameCompare(!ascending)
    );

    Arrays.sort(sortedProducts, extremeCompator::compare);
    Product[] result = new Product[Math.min(n, sortedProducts.length)];
    System.arraycopy(sortedProducts, 0, result, 0, result.length);
    return result;
  }

  public double calculateTotalPrice(){ 
    for (int i=0; i<size; i++){
      products[i].resetDiscountPrice();
    }

    double totalPrice = 0.0;
    for (int i=0; i<size; i++){
      totalPrice += products[i].getPrice();
    }

    List<Promotion> applicablePromotions = new ArrayList<>();
    for (Promotion promotion : promotions) {
      if (promotion.isApplicable(this)) {
        applicablePromotions.add(promotion);
      }
    }

    if (applicablePromotions.isEmpty()) {
      return totalPrice;
    }

    applicablePromotions.sort((p1, p2) -> 
      Double.compare(p2.calculatePromotion(this), p1.calculatePromotion(this)));

    double totalDiscount = 0.0;
    List<Promotion> appliedPromotions = new ArrayList<>();

    for (Promotion promotion : applicablePromotions) {
      double discount = promotion.calculatePromotion(this);
      if (discount > 0) {
        totalDiscount += discount;
        appliedPromotions.add(promotion);
      }
    }
      
    System.out.println("Applicable promotions:");
    for (Promotion promotion : applicablePromotions){
      System.out.println(promotion.getDescription());
    }
    System.out.println("Total price after promotions: " + totalPrice);
    return Math.round((totalPrice - totalDiscount)*100)/100.0;
  }
}
