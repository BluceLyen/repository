package test4;

import test2.Semaphore;

/**
 * 苹果桔子问题，变相的生产者消费者问题
 * @author xwjdsh
 */
public class AppleAndOrange {
	
	//表示盘子中是否可放入
	private Semaphore empty = new Semaphore(1);
	private Semaphore apple = new Semaphore(0);
	private Semaphore orange = new Semaphore(0);
	//记录盘子水果的数量
	private int count = 0;

	public static void main(String[] args) {
		final AppleAndOrange test = new AppleAndOrange();
		//通过1到10的循环，对其中的偶数奇数做不同处理，创建5个运行父亲方法，5个母亲方法，5个女儿方法，5个儿子方法的线程
		for (int i = 1; i <= 10; i++) {
			final int flag = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (flag % 2 == 0)
						while (true)
							test.father();
					else
						while (true)
							test.mather();
				}
			}).start();
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (flag % 2 == 0)
						while (true)
							test.son();
					else
						while (true)
							test.daughter();
				}
			}).start();
		}
	}
	
	public void father() {
		empty.await();
		System.out.println(Thread.currentThread().getName()
				+ "\t**父亲向盘中放了1个苹果,盘子中的水果数量为" + (++count));
		apple.signal();
	}

	public void mather() {
		empty.await();
		System.out.println(Thread.currentThread().getName()
				+ "\t--母亲向盘中放了1个桔子,盘子中的水果数量为" + (++count));
		orange.signal();
	}

	public void son() {
		orange.await();
		System.out.println(Thread.currentThread().getName()
				+ "\t&&儿子吃掉了盘中的桔子,盘子中的水果数量为" + (--count));
		empty.signal();
	}
	
	public void daughter() {
		apple.await();
		System.out.println(Thread.currentThread().getName()
				+ "\t##女儿吃掉了盘中的苹果,盘子中的水果数量为" + (--count));
		empty.signal();
	}
}
