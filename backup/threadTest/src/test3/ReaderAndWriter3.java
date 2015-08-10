package test3;

import java.util.Random;

import test2.Semaphore;

/**
 * ����д�ߵĵ�������ʽ����ƽ����
 * @author xwjdsh
 */
public class ReaderAndWriter3 {
	private int readerCount = 0;
	private Semaphore mutexReaderCount = new Semaphore(1);
	private Semaphore writer = new Semaphore(1);
	
	//ͨ���Ի������ľ���ʵ����Թ�ƽ�ľ���
	private Semaphore queue = new Semaphore(1);
	private int[] buffered = new int[1];

	// ����
	public void read() {
		queue.await();
		mutexReaderCount.await();
		if (readerCount == 0)
			writer.await();
		readerCount++;
		mutexReaderCount.signal();
		queue.signal();
		System.out.println(Thread.currentThread().getName() + "-----׼��������");
		System.out.println(Thread.currentThread().getName()
				+ "-----����������             " + buffered[0]);
		mutexReaderCount.await();
		if (--readerCount == 0)
			writer.signal();
		mutexReaderCount.signal();
	}

	// д��
	public void writ() {
		queue.await();
		writer.await();
		queue.signal();
		System.out.println(Thread.currentThread().getName() + "*****׼��д����");
		buffered[0] = new Random().nextInt(5000);
		System.out.println(Thread.currentThread().getName()
				+ "*****д��������             " + buffered[0]);
		writer.signal();
	}
}
