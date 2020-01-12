package LZW;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import InitialRead.Initial;
import lossCompressInterface.IDecode;

import java.util.Map.Entry;

public class LZWDecode implements IDecode{
	private ArrayList<Integer> encodeResult;
	private String encodeResultStr;
	private String decodeResult,decodeFilePath;
	private LinkedHashMap<String, Integer> encodeDictMap;
	private LinkedHashMap<Integer, String> decodeDictMap;
	private String dictMapString,fileForm;

	public LZWDecode(ArrayList<Integer> encodeResult, LinkedHashMap<String, Integer> encodeDictMap) {
		super();
		this.encodeResult = encodeResult;
		this.encodeDictMap = encodeDictMap;
	}

	public LZWDecode(String decodeFilePath, LinkedHashMap<String, Integer> encodeDictMap) {
		super();
		this.decodeFilePath = decodeFilePath;
		this.encodeDictMap = encodeDictMap;
	}
	
	
	@Override
	public void Decoding() {
		// TODO Auto-generated method stub
		GetEncodeResultFromFile();
//		System.out.println("\"解码结果输出：" + LZWDecoding());
		LZWDecoding();
		OutputDecodeFile();
		
	}

	//对添加过初始字典的lzw文件做相应初始操作
	public void LzwFileInite() {
		encodeDictMap = new LinkedHashMap<String, Integer>();
		dictMapString = encodeResultStr.substring(encodeResultStr.lastIndexOf("&") + 1);
		String realEncodeString = encodeResultStr.substring(0, encodeResultStr.lastIndexOf("&"));
		//生成初始字典
		int i = 0;
		for(char currentChar : dictMapString.toCharArray()) {
			encodeDictMap.put("" + currentChar, i ++);
		}
		//再生成压缩数据码
		encodeResult.clear();
		for(char currentChar : realEncodeString.toCharArray()) {
			encodeResult.add((int)currentChar);
		}
		
	}

	//字节读取，从LZW文件读取每个字节，获得对应的压缩数据码ASCII
	public void GetEncodeResultFromFile() {
		File decodeFile = new File(decodeFilePath);
		StringBuilder encodeBuilder = new StringBuilder("");
		encodeResult = new ArrayList<Integer>();
		int datachar = 0;
		try {
			FileReader in = new FileReader(decodeFile);
			while((datachar = in.read()) != -1) {
				encodeResult.add(datachar);
				encodeBuilder.append("" + (char)datachar);
			}
			encodeResultStr = encodeBuilder.toString();
//			System.out.println(encodeResultStr);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ReplaceDict(){
		decodeDictMap = new LinkedHashMap<Integer, String>();
		Set<Entry<String, Integer>> dictSet = encodeDictMap.entrySet();
		for(Entry<String,Integer> dictEntry : dictSet) {
			decodeDictMap.put(dictEntry.getValue(), dictEntry.getKey());
		}
	}

	public String LZWDecoding() {
		StringBuilder resultBuilder = new StringBuilder("");
		String previousWord = "";
		String currentWord = "";
		ReplaceDict();
		int num = 0;
		int dictSize = decodeDictMap.size();
		for(int currentCode : encodeResult) {
			currentWord = decodeDictMap.get(currentCode);
			if(decodeDictMap.containsKey(currentCode)) {
				resultBuilder.append(currentWord);
				if(num != 0) {
					decodeDictMap.put(dictSize ++, previousWord + currentWord.charAt(0));
				}//如果不是第一次就加入字典
			} else {
				decodeDictMap.put(dictSize ++, previousWord + previousWord.charAt(0));
				resultBuilder.append(previousWord + previousWord.charAt(0));
			}
			previousWord = currentWord;
			num ++;
		}
		return decodeResult = resultBuilder.toString();
	}

	@Override
	public void OutputDecodeFile() {
		// TODO Auto-generated method stub
		File lateFile = new File(decodeFilePath.substring(0, decodeFilePath.lastIndexOf(".")) + "lzw.txt");
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
