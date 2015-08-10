package test2;

/**
 * ģ����Դ�࣬����Ŀ�е�pv���ⶼ�����������
 * @author xwjdsh
 */

public class Semaphore {
	//��ʾ�ź���
	private int value;
	
	public Semaphore(int value){
		this.value=value;
	}
	
	// P��������������Ϊawait()
	public synchronized void await(){
		if(--value<0){//�ź���������
			try {
				//wait�������Զ��Ľ��߳����������ҡ��ڵ�ǰ�����ϣ����������Զ����̶߳���
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// V����������Ϊsignal()
	public synchronized void signal(){
		if(++value<=0){//�źſ���
			this.notify();//����һ������
		}
	}
}
