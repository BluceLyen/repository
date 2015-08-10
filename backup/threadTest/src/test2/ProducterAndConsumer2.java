package test2;

import java.util.Random;

/**
 * ����������������ĵ�һ����ʽ��һ�������ߣ�һ�������ߣ�n��������
 * @author xwjdsh
 */
public class ProducterAndConsumer2 {
	
	public static void main(String[] args) {
		//Ҫ�������ڲ����з����ⲿ�ı�������Ҫ��final����
		final ProducterAndConsumer2 test = new ProducterAndConsumer2();
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
	
	//��ʼnΪ10���������޸ģ����������߿ɷ���ĸ���
	private Semaphore empty=new Semaphore(10);
	private Semaphore full=new Semaphore(0);
	
	//��������������������λ�����ߣ������߶Ի������Ĳ������γ�ѭ������
	private int in=0;
	private int out=0;
	
	//������n��ȡֵ��ͬ
	private int[] buffered=new int[10];
	
	public void produce(){
		empty.await();
		int product=new Random().nextInt(5000);
		buffered[in++]=product;
		//��in+1����ȡģ�����in=9����ô��һ����Ϊ0���γ�ѭ��
		in%=buffered.length;
		System.out.println(Thread.currentThread().getName()+"--�����˲�Ʒ��  "+product);
		full.signal();
	}
	public void consume(){
		full.await();
		int product=buffered[out++];
		out%=buffered.length;
		System.out.println(Thread.currentThread().getName()+"**�����˲�Ʒ��  "+product);
		empty.signal();
	}
}
