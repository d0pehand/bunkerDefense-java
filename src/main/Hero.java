package main;
import unit.Marine;
import unit.Unit;

//�÷��̾� �ڽ��� ������ ��Ƶΰ� �ִ� Ŭ����
public class Hero extends Thread {
	
	int money;
	int kill;
	
	public static int upgrade = 0;
	int productivity;
	
	int hp;
	int maxhp; //������ ü��
	int scv; //������ �ʴ� ȸ����
	
	int steamPack;
	boolean steamPackNow;
	
	
	Unit[] unit; //�÷��̾ ������ �ִ� ���� ���

	public Hero() {
		
		//���⼭ �÷��̾��� �⺻�� ����
		money = 1000;
		kill = 0;
		hp = 3000;
		maxhp = 8000;
		scv = 1;
		productivity = 1;
		unit = new Unit[8];
		
		steamPack = 3;
		steamPackNow = false;
		
		//���� 4���� ����
		for (int i = 0; i < 1; i++) {
			unit[i] = new Marine(); //new �ڿ� �ٸ� ������ �־��൵ ��.
		}
		for (int i = 4; i < unit.length; i++) {
			unit[i] = null;
		}
	}
	
	public void run() {

		while (true) {
			//�� ����
			money += productivity;
			
			//������ ü�� ����
			if (hp < maxhp) {hp += scv;}
			
			// (1000 = 1��) x�� �Ͻ�����
			try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

}
