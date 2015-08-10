package test3;

import java.util.Random;

import test2.Semaphore;

/**
 * ����д������ĵ�һ����ʽ����������
 * @author xwjdsh
 */
public class ReaderAndWriter1 {
	
	//��¼���ж��ߵ�����
	private int readerCount = 0;
	
	//�Զ��ߵĻ��⣬�����ٽ���ԴreadCount
	private Semaphore mutexReaderCount = new Semaphore(1);
	
	//д�߻���
	private Semaphore writer = new Semaphore(1);
	
	//ֱ����int������ʾҲ���Ե�
	private int[] buffered = new int[1];
	
	// ����
	public void read() {
		mutexReaderCount.await();
		if (readerCount == 0)
			writer.await();
		readerCount++;
		mutexReaderCount.signal();
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
		writer.await();
		System.out.println(Thread.currentThread().getName() + "*****׼��д����");
		buffered[0] = new Random().nextInt(5000);
		System.out.println(Thread.currentThread().getName()
				+ "*****д��������             " + buffered[0]);
		writer.signal();
	}
}
