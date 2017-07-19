package co.za.tax.dao;

import java.util.ArrayList;
import java.util.List;

import co.za.tax.model.Goods;
import co.za.tax.util.TaxUtils;


public class TaxCalculatorDAO {
	
	/**
	 * 
	 * @param goods
	 * @return
	 */
	public double retrieveTax(Goods goods) {
		double tax = 0.00;
		if (goods.isLocalGoods()) {
			tax = getTaxForLocalGoods(goods, tax);
		} else {
			if (!isExemptedGoods(goods.getName())) {				
				tax = getTaxForUnExemptedGoods(goods);
			} else {
				tax = getTaxForLocalUnExemptedGoods(goods);
			}
		}
		return TaxUtils.twoDecimalRoundUp(tax);
	}

	private double getTaxForLocalUnExemptedGoods(Goods goods) {
		double tax;
		tax = goods.getPrice() * 0.05;
		return tax;
	}

	private double getTaxForUnExemptedGoods(Goods goods) {
		double tax;
		tax = goods.getPrice() * 0.15;
		return tax;
	}

	private double getTaxForLocalGoods(Goods goods, double tax) {
		if (!isExemptedGoods(goods.getName())) {				
			tax = goods.getPrice() * 0.10;
		}
		return tax;
	}
	
	private boolean isExemptedGoods(String name) {
		boolean isExempted = false;
		for (String goods : getExemptedGoods()) {
			if (goods.contains(name)) {
				isExempted = true;
			}
		}
		return isExempted;
	}
	
	private List<String> getExemptedGoods() {
		List<String> exemptedFoods = new ArrayList<String>();
		exemptedFoods.add("book");
		exemptedFoods.add("chocolate bar");
		exemptedFoods.add("box of chocolates");
		exemptedFoods.add("imported box of chocolates");
		exemptedFoods.add("chocolates");
		exemptedFoods.add("packet of headache pills");
		
		return exemptedFoods;
	}
	
}
