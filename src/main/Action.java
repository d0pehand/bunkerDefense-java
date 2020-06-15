package main;

import java.util.ArrayList;

import enemy.Archon;
import enemy.Broodling;
import enemy.Dragon;
import enemy.Enemy;
import enemy.Hydra;
import enemy.Probe;
import enemy.Vulture;
import enemy.Zealot;
import enemy.Zergling;
import unit.Firebat;
import unit.Ghost;
import unit.Goliath;
import unit.HeroFirebat;
import unit.HeroGhost;
import unit.HeroMarine;
import unit.Marine;
import unit.Tank;

//유닛들의 행동을 처리하는 스레드
public class Action extends Thread {

	ArrayList<Enemy> enemy = new ArrayList<Enemy>(); // 적 유닛
	Hero hero;
	
	boolean isStop = false;

	long start = 0;
	long end = 0;
	int time = 0; // 경과한 시간 저장

	boolean killBoss = false; // 보스를 죽이면 게임 종료
	int round = 1; // 10라운드 까지 웨이브가 있음
	boolean[] roundFirst;

	int wave = 50; // 웨이브 시간
	int rest = 10; // 휴식 시간

	Music music;
	Music attackMusic; // 아군 유닛이 공격할때 나는 소리
	Music createMusic; // 웨이브가 시작될때 나는 소리
	Music deathMusic; // 적 유닛이 죽을때 나는 소리

	int randomEnemy; // 적이 나오는 갯수

	int count = 0; // 아군 유닛의 숫자

	public Action(Hero hero) {
		this.hero = hero;
		this.roundFirst = new boolean[10];
	}

	public void run() {

		start = System.currentTimeMillis(); // 게임 시작 시 현재시간 저장

		while (!isStop) {
			end = System.currentTimeMillis(); // 현재시간 저장
			time = (int) (end - start) / 1000; // 게임의 경과 시간

			round = time / (wave + rest) + 1;
			if (round > 8) {
				round = 8;
			} // 라운드 계산

			if ((time % (wave + rest)) < wave) { // WaveTime인 경우에
				enemyCreate(); // 적 유닛 생성
			}

			move(); // 적 유닛 이동

			unitAttack(); // 아군 유닛이 공격

			enemyAttack(); // 적 유닛이 공격

			// 유닛 생산
			if (InputKey.createCommand) {
				create(InputKey.command);
				InputKey.createCommand = false;
				InputKey.command = "";
			}

			// 유닛 조합
			if (InputKey.combinationCommand) {
				combination(InputKey.command);
				InputKey.combinationCommand = false;
				InputKey.command = "";
			}
			// 유닛 삭제
			if (InputKey.removeCommand) {
				remove(InputKey.command);
				InputKey.removeCommand = false;
				InputKey.command = "";
			}

			// 500 = 0.5초 일시정지
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void create(String command) {

		count = 0; // 유닛의 숫자 저장

		for (int i = 0; i < hero.unit.length; i++) {
			if (hero.unit[i] != null) {
				count++;
			}
		}

		// 입력 값에 따라 처리
		switch (command) {

		// 마린 생산
		case "마린 생산":
			if (hero.money > 80 && count < 8) {
				hero.money -= 80;

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("마린.mp3", false);
						music.start();
						hero.unit[i] = new Marine();
						Print.notice = "마린이 생성되었습니다.";
						break;
					}
				}
			} else if (hero.money < 80) {
				music = new Music("미네랄부족.mp3", false);
				music.start();
				Print.notice = "돈이 부족합니다.";
			} else if (count >= 8) {
				music = new Music("실패음.mp3", false);
				music.start();
				Print.notice = "유닛이 꽉 찼습니다.";
			}

			break;

		// 파이어뱃 생산
		case "파이어뱃 생산":
			if (hero.money > 200 && count < 8) {
				hero.money -= 200;

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("파이어뱃.mp3", false);
						music.start();
						hero.unit[i] = new Firebat();
						Print.notice = "파이어뱃이 생성되었습니다.";
						break;
					}
				}
			} else if (hero.money < 200) {
				music = new Music("미네랄부족.mp3", false);
				music.start();
				Print.notice = "돈이 부족합니다.";
			} else if (count >= 8) {
				music = new Music("실패음.mp3", false);
				music.start();
				Print.notice = "유닛이 꽉 찼습니다.";
			}

			break;
		// 고스트 생산
		case "고스트 생산":
			if (hero.money > 500 && count < 8) {
				hero.money -= 500;

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("고스트.mp3", false);
						music.start();
						hero.unit[i] = new Ghost();
						Print.notice = "고스트가 생성되었습니다.";
						break;
					}
				}
			} else if (hero.money < 500) {
				music = new Music("미네랄부족.mp3", false);
				music.start();
				Print.notice = "돈이 부족합니다.";
			} else if (count >= 8) {
				music = new Music("실패음.mp3", false);
				music.start();
				Print.notice = "유닛이 꽉 찼습니다.";
			}

			break;

		default:
			break;
		}
	}

	public void remove(String command) {
		// 입력 값에 따라 처리
		switch (command) {

		// 마린 제거
		case "마린 제거":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "마린") {
					hero.unit[i] = null;
					music = new Music("버튼음.mp3", false);
					music.start();
					Print.notice = "마린이 제거되었습니다.";
					break;
				}
			}

			break;

		// 파이어뱃 제거
		case "파이어뱃 제거":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "파이어뱃") {
					hero.unit[i] = null;
					music = new Music("버튼음.mp3", false);
					music.start();
					Print.notice = "파이어뱃이 제거되었습니다.";
					break;
				}
			}

			break;
		// 고스트 제거
		case "고스트 제거":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "고스트") {
					hero.unit[i] = null;
					music = new Music("버튼음.mp3", false);
					music.start();
					Print.notice = "고스트가 제거되었습니다.";
					break;
				}
			}

			break;

		// 골리앗 제거
		case "골리앗 제거":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "골리앗") {
					hero.unit[i] = null;
					music = new Music("버튼음.mp3", false);
					music.start();
					Print.notice = "골리앗이 제거되었습니다.";
					break;
				}
			}

			break;

		// 탱크 제거
		case "탱크 제거":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "탱크") {
					hero.unit[i] = null;
					music = new Music("버튼음.mp3", false);
					music.start();
					Print.notice = "탱크가 제거되었습니다.";
					break;
				}
			}

			break;
		// 영웅마린 제거
		case "영웅마린 제거":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "영웅마린") {
					hero.unit[i] = null;
					music = new Music("버튼음.mp3", false);
					music.start();
					Print.notice = "영웅마린이 제거되었습니다.";
					break;
				}
			}

			break;

		// 영웅파이어뱃 제거
		case "영웅파이어뱃 제거":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "영웅파이어뱃") {
					hero.unit[i] = null;
					music = new Music("버튼음.mp3", false);
					music.start();
					Print.notice = "영웅파이어뱃이 제거되었습니다.";
					break;
				}
			}

			break;

		// 영웅고스트 제거
		case "영웅고스트 제거":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "영웅고스트") {
					hero.unit[i] = null;
					music = new Music("버튼음.mp3", false);
					music.start();
					Print.notice = "영웅고스트가 제거되었습니다.";
					break;
				}
			}

			break;

		default:
			break;
		}
	}

	public void combination(String command) {
		int unitCount = 0;
		boolean isGhost = false;
		boolean isHeroMarine = false;
		boolean isHeroFirebat = false;

		// 입력 값에 따라 처리
		switch (command) {

		// 영웅 마린 조합
		case "영웅마린 조합":
			// 마린이 4이상인지 확인
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "마린") {
					unitCount++;
				}
			}

			// 영웅 마린 조합
			if (unitCount >= 4) {
				int material = 4;

				for (int i = 0; i < hero.unit.length; i++) { // 재료 유닛 소모
					if (hero.unit[i] != null && hero.unit[i].name == "마린" && material > 0) {
						material--;
						hero.unit[i] = null;
					}
				}

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("마린.mp3", false);
						music.start();
						hero.unit[i] = new HeroMarine();
						Print.notice = "영웅 마린을 조합했습니다.";
						break;
					}
				}

			}
			break;

		// 영웅 파이어뱃 조합
		case "영웅파이어뱃 조합":
			// 파이어뱃이 2이상인지 확인
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "파이어뱃") {
					unitCount++;
				}
			}

			// 영웅 파이어뱃 조합
			if (unitCount >= 2) {
				int material = 2;

				for (int i = 0; i < hero.unit.length; i++) { // 재료 유닛 소모
					if (hero.unit[i] != null && hero.unit[i].name == "파이어뱃" && material > 0) {
						material--;
						hero.unit[i] = null;
					}
				}

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("파이어뱃.mp3", false);
						music.start();
						hero.unit[i] = new HeroFirebat();
						Print.notice = "영웅 파이어뱃을 조합했습니다.";
						break;
					}
				}

			}

			break;
		// 영웅고스트 조합
		case "영웅고스트 조합":
			// 고스트가 2이상인지 확인
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "고스트") {
					unitCount++;
				}
			}

			// 영웅 고스트 조합
			if (unitCount >= 2) {
				int material = 2;

				for (int i = 0; i < hero.unit.length; i++) { // 재료 유닛 소모
					if (hero.unit[i] != null && hero.unit[i].name == "고스트" && material > 0) {
						material--;
						hero.unit[i] = null;
					}
				}

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("고스트.mp3", false);
						music.start();
						hero.unit[i] = new HeroGhost();
						Print.notice = "영웅 고스트를 조합했습니다.";
						break;
					}
				}

			}
			break;

		// 골리앗 조합
		case "골리앗 조합":
			isGhost = false;
			isHeroMarine = false;

			// 고스트가 1이상 인지 확인
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "고스트") {
					hero.unit[i] = null;
					isGhost = true;
					break;
				}
			}
			// 영웅마린이 1이상 인지 확인
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "영웅마린") {
					hero.unit[i] = null;
					isHeroMarine = true;
					break;
				}
			}

			// 골리앗 조합
			if (isGhost && isHeroMarine) {

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						hero.unit[i] = new Goliath();
						music = new Music("골리앗.mp3", false);
						music.start();
						Print.notice = "골리앗을 조합했습니다.";
						break;
					}
				}

			}

			break;

		// 탱크 조합
		case "탱크 조합":
			isGhost = false;
			isHeroFirebat = false;

			count = 0; // 유닛의 숫자 저장

			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "탱크") {
					count++;
				}
			}

			if (count >= 3) {
				break;
			}

			// 고스트가 1이상 인지 확인
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "고스트") {
					hero.unit[i] = null;
					isGhost = true;
					break;
				}
			}
			// 영웅파이어뱃이 1이상 인지 확인
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "영웅파이어뱃") {
					hero.unit[i] = null;
					isHeroFirebat = true;
					break;
				}
			}

			// 탱크 조합
			if (isGhost && isHeroFirebat) {

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("탱크.mp3", false);
						music.start();
						hero.unit[i] = new Tank();
						Print.notice = "탱크를 조합했습니다.";
						break;
					}
				}

			}

			break;
		}
	}

	public void enemyAttack() { // 적 유닛이 공격

		for (int i = 0; i < enemy.size(); i++) {
			if (enemy.get(i).range >= enemy.get(i).position) {
				hero.hp -= enemy.get(i).attack; // 공격
			}
		}
	}

	public void unitAttack() { // 아군 유닛이 공격

		for (int i = 0; i < hero.unit.length; i++) {

			// 일반 공격
			if (hero.unit[i] != null && enemy.size() > 0 && hero.unit[i].range >= enemy.get(0).position
					&& hero.unit[i].splash == false) {
				enemy.get(0).hp -= (hero.unit[i].attack() - enemy.get(0).armor); // 공격

				switch (hero.unit[i].name) { // 공격음 출력
				case "마린":
					attackMusic = new Music("마린공격음.mp3", false);
					attackMusic.start();
					break;

				case "파이어뱃":
					attackMusic = new Music("파이어뱃공격음.mp3", false);
					attackMusic.start();
					break;

				case "고스트":
					attackMusic = new Music("고스트공격음.mp3", false);
					attackMusic.start();
					break;

				case "영웅마린":
					attackMusic = new Music("마린공격음.mp3", false);
					attackMusic.start();
					break;

				case "영웅파이어뱃":
					attackMusic = new Music("파이어뱃공격음.mp3", false);
					attackMusic.start();
					break;

				case "영웅고스트":
					attackMusic = new Music("고스트공격음.mp3", false);
					attackMusic.start();
					break;

				case "골리앗":
					attackMusic = new Music("골리앗공격음.mp3", false);
					attackMusic.start();
					break;

				case "탱크":
					attackMusic = new Music("탱크공격음.mp3", false);
					attackMusic.start();
					break;

				default:
					break;
				}

				if (enemy.get(0).hp <= 0) { // 적 체력이 0이하가 되면 제거
					hero.kill++;
					hero.money += enemy.get(0).money;

					switch (enemy.get(0).name) { // 제거음 출력

					case "브루들링":
						deathMusic = new Music("브루들링터짐.mp3", false);
						deathMusic.start();
						break;

					case "프로브":
						deathMusic = new Music("프로브터짐.mp3", false);
						deathMusic.start();
						break;

					case "저글링":
						deathMusic = new Music("저글링터짐.mp3", false);
						deathMusic.start();
						break;

					case "질럿":
						deathMusic = new Music("질럿터짐.mp3", false);
						deathMusic.start();
						break;

					case "벌쳐":
						deathMusic = new Music("벌쳐터짐.mp3", false);
						deathMusic.start();
						break;

					case "히드라":
						deathMusic = new Music("히드라터짐.mp3", false);
						deathMusic.start();
						break;

					case "드라군":
						deathMusic = new Music("드라군터짐.mp3", false);
						deathMusic.start();
						break;

					case "아콘(boss)":
						deathMusic = new Music("아콘터짐.mp3", false);
						deathMusic.start();
						break;

					default:
						break;
					}
					
					if (enemy.get(0).name == "아콘(boss)") {
						killBoss = true;
					}

					enemy.remove(0);
				}

			}

			// 스플래시 공격
			if (hero.unit[i] != null && enemy.size() > 0 && hero.unit[i].range >= enemy.get(0).position
					&& hero.unit[i].splash == true) {

				for (int j = 0; j < enemy.size(); j++) {
					if ((enemy.get(j).position) <= (hero.unit[i].range)) {
						enemy.get(j).hp -= (hero.unit[i].attack() - enemy.get(0).armor); // 공격
					}
				}

				switch (hero.unit[i].name) { // 공격음 출력

				case "파이어뱃":
					attackMusic = new Music("파이어뱃공격음.mp3", false);
					attackMusic.start();
					break;

				case "영웅파이어뱃":
					attackMusic = new Music("파이어뱃공격음.mp3", false);
					attackMusic.start();
					break;

				case "탱크":
					attackMusic = new Music("탱크공격음.mp3", false);
					attackMusic.start();
					break;

				default:
					break;
				}

				for (int j = 0; j < enemy.size(); j++) {
					if (enemy.get(j).hp <= 0) { // 적 체력이 0이하가 되면 제거
						hero.kill++;
						hero.money += enemy.get(j).money;

						switch (enemy.get(j).name) { // 제거음 출력

						case "브루들링":
							deathMusic = new Music("브루들링터짐.mp3", false);
							deathMusic.start();
							break;

						case "프로브":
							deathMusic = new Music("프로브터짐.mp3", false);
							deathMusic.start();
							break;

						case "저글링":
							deathMusic = new Music("저글링터짐.mp3", false);
							deathMusic.start();
							break;

						case "질럿":
							deathMusic = new Music("질럿터짐.mp3", false);
							deathMusic.start();
							break;

						case "벌쳐":
							deathMusic = new Music("벌쳐터짐.mp3", false);
							deathMusic.start();
							break;

						case "히드라":
							deathMusic = new Music("히드라터짐.mp3", false);
							deathMusic.start();
							break;

						case "드라군":
							deathMusic = new Music("드라군터짐.mp3", false);
							deathMusic.start();
							break;

						case "아콘(boss)":
							deathMusic = new Music("아콘터짐.mp3", false);
							deathMusic.start();
							break;

						default:
							break;
						}
						
						if (enemy.get(j).name == "아콘(boss)") {
							killBoss = true;
						}

						enemy.remove(j);
					}
				}
			}
		}
	}

	public void move() { // 적 유닛 이동

		if (enemy.size() > 0) {
			for (int i = 0; i < enemy.size(); i++) {
				if (enemy.get(i).position > 1) {
					enemy.get(i).position -= 1;
				}
			}
		}
	}

	public void enemyCreate() { // 적 유닛 생산

		randomEnemy = (int) (Math.random() * 3 + 1); // 적 유닛 생산 경우의 수

		switch (round) {

		case 1: // 라운드 1 = 브루들링

			if (roundFirst[round - 1]) { // 적 유닛 생산

				if (randomEnemy == 1) {
					enemy.add(new Broodling());
				} else if (randomEnemy == 2) {
					enemy.add(new Broodling());
				} else {
					enemy.add(new Broodling());
					enemy.add(new Broodling());
				}

			} else { // 라운드의 처음 시작 시 출현음 출력

				createMusic = new Music("브루들링.mp3", false);
				createMusic.start();
				Print.notice = "조심하세요! 곧 부르들링이 쳐들어옵니다.";
				roundFirst[round - 1] = true;

			}
			break;

		case 2: // 라운드 2 = 프로브

			if (roundFirst[round - 1]) { // 적 유닛 생산

				if (randomEnemy == 1) {
					enemy.add(new Probe());
				} else if (randomEnemy == 2) {
					enemy.add(new Probe());
				} else {
					enemy.add(new Probe());
					enemy.add(new Probe());
				}

			} else { // 라운드의 처음 시작 시 출현음 출력

				createMusic = new Music("프로브.mp3", false);
				createMusic.start();
				Print.notice = "조심하세요! 곧 프로브가 쳐들어옵니다.";
				roundFirst[round - 1] = true;

			}
			break;

		case 3: // 라운드 3 = 저글링

			if (roundFirst[round - 1]) { // 적 유닛 생산

				if (randomEnemy == 1) {
					enemy.add(new Zergling());
				} else if (randomEnemy == 2) {
					enemy.add(new Zergling());
					enemy.add(new Zergling());
				} else {
					enemy.add(new Zergling());
					enemy.add(new Zergling());
				}

			} else { // 라운드의 처음 시작 시 출현음 출력

				createMusic = new Music("저글링.mp3", false);
				createMusic.start();
				Print.notice = "조심하세요! 곧 저글링이 쳐들어옵니다.";
				roundFirst[round - 1] = true;

			}
			break;

		case 4: // 라운드 4 = 질럿

			if (roundFirst[round - 1]) { // 적 유닛 생산

				if (randomEnemy == 1) {
					enemy.add(new Zealot());
				} else if (randomEnemy == 2) {
					enemy.add(new Zealot());
					enemy.add(new Zealot());
				} else {
					enemy.add(new Zealot());
					enemy.add(new Zealot());
				}

			} else { // 라운드의 처음 시작 시 출현음 출력

				createMusic = new Music("질럿.mp3", false);
				createMusic.start();
				Print.notice = "조심하세요! 곧 질럿이 쳐들어옵니다.";
				roundFirst[round - 1] = true;

			}
			break;

		case 5: // 라운드 5 = 벌쳐

			if (roundFirst[round - 1]) { // 적 유닛 생산

				if (randomEnemy == 1) {
					enemy.add(new Vulture());
				} else if (randomEnemy == 2) {
					enemy.add(new Vulture());
				} else {
					enemy.add(new Vulture());
					enemy.add(new Vulture());
				}

			} else { // 라운드의 처음 시작 시 출현음 출력

				createMusic = new Music("벌쳐.mp3", false);
				createMusic.start();
				Print.notice = "조심하세요! 곧 벌쳐가 쳐들어옵니다.";
				roundFirst[round - 1] = true;

			}
			break;

		case 6: // 라운드 6 = 히드라 + 저글링

			if (roundFirst[round - 1]) { // 적 유닛 생산

				if (randomEnemy == 1) {
					enemy.add(new Zergling());
					enemy.add(new Zergling());
				} else if (randomEnemy == 2) {
					enemy.add(new Zergling());
					enemy.add(new Hydra());
				} else {
					enemy.add(new Hydra());
				}

			} else { // 라운드의 처음 시작 시 출현음 출력

				createMusic = new Music("히드라.mp3", false);
				createMusic.start();
				Print.notice = "조심하세요! 곧 히드라가 쳐들어옵니다.";
				roundFirst[round - 1] = true;

			}
			break;

		case 7: // 라운드 7 = 드라군 + 질럿

			if (roundFirst[round - 1]) { // 적 유닛 생산

				if (randomEnemy == 1) {
					enemy.add(new Zealot());
					enemy.add(new Zealot());
				} else if (randomEnemy == 2) {
					enemy.add(new Zealot());
					enemy.add(new Dragon());
				} else {
					enemy.add(new Dragon());
				}

			} else { // 라운드의 처음 시작 시 출현음 출력

				createMusic = new Music("드라군.mp3", false);
				createMusic.start();
				Print.notice = "조심하세요! 곧 드라군이 쳐들어옵니다.";
				roundFirst[round - 1] = true;

			}
			break;

		case 8: // 라운드 8 = 아칸

			if (!roundFirst[round - 1]) { // 적 유닛 생산

				enemy.add(new Archon());

				createMusic = new Music("아콘.mp3", false);
				createMusic.start();
				Print.notice = "아콘(boss)이 출현했습니다.";
				roundFirst[round - 1] = true;

			}
			break;

		default:
			break;
		}
	}
}
