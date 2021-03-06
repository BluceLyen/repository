package test2;

import java.util.Random;

/**
 * 生产者消费者问题的第一种形式，一个生产者，一个消费者，n个缓冲区
 * @author xwjdsh
 */
public class ProducterAndConsumer2 {
	
	public static void main(String[] args) {
		//要在匿名内部类中访问外部的变量，需要加final修饰
		final ProducterAndConsumer2 test = new ProducterAndConsumer2();
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
	
	//初始n为10，可任意修改，代表生产者可放入的个数
	private Semaphore empty=new Semaphore(10);
	private Semaphore full=new Semaphore(0);
	
	//引入两个索引，用来定位生产者，消费者对缓冲区的操作，形成循环队列
	private int in=0;
	private int out=0;
	
	//跟上面n的取值相同
	private int[] buffered=new int[10];
	
	public void produce(){
		empty.await();
		int product=new Random().nextInt(5000);
		buffered[in++]=product;
		//对in+1进行取模，如果in=9，那么这一步后为0，形成循环
		in%=buffered.length;
		System.out.println(Thread.currentThread().getName()+"--生产了产品：  "+product);
		full.signal();
	}
	public void consume(){
		full.await();
		int product=buffered[out++];
		out%=buffered.length;
		System.out.println(Thread.currentThread().getName()+"**消费了产品：  "+product);
		empty.signal();
	}
}
