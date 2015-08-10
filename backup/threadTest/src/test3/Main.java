package test3;


public class Main {
	
	//读者写者问题的main方法的代码，因为三种情况main都一致，故不重写
	public static void main(String[] args) {
		//这句代码视运行而修改，比如要调试公平竞争，则改为ReaderAndWriter3 test = new ReaderAndWriter3();
		//在调试写者优先时，可适当暂停线程，便于观察效果
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
