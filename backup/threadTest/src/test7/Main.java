package test7;


public class Main {
	public static void main(String[] args) {
		int[] inputdata = new int[2];
		boolean isNotInput = true;
		while (isNotInput) {
			System.out.println("���������������Դ�����������ŷָ���ֻ��Ϊ��������");
			while ((inputdata = Banker.checkInput(Banker.scanner.nextLine().split(","), 2)) == null)
				;
			if (inputdata[0] == 0 || inputdata[1] == 0) {
				System.err.println("ȷ���������û��0�������ԣ�");
				continue;
			}
			isNotInput = false;
		}
		Banker banker = new Banker(inputdata[0], inputdata[1]);
		banker.init_all();
		String result=banker.checkIsSafe()?String.format("����һ����ȫ����:%s,���ڰ�ȫ״̬��",banker.safe):"��ǰ���ڲ���ȫ״̬��";
		System.out.println();
		System.out.println(result);
	}
}
