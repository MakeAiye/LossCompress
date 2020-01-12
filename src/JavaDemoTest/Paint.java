package JavaDemoTest;

public class Paint {
	public synchronized void tell(Book book) {
		System.out.println("画要书。。。");
		book.get();
		System.out.println("2");
	}
	public synchronized void get(){
		System.out.println("画得到了书。。。");
	}

}
