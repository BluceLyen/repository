package test2;

import java.util.Random;

/**
 * 生产者消费者问题的第三种形式，多个生产者，多个消费者，n个缓冲区
 * 
 * @author xwjdsh
 */
public class ProducterAndConsumer3 {

	public static void main(String[] args) {
		final ProducterAndConsumer3 test = new ProducterAndConsumer3();
		//通过循环来创建线程，5个生产者，5个消费者
		for (int i = 1; i <= 5; i++)
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						test.produce();
					}
				}
			}).start();
		for (int i = 1; i <= 5; i++)
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						test.consume();
					}
				}
			}).start();
	}

	// 初始n为10，可任意修改，代表生产者可放入的个数
	private Semaphore empty = new Semaphore(10);
	private Semaphore full = new Semaphore(0);

	// 引入两个索引，用来定位生产者，消费者对缓冲区的操作，形成循环队列
	private int in = 0;
	private int out = 0;

	// 引入两个互斥信号量，实现生产者和消费者组之间的互斥
	private Semaphore mutexProducter = new Semaphore(1);
	private Semaphore mutexConsumer = new Semaphore(1);

	// 跟上面n的取值相同
	private int[] buffered = new int[10];

	public void produce() {
		empty.await();
		//在这一步排斥其他的生产者，保护临界资源
		mutexProducter.await();
		int product = new Random().nextInt(5000);
		buffered[in++] = product;
		// 对in+1进行取模，如果in=9，那么这一步后为0，形成循环
		in %= buffered.length;
		System.out.println(Thread.currentThread().getName() + "--生产了产品：  "
				+ product);
		mutexProducter.signal();
		full.signal();
	}

	public void consume() {
		full.await();
		mutexConsumer.await();
		int product = buffered[out++];
		out %= buffered.length;
		System.out.println(Thread.currentThread().getName() + "**消费了产品：  "
				+ product);
		mutexConsumer.signal();
		empty.signal();
	}
}
