package test6;

import test2.Semaphore;

/**
 * 哲学家进餐的第一种处理方法，加入互斥信号量
 * @author xwjdsh
 */
public class PhilosopherThinkEat {
	//筷子的信号量数组，在构造函数中new并添加
	private Semaphore[] semaphores=null;
	//互斥信号量
	private Semaphore mutex=new Semaphore(1);
	
	public PhilosopherThinkEat() {
		semaphores=new Semaphore[5];
		for(int i=0;i<5;i++){
			semaphores[i]=new Semaphore(1);
		}
	}
	
	//用参数i来表示哲学家
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
		System.out.println("哲学家"+i+"   在思考问题");
	}
	private void eat(int i){
		System.out.println("哲学家"+i+"   在吃饭");
	}
}