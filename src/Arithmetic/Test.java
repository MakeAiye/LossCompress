package Arithmetic;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new BigDecimal("1").divide(new BigDecimal("10000000000000000000000000000000"), 16, RoundingMode.CEILING));
		int effectnum = new BigDecimal(String.valueOf(0.0448)).precision();
		double value = Math.round(0.3333 * Math.pow(10.0, 2))
				/ Math.pow(10.0, 2);
		System.out.println(effectnum);
		

	}

}
