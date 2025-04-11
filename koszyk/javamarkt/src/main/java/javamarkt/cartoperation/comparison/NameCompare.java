package javamarkt.cartoperation.comparison;

import javamarkt.Product;

public class NameCompare implements CompareProduct {
  private final boolean ascending;

  public NameCompare(boolean ascending) {
    this.ascending = ascending;
  }
  
  @Override
  public int compare(Product p1,Product p2){
    int result = p1.getName().compareTo(p2.getName());
    return ascending ? result : -result;
  }
}