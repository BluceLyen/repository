package test6;

import test2.Semaphore;

/**
 * 哲学家进餐的第三种处理方法，对奇数位和偶数位哲学家以不同的竞争的顺序
 * @author xwjdsh
 */
public class PhilosopherThinkEat3 {
	//筷子的信号量数组，在构造函数中new并添加
	private Semaphore[] semaphores=null;

	
	public PhilosopherThinkEat3() {
		semaphores=new Semaphore[5];
		for(int i=0;i<5;i++){
			semaphores[i]=new Semaphore(1);
		}
	}
	
	//用参数i来表示哲学家
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
		System.out.println("哲学家"+i+"   在思考问题");
	}
	private void eat(int i){
		System.out.println("哲学家"+i+"   在吃饭");
	}
}