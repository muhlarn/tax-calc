package co.za.tax.application;

import co.za.tax.model.Goods;
import co.za.tax.service.TaxCalculatorService;
import co.za.tax.util.TaxUtils;

public class CalculateTax {

	static TaxCalculatorService taxService = new TaxCalculatorService();

	public static void main(String[] args) {
		printInput1();
		printInput2();
		printInput3();
		
	}
	
	public static void printInput1() {
		
		System.out.println("\n-----INPUT 1------");
		String book = "1 book at 12.49";
		String cd = "1 music CD at 14.99";
		String choc = "1 chocolate bar at 0.85";
				
		System.out.println(book);
		System.out.println(cd);
		System.out.println(choc);
		System.out.println("\n-----INPUT 1 RESULTS------");

		Goods bookObj = taxService.getGoods(book);
		Goods cdObj = taxService.getGoods(cd);
		Goods chocObj = taxService.getGoods(choc);
		
		double bookTax = taxService.getTax(bookObj);
		double cdTax = taxService.getTax(cdObj);
		double chocTax = taxService.getTax(chocObj);
		
		double taxSum = bookTax + cdTax + chocTax;
		double totalAmount = bookObj.getPrice() + cdObj.getPrice() + 
				chocObj.getPrice() + taxSum;
		
		System.out.println(taxService.getTaxResults(book));
		System.out.println(taxService.getTaxResults(cd));
		System.out.println(taxService.getTaxResults(choc));
		System.out.println("Sales Taxes: " + taxSum);
		System.out.println("Total: " + TaxUtils.twoDecimalRoundUp(totalAmount));
	}
	
	public static void printInput2() {
		System.out.println("\n-----INPUT 2-----");

		String choc = "1 imported box of chocolates at 10.00";
		String perfume = "1 imported bottle of perfume at 47.50";

		System.out.println(choc);
		System.out.println(perfume);
		
		System.out.println("\n-----INPUT 2 RESULTS------");
		
		Goods perfumeObj = taxService.getGoods(perfume);
		Goods chocObj = taxService.getGoods(choc);
		
		double perfumeTax = taxService.getTax(perfumeObj);
		double chocTax = taxService.getTax(chocObj);
		
		double taxSum = perfumeTax + chocTax;
		double totalAmount = perfumeObj.getPrice() + chocObj.getPrice() + taxSum;
		
		System.out.println(taxService.getTaxResults(choc));
		System.out.println(taxService.getTaxResults(perfume));
		System.out.println("Sales Taxes: " + TaxUtils.twoDecimalRoundUp(taxSum));
		System.out.println("Total: " + TaxUtils.twoDecimalRoundUp(totalAmount));
	}
	
	public static void printInput3() {
		
		System.out.println("\n-----INPUT 3------");		
		String perfume = "1 imported bottle of perfume at 27.99";
		String perfume1 = "1 bottle of perfume at 18.99";
		String pills = "1 packet of headache pills at 9.75";
		String choc = "1 box of imported chocolates at 11.25";
		
		
		System.out.println(perfume);
		System.out.println(perfume1);
		System.out.println(pills);
		System.out.println(choc);
		
		System.out.println("\n-----INPUT 3 RESULTS------");
		
		Goods perfumeObj = taxService.getGoods(perfume);
		Goods chocObj = taxService.getGoods(choc);
		Goods perfume1Obj = taxService.getGoods(perfume1);
		Goods pillsObj = taxService.getGoods(pills);
		
		double perfumeTax = taxService.getTax(perfumeObj);
		double perfume1Tax = taxService.getTax(perfume1Obj);
		double pillsTax = taxService.getTax(pillsObj);
		double chocTax = taxService.getTax(chocObj);	
		
		double taxSum = perfumeTax + perfume1Tax + pillsTax + chocTax;
		double totalAmount = perfumeObj.getPrice() + perfume1Obj.getPrice() + 
				chocObj.getPrice() + pillsObj.getPrice() + taxSum;
		
		System.out.println(taxService.getTaxResults(perfume));
		System.out.println(taxService.getTaxResults(perfume1));
		System.out.println(taxService.getTaxResults(pills));
		System.out.println(taxService.getTaxResults(choc));
		System.out.println("Sales Taxes: " + TaxUtils.twoDecimalRoundUp(taxSum));
		System.out.println("Total: " + TaxUtils.twoDecimalRoundUp(totalAmount));
	}

}
