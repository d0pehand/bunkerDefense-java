package main;

//화면을 그려주는 클래스
public class Print {

	Hero hero;
	Action action;
	int count;

	static String notice = ""; // 알림 화면에 표시할 내용

	public Print(Hero hero, Action action) {
		this.hero = hero;
		this.action = action;
		count = 0;
	}

	public static void clear() {
		for (int i = 0; i < 50; i++) {
			System.out.println("");
		}
	}

	public void draw() { // 게임 중 0.25초마다 반복
		clear();
		gameScrean(); // 게임화면을 그려준다.
		menuScrean(); // 메뉴목록을 그려준다.
	}

	public void start() {
		Music startMusic;

		for (int i = 0; i < 3; i++) {
			clear();
			System.out.println("┌──Bunker Defence - Made By S.J───────────────────────────┐");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                       Game Start                        │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
		}

		for (int i = 0; i < 3; i++) {
			clear();
			startMusic = new Music("카운트다운.mp3", false);
			startMusic.start();

			System.out.println("┌──Bunker Defence - Made By S.J───────────────────────────┐");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                       Game Start                        │");
			System.out.println("│                                                         │");
			System.out.println("│                           " + (3 - i) + "                             │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			// 1000 = 1초 일시정지
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		startMusic = new Music("입장.mp3", false);
		startMusic.start();

		clear();
		System.out.println("┌──Bunker Defence - Made By S.J───────────────────────────┐");
		System.out.println("│                                                         │");
		System.out.println("│                                                         │");
		System.out.println("│                                                         │");
		System.out.println("│                       Game Start                        │");
		System.out.println("│                                                         │");
		System.out.println("│                           " + 0 + "                             │");
		System.out.println("│                                                         │");
		System.out.println("│                                                         │");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void end(boolean killBoss) { // 보스를 죽여서 끝냈는가 체력이 닳아서 패배하였는가

		Music endMusic;

		if (killBoss) {
			clear();
			System.out.println("┌──Bunker Defence - Made By S.J───────────────────────────┐");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                         Win!                            │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");

			endMusic = new Music("승리음.mp3", false);
			endMusic.start();

		} else {
			clear();
			System.out.println("┌──Bunker Defence - Made By S.J───────────────────────────┐");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                         Defeat!                         │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");
			System.out.println("│                                                         │");

			endMusic = new Music("패배음.mp3", false);
			endMusic.start();
		}

	}

	public void gameScrean() { // 게임화면을 그려준다.

		System.out.println("───벙커 디펜스─────────────────────────────────────────────────");
		System.out.print("money : " + hero.money + "  ");
		System.out.print("    upgrade : " + Hero.upgrade);
		System.out.println("    productivity : " + hero.productivity);
		System.out.print(" kill : " + hero.kill);

		// 라운드 표시
		System.out.print("	 Round : " + action.round);

		// waveTime RestTime 남은 시간 표시
		if ((action.time % (action.wave + action.rest)) < action.wave) {
			System.out.println("	WaveTime : " + (action.wave - (action.time % (action.wave + action.rest))));
		} else {
			System.out.println(
					"	RestTime : " + (action.wave + action.rest - (action.time % (action.wave + action.rest))));
		}

		System.out.println("");

		// 적 유닛 정보 표시
		if (action.enemy.size() > 0) {
			boolean[] enemyInfo = new boolean[8];

			// 적 정보 조회
			for (int i = 0; i < action.enemy.size(); i++) {
				switch (action.enemy.get(i).name) {
				case "브루들링":
					enemyInfo[0] = true;
					break;

				case "프로브":
					enemyInfo[1] = true;
					break;

				case "저글링":
					enemyInfo[2] = true;
					break;

				case "질럿":
					enemyInfo[3] = true;
					break;

				case "벌쳐":
					enemyInfo[4] = true;
					break;

				case "히드라":
					enemyInfo[5] = true;
					break;

				case "드라군":
					enemyInfo[6] = true;
					break;

				case "아콘(boss)":
					enemyInfo[7] = true;
					break;

				default:
					break;
				}

			}

			// 적 스탯 출력
			if (enemyInfo[0]) {
				System.out.println("info : 브루들링   공격력 4   방어력 0   체력 40   사거리 1   돈 3");
			}
			if (enemyInfo[1]) {
				System.out.println("info : 프로브   공격력 10   방어력 4   체력 40   사거리 1   돈 4");
			}
			if (enemyInfo[2]) {
				System.out.println("info : 저글링   공격력10   방어력 0   체력 100   사거리 1   돈 5");
			}
			if (enemyInfo[3]) {
				System.out.println("info : 질럿   공격력 20   방어력 2   체력 250   사거리 1   돈 5");
			}
			if (enemyInfo[4]) {
				System.out.println("info : 벌쳐   공격력 10   방어력 0   체력 120   사거리 2   돈 6");
			}
			if (enemyInfo[5]) {
				System.out.println("info : 히드라   공격력 20   방어력 0   체력 200   사거리 2   돈 7");
			}
			if (enemyInfo[6]) {
				System.out.println("info : 드라군   공격력 10   방어력 20   체력 350   사거리 3   돈 10");
			}
			if (enemyInfo[7]) {
				System.out.println("info : 아콘(boss)   공격력 40   방어력 100   체력 1000000   사거리 2");
			}

			// 맨 앞의 적 체력 표시
			if (action.enemy.size() > 0) {
				System.out.println("");
				int firstEnemyHp = action.enemy.get(0).hp;
				if (firstEnemyHp < 0) {
					firstEnemyHp = 0;
				}
				System.out.println("		" + action.enemy.get(0).name + "   체력 " + firstEnemyHp);
			} else {
				System.out.println("");
				System.out.println("");
			}

		} else { // 적 유닛이 없다면(그럴리는 없겠지만)
			System.out.println("");
			System.out.println("");
		}

		System.out.println("");
		System.out.println("");

		// 위치별 적 유닛 숫자 출력
		int[] enemycount = new int[8];
		if (action.enemy.size() > 0) {
			for (int i = 0; i < 6; i++) {

				for (int j = 0; j < 8; j++) {
					enemycount[j] = 0;
				}

				for (int j = 0; j < action.enemy.size(); j++) {

					if (action.enemy.get(j).position == 6 - i) {
						switch (action.enemy.get(j).name) {
						case "브루들링":
							enemycount[0]++;
							break;

						case "프로브":
							enemycount[1]++;
							break;

						case "저글링":
							enemycount[2]++;
							break;

						case "질럿":
							enemycount[3]++;
							break;

						case "벌쳐":
							enemycount[4]++;
							break;

						case "히드라":
							enemycount[5]++;
							break;

						case "드라군":
							enemycount[6]++;
							break;

						case "아콘(boss)":
							enemycount[7]++;
							break;

						default:
							break;
						}
					}
				}

				String n = "		";
				if (enemycount[0] > 0) {
					n += "브루들링 x" + enemycount[0] + " ";
				}
				if (enemycount[1] > 0) {
					n += "프로브 x" + enemycount[1] + " ";
				}
				if (enemycount[2] > 0) {
					n += "저글링 x" + enemycount[2] + " ";
				}
				if (enemycount[3] > 0) {
					n += "질럿 x" + enemycount[3] + " ";
				}
				if (enemycount[4] > 0) {
					n += "벌쳐 x" + enemycount[4] + " ";
				}
				if (enemycount[5] > 0) {
					n += "히드라 x" + enemycount[5] + " ";
				}
				if (enemycount[6] > 0) {
					n += "드라군 x" + enemycount[6] + " ";
				}
				if (enemycount[7] > 0) {
					n += "아콘(boss) x" + enemycount[7] + " ";
				}

				System.out.println(n);

			}
		} else { // 적 유닛이 없는 경우
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
		}

		// 벙커, 유닛 UI
		System.out.println("		┌────────────────┐			");
		if (hero.steamPack > 0) {
			System.out.println("		│      scv " + hero.scv + "     │      	T.스팀팩" + hero.steamPack);
		} else {
			System.out.println("		│      scv " + hero.scv + "     │");
		}

		System.out.println("		│                │			");

		System.out.println("		    벙커 : " + hero.hp + "/" + hero.maxhp);
		System.out.println("");

		count = 0; // 아군 유닛의 숫자 연산
		for (int i = 0; i < hero.unit.length; i++) {
			if (hero.unit[i] != null) {
				count++;
			}
		}

		if (count != 0) {
			System.out.println("Unit " + count + "/8");
		} else {
			System.out.println("");
			System.out.println("");
			System.out.println("");
		}

		// 아군 유닛 종류별로 숫자 조회
		int[] unitCount = new int[8];

		for (int i = 0; i < hero.unit.length; i++) {

			if (hero.unit[i] != null) {
				switch (hero.unit[i].name) {
				case "마린":
					unitCount[0] += 1;
					break;

				case "파이어뱃":
					unitCount[1] += 1;
					break;

				case "고스트":
					unitCount[2] += 1;
					break;

				case "영웅마린":
					unitCount[3] += 1;
					break;

				case "영웅파이어뱃":
					unitCount[4] += 1;
					break;

				case "영웅고스트":
					unitCount[5] += 1;
					break;

				case "골리앗":
					unitCount[6] += 1;
					break;

				case "탱크":
					unitCount[7] += 1;
					break;

				default:
					break;
				}
			}
		}

		// 아군 유닛 정보 출력
		if (unitCount[0] > 0) {
			if (hero.steamPackNow) {
				System.out.println("    마린 : 공격력 " + (20 + Hero.upgrade * 4) + "   사거리 2   x" + unitCount[0]);
			} else {
				System.out.println("    마린 : 공격력 " + (10 + Hero.upgrade * 2) + "   사거리 2   x" + unitCount[0]);
			}
		}
		if (unitCount[1] > 0) {
			if (hero.steamPackNow) {
				System.out.println(
						"    파이어뱃 : 공격력 " + (80 + Hero.upgrade * 8) + "   사거리 1   x" + unitCount[1] + "   스플래쉬");
			} else {
				System.out.println(
						"    파이어뱃 : 공격력 " + (40 + Hero.upgrade * 4) + "   사거리 1   x" + unitCount[1] + "   스플래쉬");
			}
		}
		if (unitCount[2] > 0) {
			System.out.println("    고스트 : 공격력 " + (100 + Hero.upgrade * 10) + "   사거리 4   x" + unitCount[2]);
		}
		if (unitCount[3] > 0) {
			if (hero.steamPackNow) {
				System.out.println("    영웅 마린 : 공격력 " + (80 + Hero.upgrade * 16) + "   사거리 2   x" + unitCount[3]);
			} else {
				System.out.println("    영웅 마린 : 공격력 " + (40 + Hero.upgrade * 8) + "   사거리 2   x" + unitCount[3]);
			}
		}
		if (unitCount[4] > 0) {
			if (hero.steamPackNow) {
				System.out.println(
						"    영웅 파이어뱃 : 공격력 " + (160 + Hero.upgrade * 20) + "   사거리 1   x" + unitCount[4] + "   스플래쉬");
			} else {
				System.out.println(
						"    영웅 파이어뱃 : 공격력 " + (80 + Hero.upgrade * 10) + "   사거리 1   x" + unitCount[4] + "   스플래쉬");
			}
		}
		if (unitCount[5] > 0) {
			System.out.println("    영웅 고스트 : 공격력 " + (200 + Hero.upgrade * 20) + "   사거리 4   x" + unitCount[5]);
		}
		if (unitCount[6] > 0) {
			System.out.println("    골리앗 : 공격력 " + (100 + Hero.upgrade * 25) + "   사거리 3   x" + unitCount[6]);
		}
		if (unitCount[7] > 0) {
			System.out.println(
					"    탱크 : 공격력 " + (100 + Hero.upgrade * 10) + "   사거리 4   x" + unitCount[7] + "/3" + "   스플래쉬");
		}

		System.out.println("───────────────────────────────────────────────────────────");
		System.out.println("	알림 : " + notice);
		System.out.println("───────────────────────────────────────────────────────────");

	}

	public void menuScrean() { // 메뉴 화면을 그려준다.

		if (InputKey.menuType == 1) { // 메인 창
			System.out.println("수행할 행동을 선택해 주세요.");

			System.out.print("	1. 마린 구입      | 80원  ");
			System.out.println("		Q. 공격력 업   | " + (10 + hero.upgrade * 10) + "원");
			System.out.print("	2. 파이어뱃 구입| 200원");
			System.out.println("		W. 생산력 업   | " + (hero.productivity * 100 + 100) + "원");
			System.out.print("	3. 고스트 구입   | 500원");
			System.out.println("		E. 조합창으로 | ");
			System.out.print("	4. scv 구입    | 250원");
			System.out.println("		R. 삭제창으로 | ");

			System.out.println("");
			System.out.println("");
		}

		if (InputKey.menuType == 2) { // 조합 창
			System.out.println("수행할 행동을 선택해 주세요. | Q. 뒤로가기");

			System.out.println("	1. 영웅 마린 조합      | 마린 x4           ");
			System.out.println("	2. 영웅 파이어뱃 조합| 파이어뱃 x2        ");
			System.out.println("	3. 영웅 고스트 조합   | 고스트 x2         ");
			System.out.println("	4. 골리앗 조합          | 고스트 + 영웅 마린      ");
			System.out.println("	5. 탱크 조합             | 고스트 + 영웅 파이어뱃");

			System.out.println("");
			System.out.println("");
		}

		if (InputKey.menuType == 3) { // 제거 창
			System.out.println("수행할 행동을 선택해 주세요. | Q. 뒤로가기");

			System.out.println("	1. 마린 제거            | W. 영웅 마린 제거         ");
			System.out.println("	2. 파이어뱃 제거      | E. 영웅 파이어뱃 제거   ");
			System.out.println("	3. 고스트 제거         | R. 영웅 고스트 제거      ");
			System.out.println("	4. 골리앗 제거         |                 ");
			System.out.println("	5. 탱크 제거            |                 ");

			System.out.println("");
			System.out.println("");
		}
	}
}
