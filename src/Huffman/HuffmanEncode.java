package Huffman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import InitialRead.Initial;
import lossCompressInterface.IEncode;

import java.util.Set;
/**
 * @author WenHui Li
 * @getedString 所给要编码的字符串
 * @initNodeList 初始节点集合
 * @encodingNodeList 编码下的所有节点集合，包含所有父节点，也可以表示成生成树
 * @len 初始节点集合长度
 * @encodeResult 编码结果，以二进制所表的字符串显示
 * @codePair 最后编码字符对
 * @frequencyMap 频数集合
 */
public class HuffmanEncode extends Initial implements IEncode{
	private Node[] initNodeList;
	private Node[] initTreeList;
	private Node[] changedTreeList;

	private int len;
	private String encodeResult;
	private LinkedHashMap<String, String> codePair = new LinkedHashMap<String, String>();
	private String resultFileContent;
	
	@Override
	public void Encoding() {
		// TODO Auto-generated method stub
		newInit();
		GetFrequencyMap();
		GetListFromSting();
		CreatingHuffmanTree();
//		GetCodePair();
		GetKeyCodePair();
		formEncodeResult();
		OutputResultFileContent();
		OutputTypeFile("huffman", resultFileContent);
		CompareFileSize();
	}
	
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
		resultFileContent = builder.toString();
//		System.out.println(resultFileContent);
	}

	public void formEncodeResult() {
		StringBuilder builder = new StringBuilder();
		for(char currentChar : inputFileContent.toCharArray()) {
			builder.append(codePair.get("" + currentChar));
		}
		encodeResult = builder.toString();
//		System.out.println("哈夫曼编码结果：" + encodeResult);
	}
	
	public void GetListFromSting() {
		len = frequencyMap.size();
		initNodeList = new Node[len];
		Set<Map.Entry<String,Integer>> set = frequencyMap.entrySet();
		int num = 0;
		for(Map.Entry<String,Integer> entry : set) {
			initNodeList[num ++] = new Node(entry.getKey(), entry.getValue());
		}
		Arrays.sort(initNodeList);
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
	
	
	
	public void GetCodePair() {
		for(int num = 0; num < 2 * len - 1;num ++) {
			StringBuilder builder = new StringBuilder("");
			if(initTreeList[num].getInintchar() == "") {
				continue;
			}else {
				Node referenceNode = initTreeList[num];
				while(referenceNode.getParent() != null) {
					if(initTreeList[SexinitNode(referenceNode.getParent())].getLeftChild().equals(referenceNode)) {
						builder.append("0");
//						System.out.println("ok");
					}else {
//						System.out.println("ok");
						builder.append("1");
					}
					referenceNode = referenceNode.getParent();
				}
				//将二进制码设为Key，方便后期查询
				codePair.put(initTreeList[num].getInintchar(), builder.reverse().toString());
			}
		}
//		System.out.println("");
//		Set<Entry<String, String>> Set = codePair.entrySet();
//		for(Entry<String, String> entry : Set) {
//			System.out.println(entry.getKey() + " = " + entry.getValue());
//		}
	}
	
	public int SexNode(Node chlid) {
		for(int i = 0; i < changedTreeList.length; i ++) {
			if(changedTreeList[i].equals(chlid)) {
				return i;
			}
		}
		return -1;
	}
	public int SexinitNode(Node chlid) {
		for(int i = 0; i < initTreeList.length; i ++) {
			if(initTreeList[i].equals(chlid)) {
				return i;
			}
		}
		return -1;
	}
	
	//加密钥后
	public void GetKeyCodePair() {
		
		Key keying = new Key(initTreeList);
		keying.GenerateKey();
		keying.ChangeTree();
		changedTreeList = keying.getTurnNodeList();
		for(int num = 0; num < 2 * len - 1;num ++) {
			StringBuilder builder = new StringBuilder("");
			if(changedTreeList[num].getInintchar() == "") {
				continue;
			}else {
				Node referenceNode = changedTreeList[num];
				while(referenceNode.getParent() != null) {
					if(changedTreeList[SexNode(referenceNode.getParent())].getLeftChild().equals(referenceNode)) {
						builder.append("0");
//						System.out.println("ok");
					}else {
//						System.out.println("ok");
						builder.append("1");
					}
					referenceNode = changedTreeList[SexNode(referenceNode.getParent())];
				}
				//将二进制码设为Key，方便后期查询
				codePair.put(changedTreeList[num].getInintchar(), builder.reverse().toString());
			}
		}
//		System.out.println("qian:");
//		Set<Entry<String, String>> Set = codePair.entrySet();
//		for(Entry<String, String> entry : Set) {
//			System.out.println(entry.getKey() + " = " + entry.getValue());
//		}
//		
//		for(int i = 0; i < changedTreeList.length; i ++) {
//			System.out.println("第" + i + "个changedTreeList元素权：" + changedTreeList[i].getInintchar());
//		}
		
	}
	

	public String getResultFileContent() {
		return resultFileContent;
	}

	public void setResultFileContent(String resultFileContent) {
		this.resultFileContent = resultFileContent;
	}

	public Node[] getInitNodeList() {
		return initNodeList;
	}

	public void setInitNodeList(Node[] initNodeList) {
		this.initNodeList = initNodeList;
	}
	
	public String getEncodeResult() {
		return encodeResult;
	}

	public void setEncodeResult(String encodeResult) {
		this.encodeResult = encodeResult;
	}

	public LinkedHashMap<String, String> getCodePair() {
		return codePair;
	}

	public void setCodePair(LinkedHashMap<String, String> codePair) {
		this.codePair = codePair;
	}

	@Override
	public LinkedHashMap GetMap() {
		// TODO Auto-generated method stub
		return this.codePair;
	}

	@Override
	public String GetOutFilePath() {
		// TODO Auto-generated method stub
		return this.outputFilePath;
	}
	
	

}
