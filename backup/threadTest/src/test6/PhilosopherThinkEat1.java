package test6;

import test2.Semaphore;

/**
 * ��ѧ�ҽ�������ĵڶ��ֽⷨ������table�ź��������پ���������
 * @author xwjdsh
 *
 */
public class PhilosopherThinkEat1 {
	private Semaphore[] semaphores = null;

	public PhilosopherThinkEat1() {
		semaphores = new Semaphore[5];
		for (int i = 0; i < 5; i++) {
			semaphores[i] = new Semaphore(1);
		}
	}
	private Semaphore table = new Semaphore(4);
	public void philosopher(int i) {
		think(i);
		table.await();
		semaphores[i].await();
		semaphores[(i + 1) % 5].await();
		eat(i);
		semaphores[(i + 1) % 5].signal();
		semaphores[i].signal();
		table.signal();
	}

	public void think(int i) {
		System.out.println("��ѧ��" + i + "   ��˼������");
	}

	public void eat(int i) {
		System.out.println("��ѧ��" + i + "   �ڳԷ�");
	}
}