package RunLength;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import InitialRead.Initial;
import lossCompressInterface.IEncode;

public class RunLengthEncode extends Initial implements IEncode{
	private LinkedHashMap<String, Integer> fucValueMap;
	private int[] fuc, diff;
	private int len;
	private String encodeResult;
	

	public void GetFucAndDiff() {
		len = inputFileContent.length();
		fucValueMap = new LinkedHashMap<String, Integer>();
		fuc = new int[len];
		diff = new int[len];
		int num = 1;
		for(int i = 0;i < len; i ++) {	
			//首先刷新fucValueMap
			if(!fucValueMap.containsKey("" + inputFileContent.charAt(i))) {
				fucValueMap.put("" + inputFileContent.charAt(i), num ++);
			}
			//再赋值fuc每个值
			fuc[i] = fucValueMap.get("" + inputFileContent.charAt(i));
			//最后前向差分，赋值diff每个值
			if(i == 0) {
				diff[0] = fuc[0];
			}else {
				if(fuc[i] - fuc[i - 1] == 0) {
					diff[i] = 0;
				}else {
					diff[i] = 1;
				};
			}
		}
//		for(int temp : fuc) {
//			System.out.print(temp + "、");
//		}
//		System.out.println("");
//		for(int temp : diff) {
//			System.out.print(temp + "、");
//		}
	}
		
	
	
	public void CheckDiffToResult() {
		StringBuilder resultBuilder = new StringBuilder();
		for(int i = 0; i < len; i ++) {
			//统计当前的0个数
			if(diff[i] == 0) {
				int zeroNum = 1;
				//如果没到结尾
				if(i + zeroNum < len) {
					while(diff[i + zeroNum] == 0){
						zeroNum ++;
						if(i + zeroNum >= len) {
							break;
						}
					}
					//针对统计结果衍生编码结果
					//zeroNum如果小于3，代表相同字符个数小于等于3，此时zeroNum无实际意义
					//zeroNum如果大于等于3，代表相同字符个数大于3，此时zeroNum有实际意义
					if(zeroNum < 3) {
						for(int j = 0; j < zeroNum; j ++)
							resultBuilder.append(inputFileContent.charAt(i + j));
					}else {
						resultBuilder.append("" + FLAG + (char)(zeroNum + 1));
					}
					i += zeroNum;
				}
			}
			if(i < len) {
				resultBuilder.append(inputFileContent.charAt(i));
			}
		}
		encodeResult = resultBuilder.toString();
		System.out.println("");
//		System.out.println("游程编码结果：" + encodeResult);
	}

	@Override
	public void Encoding() {
		// TODO Auto-generated method stub
		newInit();
		GetFucAndDiff();
		CheckDiffToResult();
		OutputTypeFile("runlength", encodeResult);
		CompareFileSize();
	}



	public LinkedHashMap<String, Integer> getFucValueMap() {
		return fucValueMap;
	}



	public void setFucValueMap(LinkedHashMap<String, Integer> fucValueMap) {
		this.fucValueMap = fucValueMap;
	}



	@Override
	public LinkedHashMap GetMap() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String GetOutFilePath() {
		// TODO Auto-generated method stub
		return this.outputFilePath;
	}

}
