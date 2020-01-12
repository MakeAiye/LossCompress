package InitialRead;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import Huffman.Node;

import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @author WenHui
 *  根据用户指定，读取文件，并提供相应算法初始化操作
 *
 */

public class Initial {
	private String inputFilePath;
	public String outputFilePath;
	private Scanner inputFileScan;
	protected String inputFileContent, inputFileForm;
	protected HashMap<String,Integer> frequencyMap;
	public Initial(String inputFileContent) {
		super();
		this.inputFileContent = inputFileContent;
	}

	public Initial() {
		// TODO Auto-generated constructor stub
	}
	
	public void newInit() {
		//FilePath: d:\\mldn.txt
		this.GetInputFilePath();
		inputFileForm = inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
//		TurntxtForm();
		GetInputFileContent();
	}

	//获取输入文件路径
	public void GetInputFilePath() {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入文件路径：");
		String inputFilePath = scan.next();
		this.inputFilePath = inputFilePath;
		
	}
	
	//打开指定路径后的文件
//	public void OpenFile() {
//		try {
//			inputFileScan = new Scanner(new File(inputFilePath.substring(0, inputFilePath.lastIndexOf(".")) + ".txt"));
////			inputFileScan = new Scanner(new File("d:\\mldn.txt"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			System.err.println("Error- File Not Found");
//            System.exit(1);
//		}
//	}
	
	//获取输入文件内容
	public void GetInputFileContent() {
		File file = new File(inputFilePath);
		StringBuilder builder = new StringBuilder();
		Reader in;
		try {
			in  = new FileReader(file);
			int data = 0;
			int i = 1;
			while((data = in.read()) != -1) {
//				System.out.print(i ++ + ",");
				builder.append((char)data);
			}
			this.inputFileContent = builder.toString();
//			System.out.println(inputFileContent.length());
//			System.out.println( "文件内容： " + inputFileContent + "...");
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		File file = new File(inputFilePath.substring(0, inputFilePath.lastIndexOf(".")) + ".txt");
//		file.renameTo(new File(inputFilePath));
	}
	
	//得到文件内容的字符类型及对应频数
	public HashMap<String,Integer> GetFrequencyMap(){
	    char currentChar;
		String currentString = "";
		frequencyMap = new HashMap<String, Integer>();
		for(int i = 0;i < inputFileContent.length();i ++) {
			currentChar = inputFileContent.charAt(i);
			currentString = Character.toString(currentChar);
//			System.out.print(i + ",");
			if(frequencyMap.containsKey(currentString)) {
				
				frequencyMap.put(currentString,frequencyMap.get(currentString) + 1);
			}else {
				frequencyMap.put(currentString,1);
			}				
		}
//		System.out.println("输出频数Map: ");
//		Set<Map.Entry<String,Integer>> set = frequencyMap.entrySet();
//		Iterator<Map.Entry<String,Integer>> iter = set.iterator();
//		while(iter.hasNext()) {
//			Map.Entry<String,Integer> me = iter.next();
//			System.out.print(me.getKey() + "," + me.getValue() + " ");
//		}
//		System.out.println("");
		return frequencyMap;
	}
	
	
	
	
	//输出指定类型文件
	public void OutputTypeFile(String type, String fileContent) {
		String parentPath = inputFilePath.substring(0, inputFilePath.lastIndexOf("."));
		outputFilePath = parentPath + "." + type;
//		outputFilePath = "E:\\mldn.txt";
		try {
			PrintWriter write = new PrintWriter(outputFilePath, "UTF-8");
//			System.out.println(fileContent);
			write.print(fileContent); 
			write.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println("---EXECPTION OCCURED---");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//比较编码文件前后大小方法
	public void CompareFileSize() {
		File beforeFile = new File(inputFilePath);
		System.out.println("编码前文件大小： " + beforeFile.length() + " byte.");
		File afterFile = new File(outputFilePath);
		System.out.println("编码后文件大小： " + afterFile.length() + " byte.");
	}
	
	/**
     * @param binaryString 二进制小数(如 0.1001)的字符串形式转为BigDecimal实际值输出
     * @return
     */
    public static BigDecimal binaryStringToBigDecimal(String binaryString) {
    	 BigDecimal result = new BigDecimal("0");
        for (int i = 0; i < binaryString.length(); i++) {
            BigDecimal temp = new BigDecimal(binaryString.substring(i, i + 1))
            		.multiply(new BigDecimal(1).divide(new BigDecimal(2).pow(i + 1)));
            result = result.add(temp);
        }
        return result;
    }

	public String getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

	public Scanner getInputFileScan() {
		return inputFileScan;
	}

	public void setInputFileScan(Scanner inputFileScan) {
		this.inputFileScan = inputFileScan;
	}

	public String getInputFileContent() {
		return inputFileContent;
	}

	public void setInputFileContent(String inputFileContent) {
		this.inputFileContent = inputFileContent;
	}

	public HashMap<String, Integer> getFrequencyMap() {
		return frequencyMap;
	}

	public void setFrequencyMap(HashMap<String, Integer> frequencyMap) {
		this.frequencyMap = frequencyMap;
	}

	
	
	

}
