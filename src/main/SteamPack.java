package main;

//플레이어가 T.스팀팩 키를 눌렀을때 실행되는 스레드
public class SteamPack extends Thread {

	Hero hero;

	public SteamPack(Hero hero) {
		this.hero = hero;
	}

	public void run() {

		hero.steamPackNow = true; // 현재 스팀팩 상태인지 판단

		for (int i = 0; i < 8; i++) { // 스팀팩 효과 적용
			if ((hero.unit[i] != null) && (hero.unit[i].name == "마린" || hero.unit[i].name == "영웅마린"
					|| hero.unit[i].name == "파이어뱃" || hero.unit[i].name == "영웅파이어뱃")) {
				hero.unit[i].attack = 20;
				hero.unit[i].plusAttack = 4;
			}
		}

		try {
			Thread.sleep(10000); // 스팀팩 지속시간 = 10초
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		hero.steamPackNow = false;

		for (int i = 0; i < 8; i++) { // 스팀팩 효과 풀기
			if ((hero.unit[i] != null) && (hero.unit[i].name == "마린" || hero.unit[i].name == "영웅마린")) {
				hero.unit[i].attack = 10;
				hero.unit[i].plusAttack = 2;
			}
		}

	}
}
