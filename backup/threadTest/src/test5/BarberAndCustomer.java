package test5;

import test2.Semaphore;

/**
 * 理发师问题
 * @author xwjdsh
 */
public class BarberAndCustomer {
	private Semaphore barber = new Semaphore(0);
	private Semaphore customer = new Semaphore(0);
	private Semaphore mutex = new Semaphore(1);//互斥信号，申请资源
	private int waitingCount = 0;
	// 5把椅子
	private final int waitChair = 5;

	public static void main(String[] args) {
		final BarberAndCustomer test = new BarberAndCustomer();
		//10个顾客线程，1个理发师线程
		for (int i = 1; i <= 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {//匿名内部类
					while (true)
						test.customer();
				}
			}).start();
		}
		while (true)//如果有可用资源，线程可执行
			test.barber();
	}

	public void barber() {//理发师的方法
		customer.await();//顾客等待
		mutex.await();
		System.out
				.println("理发师" + Thread.currentThread().getName() + "准备为顾客理发");
		System.out.println("现在还有" + (--waitingCount) + "个顾客在等待");
		barber.signal();
		mutex.signal();
		System.out.println("理发师" + Thread.currentThread().getName() + "为顾客理发");
	}

	public void customer() {
		mutex.await();
		if (waitingCount >= waitChair) {
			System.out.println("顾客" + Thread.currentThread().getName()
					+ "因为椅子满了离开了");
			mutex.signal();
		} else {
			System.out.println("顾客" + Thread.currentThread().getName()
					+ "进入了理发店");
			System.out.println("现在有" + (++waitingCount) + "个顾客在等待");
			customer.signal();
			mutex.signal();
			barber.await();
			System.out.println("顾客" + Thread.currentThread().getName() + "被理发");
		}
	}
}
