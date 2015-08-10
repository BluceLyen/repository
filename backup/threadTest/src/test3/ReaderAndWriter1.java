package test3;

import java.util.Random;

import test2.Semaphore;

/**
 * 读者写者问题的第一种形式，读者优先
 * @author xwjdsh
 */
public class ReaderAndWriter1 {
	
	//记录已有读者的数量
	private int readerCount = 0;
	
	//对读者的互斥，保护临界资源readCount
	private Semaphore mutexReaderCount = new Semaphore(1);
	
	//写者互斥
	private Semaphore writer = new Semaphore(1);
	
	//直接用int变量表示也可以的
	private int[] buffered = new int[1];
	
	// 读者
	public void read() {
		mutexReaderCount.await();
		if (readerCount == 0)
			writer.await();
		readerCount++;
		mutexReaderCount.signal();
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
		writer.await();
		System.out.println(Thread.currentThread().getName() + "*****准备写数据");
		buffered[0] = new Random().nextInt(5000);
		System.out.println(Thread.currentThread().getName()
				+ "*****写入了数据             " + buffered[0]);
		writer.signal();
	}
}
