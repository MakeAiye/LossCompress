package Huffman;

import java.util.Random;
import java.util.Scanner;

public class Key {
	private Node[] turnNodeList;
	private String key;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Key(Node[] turnNodeList) {

		this.turnNodeList = turnNodeList;
	}

	public Node[] getTurnNodeList() {
		return turnNodeList;
	}

	public void setTurnNodeList(Node[] turnNodeList) {
		this.turnNodeList = turnNodeList;
	}

	public void GenerateKey() {
		StringBuilder builder = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < 5;i ++) {
			builder.append(String.valueOf(rand.nextInt(2)));
		}
		System.out.println( "正确密钥key： " + builder.toString());
		key = builder.toString();
		if(key.indexOf("1") == -1) {
			GenerateKey();
		}
//		
//		key = "01001101010001110011100010111111001111000010010111010110110000000001001110110010001101110110000"
//				+ "1110101110110100000110010101101110111110001000010001001011";

	}
	
	public void InputKey() {
		Scanner scan = new Scanner(System.in);
//		System.out.println("");
		System.out.println("-------------------");
		System.out.println("请输入5位密钥解码：");
		key = scan.next();
		
	}

	
	public void ChangeTree() {
		Node temp = new Node();
		char[] keyArray = key.toCharArray();
		int num = 0;
		for(int i = 0; i < turnNodeList.length; i ++) {
			if(turnNodeList[i].getInintchar() == "") {
				if(num < keyArray.length) {
					if(keyArray[num] == '1') {
						temp = turnNodeList[i].getLeftChild();
						turnNodeList[i].setLeftChild(turnNodeList[i].getRightChild());
						turnNodeList[i].setRightChild(temp);
//						System.out.print( "!");
					}
				}
				
				num ++;
//				System.out.print( keyArray[num - 1] + "?");
			}
		}
	}
	
	public int SexChlidNode(Node chlid) {
		for(int i = 0; i < turnNodeList.length; i ++) {
			if(turnNodeList[i].equals(chlid)) {
				return i;
			}
		}
		return -1;
	}
	
}
