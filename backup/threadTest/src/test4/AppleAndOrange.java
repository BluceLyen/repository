package test4;

import test2.Semaphore;

/**
 * ƻ���������⣬���������������������
 * @author xwjdsh
 */
public class AppleAndOrange {
	
	//��ʾ�������Ƿ�ɷ���
	private Semaphore empty = new Semaphore(1);
	private Semaphore apple = new Semaphore(0);
	private Semaphore orange = new Semaphore(0);
	//��¼����ˮ��������
	private int count = 0;

	public static void main(String[] args) {
		final AppleAndOrange test = new AppleAndOrange();
		//ͨ��1��10��ѭ���������е�ż����������ͬ��������5�����и��׷�����5��ĸ�׷�����5��Ů��������5�����ӷ������߳�
		for (int i = 1; i <= 10; i++) {
			final int flag = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (flag % 2 == 0)
						while (true)
							test.father();
					else
						while (true)
							test.mather();
				}
			}).start();
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (flag % 2 == 0)
						while (true)
							test.son();
					else
						while (true)
							test.daughter();
				}
			}).start();
		}
	}
	
	public void father() {
		empty.await();
		System.out.println(Thread.currentThread().getName()
				+ "\t**���������з���1��ƻ��,�����е�ˮ������Ϊ" + (++count));
		apple.signal();
	}

	public void mather() {
		empty.await();
		System.out.println(Thread.currentThread().getName()
				+ "\t--ĸ�������з���1������,�����е�ˮ������Ϊ" + (++count));
		orange.signal();
	}

	public void son() {
		orange.await();
		System.out.println(Thread.currentThread().getName()
				+ "\t&&���ӳԵ������еĽ���,�����е�ˮ������Ϊ" + (--count));
		empty.signal();
	}
	
	public void daughter() {
		apple.await();
		System.out.println(Thread.currentThread().getName()
				+ "\t##Ů���Ե������е�ƻ��,�����е�ˮ������Ϊ" + (--count));
		empty.signal();
	}
}
