package test6;

import test2.Semaphore;

/**
 * ��ѧ�ҽ��͵ĵ�һ�ִ����������뻥���ź���
 * @author xwjdsh
 */
public class PhilosopherThinkEat {
	//���ӵ��ź������飬�ڹ��캯����new�����
	private Semaphore[] semaphores=null;
	//�����ź���
	private Semaphore mutex=new Semaphore(1);
	
	public PhilosopherThinkEat() {
		semaphores=new Semaphore[5];
		for(int i=0;i<5;i++){
			semaphores[i]=new Semaphore(1);
		}
	}
	
	//�ò���i����ʾ��ѧ��
	public void philosopher(int i){
		think(i);
		mutex.await();
		semaphores[i].await();
		semaphores[(i+1)%5].await();
		mutex.signal();
		eat(i);
		semaphores[i].signal();
		semaphores[(i+1)%5].signal();
	}
	private void think(int i){
		System.out.println("��ѧ��"+i+"   ��˼������");
	}
	private void eat(int i){
		System.out.println("��ѧ��"+i+"   �ڳԷ�");
	}
}