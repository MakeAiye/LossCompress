package Arithmetic;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import Huffman.Node;

public class Interval implements Comparable<Interval>{
	private BigDecimal leftNode;
	private BigDecimal rightNode;
	private String characterChar;
	private BigDecimal range;
	private int weight;//用于后期排序
	
	//刷新区间进度条
	public LinkedHashMap<String, Interval> RefreshIntervalMap(LinkedHashMap<String, Interval> initMap){
		LinkedHashMap<String, Interval> refreshMap = new LinkedHashMap<String, Interval>();
		BigDecimal nextleft = new BigDecimal("0");
		int i = 0;
		for(Entry<String, Interval> entry : initMap.entrySet()) {
			Interval interval = new Interval();
			if(i == 0) {
				interval.setLeftNode(this.leftNode);
			}else {
				interval.setLeftNode(nextleft);
			}
			interval.setRange(this.range.multiply(entry.getValue().getRange()));
			nextleft = interval.getLeftNode().add(interval.getRange());
			interval.setRightNode(nextleft);
			interval.setCharacterChar(entry.getValue().getCharacterChar());
			refreshMap.put(entry.getKey(), interval);
			i ++;
		}
//		for(Entry<String, Interval> entry : refreshMap.entrySet()) {
//			System.out.println(entry.getKey() + " = " + entry.getValue());
//		}
		return refreshMap;
	}
	
	
	public BigDecimal getLeftNode() {
		return leftNode;
	}
	public void setLeftNode(BigDecimal leftNode) {
		this.leftNode = leftNode;
	}
	public BigDecimal getRightNode() {
		return rightNode;
	}
	public void setRightNode(BigDecimal rightNode) {
		this.rightNode = rightNode;
	}
	public String getCharacterChar() {
		return characterChar;
	}
	public void setCharacterChar(String characterChar) {
		this.characterChar = characterChar;
	}
	@Override
	public int compareTo(Interval interval) {
		// TODO Auto-generated method stub
		return this.weight - interval.weight;
	}
	public BigDecimal getRange() {
		return range;
	}
	public void setRange(BigDecimal range) {
		this.range = range;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "Interval [rightNode=" + rightNode.doubleValue() + ", characterChar=" + characterChar + ", range=" + range.doubleValue() + "]";
	}
	
	
	
	
	
	
	

}
