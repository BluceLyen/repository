package test3;


public class Main {
	
	//����д�������main�����Ĵ��룬��Ϊ�������main��һ�£��ʲ���д
	public static void main(String[] args) {
		//�����������ж��޸ģ�����Ҫ���Թ�ƽ���������ΪReaderAndWriter3 test = new ReaderAndWriter3();
		//�ڵ���д������ʱ�����ʵ���ͣ�̣߳����ڹ۲�Ч��
		final ReaderAndWriter3 test = new ReaderAndWriter3();
		for (int i = 1; i <= 10; i++) {
			final int flag = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (flag % 2 == 0)
						while (true) {
						try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							test.read();
						}
					else
						while (true){
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							test.writ();
						}
				}
			}).start();
		}
	}

}
