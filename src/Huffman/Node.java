package Huffman;
/**
 * Huffman树的节点
 * @author WenHui Li
 *
 */
public class Node implements Comparable<Node>{
	private Node leftChild;
	private Node rightChild;
	private Node parent;
	private int weight = 0;
	private String inintchar = "";
	private int flag = -1;
	
	public Node(String inintchar, int weight) {
		super();
		this.weight = weight;
		this.inintchar = inintchar;
	}

	public Node() {
		// TODO Auto-generated constructor stub
	}
	

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof Node)) {
			return false;
		}
		Node node = (Node) obj;
		
		return this.inintchar == node.inintchar && this.flag == node.flag;
	}

	@Override
	public String toString() {
		return "Node对象 [weight=" + weight + ", inintchar=" + inintchar + "]";
	}

	@Override
	public int compareTo(Node node) {
		// TODO Auto-generated method stub
		return node.weight - this.weight;
	}

	public String getInintchar() {
		return inintchar;
	}

	public void setInintchar(String inintchar) {
		this.inintchar = inintchar;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
