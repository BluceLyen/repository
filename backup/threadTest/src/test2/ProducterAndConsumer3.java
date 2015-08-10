package test2;

import java.util.Random;

/**
 * ����������������ĵ�������ʽ����������ߣ���������ߣ�n��������
 * 
 * @author xwjdsh
 */
public class ProducterAndConsumer3 {

	public static void main(String[] args) {
		final ProducterAndConsumer3 test = new ProducterAndConsumer3();
		//ͨ��ѭ���������̣߳�5�������ߣ�5��������
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

	// ��ʼnΪ10���������޸ģ����������߿ɷ���ĸ���
	private Semaphore empty = new Semaphore(10);
	private Semaphore full = new Semaphore(0);

	// ��������������������λ�����ߣ������߶Ի������Ĳ������γ�ѭ������
	private int in = 0;
	private int out = 0;

	// �������������ź�����ʵ�������ߺ���������֮��Ļ���
	private Semaphore mutexProducter = new Semaphore(1);
	private Semaphore mutexConsumer = new Semaphore(1);

	// ������n��ȡֵ��ͬ
	private int[] buffered = new int[10];

	public void produce() {
		empty.await();
		//����һ���ų������������ߣ������ٽ���Դ
		mutexProducter.await();
		int product = new Random().nextInt(5000);
		buffered[in++] = product;
		// ��in+1����ȡģ�����in=9����ô��һ����Ϊ0���γ�ѭ��
		in %= buffered.length;
		System.out.println(Thread.currentThread().getName() + "--�����˲�Ʒ��  "
				+ product);
		mutexProducter.signal();
		full.signal();
	}

	public void consume() {
		full.await();
		mutexConsumer.await();
		int product = buffered[out++];
		out %= buffered.length;
		System.out.println(Thread.currentThread().getName() + "**�����˲�Ʒ��  "
				+ product);
		mutexConsumer.signal();
		empty.signal();
	}
}
