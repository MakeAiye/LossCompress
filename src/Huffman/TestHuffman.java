package Huffman;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import InitialRead.Initial;
import lossCompressInterface.IDecode;
import lossCompressInterface.IEncode;

public class TestHuffman {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//FilePath: d:\\mldn.txt d:\\减肥.jpg
		//Huffman编码
		IEncode huffmanEncode = new HuffmanEncode();
		huffmanEncode.Encoding();
	

		//Huffman解码d:\\ceshitest.txt
//		HuffmanDecode huffmanDecode = new HuffmanDecode(huffmanEncode.getEncodeResult(), huffmanEncode.getCodePair());
		IDecode huffmanDecode = new HuffmanDecode(huffmanEncode.GetOutFilePath(), ((HuffmanEncode)huffmanEncode).getInitNodeList());
		System.out.println("----------------所加正确密钥Huffman解码：" + ((HuffmanDecode)huffmanDecode).getKeying().getKey() + "--------------------");
		huffmanDecode.Decoding();
		
		System.out.println("-----Play again-----");

		IDecode huffmanDecode1 = new HuffmanDecode(huffmanEncode.GetOutFilePath(), ((HuffmanEncode)huffmanEncode).getInitNodeList());
		System.out.println("----------------所加错误密钥Huffman解码：" + ((HuffmanDecode)huffmanDecode1).getKeying().getKey() + "--------------------");
		huffmanDecode1.Decoding();
		System.out.println();
		

		
//		System.out.println((int)(3 * Math.pow(2, 5)));
//		Integer.parseInt(s, radix)
		
//		int by = 288;
//		byte byby[] = (byte) 288;
	}
}
