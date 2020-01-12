package LZW;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import InitialRead.Initial;
import lossCompressInterface.IDecode;
import lossCompressInterface.IEncode;

public class Test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		System.out.println(inputFileContent);
//		frequencyMap = initial.GetFrequencyMap();
//		System.out.println(frequencyMap.get("得"));
//		dictMap = initial.GetDictMap();
//		System.out.println(dictMap.get("d"));
		
		//输出初始dict内容
//		Set<Entry<String, Integer>> dictSet = dictMap.entrySet();
//		for(Entry<String,Integer> dictEntry : dictSet) {
//			System.out.println(dictEntry.getKey() + " = " + dictEntry.getValue());
//		}
		
		//开始压缩(加过字典)
//		System.out.println("-----------加过字典-------------");
//		LZWEncoding lwzEncoding1 = new LZWEncoding(inputFileContent);
//		codeResult = lwzEncoding1.Encoding();
//		System.out.println("编码数据码结果输出： ");
//		codeResult.forEach((str) -> {
//			System.out.print(str + "、 ");
//		});
//		System.out.println("");
//		String lzwOutStream1 = lwzEncoding1.LzwOutStringStream1();
//		initial.OutputTypeFile("lzw1", lzwOutStream1);
//		initial.CompareFileSize();
//		
//		//开始解码:自带初始字典
//		LZWDecoding lwzDecoding1 = new LZWDecoding("D:\\mldn.lzw1");
//		lwzDecoding1.GetEncodeResultFromFile();
//		lwzDecoding1.LzwFileInite();
////		LZWDecoding lwzDecoding = new LZWDecoding(codeResult,lwzEncoding.getDictMap());
//		decodeResult = lwzDecoding1.Decoding();
//		System.out.println("解码结果输出： ");
//		System.out.println(decodeResult);
		
		//FilePath: d:\\mldn.txt d:\\减肥.jpg d:\\下载说明.htm d:\\ceshitest.txt
		//开始压缩(没加过字典)
		System.out.println("-----------没加过字典-------------");
		IEncode lwzEncoding2 = new LZWEncode();
		long startLzwEncode = System.currentTimeMillis();
		lwzEncoding2.Encoding();
		long endLzwEncode = System.currentTimeMillis();
		System.out.println("Lzw编码所花时间： " + (endLzwEncode - startLzwEncode) + "ms.");
		
//		开始解码:不自带初始字典
		IDecode lwzDecoding2 = new LZWDecode(lwzEncoding2.GetOutFilePath(),lwzEncoding2.GetMap());
		long startLzwDecode = System.currentTimeMillis();
		lwzDecoding2.Decoding();
		long endLzwDecode = System.currentTimeMillis();
		System.out.println("Lzw解码所花时间： " + (endLzwDecode - startLzwDecode) + "ms.");
		
	}
	

}
