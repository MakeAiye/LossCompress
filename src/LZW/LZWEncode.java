package LZW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import InitialRead.Initial;
import lossCompressInterface.IEncode;

public class LZWEncode extends Initial implements IEncode{
	ArrayList<Integer> codeResult = new ArrayList<Integer>();
	private LinkedHashMap<String, Integer> dictMap;
	private String dictMapString;
	private String encodeResult;

	public void Encoding() {
		newInit();
		this.dictMap = GetDictMap();
		String previousWord = "";
		int dictIniteSize = dictMap.size();
//		System.out.println(dictIniteSize);
		for(char currentChar : inputFileContent.toCharArray()) {
			if(dictMap.containsKey(previousWord + currentChar)) {
				previousWord += currentChar;
			}else {
				dictMap.put(previousWord + currentChar, dictIniteSize ++);
				codeResult.add(dictMap.get(previousWord));
//				System.out.println(dictMap.get(previousWord + currentChar));
				previousWord = "" + currentChar;
			}
		}
		//最后一次输出
		if(!previousWord.equals("")) {
			codeResult.add(dictMap.get(previousWord));
		}
		LzwOutStringStream2();
//		System.out.println("ok");
		OutputTypeFile("lzw", encodeResult);
		CompareFileSize();
	}
	
	//将LZW编码数据换成对应字符流（加过初始字典）
//	public String LzwOutStringStream1() {
//		StringBuilder lzwOutBuilder = new StringBuilder("");
//		String lzwOutStream;
//		Iterator<Integer> iter = codeResult.iterator();
//		char charResult;
//		while(iter.hasNext()) {
//			charResult = (char) iter.next().intValue();
//			lzwOutBuilder.append(charResult);
//		}
//		lzwOutBuilder.append("&" + dictMapString);
//		lzwOutStream = lzwOutBuilder.toString();
//		System.out.println("对应编码文件内容(加过初始字典)：");
//		System.out.println(lzwOutStream);
//		return lzwOutStream;
//	}
	
	//将LZW编码数据换成对应字符流（没加初始字典）
		public void LzwOutStringStream2() {
			StringBuilder lzwOutBuilder = new StringBuilder("");
			Iterator<Integer> iter = codeResult.iterator();
			char charResult;
			while(iter.hasNext()) {
				charResult = (char) iter.next().intValue();
				lzwOutBuilder.append(charResult);
			}
//			lzwOutBuilder.append("." + dictMapString);
			encodeResult = lzwOutBuilder.toString();
//			System.out.println("对应编码文件内容(没加过初始字典)：");
//			System.out.println(encodeResult);
		}
	
	//获取初始LZW字典
		public LinkedHashMap<String,Integer> GetDictMap(){
			dictMap = new LinkedHashMap<String, Integer>();
			StringBuilder dictStringBuilder = new StringBuilder("");
			char currentChar;
			String currentString = "";
			int num = 0;
//			System.out.println("输出初始字典Map:  ");
			for(int i = 0;i < inputFileContent.length();i ++) {
				currentChar = inputFileContent.charAt(i);
				currentString = Character.toString(currentChar);
				if(dictMap.containsKey(currentString)) {
					continue;
				}else {
					dictMap.put(currentString,num ++);
					dictStringBuilder.append(currentString);
				}
			}
			this.dictMapString = dictStringBuilder.toString();
//			System.out.print(this.dictMapString);
			//输出初始字典
			Set<Map.Entry<String,Integer>> set = dictMap.entrySet();
			for(Map.Entry<String,Integer> me : set) {
				System.out.print(me.getKey() + "," + me.getValue() + " ");
			}
			System.out.println("");
			return dictMap;
		}

	public ArrayList<Integer> getCodeResult() {
		return codeResult;
	}

	public void setCodeResult(ArrayList<Integer> codeResult) {
		this.codeResult = codeResult;
	}

	public LinkedHashMap<String, Integer> getDictMap() {
		return dictMap;
	}

	public void setDictMap(LinkedHashMap<String, Integer> dictMap) {
		this.dictMap = dictMap;
	}

	@Override
	public LinkedHashMap GetMap() {
		// TODO Auto-generated method stub
		return this.dictMap;
	}
	
	@Override
	public String GetOutFilePath() {
		// TODO Auto-generated method stub
		return this.outputFilePath;
	}

	

	
}
