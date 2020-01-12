package Huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int i = 32;
//	    String to12Bit = Integer.toBinaryString(i);
//	    while (to12Bit.length() < 8) to12Bit = "0" + to12Bit;
//	  
//		System.out.println(to12Bit);0.14432
//		StringBuilder Builder = new StringBuilder("");
//		
//		File file = new File("d:\\下载说明.htm");
//		
//			try {
//				Reader in  = new FileReader(file);
//				char data[] = new char[1024];
//				System.out.println( "文件内容： " + new String(data, 0 ,in.read(data)));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		
//		System.out.println( "文件内容： " + Builder.toString());
		
		StringBuilder builder = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < 5;i ++) {
			builder.append(String.valueOf(rand.nextInt(2)));
		}
		System.out.println( "key： " + builder.toString());
		
		

	}

}
