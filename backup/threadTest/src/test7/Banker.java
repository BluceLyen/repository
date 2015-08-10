package test7;

import java.util.Scanner;

/**
 * ���м��㷨ʾ������
 * @author xwjdsh
 */
public class Banker {

	// ������
	private int processCount;

	// ��Դ��
	private int resourceCount;

	// ���߳���ռ����
	private int[][] allocation;

	// ���߳����ռ����
	private int[][] max;

	// ���̻߳���Ҫ����Դ��
	private int[][] need;

	// �ܵĿ�����Դ��
	private int[] available;

	// ״̬��־
	private boolean[] isFinish;

	// �������
	public static Scanner scanner = new Scanner(System.in);
	
	//���氲ȫ����
	public String safe="";

	

	/**
	 * �����������ݵľ�̬����
	 * 
	 * @param input
	 *            ���������
	 * @param inputLength
	 *            �޶�����ĳ���
	 * @return ת�������ȷ��ʽ����,����ȷ��Ϊnull
	 */
	public static int[] checkInput(String[] input, int inputLength) {
		if (input.length != inputLength) {
			System.err.println(String.format("ȷ�����������%d�����������ԣ�", inputLength));
			return null;
		}
		int[] inputdata = new int[inputLength];
		try {
			for (int i = 0; i < inputLength; i++) {
				inputdata[i] = Integer.parseInt(input[i]);
			}
		} catch (Exception e) {
			System.err.println("ȷ��������������֣������ԣ�");
			return null;
		}
		for (int i : inputdata) {
			if (i < 0) {
				System.err.println("ȷ��û�����븺���������ԣ�");
				return null;
			}
		}
		return inputdata;
	}

	/**
	 * ��Դ����
	 * 
	 * @param processCount
	 *            ������߳���
	 * @param resourceCount
	 *            �������Դ�� ע��available�ɳ�ʼ����������ռ䲢��ֵ
	 */
	public Banker(int processCount, int resourceCount) {
		// ��ʼ��������
		this.processCount = processCount;
		// ��ʼ����Դ��
		this.resourceCount = resourceCount;
		// ����洢��������ռ����Դ���Ĵ洢�ռ�,����ÿһ�е������г�ʼ����������ռ䲢��ֵ
		this.allocation = new int[processCount][];
		// ����洢���������ռ����Դ���Ĵ洢�ռ䣬����ÿһ�е������г�ʼ����������ռ䲢��ֵ
		this.max = new int[processCount][];
		// ����洢�����̻���Ҫռ����Դ���Ĵ洢�ռ�
		this.need = new int[processCount][resourceCount];

	}

	/**
	 * ��ʼ�����У��ܱ�����Ķ������
	 */
	public void init_all() {
		init_max_allocation();
		System.out.println();
		init_need();
		init_available();
		System.out.println();
	}

	/**
	 * ˽�У���ʼ��max��allocation����
	 */
	private void init_max_allocation() {
		// ����Ϊ�ڼ������̸�ֵ
		int processNum = 0;
		// ��ʱ��¼max��allocationĳһ�е�����
		int[] maxRowData = null;
		int[] allRowData = null;
		// ѭ��ÿһ�����̣���ÿ�У������Ӧ����Դ��ֵ
		for (; processNum < processCount; processNum++) {
			boolean isNotInput = true;
			while (isNotInput) {
				System.out.println(String.format(
						"���������  %d �������Դ�����������ŷָ�������ȫΪ0����", processNum + 1));
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
					System.err.println("ȷ�����벻ȫΪ0�������ԣ�");
				}
			}
			while (!isNotInput) {
				System.out.println(String.format(
						"���������  %d ����ռ�õ���Դ�������ŷָ���ÿһ�����ȸý��̵������Դ����С����",
						processNum + 1));
				while ((allRowData = checkInput(scanner.nextLine().split(","),
						this.resourceCount)) == null)
					;
				for (int i = 0; i < this.resourceCount; i++) {
					if (maxRowData[i] < allRowData[i]) {
						System.err.println("ȷ�������ÿһ�С����������������ԣ�");
						continue;
					}
				}
				isNotInput = true;
			}
			// ��ֵ
			max[processNum] = maxRowData;
			allocation[processNum] = allRowData;
		}
	}

	/**
	 * ˽�У���ʼ��need����
	 */
	private void init_need() {
		for (int i = 0; i < this.processCount; i++) {
			for (int j = 0; j < this.resourceCount; j++) {
				need[i][j] = max[i][j] - allocation[i][j];
			}
		}
	}

	/**
	 * ˽�У���ʼ��available����
	 */
	private void init_available() {
		boolean isNotInput = true;
		while (isNotInput) {
			System.out.println("������ʣ�����Դ��(���ŷָ�����ȫΪ0)��");
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
				System.err.println("ȷ�����벻ȫΪ0�������䣺");
				continue;
			}
			available = ava;
		}
	}

	public boolean checkIsSafe() {
		// ��ʱ�����洢�������Դ��ʣ���� ģ�����
		int work[] = new int[this.resourceCount];
		for(int i=0;i<this.resourceCount;i++){
			work[i]=available[i];
		}
		// ����洢������״̬��־�Ĵ洢�ռ�
		boolean[] detection = new boolean[processCount];
		boolean isDistribute=false;
		//��ͨ���ļ���
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
