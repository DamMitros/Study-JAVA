import marketplace.product.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

	@Test
	void productCreationAndGetters() {
		Product product = new Product("Milk", 2.5, ProductType.ESSENTIAL);
		assertEquals("Milk", product.getName());
		assertEquals(2.5, product.getBaseCost());
		assertEquals(ProductType.ESSENTIAL, product.getType());
	}

	@Test
	void productEqualsAndHashCode() {
		Product product1 = new Product("Milk", 2.5, ProductType.ESSENTIAL);
		Product product2 = new Product("Milk", 2.5, ProductType.ESSENTIAL);
		Product product3 = new Product("Bread", 1.0, ProductType.ESSENTIAL);

		assertEquals(product1, product2);
		assertEquals(product1.hashCode(), product2.hashCode());
		assertNotEquals(product1, product3);
	}

	@Test
	void productToString() {
		Product product = new Product("Milk", 2.5, ProductType.ESSENTIAL);
		String expectedString = "Product{name='Milk', baseCost=2.5, type=ESSENTIAL}";
		assertEquals(expectedString, product.toString());
	}

	@Test
	void productNotEqualsNull() {
		Product product = new Product("Milk", 2.5, ProductType.ESSENTIAL);
		assertNotEquals(product, null);
	}

	@Test
	void productNotEqualsDifferentClass() {
		Product product = new Product("Milk", 2.5, ProductType.ESSENTIAL);
		String notAProduct = "Not a product";
		assertNotEquals(product, notAProduct);
	}

	@Test
	void productNotEqualsDifferentName() {
		Product product1 = new Product("Milk", 2.5, ProductType.ESSENTIAL);
		Product product2 = new Product("Bread", 2.5, ProductType.ESSENTIAL);
		assertNotEquals(product1, product2);
	}

	@Test
	void productNotEqualsDifferentBaseCost() {
		Product product1 = new Product("Milk", 2.5, ProductType.ESSENTIAL);
		Product product2 = new Product("Milk", 3.0, ProductType.ESSENTIAL);
		assertNotEquals(product1, product2);
	}

	@Test
	void productNotEqualsDifferentType() {
		Product product1 = new Product("Milk", 2.5, ProductType.ESSENTIAL);
		Product product2 = new Product("Milk", 2.5, ProductType.LUXURY);
		assertNotEquals(product1, product2);
	}
}