package main;

import java.util.Scanner;

//입력받은 키를 실행하는 클래스
//1.메인창 2.조합창 3.삭제창 	세가지의 경우로 입력받은 키을 실행한다.
public class InputKey extends Thread {

	Hero hero;
	Music music;

	Scanner scanner = new Scanner(System.in);

	String select; // 입력 값
	static int menuType = 1; // 1.메인창 2.조합창 3.삭제창
	
	static boolean createCommand = false;
	static boolean combinationCommand = false;
	static boolean removeCommand = false;
	static String command = "";

	public InputKey(Hero hero) {
		select = "";
		this.hero = hero;
	}

	public void run() {

		while (true) {

				if (menuType == 1) {

					// 입력 값에 따라 처리
					switch (select) {

					// 마린 생산
					case "1":
					case "11":

						createCommand = true;
						command = "마린 생산";

						break;

					// 파이어뱃 생산
					case "2":
					case "22":

						createCommand = true;
						command = "파이어뱃 생산";
						
						break;

					// 고스트 생산
					case "3":
					case "33":

						createCommand = true;
						command = "고스트 생산";
						
						break;

					// scv 생성 키
					case "4":
					case "44":

						if (hero.money > 250) {
							hero.money -= 250;
							hero.scv++;
							music = new Music("scv.mp3", false);
							music.start();
							Print.notice = "scv가 추가 되었습니다.";
						} else {
							music = new Music("미네랄부족.mp3", false);
							music.start();
							Print.notice = "돈이 부족합니다.";
						}
						break;

					// 업그레이드 키
					case "q":
					case "qq":
					case "Q":
					case "QQ":
					case "ㅂ":
					case "ㅂㅂ":

						if (hero.money > (10 + Hero.upgrade * 10)) {
							hero.money -= 10 + Hero.upgrade * 10;
							Hero.upgrade++;

							music = new Music("업글성공.mp3", false);
							music.start();
							Print.notice = "업그레이드 완료.";

						} else {
							music = new Music("미네랄부족.mp3", false);
							music.start();
							Print.notice = "돈이 부족합니다.";
						}
						break;

					// 생산력 연구 키
					case "w":
					case "ww":
					case "W":
					case "WW":
					case "ㅈ":
					case "ㅈㅈ":

						if (hero.money > (100 + hero.productivity * 100)) {
							hero.money -= 100 + hero.productivity * 100;
							hero.productivity++;

							music = new Music("연구성공.mp3", false);
							music.start();
							Print.notice = "생산력 연구 완료.";

						} else {
							music = new Music("미네랄부족.mp3", false);
							music.start();
							Print.notice = "돈이 부족합니다.";
						}
						break;

					// 조합창으로 이동 키
					case "e":
					case "ee":
					case "E":
					case "EE":
					case "ㄷ":
					case "ㄷㄷ":

						menuType = 2;
						break;

					// 제거창으로 이동 키
					case "r":
					case "rr":
					case "R":
					case "RR":
					case "ㄱ":
					case "ㄱㄱ":

						menuType = 3;
						break;
						
						// 생산력 연구 키
					case "t":
					case "tt":
					case "T":
					case "TT":
					case "ㅅ":
					case "ㅅㅅ":

						if (hero.steamPack > 0) {
							hero.steamPack -= 1;
							
							music = new Music("스팀팩.mp3", false);
							music.start();
							
							SteamPack steamPack;
							steamPack = new SteamPack(hero);
							steamPack.start();

						}
						break;

					default:
						break;
					}

				}

				else if (menuType == 2) {

					// 입력 값에 따라 처리
					switch (select) {

					// 영웅마린 조합
					case "1":
					case "11":
						combinationCommand = true;
						command = "영웅마린 조합";
						break;

					// 영웅 파이어뱃 조합
					case "2":
					case "22":
						combinationCommand = true;
						command = "영웅파이어뱃 조합";

						break;
					// 영웅고스트 조합
					case "3":
					case "33":
						combinationCommand = true;
						command = "영웅고스트 조합";
						
						break;

					// 골리앗 조합
					case "4":
					case "44":
						combinationCommand = true;
						command = "골리앗 조합";

						break;

					// 탱크 조합
					case "5":
					case "55":
						combinationCommand = true;
						command = "탱크 조합";
						
						break;

					// 메인창으로
					case "q":
					case "qq":
					case "Q":
					case "QQ":
					case "ㅂ":
					case "ㅂㅂ":

						menuType = 1;
						break;

					default:
						break;
					}

				}

				else if (menuType == 3) {

					// 입력 값에 따라 처리
					switch (select) {

					// 마린 제거
					case "1":
					case "11":
						removeCommand = true;
						command = "마린 제거";

						break;

					// 파이어뱃 제거
					case "2":
					case "22":
						removeCommand = true;
						command = "파이어뱃 제거";

						break;

					// 골리앗 제거
					case "4":
					case "44":
						removeCommand = true;
						command = "골리앗 제거";

						break;

					// 탱크 제거
					case "5":
					case "55":
						removeCommand = true;
						command = "탱크 제거";
						
						break;

					// 메인창으로
					case "q":
					case "qq":
					case "Q":
					case "QQ":
					case "ㅂ":
					case "ㅂㅂ":

						menuType = 1;
						break;

					// 영웅마린 제거
					case "w":
					case "ww":
					case "W":
					case "WW":
					case "ㅈ":
					case "ㅈㅈ":
						removeCommand = true;
						command = "영웅마린 제거";

						break;

					// 영웅파이어뱃 제거
					case "e":
					case "ee":
					case "E":
					case "EE":
					case "ㄷ":
					case "ㄷㄷ":
						removeCommand = true;
						command = "영웅파이어뱃 제거";

						break;

					// 영웅고스트 제거
					case "r":
					case "rr":
					case "R":
					case "RR":
					case "ㄱ":
					case "ㄱㄱ":
						removeCommand = true;
						command = "영웅고스트 제거";

						break;

					default:
						break;
					}

				}
			select = scanner.next(); // 입력 받을때 까지 대기
		}

	}

}
