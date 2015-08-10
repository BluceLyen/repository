package test3;

import java.util.Random;

import test2.Semaphore;

/**
 * 读者写者的第三种形式，公平竞争
 * @author xwjdsh
 */
public class ReaderAndWriter3 {
	private int readerCount = 0;
	private Semaphore mutexReaderCount = new Semaphore(1);
	private Semaphore writer = new Semaphore(1);
	
	//通过对互斥锁的竞争实现相对公平的竞争
	private Semaphore queue = new Semaphore(1);
	private int[] buffered = new int[1];

	// 读者
	public void read() {
		queue.await();
		mutexReaderCount.await();
		if (readerCount == 0)
			writer.await();
		readerCount++;
		mutexReaderCount.signal();
		queue.signal();
		System.out.println(Thread.currentThread().getName() + "-----准备读数据");
		System.out.println(Thread.currentThread().getName()
				+ "-----读到了数据             " + buffered[0]);
		mutexReaderCount.await();
		if (--readerCount == 0)
			writer.signal();
		mutexReaderCount.signal();
	}

	// 写者
	public void writ() {
		queue.await();
		writer.await();
		queue.signal();
		System.out.println(Thread.currentThread().getName() + "*****准备写数据");
		buffered[0] = new Random().nextInt(5000);
		System.out.println(Thread.currentThread().getName()
				+ "*****写入了数据             " + buffered[0]);
		writer.signal();
	}
}
