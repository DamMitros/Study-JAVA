package marketplace.visitor;

import marketplace.Seller;
import marketplace.Buyer;

public interface Visitor {
  void visit(Seller seller);
  void visit(Buyer buyer);
}
