package co.za.tax.service;

import co.za.tax.dao.TaxCalculatorDAO;
import co.za.tax.model.Goods;
import co.za.tax.util.TaxUtils;

public class TaxCalculatorService {
	
	TaxCalculatorDAO taxDAO = new TaxCalculatorDAO();
	
	/**
	 * 
	 * @param goods
	 * @return
	 */
	public double getTax(Goods goods) {		
		double tax = taxDAO.retrieveTax(goods);
		
		return tax;
	}
	
	/**
	 * 
	 * @param productSentence
	 * @return
	 */
	public double getPrice(String productSentence) {
		String strPrice = productSentence.substring(productSentence.lastIndexOf("at ") + 2).trim();
		double price = Double.parseDouble(strPrice);
		
		return price;
	}

	/**
	 * 
	 * @param sentence
	 * @return
	 */
	public String getGoodsName(String sentence) {
		String[] strArr = sentence.split(" ");
		StringBuilder nameOfGoods = new StringBuilder();
		int count = 0;
		int strArrSize = strArr.length;
		
		count = createGoodsName(strArr, nameOfGoods, count, strArrSize);
		
		return nameOfGoods.toString().trim();
	}

	private int createGoodsName(String[] strArr, StringBuilder nameOfGoods,
			int count, int strArrSize) {
		for (String goods : strArr) {			
			if (count > 0 && count < strArrSize - 2) {
				nameOfGoods.append(goods);
				nameOfGoods.append(" ");
			}
			++count;
		}
		return count;
	}

	/**
	 * 
	 * @param sentence
	 * @return
	 */
	public int getGoodsQuantity(String sentence) {		
		int quantity = Integer.parseInt(sentence.split(" ")[0]);
		return quantity;
	}
	
	/**
	 * 
	 * @param sentence
	 * @return
	 */
	public Goods getGoods(String sentence) {
		boolean isLocalGoods = TaxUtils.isLocalGoods(sentence);
		double price = getPrice(sentence);
		String goodsName = getGoodsName(sentence);
		int quantity = getGoodsQuantity(sentence);
		
		Goods goods = createGoods(isLocalGoods, price, goodsName, quantity);
		
		return goods;
	}

	private Goods createGoods(boolean isLocalGoods, double price,
			String goodsName, int quantity) {
		Goods goods = new Goods();
		goods.setLocalGoods(isLocalGoods);
		goods.setName(goodsName);
		goods.setPrice(price);
		goods.setQuantity(quantity);
		return goods;
	}

	/**
	 * 
	 * @param sentence
	 * @return
	 */
	public String getTaxResults(String sentence) {
		String fixedSentence = TaxUtils.fixSentence(sentence);
		Goods goods = getGoods(fixedSentence);
		double tax = taxDAO.retrieveTax(goods);
		double total = goods.getPrice() + tax;
		String taxResult = goods.getQuantity() + " " + goods.getName() + ": " 
				+ TaxUtils.twoDecimalRoundUp(total);
		
		return taxResult;
	}

}
