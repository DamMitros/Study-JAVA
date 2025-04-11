package javamarkt.cartoperation.comparison;

import javamarkt.Product;

public class PriceCompare implements CompareProduct {
  private final boolean ascending;

  public PriceCompare(boolean ascending) {
    this.ascending = ascending;
  }
  
  @Override
  public int compare(Product p1,Product p2){
    int result = p1.getPrice().compareTo(p2.getPrice());
    return ascending ? result : -result;
  }
}