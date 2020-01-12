package Arithmetic;

import InitialRead.Initial;
import lossCompressInterface.IEncode;

public class ArithmeticTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//FilePath: d:\\mldn.txt d:\\ceshitest.txt
		IEncode arithmeticEncode = new ArithmeticEncode();
		arithmeticEncode.Encoding();

		
//		ArithmeticDecode arithmeticdecode = new ArithmeticDecode(arithmeticEncode.getEncodeResult(), arithmeticEncode.getInitIntervalMap());
		ArithmeticDecode arithmeticdecode = new ArithmeticDecode(arithmeticEncode.GetOutFilePath(), arithmeticEncode.GetMap());
		arithmeticdecode.GetEncodeResultFromFile();
		arithmeticdecode.AnalyseFileContent();
		System.out.println("算术解码输出： " + arithmeticdecode.ArithmeticDecoding());
	}

}
