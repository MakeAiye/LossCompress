package Huffman;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import lossCompressInterface.IDecode;

public class HuffmanDecode implements IDecode{
	private String encodeResult, decodeResult, decodeFilePath;
	private LinkedHashMap<String, String> codePair;
	private String resultFileContent;
	private Node[] initTreeList, changedTreeList, initNodeList;
	private Key keying;
	
	public Key getKeying() {
		return keying;
	}


	public void setKeying(Key keying) {
		this.keying = keying;
	}

	private int len;

	@Override
	public void Decoding() {
		// TODO Auto-generated method stub
		GetEncodeResultFromFile();
		AnalyseFileContent();
		keying.ChangeTree();
		GetKeyCodePair();
		HuffmanDecoding();
		OutputDecodeFile();
	}

	
	public HuffmanDecode(String decodeFilePath, Node[] initNodeList) {
		super();
		this.decodeFilePath = decodeFilePath;
		this.initNodeList = initNodeList;
		len = initNodeList.length;
		CreatingHuffmanTree();
		keying = new Key(initTreeList);
		keying.InputKey();
		
		
	}
	
	public void CreatingHuffmanTree() {
		initTreeList = new Node[2 * len - 1];
		//初始化树表
		for(int num = 0; num < 2 * len - 1;num ++) {
			if(num < len) {
				initTreeList[num] = initNodeList[num];
			}else {
				initTreeList[num] = new Node();
			}
			
		}
		Arrays.sort(initTreeList);
		//开始构造父节点，在树表中逐步生成其它len-1父节点
		for(int num = 0; num < len - 1;num ++) {
			Node parentNode = new Node();
			
			parentNode.setLeftChild(initTreeList[(len - 1) - num]);
			parentNode.setRightChild(initTreeList[(len - 1) - (num + 1)]);
			parentNode.setWeight(initTreeList[(len - 1) - num].getWeight() +
					(initTreeList[(len - 1) - (num + 1)].getWeight()));
			parentNode.setFlag(num);
			initTreeList[(len - 1) - num].setParent(parentNode);
			initTreeList[(len - 1) - (num + 1)].setParent(parentNode);
			initTreeList[len + num] = parentNode;
			Arrays.sort(initTreeList);
		}
//		System.out.print(Arrays.toString(encodingNodeList));
	}
	
	
	public int SexNode(Node chlid) {
		for(int i = 0; i < changedTreeList.length; i ++) {
			if(changedTreeList[i].equals(chlid)) {
				return i;
			}
		}
		return -1;
	}
	//加密钥后
	public void GetKeyCodePair() {
		this.codePair = new LinkedHashMap<String, String>();
		changedTreeList = keying.getTurnNodeList();
		for(int num = 0; num < 2 * len - 1;num ++) {
			StringBuilder builder = new StringBuilder("");
			
			if(changedTreeList[num].getInintchar() == "") {
				continue;
			}else {
				Node referenceNode = changedTreeList[num];
				while(referenceNode.getParent() != null) {
					if(referenceNode.getParent().getLeftChild().equals(referenceNode)) {
						builder.append("0");
//						System.out.println("ok");
					}else {
//						System.out.println("ok");
						builder.append("1");
					}
					referenceNode = changedTreeList[SexNode(referenceNode.getParent())];
				}
				//将二进制码设为Key，方便后期查询
				codePair.put(builder.reverse().toString(), changedTreeList[num].getInintchar());
			}
			
		}
		CreatingHuffmanTree();
		
//			System.out.println("");
//			Set<Entry<String, String>> Set = codePair.entrySet();
//			for(Entry<String, String> entry : Set) {
//				System.out.println(entry.getKey() + " = " + entry.getValue());
//			}
//			System.out.println(changedTreeList.length);
//		for(int i = 0; i < changedTreeList.length; i ++) {
//			System.out.println("第" + i + "个changedTreeList元素权：" + changedTreeList[i].getInintchar());
//		}
		}


	public HuffmanDecode(String decodeFilePath, LinkedHashMap<String, String> codePair) {
		super();
		this.decodeFilePath = decodeFilePath;
		//置换codePair真的Key和Value
		this.codePair = new LinkedHashMap<String, String>();
		
//		Set<Entry<String, String>> dictSet = codePair.entrySet();
//		for(Entry<String, String> dictEntry : dictSet) {
//			this.codePair.put(dictEntry.getValue(), dictEntry.getKey());
//		}
	}
	
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
			encodeResult = encodeResult.substring(0, encodeResult.length() - (8 - surplus));
		}
//		System.out.print("对压缩文件读分析出的内容： ");
//		System.out.println(encodeResult);
	}
	
	public HuffmanDecode(String encodeResult, HashMap<String, String> codePair) {
		super();
		this.encodeResult = encodeResult;
		//置换codePair真的Key和Value
		this.codePair = new LinkedHashMap<String, String>();
		Set<Entry<String, String>> dictSet = codePair.entrySet();
		for(Entry<String, String> dictEntry : dictSet) {
			this.codePair.put(dictEntry.getValue(), dictEntry.getKey());
		}
	}
	
	//字节读取，从LZW文件读取每个字节，获得对应的压缩数据码ASCII
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
//				System.out.print("从压缩文件读取的内容： ");
//				System.out.println(resultFileContent);
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	public void HuffmanDecoding() {
		StringBuilder readCodeByBit = new StringBuilder();
		StringBuilder decodeBuilder = new StringBuilder();
		int i = 0;
		for(char currentChar : encodeResult.toCharArray()) {
			
			readCodeByBit.append("" + currentChar);
			if(codePair.containsKey(readCodeByBit.toString())) {
				decodeBuilder.append(codePair.get(readCodeByBit.toString()));
				readCodeByBit.delete(0, readCodeByBit.toString().length());
			}else {
				continue;
			}
		}
		decodeResult = decodeBuilder.toString();
		System.out.println("最后Huffman文件解压结果： ");
		System.out.println(decodeResult);
	}
	
	@Override
	public void OutputDecodeFile() {
		// TODO Auto-generated method stub
		File lateFile = new File(decodeFilePath.substring(0, decodeFilePath.lastIndexOf(".")) + "Huffman.txt");
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
