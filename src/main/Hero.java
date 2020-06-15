package main;
import unit.Marine;
import unit.Unit;

//플레이어 자신의 정보를 담아두고 있는 클래스
public class Hero extends Thread {
	
	int money;
	int kill;
	
	public static int upgrade = 0;
	int productivity;
	
	int hp;
	int maxhp; //기지의 체력
	int scv; //기지의 초당 회복량
	
	int steamPack;
	boolean steamPackNow;
	
	
	Unit[] unit; //플레이어가 가지고 있는 유닛 목록

	public Hero() {
		
		//여기서 플레이어의 기본값 설정
		money = 1000;
		kill = 0;
		hp = 3000;
		maxhp = 8000;
		scv = 1;
		productivity = 1;
		unit = new Unit[8];
		
		steamPack = 3;
		steamPackNow = false;
		
		//마린 4마리 생성
		for (int i = 0; i < 1; i++) {
			unit[i] = new Marine(); //new 뒤에 다른 유닛을 넣어줘도 됨.
		}
		for (int i = 4; i < unit.length; i++) {
			unit[i] = null;
		}
	}
	
	public void run() {

		while (true) {
			//돈 증가
			money += productivity;
			
			//기지의 체력 증가
			if (hp < maxhp) {hp += scv;}
			
			// (1000 = 1초) x초 일시정지
			try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

}
