package RunLength;

import lossCompressInterface.IDecode;
import lossCompressInterface.IEncode;

public class TestRun {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//FilePath: d:\\mldn.txt d:\\ceshitest.txt
		IEncode runLengthEncode = new RunLengthEncode();
		runLengthEncode.Encoding();
		
		IDecode runLengthDecode = new RunLengthDecode(runLengthEncode.GetOutFilePath()
				, runLengthEncode.GetMap());
		runLengthDecode.Decoding();

	}

}
