package test2;

import java.util.Random;

/**
 * 生产者消费者问题的第一种形式，一个生产者，一个消费者，一个缓冲区
 * @author xwjdsh
 */
public class ProducterAndConsumer1 {

	public static void main(String[] args) {
		//要在匿名内部类中访问外部的变量，需要加final修饰
		final ProducterAndConsumer1 test = new ProducterAndConsumer1();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					test.produce();
				}
			}
		}).start();
		//直接用主线程，不创建线程
		while (true) {
			test.consume();
		}
	}

	// 初始为1,等于互斥
	private Semaphore empty = new Semaphore(1);
	private Semaphore full = new Semaphore(0);

	// 一个缓冲区
	private int[] buffered = new int[1];

	public void produce() {
		empty.await();
		//用随机数模拟产品
		int product = new Random().nextInt(5000);
		buffered[0] = product;
		System.out.println(Thread.currentThread().getName() + "--生产了产品：  "
				+ product);
		full.signal();
	}

	public void consume() {
		full.await();
		int product = buffered[0];
		System.out.println(Thread.currentThread().getName() + "**消费了产品：  "
				+ product);
		empty.signal();
	}
}
