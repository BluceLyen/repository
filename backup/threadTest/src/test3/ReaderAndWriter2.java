package test3;

import java.util.Random;
import test2.Semaphore;

/**
 * ����д������ĵڶ�����ʽ��д������
 * @author xwjdsh
 */
public class ReaderAndWriter2 {
	private int readerCount = 0;
	//������Ŷ�д�ߵļ���
	private int writerCount = 0;
	private Semaphore mutexReaderCount = new Semaphore(1);
	//�����ٽ���ԴwriterCount
	private Semaphore mutexWriterCount = new Semaphore(1);
	private Semaphore writer = new Semaphore(1);
	
	//��������ͨ������д�ߵĲ�ͬ����ʵ������
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
		mutexWriterCount.await();
		if (writerCount == 0)
			queue.await();
		writerCount++;
		mutexWriterCount.signal();
		writer.await();
		System.out.println(Thread.currentThread().getName() + "*****׼��д����");
		buffered[0] = new Random().nextInt(5000);
		System.out.println(Thread.currentThread().getName()
				+ "*****д��������             " + buffered[0]);
		writer.signal();
		mutexWriterCount.await();
		if (--readerCount == 0)
			queue.signal();
		mutexWriterCount.signal();
	}
}
