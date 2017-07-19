package co.za.tax.calculator;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.za.tax.dao.TaxCalculatorDAO;
import co.za.tax.model.Goods;
import co.za.tax.service.TaxCalculatorService;
import co.za.tax.util.TaxUtils;


public class TestTaxCalculator extends TestCase {

	@InjectMocks
	TaxCalculatorService taxCalculatorService;

	@Mock
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
	public void testCalculateTaxForNonExemptedLocalGoods() {

		try {
			// given
			Goods cd = getCD(12.49);
			double expectedTax = 16.49;
			
			when(taxCalculatorDAO.retrieveTax(cd)).thenReturn(expectedTax);
			
			// when
			double result = taxCalculatorService.getTax(cd);

			// then
			verify(taxCalculatorDAO, times(1)).retrieveTax(cd);
			assertEquals(expectedTax, result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetPriceFromSaleSentence() {
		
		try {
			// given
			String sentence = "1 book at 12.49";
			double expectedPrice = 12.49;
			
			// when
			double result = taxCalculatorService.getPrice(sentence);
			
			// then
			assertEquals(expectedPrice, result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetNameOfGoodsFromLocalSaleSentence() {
		
		try {
			// given
			String sentence = "1 book at 12.49";
			String expectedGoodsName = "book";
			
			// when
			String result = taxCalculatorService.getGoodsName(sentence);
			
			// then
			assertEquals(expectedGoodsName, result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetNameOfGoodsFromExportedSaleSentence() {
		
		try {
			// given
			String sentence = "1 imported box of chocolates at 10.00";
			String expectedGoodsName = "imported box of chocolates";
			
			// when
			String result = taxCalculatorService.getGoodsName(sentence);
			
			// then
			assertEquals(expectedGoodsName, result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetQuantityOfGoodsFromSaleSentence() {
		
		try {
			// given
			String sentence = "1 book at 12.49";
			int expectedGoodsQuantity = 1;
			
			// when
			int result = taxCalculatorService.getGoodsQuantity(sentence);
			
			// then
			assertEquals(expectedGoodsQuantity, result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetLocalGoodsFromSaleSentence() {
		
		try {
			// given
			String sentence = "1 book at 12.49";
			boolean expectedLocalGoods = true;
			
			// when
			boolean result = TaxUtils.isLocalGoods(sentence);
			
			// then
			assertEquals(expectedLocalGoods, result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetGoodsObjectFromSaleSentence() {
		
		try {
			// given
			String sentence = "2 book at 12.49";
			Goods expectedGoods = getBook(12.49, true);
			
			// when
			Goods result = taxCalculatorService.getGoods(sentence);
			
			// then
			assertEquals(expectedGoods, result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetTaxSentenceForLocalGoods() {
		
		try {
			// given
			String sentence = "1 book at 12.49";
			String expectedTaxSentence = "1 book: 12.49";
			
			// when
			String result = taxCalculatorService.getTaxResults(sentence);
			
			// then
			assertEquals(expectedTaxSentence, result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetTaxSentenceForScrambledSentence() {
		
		try {
			// given
			String sentence = "1 box of imported chocolates at 11.25";
			String expectedTaxSentence = "1 imported box of chocolates: 11.25";
			
			// when
			String result = taxCalculatorService.getTaxResults(sentence);
			
			// then
			assertEquals(expectedTaxSentence, result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	private Goods getCD(Double price) {
		Goods book = new Goods();
		book.setName("CD");
		book.setPrice(price);
		
		return book;
	}
	
	private Goods getBook(Double price, boolean isLocalGoods) {
		Goods book = new Goods();
		book.setName("book");
		book.setPrice(price);
		book.setQuantity(2);
		book.setLocalGoods(isLocalGoods);
		
		return book;
	}
}
