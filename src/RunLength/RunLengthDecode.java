package RunLength;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

import lossCompressInterface.IDecode;

public class RunLengthDecode implements IDecode{
	private String decodeFilePath, resultFileContent, decodeResult;
	private LinkedHashMap<String, ?> map;

	public RunLengthDecode(String decodeFilePath, LinkedHashMap<String, Integer> map) {
		super();
		this.decodeFilePath = decodeFilePath;
		this.map = null;
	}

	@Override
	public void Decoding() {
		// TODO Auto-generated method stub
		StringBuilder decodeBuilder = new StringBuilder();
		GetEncodeResultFromFile();
		for(int i = 0; i < resultFileContent.length(); i ++) {
			if(resultFileContent.charAt(i) == FLAG) {
				int num = (int)resultFileContent.charAt(i + 1);
				for(int j = 0; j < num; j ++) {
					decodeBuilder.append(resultFileContent.charAt(i - 1));
				}
				i += 2;
			}
			if(i < resultFileContent.length())
			decodeBuilder.append(resultFileContent.charAt(i));
		}
		decodeResult = decodeBuilder.toString();
//		System.out.println("游程解码结果：" + decodeResult);
		OutputDecodeFile();
	}
	
	//字节读取，从文件读取每个字节，获得对应的压缩数据码ASCII
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
//			System.out.print("从压缩文件读取的内容： ");
//			System.out.println(resultFileContent);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void OutputDecodeFile() {
		// TODO Auto-generated method stub
		File lateFile = new File(decodeFilePath.substring(0, decodeFilePath.lastIndexOf(".")) + "runlen.txt");
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
