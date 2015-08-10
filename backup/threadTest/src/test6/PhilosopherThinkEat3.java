package test6;

import test2.Semaphore;

/**
 * ��ѧ�ҽ��͵ĵ����ִ�������������λ��ż��λ��ѧ���Բ�ͬ�ľ�����˳��
 * @author xwjdsh
 */
public class PhilosopherThinkEat3 {
	//���ӵ��ź������飬�ڹ��캯����new�����
	private Semaphore[] semaphores=null;

	
	public PhilosopherThinkEat3() {
		semaphores=new Semaphore[5];
		for(int i=0;i<5;i++){
			semaphores[i]=new Semaphore(1);
		}
	}
	
	//�ò���i����ʾ��ѧ��
	public void philosopher(int i){
		think(i);
		if(i%2!=0){
			semaphores[i].await();
			semaphores[(i+1)%5].await();
		}else{
			semaphores[(i+1)%5].await();
			semaphores[i].await();
		}
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