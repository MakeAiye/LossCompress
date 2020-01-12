package JavaDemoTest;

public class DeadLock {
	private Book book = new Book();
	private Paint paint = new Paint();
	public DeadLock() {
		new Thread(() -> {
				paint.tell(book);
				System.out.println("子");
		}).start();
		book.tell(paint);
		System.out.println("主");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DeadLock();

	}

	

}
