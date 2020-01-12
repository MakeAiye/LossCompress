package JavaDemoTest;

public class ThreadDemo {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread mianth = Thread.currentThread();
		Thread thread = new Thread(() -> {
			for(int x = 0; x < 10; x ++) {
				if(x == 3) {
//					Thread.yield();
					try {
						mianth.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("【YIELD】线程礼让，" + Thread.currentThread().getName());
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "执行、x = " + x);
			}
		},"玩耍的线程");
		thread.start();
		for(int x = 0; x < 10; x ++) {
			Thread.sleep(100);
			System.out.println("【霸道的主线程】number = " + x);
		}

	}

}
