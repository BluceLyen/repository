package test3;

import java.util.Random;
import test2.Semaphore;

/**
 * 读者写者问题的第二种形式，写者优先
 * @author xwjdsh
 */
public class ReaderAndWriter2 {
	private int readerCount = 0;
	//引入对排队写者的计数
	private int writerCount = 0;
	private Semaphore mutexReaderCount = new Semaphore(1);
	//保护临界资源writerCount
	private Semaphore mutexWriterCount = new Semaphore(1);
	private Semaphore writer = new Semaphore(1);
	
	//队列锁，通过读者写者的不同处理实现优先
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
		mutexWriterCount.await();
		if (writerCount == 0)
			queue.await();
		writerCount++;
		mutexWriterCount.signal();
		writer.await();
		System.out.println(Thread.currentThread().getName() + "*****准备写数据");
		buffered[0] = new Random().nextInt(5000);
		System.out.println(Thread.currentThread().getName()
				+ "*****写入了数据             " + buffered[0]);
		writer.signal();
		mutexWriterCount.await();
		if (--readerCount == 0)
			queue.signal();
		mutexWriterCount.signal();
	}
}
