package co.za.tax.dao;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import co.za.tax.model.Goods;
import co.za.tax.util.TaxUtils;


public class TestTaxDAO extends TestCase {


	@InjectMocks
	TaxCalculatorDAO taxCalculatorDAO;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testGetLocalTaxForExemptedGoods() {
		
		try {
			// given
			Goods goods = getBook(12.49, true);
			double expectedTax = 0.05;
			
			// when
			double result = taxCalculatorDAO.retrieveTax(goods);
			
			// then
			assertEquals(expectedTax, result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetLocalTaxForNoneExemptedGoods() {
		
		try {
			// given
			Goods goods = getCD(14.99, true);
			double expectedTax = 14.99 * 0.10;
			
			// when
			double result = taxCalculatorDAO.retrieveTax(goods);
			
			// then
			assertEquals(TaxUtils.twoDecimalRoundUp(expectedTax), result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetImportedTaxForExemptedGoods() {
		
		try {
			// given
			Goods goods = getBook(12.49, false);
			double expectedTax = 12.49 * 0.05;
			
			// when
			double result = taxCalculatorDAO.retrieveTax(goods);
			
			// then
			assertEquals(TaxUtils.twoDecimalRoundUp(expectedTax), result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetImportedTaxForNoneExemptedGoods() {
		
		try {
			// given
			Goods goods = getCD(14.99, false);
			double expectedTax = 14.99 * 0.15;
			
			// when
			double result = taxCalculatorDAO.retrieveTax(goods);
			
			// then
			assertEquals(TaxUtils.twoDecimalRoundUp(expectedTax), result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	private Goods getCD(Double price, boolean isLocalGoods) {
		Goods book = new Goods();
		book.setName("CD");
		book.setPrice(price);
		book.setLocalGoods(isLocalGoods);
		book.setQuantity(1);
		
		return book;
	}
	
	private Goods getBook(Double price, boolean isLocalGoods) {
		Goods book = new Goods();
		book.setName("book");
		book.setPrice(price);
		book.setQuantity(1);
		book.setLocalGoods(isLocalGoods);
		
		return book;
	}
}
