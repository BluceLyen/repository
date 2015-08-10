package test2;

/**
 * 模拟资源类，此项目中的pv问题都会依赖这个类
 * @author xwjdsh
 */

public class Semaphore {
	//表示信号量
	private int value;
	
	public Semaphore(int value){
		this.value=value;
	}
	
	// P操作，这里命名为await()
	public synchronized void await(){
		if(--value<0){//信号量不可用
			try {
				//wait方法会自动的将线程阻塞并“挂”在当前对象上，所以无需自定义线程队列
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// V操作，命名为signal()
	public synchronized void signal(){
		if(++value<=0){//信号可用
			this.notify();//唤醒一个进程
		}
	}
}
