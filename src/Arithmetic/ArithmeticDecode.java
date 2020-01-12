package Arithmetic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


import java.util.Set;

import InitialRead.Initial;
import lossCompressInterface.IDecode;

public class ArithmeticDecode implements IDecode{
	private String encodeResult, decodeResult, decodeFilePath;
	private LinkedHashMap<String, Interval> initIntervalMap;
	private String resultFileContent;

	@Override
	public void Decoding() {
		// TODO Auto-generated method stub
		GetEncodeResultFromFile();
		AnalyseFileContent();
//		System.out.println("算术解码输出： " + ArithmeticDecoding());
		ArithmeticDecoding();
		OutputDecodeFile();
		
	}

	public ArithmeticDecode(String decodeFilePath, LinkedHashMap<String, Interval> initIntervalMap) {
		super();
		this.decodeFilePath = decodeFilePath;
		this.initIntervalMap = initIntervalMap;
	}
	
	public int GetMapPrecision() {
		String value = String.valueOf(initIntervalMap.get("" + FLAG).getRightNode());
		if(value.contains("E")) {
			return Integer.valueOf(value.substring(value.indexOf('E') + 2));
		}else {
			return value.length();
		}
		
	}

//	public ArithmeticDecode(String encodeResult, LinkedHashMap<String, Interval> initIntervalMap) {
//		super();
//		this.encodeResult = encodeResult;
//		this.initIntervalMap = initIntervalMap;
//	}
	
	//字节读取，从文件读取每个字节，获得对应的压缩数据码ASCII
	public void GetEncodeResultFromFile() {
		File decodeFile = new File(decodeFilePath);
		StringBuilder encodeBuilder = new StringBuilder("");
		int datachar = 0;
		try {
			FileReader in = new FileReader(decodeFile);
			while((datachar = in.read()) != -1) {
					encodeBuilder.append("" + (char)datachar);
			}
			resultFileContent = encodeBuilder.toString();
//			System.out.print("从压缩文件读取的内容： ");
//			System.out.println(resultFileContent);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//和哈夫曼做法一致
	public void AnalyseFileContent() {
		int surplus = resultFileContent.charAt(0);
		StringBuilder builder = new StringBuilder();
		int currentAsc;
		String to8Bit;
//		System.out.println(surplus);
		//换成二进制并用字符串表示
		for(int i = 1;i < resultFileContent.length();i ++) {
			currentAsc = (int)resultFileContent.charAt(i);
			to8Bit = Integer.toBinaryString(currentAsc);
			while (to8Bit.length() < 8) to8Bit = "0" + to8Bit;
			builder.append(to8Bit);
		}
		encodeResult = builder.toString();
		if(surplus != 0) {
			encodeResult = encodeResult.substring(0, encodeResult.length() - (8 - surplus) + 1);
		}
		System.out.print("对压缩文件读分析出的内容： ");
//		System.out.println(encodeResult);
		
	}

	//刷新来做
//	public String ArithmeticDecoding() {
//		BigDecimal enresultBig = Initial.binaryStringToBigDecimal(encodeResult);
//		HashMap<String, Interval> refreshIntervalMap = initIntervalMap;
//		StringBuilder decodeBuilder = new StringBuilder();
//		Interval currentInterval = null;
//		BigDecimal left = new BigDecimal("0");
//		BigDecimal right = new BigDecimal("1");
//		currentInterval = FindInterval(enresultBig, refreshIntervalMap);
//		do {
//			decodeBuilder.append(currentInterval.getCharacterChar());
////			System.out.println(currentInterval.getCharacterChar());
//			refreshIntervalMap = currentInterval.RefreshIntervalMap(initIntervalMap);
//			currentInterval = FindInterval(enresultBig, refreshIntervalMap);
//		}while(!currentInterval.getCharacterChar().equals("" + TERMINATER));
//		 return decodeResult = decodeBuilder.append("$").toString();
//	}
	
	public String ArithmeticDecoding() {
		BigDecimal enresultBig = Initial.binaryStringToBigDecimal(encodeResult);
		StringBuilder decodeBuilder = new StringBuilder();
		
		BigDecimal sumRange = new BigDecimal("1");
		BigDecimal value = enresultBig;
		BigDecimal barStart = new BigDecimal("0");
		//找到第一个 d:\\ceshitest.txt
		Interval currentInterval = FindInterval(value);
//		System.out.println(value);
		decodeBuilder.append(currentInterval.getCharacterChar());
//		System.out.println(currentInterval.getCharacterChar());
		BigDecimal left;
		//开始循环找第二个
		int i = 2;
		int precision = GetMapPrecision();
//		System.out.println(precision);
		do {
			//找特别的第二个
			if(i == 2) {
				left = currentInterval.getLeftNode();
				sumRange = sumRange.multiply(currentInterval.getRange());
				barStart = barStart.add(left);
				value = enresultBig.subtract(barStart).divide(sumRange, precision, RoundingMode.HALF_UP);
				currentInterval = FindInterval(value);
				decodeBuilder.append(currentInterval.getCharacterChar());
				i ++;
			}else {//找往后的第三个等等
				left = currentInterval.getLeftNode();
				barStart = barStart.add(sumRange.multiply(left));
				value = enresultBig.subtract(barStart).divide(sumRange
						.multiply(currentInterval.getRange()), precision, RoundingMode.HALF_UP);
//				System.out.println(value);
				sumRange = sumRange.multiply(currentInterval.getRange());
				
				currentInterval = FindInterval(value);
//				System.out.println(currentInterval.getLeftNode());
				decodeBuilder.append(currentInterval.getCharacterChar());
//				System.out.println(currentInterval.getCharacterChar());
			}
		}while(!currentInterval.getCharacterChar().equals("" + FLAG));
		//删除结束符
		return decodeResult = decodeBuilder.toString().substring(0, decodeBuilder.toString().length() - 1);
	}
	
	//找区间,若刷新再传参HashMap<String, Interval> refreshIntervalMap
	public Interval FindInterval(BigDecimal value) {
		for(Entry<String, Interval> entry : initIntervalMap.entrySet()) {
			if (value.compareTo(entry.getValue().getLeftNode()) > 0
                    && value.compareTo(entry.getValue().getRightNode()) <= 0) {
                return entry.getValue();
            }
		}
		return null;
	}

	@Override
	public void OutputDecodeFile() {
		// TODO Auto-generated method stub
		File lateFile = new File(decodeFilePath.substring(0, decodeFilePath.lastIndexOf(".")) + "算术.txt");
		try {
			if(lateFile.exists()) {
				lateFile.delete();
				lateFile.createNewFile();
			}
			Writer out = new FileWriter(lateFile);
			out.write(decodeResult);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
