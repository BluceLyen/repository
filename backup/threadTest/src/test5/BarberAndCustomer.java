package test5;

import test2.Semaphore;

/**
 * ��ʦ����
 * @author xwjdsh
 */
public class BarberAndCustomer {
	private Semaphore barber = new Semaphore(0);
	private Semaphore customer = new Semaphore(0);
	private Semaphore mutex = new Semaphore(1);//�����źţ�������Դ
	private int waitingCount = 0;
	// 5������
	private final int waitChair = 5;

	public static void main(String[] args) {
		final BarberAndCustomer test = new BarberAndCustomer();
		//10���˿��̣߳�1����ʦ�߳�
		for (int i = 1; i <= 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {//�����ڲ���
					while (true)
						test.customer();
				}
			}).start();
		}
		while (true)//����п�����Դ���߳̿�ִ��
			test.barber();
	}

	public void barber() {//��ʦ�ķ���
		customer.await();//�˿͵ȴ�
		mutex.await();
		System.out
				.println("��ʦ" + Thread.currentThread().getName() + "׼��Ϊ�˿���");
		System.out.println("���ڻ���" + (--waitingCount) + "���˿��ڵȴ�");
		barber.signal();
		mutex.signal();
		System.out.println("��ʦ" + Thread.currentThread().getName() + "Ϊ�˿���");
	}

	public void customer() {
		mutex.await();
		if (waitingCount >= waitChair) {
			System.out.println("�˿�" + Thread.currentThread().getName()
					+ "��Ϊ���������뿪��");
			mutex.signal();
		} else {
			System.out.println("�˿�" + Thread.currentThread().getName()
					+ "����������");
			System.out.println("������" + (++waitingCount) + "���˿��ڵȴ�");
			customer.signal();
			mutex.signal();
			barber.await();
			System.out.println("�˿�" + Thread.currentThread().getName() + "����");
		}
	}
}
