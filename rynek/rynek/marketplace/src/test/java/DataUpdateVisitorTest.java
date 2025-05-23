import marketplace.*;
import marketplace.visitor.*;
import marketplace.product.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataUpdateVisitorTest {
	private DataUpdateVisitor visitor;
	private Seller seller;
	private Buyer buyer;
	private Product essentialProduct;
	
	@BeforeEach
	void setUp() {
		visitor = new DataUpdateVisitor();
		seller = new Seller("TestSeller", 0.2);
		buyer = new Buyer("TestBuyer", 100.0);
		essentialProduct = new Product("Bread", 2.0, ProductType.ESSENTIAL);
		
		seller.addProductToSell(essentialProduct, 10, 1.5);
		buyer.addNeed(essentialProduct, 3);
	}
	
	@Test
	void testVisitSeller() {
		int initialInventory = seller.getInventory().get(essentialProduct);
		double initialMargin = seller.getMargin();
		assertEquals(initialMargin, 0.2);
		visitor.visit(seller);
		
		assertTrue(seller.getInventory().get(essentialProduct) > initialInventory);
		assertTrue(seller.getMargin() > 0);
	}
	
	@Test
	void testVisitBuyer() {
		double initialMoney = buyer.getMoney();
		int initialNeed = buyer.getNeeds().get(essentialProduct);
		visitor.visit(buyer);
		
		assertTrue(buyer.getMoney() > initialMoney);
		assertTrue(buyer.getNeeds().get(essentialProduct) >= initialNeed);
	}
}