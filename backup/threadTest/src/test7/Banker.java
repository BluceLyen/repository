package test7;

import java.util.Scanner;

/**
 * 银行家算法示例代码
 * @author xwjdsh
 */
public class Banker {

	// 进程数
	private int processCount;

	// 资源数
	private int resourceCount;

	// 各线程已占用数
	private int[][] allocation;

	// 各线程最大占用数
	private int[][] max;

	// 各线程还需要的资源数
	private int[][] need;

	// 总的可用资源数
	private int[] available;

	// 状态标志
	private boolean[] isFinish;

	// 输入对象
	public static Scanner scanner = new Scanner(System.in);
	
	//缓存安全序列
	public String safe="";

	

	/**
	 * 检验输入数据的静态方法
	 * 
	 * @param input
	 *            输入的数据
	 * @param inputLength
	 *            限定输入的长度
	 * @return 转换后的正确格式数据,不正确则为null
	 */
	public static int[] checkInput(String[] input, int inputLength) {
		if (input.length != inputLength) {
			System.err.println(String.format("确认你输入的是%d个数！请重试：", inputLength));
			return null;
		}
		int[] inputdata = new int[inputLength];
		try {
			for (int i = 0; i < inputLength; i++) {
				inputdata[i] = Integer.parseInt(input[i]);
			}
		} catch (Exception e) {
			System.err.println("确认你输入的是数字！请重试：");
			return null;
		}
		for (int i : inputdata) {
			if (i < 0) {
				System.err.println("确认没有输入负数！请重试：");
				return null;
			}
		}
		return inputdata;
	}

	/**
	 * 资源方法
	 * 
	 * @param processCount
	 *            传入的线程数
	 * @param resourceCount
	 *            传入的资源数 注：available由初始化方法分配空间并赋值
	 */
	public Banker(int processCount, int resourceCount) {
		// 初始化进程数
		this.processCount = processCount;
		// 初始化资源数
		this.resourceCount = resourceCount;
		// 分配存储各进程已占用资源数的存储空间,具体每一行的数据有初始化方法分配空间并赋值
		this.allocation = new int[processCount][];
		// 分配存储各进程最大占用资源数的存储空间，具体每一行的数据有初始化方法分配空间并赋值
		this.max = new int[processCount][];
		// 分配存储各进程还需要占用资源数的存储空间
		this.need = new int[processCount][resourceCount];

	}

	/**
	 * 初始化所有，能被外面的对象调用
	 */
	public void init_all() {
		init_max_allocation();
		System.out.println();
		init_need();
		init_available();
		System.out.println();
	}

	/**
	 * 私有，初始化max和allocation矩阵
	 */
	private void init_max_allocation() {
		// 正在为第几个进程赋值
		int processNum = 0;
		// 临时记录max和allocation某一行的数据
		int[] maxRowData = null;
		int[] allRowData = null;
		// 循环每一个进程，即每行，赋相对应的资源的值
		for (; processNum < processCount; processNum++) {
			boolean isNotInput = true;
			while (isNotInput) {
				System.out.println(String.format(
						"请输入进程  %d 的最大资源需求数（逗号分隔，不能全为0）：", processNum + 1));
				while ((maxRowData = checkInput(scanner.nextLine().split(","),
						this.resourceCount)) == null)
					;
				for (int i : maxRowData) {
					if (i > 0) {
						isNotInput = false;
						break;
					}
				}
				if (isNotInput) {
					System.err.println("确认输入不全为0！请重试：");
				}
			}
			while (!isNotInput) {
				System.out.println(String.format(
						"请输入进程  %d 的已占用的资源数（逗号分隔，每一项必须比该进程的最大资源需求小）：",
						processNum + 1));
				while ((allRowData = checkInput(scanner.nextLine().split(","),
						this.resourceCount)) == null)
					;
				for (int i = 0; i < this.resourceCount; i++) {
					if (maxRowData[i] < allRowData[i]) {
						System.err.println("确认输入的每一项都小于其最大需求！请重试：");
						continue;
					}
				}
				isNotInput = true;
			}
			// 赋值
			max[processNum] = maxRowData;
			allocation[processNum] = allRowData;
		}
	}

	/**
	 * 私有，初始化need矩阵
	 */
	private void init_need() {
		for (int i = 0; i < this.processCount; i++) {
			for (int j = 0; j < this.resourceCount; j++) {
				need[i][j] = max[i][j] - allocation[i][j];
			}
		}
	}

	/**
	 * 私有，初始化available数组
	 */
	private void init_available() {
		boolean isNotInput = true;
		while (isNotInput) {
			System.out.println("请输入剩余的资源数(逗号分隔，不全为0)：");
			int[] ava = null;
			while ((ava = checkInput(scanner.nextLine().split(","),
					this.resourceCount)) == null)
				;
			for (int i : ava) {
				if (i > 0) {
					isNotInput = false;
					break;
				}
			}
			if (isNotInput) {
				System.err.println("确认输入不全为0！请重输：");
				continue;
			}
			available = ava;
		}
	}

	public boolean checkIsSafe() {
		// 临时用来存储分配后资源的剩余数 模拟分配
		int work[] = new int[this.resourceCount];
		for(int i=0;i<this.resourceCount;i++){
			work[i]=available[i];
		}
		// 分配存储各进程状态标志的存储空间
		boolean[] detection = new boolean[processCount];
		boolean isDistribute=false;
		//对通过的计数
		int count=0;
		do{
			isDistribute=false;
			for(int i=0;i<this.processCount;i++){
				if(!detection[i]&&isDistributed(need[i],available)){
					distributed(i,work);
					detection[i]=true;
					isDistribute=true;
					safe+=i+1+" ";
					count++;
				}
			}
			
		}while(isDistribute);
		for(int j=count;j<this.processCount;j++){
			safe+="X ";
		}
		return isNotFinish(detection);
		
	}

	private void distributed(int i, int[] work) {
		for(int j=0;j<work.length;j++){
			work[j]+=max[i][j];
		}
	}

	private boolean isNotFinish(boolean[] detection) {
		for (boolean bl : detection) {
			if (!bl)
				return true;
		}
		return false;
	}
	
	private boolean isDistributed(int[] need,int[] ava){
		for(int i=0;i<need.length;i++){
			if(need[i]>ava[i]){
				return false;
			}
		}
		return true;
	}

}
