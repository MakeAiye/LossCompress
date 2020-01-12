package lossCompressInterface;

import Arithmetic.ArithmeticEncode;
import Huffman.HuffmanEncode;
import LZW.LZWEncode;
import RunLength.RunLengthEncode;

public class LossCompressImpliments {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//FilePath: d:\\mldn.txt  d:\\ceshitest.txt
		
		//LZW编码/解码
//		String type = "LZW";
		//游程编码/解码
//		String type = "RunLength";
		//哈夫曼编码/解码
		String type = "Huffman";
		//算术编码/解码
//		String type = "Arithmetic";
		
		IEncode enco = Factory.GetEncodeInstance(Factory.GetEncodeClassName(type));
		long startEncode = System.currentTimeMillis();
		enco.Encoding();
		long endEncode = System.currentTimeMillis();
		System.out.println("编码所花时间： " + (endEncode - startEncode) + "ms.");
		
		IDecode deco = Factory.GetDecodeInstance(Factory.GetDecodeClassName(type), enco.GetOutFilePath()
				, enco.GetMap());
		long startDecode = System.currentTimeMillis();
		deco.Decoding();
		long endDecode = System.currentTimeMillis();
		System.out.println("解码所花时间： " + (endDecode - startDecode) + "ms.");
	}
}
