package test2;

import java.util.Random;

/**
 * ����������������ĵ�һ����ʽ��һ�������ߣ�һ�������ߣ�һ��������
 * @author xwjdsh
 */
public class ProducterAndConsumer1 {

	public static void main(String[] args) {
		//Ҫ�������ڲ����з����ⲿ�ı�������Ҫ��final����
		final ProducterAndConsumer1 test = new ProducterAndConsumer1();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					test.produce();
				}
			}
		}).start();
		//ֱ�������̣߳��������߳�
		while (true) {
			test.consume();
		}
	}

	// ��ʼΪ1,���ڻ���
	private Semaphore empty = new Semaphore(1);
	private Semaphore full = new Semaphore(0);

	// һ��������
	private int[] buffered = new int[1];

	public void produce() {
		empty.await();
		//�������ģ���Ʒ
		int product = new Random().nextInt(5000);
		buffered[0] = product;
		System.out.println(Thread.currentThread().getName() + "--�����˲�Ʒ��  "
				+ product);
		full.signal();
	}

	public void consume() {
		full.await();
		int product = buffered[0];
		System.out.println(Thread.currentThread().getName() + "**�����˲�Ʒ��  "
				+ product);
		empty.signal();
	}
}
