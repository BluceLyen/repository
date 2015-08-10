package test6;

public class Main {
	public static void main(String[] args) {
		//5个哲学家进程，调试不同的方式修改类对象
		final PhilosopherThinkEat3 test = new PhilosopherThinkEat3();
		for (int i = 0; i < 5; i++) {
			final int flag = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						test.philosopher(flag);
					}
				}
			}).start();
		}
	}
}
