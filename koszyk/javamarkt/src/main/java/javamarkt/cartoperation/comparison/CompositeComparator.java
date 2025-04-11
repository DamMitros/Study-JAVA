package javamarkt.cartoperation.comparison;

import javamarkt.Product; 

public class CompositeComparator implements CompareProduct {
  private final CompareProduct[] comparators;

  public CompositeComparator(CompareProduct... comparators) {
    this.comparators = comparators;
  }

  @Override
  public int compare(Product p1, Product p2) {
    for (CompareProduct comparator : comparators) {
      int result = comparator.compare(p1, p2);
      if (result != 0) {
        return result;
      };
    }
    return 0;
  }
}