package Arithmetic;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import InitialRead.Initial;
import lossCompressInterface.IEncode;

public class ArithmeticEncode extends Initial implements IEncode{
	private String encodeResult;
	private LinkedHashMap<String, Interval> initIntervalMap;
	private String resultFileContent;
//	private static final char TERMINATER = '$';
	
	public void OutputResultFileContent() {
		char surplusAtfrist = (char)(encodeResult.length() % 8);
		resultFileContent = "";
		StringBuilder builder = new StringBuilder();
		builder.append("" + surplusAtfrist);
//		System.out.println((int)surplusAtfrist);
		for(int i = 0;i < encodeResult.length() / 8;i ++) {
			builder.append("" + (char)Integer.parseInt(encodeResult.substring(i * 8, i * 8 + 8) ,2));
		}
		if((int)surplusAtfrist != 0) {
			int resultEnd = Integer.parseInt(encodeResult.substring((encodeResult.length() / 8) * 8), 2);
			builder.append("" + (char)(resultEnd * (int)Math.pow(2, 8 - (int)surplusAtfrist)));
		}//如果最后8的余数是0，就不填补0
		
		//采取终止符
		resultFileContent = builder.toString();
//		//不采取终止符
//		resultFileContent = builder.insert(0, (char)len).toString();
		System.out.println("编码文件内容： " + resultFileContent);
	}
	
	//对频数排序后输出
	public void GetInitIntervalList() {
		initIntervalMap = new LinkedHashMap<String, Interval>();
		Set<Map.Entry<String,Integer>> set = frequencyMap.entrySet();
		BigDecimal left = new BigDecimal("0");
		BigDecimal right = new BigDecimal("0");
		int len = frequencyMap.size();
		Interval[] initIntervalList = new Interval[len];
		int num = 0;
		for(Map.Entry<String, Integer> entry : set) {
			Interval interval = new Interval();
			interval.setCharacterChar(entry.getKey());
			interval.setWeight(entry.getValue());
			initIntervalList[num ++] = interval;
		}
		Arrays.sort(initIntervalList);
		String rightString;
		int numWeight;
		long biglong = inputFileContent.length();
		for(num = 0; num < len; num ++) {
			initIntervalList[num].setLeftNode(left);
			numWeight = initIntervalList[num].getWeight();
//			System.out.println(String.valueOf((double)numWeight / biglong));
			rightString = Rounding(String.valueOf((double)numWeight / biglong));
//			if(num == 0) {
//				System.out.println(rightString);
//			}
			if(num != len - 1) {
				right = left.add(new BigDecimal(rightString));
		//为double型，方便后期做小数除法运算，若为int型则是整形除法
			}else {
				right = new BigDecimal("1");
			}
			initIntervalList[num].setRightNode(right);
			initIntervalList[num].setRange(right.subtract(left));
			left = right;
			initIntervalMap.put(initIntervalList[num].getCharacterChar(), initIntervalList[num]);
		}
//		System.out.println(initIntervalMap.get("$").getRange());
//		Iterator<Entry<String, Interval>> iter = initIntervalMap.entrySet().iterator();
//		while(iter.hasNext()) {
//			Entry<String, Interval> me = iter.next();
//			System.out.println(me.getKey() + " = " + me.getValue());
//		}
		
		for(Entry<String, Interval> entry : initIntervalMap.entrySet()) {
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
	}
	
//	public void ArithmeticEncoding() {
//		Interval resultInterval = new Interval();
//		LinkedHashMap<String, Interval> refreshMap = initIntervalMap;
//		for(char currentChar : getedString.toCharArray()) {
//			resultInterval = refreshMap.get(currentChar + "");
////			System.out.println(currentChar);
//			if(currentChar != TERMINATER) {
//				refreshMap = resultInterval.RefreshIntervalMap(initIntervalMap);
//				
//			}else {
//				
//				break;
//			}
//		}
////		System.out.println(resultInterval.getLeftNode().doubleValue() + " " + resultInterval.getRightNode().doubleValue());
//		this.encodeResult = GeneratIntervalCode(resultInterval);
//		System.out.println("算术编码结果输出： " + "0." + encodeResult);
//	}
	
	@Override
	public void Encoding() {
		// TODO Auto-generated method stub
		newInit();
		//修改添加结束符
		inputFileContent += FLAG;
		GetFrequencyMap();
		GetInitIntervalList();
		ArithmeticEncoding();
		OutputResultFileContent();
		OutputTypeFile("arithmetic", resultFileContent);
		CompareFileSize();
	}
	
	//0.64627312582928 0.64629253361992
	public void ArithmeticEncoding() {
		Interval resultInterval = new Interval();
		BigDecimal currentLeft = new BigDecimal("0");
		BigDecimal currentRight = new BigDecimal("1");
		BigDecimal currentRange = new BigDecimal("1");
		long startGeneratInterval = System.currentTimeMillis();
		for(char currentChar : inputFileContent.toCharArray()) {
			Interval checkedInterval = initIntervalMap.get(currentChar + "");
			currentLeft = currentLeft.add(checkedInterval.getLeftNode().multiply(currentRange));
			currentRight = currentLeft.add(checkedInterval.getRange().multiply(currentRange));
			currentRange = currentRight.subtract(currentLeft);
		}
		long endGeneratInterval = System.currentTimeMillis();
		System.out.println("生成结果区间所花时间： " + (endGeneratInterval - startGeneratInterval) + "ms.");
		resultInterval.setLeftNode(currentLeft);
		resultInterval.setRightNode(currentRight);
		resultInterval.setRange(currentRange);
		System.out.println(String.valueOf(resultInterval.getLeftNode()));
		System.out.println(String.valueOf(resultInterval.getRightNode()));
		long startGenerat = System.currentTimeMillis();
		this.encodeResult = GeneratIntervalCode(resultInterval);
		long endGenerat = System.currentTimeMillis();
		System.out.println("由结果区间生成码所花时间： " + (endGenerat - startGenerat) + "ms.");
//		System.out.println(resultInterval.getLeftNode().doubleValue() + " " + resultInterval.getRightNode().doubleValue());
		System.out.println("算术编码结果输出： " + "0." + encodeResult);
	}
	
	//找到在resultInterval区间的编码
//    private String GeneratIntervalCode(Interval interval) {
//        StringBuilder codeword = new StringBuilder("");
//        while (Initial.binaryStringToBigDecimal(codeword.toString()).compareTo(interval.getLeftNode()) < 0) {
//            codeword.append('1');
//            if (Initial.binaryStringToBigDecimal(codeword.toString()).compareTo(interval.getRightNode()) > 0) {
//                codeword.setCharAt(codeword.length() - 1, '0');
//            }
//        }
//        return codeword.toString();
//    }
	
	//(针对前面生成方法运行效率缓慢问题做出改进)：找到在resultInterval区间的编码
	private String GeneratIntervalCode(Interval resultInterval) {
        StringBuilder codeword = new StringBuilder("");
        BigDecimal sumResult = new BigDecimal("0");
        BigDecimal temp,i;
        int k = 1;
//        while(sumResult.compareTo(resultInterval.getLeftNode()) < 0 ) {
////        	long s = System.currentTimeMillis();
//        	temp = sumResult;
//        	sumResult = sumResult.add(new BigDecimal("1").divide(new BigDecimal("2").pow(k)));
//        	codeword.append("1");
//        	k ++;
//        	if(sumResult.compareTo(resultInterval.getRightNode()) > 0 ) {
//        		sumResult = temp;
//        		codeword.setCharAt(codeword.length() - 1, '0');
//        	}
////        	long e = System.currentTimeMillis();
////        	System.out.println(k + "： " + (e - s) + "ms.");
//        }
        BigDecimal one = new BigDecimal("1");
        BigDecimal two = new BigDecimal("2");
        BigDecimal zero = new BigDecimal("0");
        BigDecimal precision = zero;
        BigDecimal value = one;
        BigDecimal dis = resultInterval.getRightNode().subtract(resultInterval.getLeftNode());
        while(value.compareTo(dis) > 0) {
        	value = value.divide(two);
        	precision = precision.add(one);
        }
        System.out.println(precision);
        value = one;
        for(i = zero; i.compareTo(precision) < 0; i = i.add(one)){
//        	long s = System.currentTimeMillis();
        	temp = sumResult;
        	value = value.divide(two);
        	sumResult = sumResult.add(value);
        	codeword.append("1");
        	if(sumResult.compareTo(resultInterval.getRightNode()) > 0 ) {
        		sumResult = temp;
        		codeword.setCharAt(codeword.length() - 1, '0');
        	}
//        	long e = System.currentTimeMillis();
//        	System.out.println(i + "： " + (e - s) + "ms.");
        }
        System.out.println("ok");
        return codeword.toString();
    }
	
	//主要针对于结束符$：比率四舍五入，两位
	public static String Rounding(String num) {
		double value = 0;
		StringBuilder valueBuilder = new StringBuilder();
		String decimalPart, zerosPow;
		int i;
		if(num.contains("E")) {
			valueBuilder.append(num.charAt(0));
	        decimalPart = num.substring(num.indexOf('.') + 1, num.indexOf('E'));
	        valueBuilder.append(decimalPart);
	        valueBuilder.insert(0, "0.");
//	        System.out.println(valueBuilder.toString());
			zerosPow = num.substring(num.indexOf('E') + 2);
		    i = Integer.valueOf(zerosPow) - 1;
		}else {
			decimalPart = num.substring(num.indexOf('.') + 1);
			for(i = 0; i < decimalPart.length(); i ++) {
				if(decimalPart.charAt(i) != '0') {
					decimalPart = decimalPart.substring(i);
//					System.out.println(i);
					valueBuilder.append("0." + decimalPart);
					break;
				}else {
					continue;
				}
			}
		}
		value = Math.round(Double.valueOf(valueBuilder.toString()) * Math.pow(10.0, 2))
				/ Math.pow(10.0, 2);
//		System.out.println(Double.valueOf(valueBuilder.toString()));
		valueBuilder = new StringBuilder(String.valueOf(value));
		while(i != 0) {
			valueBuilder.insert(2, "0");
			i --;
		}
//		System.out.println(value);
		return valueBuilder.toString();
	}

	public String getResultFileContent() {
		return resultFileContent;
	}

	public void setResultFileContent(String resultFileContent) {
		this.resultFileContent = resultFileContent;
	}


	public String getEncodeResult() {
		return encodeResult;
	}

	public void setEncodeResult(String encodeResult) {
		this.encodeResult = encodeResult;
	}

	public LinkedHashMap<String, Interval> getInitIntervalMap() {
		return initIntervalMap;
	}

	public void setInitIntervalMap(LinkedHashMap<String, Interval> initIntervalMap) {
		this.initIntervalMap = initIntervalMap;
	}

	@Override
	public LinkedHashMap GetMap() {
		// TODO Auto-generated method stub
		return this.initIntervalMap;
	}

	@Override
	public String GetOutFilePath() {
		// TODO Auto-generated method stub
		return this.outputFilePath;
	}

	
	

}
