package JavaDemoTest;

public class Superman extends Person{
	
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
////		super.run();
//		System.out.println("吃饭。。。");
//	}
	public void fly() {
		int le = len;
		run();
		System.out.println("超音速飞行。。。");
	}
	public void fire() {
		System.out.println("喷出三味真火。。。");
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("吃饭。。。");
	}
	
	

}
