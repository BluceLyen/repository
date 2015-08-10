package test7;


public class Main {
	public static void main(String[] args) {
		int[] inputdata = new int[2];
		boolean isNotInput = true;
		while (isNotInput) {
			System.out.println("请输入进程数和资源类型数（逗号分隔，只能为正数）：");
			while ((inputdata = Banker.checkInput(Banker.scanner.nextLine().split(","), 2)) == null)
				;
			if (inputdata[0] == 0 || inputdata[1] == 0) {
				System.err.println("确认你的输入没有0！请重试：");
				continue;
			}
			isNotInput = false;
		}
		Banker banker = new Banker(inputdata[0], inputdata[1]);
		banker.init_all();
		String result=banker.checkIsSafe()?String.format("存在一条安全序列:%s,处于安全状态。",banker.safe):"当前处于不安全状态。";
		System.out.println();
		System.out.println(result);
	}
}
