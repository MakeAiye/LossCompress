package JavaDemoTest;

public class Book {
	public synchronized void tell(Paint paint) {
		System.out.println("书要画。。。");
		paint.get();
		System.out.println("1");
	}
	public synchronized void get(){
		System.out.println("书得到了画。。。");
	}

}
