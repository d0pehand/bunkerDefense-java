package main;

public class Main {

	//프로그램의 실행 진입 점
	public static void main(String[] args) {

		Hero hero = new Hero(); // 주인공의 정보 저장
		InputKey inputKey = new InputKey(hero); // 키보드 입력을 담당
		Action action = new Action(hero); // 적 생성, 이동, 전투를 담당
		Print print = new Print(hero, action); // 화면출력을 담당

		print.start(); // 시작 화면

		// 스레드 실행
		hero.setDaemon(true);
		inputKey.setDaemon(true);
		action.setDaemon(true);

		hero.start();
		inputKey.start();
		action.start();

		// 배경음 실행
		Music bgmmusic;
		bgmmusic = new Music("bgm.mp3", true);
		bgmmusic.setDaemon(true);
		bgmmusic.start();

		// 게임 중..
		while (hero.hp > 0 && !action.killBoss) {
			print.draw();
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		action.isStop = true;
		bgmmusic.close(); // 배경음 종료
		print.end(action.killBoss); // 종료 화면

	}
}
