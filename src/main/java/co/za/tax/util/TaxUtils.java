package co.za.tax.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class TaxUtils {

	
	public static double twoDecimalRoundUp(Double val) {
		long wholeNum = (long) val.doubleValue();
		double fraction = (val.doubleValue() - wholeNum) * 10;
		
		long centsWholeNum = (long) fraction;
		double cents = fraction - centsWholeNum;
		
		double twoDecVal = (double) centsWholeNum;
		Double finalTwoDecimalVal = null; //new Double(twoDecVal);
		if (cents < 0.5) {
			finalTwoDecimalVal = (twoDecVal / 10) + wholeNum + 0.05;
		} else {
			finalTwoDecimalVal = val;
		}
		
	    return new BigDecimal(finalTwoDecimalVal.toString()).setScale(2,RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 
	 * @param sentence
	 * @return
	 */
	public static String fixSentence(String sentence) {
		String sentenceFixer = "imported";
		String[] strArr = sentence.split(" ");
		List<String> strList = new ArrayList<String>();
		boolean isImportFound = false;
		
		isImportFound = createFixedSentence(sentenceFixer, strArr, strList,
				isImportFound);		
		addMissingFixer(sentenceFixer, strList, isImportFound);
		String fixedSentence = String.join(" ", strList); 
		
		return fixedSentence;
	}

	private static void addMissingFixer(String sentenceFixer,
			List<String> strList, boolean isImportFound) {
		if (isImportFound) {
			strList.add(1, sentenceFixer);
		}
	}

	private static boolean createFixedSentence(String sentenceFixer,
			String[] strArr, List<String> strList, boolean isImportFound) {
		for(String words : strArr) {
			if (!sentenceFixer.equalsIgnoreCase(words)) {
				strList.add(words);
			} else {
				isImportFound = true;
			}
		}
		return isImportFound;
	}

	/**
	 * 
	 * @param sentence
	 * @return
	 */
	public static boolean isLocalGoods(String sentence) {
		boolean isLocalGoods = !isImportedGoods(sentence.split(" "));
		
		return isLocalGoods;
	}
	

	private static boolean isImportedGoods(String[] strArr) {
		return "imported".equalsIgnoreCase(strArr[1]);
	}	

}
