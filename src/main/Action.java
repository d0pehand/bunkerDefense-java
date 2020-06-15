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

//���ֵ��� �ൿ�� ó���ϴ� ������
public class Action extends Thread {

	ArrayList<Enemy> enemy = new ArrayList<Enemy>(); // �� ����
	Hero hero;
	
	boolean isStop = false;

	long start = 0;
	long end = 0;
	int time = 0; // ����� �ð� ����

	boolean killBoss = false; // ������ ���̸� ���� ����
	int round = 1; // 10���� ���� ���̺갡 ����
	boolean[] roundFirst;

	int wave = 50; // ���̺� �ð�
	int rest = 10; // �޽� �ð�

	Music music;
	Music attackMusic; // �Ʊ� ������ �����Ҷ� ���� �Ҹ�
	Music createMusic; // ���̺갡 ���۵ɶ� ���� �Ҹ�
	Music deathMusic; // �� ������ ������ ���� �Ҹ�

	int randomEnemy; // ���� ������ ����

	int count = 0; // �Ʊ� ������ ����

	public Action(Hero hero) {
		this.hero = hero;
		this.roundFirst = new boolean[10];
	}

	public void run() {

		start = System.currentTimeMillis(); // ���� ���� �� ����ð� ����

		while (!isStop) {
			end = System.currentTimeMillis(); // ����ð� ����
			time = (int) (end - start) / 1000; // ������ ��� �ð�

			round = time / (wave + rest) + 1;
			if (round > 8) {
				round = 8;
			} // ���� ���

			if ((time % (wave + rest)) < wave) { // WaveTime�� ��쿡
				enemyCreate(); // �� ���� ����
			}

			move(); // �� ���� �̵�

			unitAttack(); // �Ʊ� ������ ����

			enemyAttack(); // �� ������ ����

			// ���� ����
			if (InputKey.createCommand) {
				create(InputKey.command);
				InputKey.createCommand = false;
				InputKey.command = "";
			}

			// ���� ����
			if (InputKey.combinationCommand) {
				combination(InputKey.command);
				InputKey.combinationCommand = false;
				InputKey.command = "";
			}
			// ���� ����
			if (InputKey.removeCommand) {
				remove(InputKey.command);
				InputKey.removeCommand = false;
				InputKey.command = "";
			}

			// 500 = 0.5�� �Ͻ�����
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void create(String command) {

		count = 0; // ������ ���� ����

		for (int i = 0; i < hero.unit.length; i++) {
			if (hero.unit[i] != null) {
				count++;
			}
		}

		// �Է� ���� ���� ó��
		switch (command) {

		// ���� ����
		case "���� ����":
			if (hero.money > 80 && count < 8) {
				hero.money -= 80;

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("����.mp3", false);
						music.start();
						hero.unit[i] = new Marine();
						Print.notice = "������ �����Ǿ����ϴ�.";
						break;
					}
				}
			} else if (hero.money < 80) {
				music = new Music("�̳׶�����.mp3", false);
				music.start();
				Print.notice = "���� �����մϴ�.";
			} else if (count >= 8) {
				music = new Music("������.mp3", false);
				music.start();
				Print.notice = "������ �� á���ϴ�.";
			}

			break;

		// ���̾�� ����
		case "���̾�� ����":
			if (hero.money > 200 && count < 8) {
				hero.money -= 200;

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("���̾��.mp3", false);
						music.start();
						hero.unit[i] = new Firebat();
						Print.notice = "���̾���� �����Ǿ����ϴ�.";
						break;
					}
				}
			} else if (hero.money < 200) {
				music = new Music("�̳׶�����.mp3", false);
				music.start();
				Print.notice = "���� �����մϴ�.";
			} else if (count >= 8) {
				music = new Music("������.mp3", false);
				music.start();
				Print.notice = "������ �� á���ϴ�.";
			}

			break;
		// ��Ʈ ����
		case "��Ʈ ����":
			if (hero.money > 500 && count < 8) {
				hero.money -= 500;

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("��Ʈ.mp3", false);
						music.start();
						hero.unit[i] = new Ghost();
						Print.notice = "��Ʈ�� �����Ǿ����ϴ�.";
						break;
					}
				}
			} else if (hero.money < 500) {
				music = new Music("�̳׶�����.mp3", false);
				music.start();
				Print.notice = "���� �����մϴ�.";
			} else if (count >= 8) {
				music = new Music("������.mp3", false);
				music.start();
				Print.notice = "������ �� á���ϴ�.";
			}

			break;

		default:
			break;
		}
	}

	public void remove(String command) {
		// �Է� ���� ���� ó��
		switch (command) {

		// ���� ����
		case "���� ����":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "����") {
					hero.unit[i] = null;
					music = new Music("��ư��.mp3", false);
					music.start();
					Print.notice = "������ ���ŵǾ����ϴ�.";
					break;
				}
			}

			break;

		// ���̾�� ����
		case "���̾�� ����":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "���̾��") {
					hero.unit[i] = null;
					music = new Music("��ư��.mp3", false);
					music.start();
					Print.notice = "���̾���� ���ŵǾ����ϴ�.";
					break;
				}
			}

			break;
		// ��Ʈ ����
		case "��Ʈ ����":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "��Ʈ") {
					hero.unit[i] = null;
					music = new Music("��ư��.mp3", false);
					music.start();
					Print.notice = "��Ʈ�� ���ŵǾ����ϴ�.";
					break;
				}
			}

			break;

		// �񸮾� ����
		case "�񸮾� ����":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "�񸮾�") {
					hero.unit[i] = null;
					music = new Music("��ư��.mp3", false);
					music.start();
					Print.notice = "�񸮾��� ���ŵǾ����ϴ�.";
					break;
				}
			}

			break;

		// ��ũ ����
		case "��ũ ����":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "��ũ") {
					hero.unit[i] = null;
					music = new Music("��ư��.mp3", false);
					music.start();
					Print.notice = "��ũ�� ���ŵǾ����ϴ�.";
					break;
				}
			}

			break;
		// �������� ����
		case "�������� ����":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "��������") {
					hero.unit[i] = null;
					music = new Music("��ư��.mp3", false);
					music.start();
					Print.notice = "���������� ���ŵǾ����ϴ�.";
					break;
				}
			}

			break;

		// �������̾�� ����
		case "�������̾�� ����":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "�������̾��") {
					hero.unit[i] = null;
					music = new Music("��ư��.mp3", false);
					music.start();
					Print.notice = "�������̾���� ���ŵǾ����ϴ�.";
					break;
				}
			}

			break;

		// ������Ʈ ����
		case "������Ʈ ����":
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "������Ʈ") {
					hero.unit[i] = null;
					music = new Music("��ư��.mp3", false);
					music.start();
					Print.notice = "������Ʈ�� ���ŵǾ����ϴ�.";
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

		// �Է� ���� ���� ó��
		switch (command) {

		// ���� ���� ����
		case "�������� ����":
			// ������ 4�̻����� Ȯ��
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "����") {
					unitCount++;
				}
			}

			// ���� ���� ����
			if (unitCount >= 4) {
				int material = 4;

				for (int i = 0; i < hero.unit.length; i++) { // ��� ���� �Ҹ�
					if (hero.unit[i] != null && hero.unit[i].name == "����" && material > 0) {
						material--;
						hero.unit[i] = null;
					}
				}

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("����.mp3", false);
						music.start();
						hero.unit[i] = new HeroMarine();
						Print.notice = "���� ������ �����߽��ϴ�.";
						break;
					}
				}

			}
			break;

		// ���� ���̾�� ����
		case "�������̾�� ����":
			// ���̾���� 2�̻����� Ȯ��
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "���̾��") {
					unitCount++;
				}
			}

			// ���� ���̾�� ����
			if (unitCount >= 2) {
				int material = 2;

				for (int i = 0; i < hero.unit.length; i++) { // ��� ���� �Ҹ�
					if (hero.unit[i] != null && hero.unit[i].name == "���̾��" && material > 0) {
						material--;
						hero.unit[i] = null;
					}
				}

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("���̾��.mp3", false);
						music.start();
						hero.unit[i] = new HeroFirebat();
						Print.notice = "���� ���̾���� �����߽��ϴ�.";
						break;
					}
				}

			}

			break;
		// ������Ʈ ����
		case "������Ʈ ����":
			// ��Ʈ�� 2�̻����� Ȯ��
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "��Ʈ") {
					unitCount++;
				}
			}

			// ���� ��Ʈ ����
			if (unitCount >= 2) {
				int material = 2;

				for (int i = 0; i < hero.unit.length; i++) { // ��� ���� �Ҹ�
					if (hero.unit[i] != null && hero.unit[i].name == "��Ʈ" && material > 0) {
						material--;
						hero.unit[i] = null;
					}
				}

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("��Ʈ.mp3", false);
						music.start();
						hero.unit[i] = new HeroGhost();
						Print.notice = "���� ��Ʈ�� �����߽��ϴ�.";
						break;
					}
				}

			}
			break;

		// �񸮾� ����
		case "�񸮾� ����":
			isGhost = false;
			isHeroMarine = false;

			// ��Ʈ�� 1�̻� ���� Ȯ��
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "��Ʈ") {
					hero.unit[i] = null;
					isGhost = true;
					break;
				}
			}
			// ���������� 1�̻� ���� Ȯ��
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "��������") {
					hero.unit[i] = null;
					isHeroMarine = true;
					break;
				}
			}

			// �񸮾� ����
			if (isGhost && isHeroMarine) {

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						hero.unit[i] = new Goliath();
						music = new Music("�񸮾�.mp3", false);
						music.start();
						Print.notice = "�񸮾��� �����߽��ϴ�.";
						break;
					}
				}

			}

			break;

		// ��ũ ����
		case "��ũ ����":
			isGhost = false;
			isHeroFirebat = false;

			count = 0; // ������ ���� ����

			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "��ũ") {
					count++;
				}
			}

			if (count >= 3) {
				break;
			}

			// ��Ʈ�� 1�̻� ���� Ȯ��
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "��Ʈ") {
					hero.unit[i] = null;
					isGhost = true;
					break;
				}
			}
			// �������̾���� 1�̻� ���� Ȯ��
			for (int i = 0; i < hero.unit.length; i++) {
				if (hero.unit[i] != null && hero.unit[i].name == "�������̾��") {
					hero.unit[i] = null;
					isHeroFirebat = true;
					break;
				}
			}

			// ��ũ ����
			if (isGhost && isHeroFirebat) {

				for (int i = 0; i < hero.unit.length; i++) {
					if (hero.unit[i] == null) {
						music = new Music("��ũ.mp3", false);
						music.start();
						hero.unit[i] = new Tank();
						Print.notice = "��ũ�� �����߽��ϴ�.";
						break;
					}
				}

			}

			break;
		}
	}

	public void enemyAttack() { // �� ������ ����

		for (int i = 0; i < enemy.size(); i++) {
			if (enemy.get(i).range >= enemy.get(i).position) {
				hero.hp -= enemy.get(i).attack; // ����
			}
		}
	}

	public void unitAttack() { // �Ʊ� ������ ����

		for (int i = 0; i < hero.unit.length; i++) {

			// �Ϲ� ����
			if (hero.unit[i] != null && enemy.size() > 0 && hero.unit[i].range >= enemy.get(0).position
					&& hero.unit[i].splash == false) {
				enemy.get(0).hp -= (hero.unit[i].attack() - enemy.get(0).armor); // ����

				switch (hero.unit[i].name) { // ������ ���
				case "����":
					attackMusic = new Music("����������.mp3", false);
					attackMusic.start();
					break;

				case "���̾��":
					attackMusic = new Music("���̾�������.mp3", false);
					attackMusic.start();
					break;

				case "��Ʈ":
					attackMusic = new Music("��Ʈ������.mp3", false);
					attackMusic.start();
					break;

				case "��������":
					attackMusic = new Music("����������.mp3", false);
					attackMusic.start();
					break;

				case "�������̾��":
					attackMusic = new Music("���̾�������.mp3", false);
					attackMusic.start();
					break;

				case "������Ʈ":
					attackMusic = new Music("��Ʈ������.mp3", false);
					attackMusic.start();
					break;

				case "�񸮾�":
					attackMusic = new Music("�񸮾Ѱ�����.mp3", false);
					attackMusic.start();
					break;

				case "��ũ":
					attackMusic = new Music("��ũ������.mp3", false);
					attackMusic.start();
					break;

				default:
					break;
				}

				if (enemy.get(0).hp <= 0) { // �� ü���� 0���ϰ� �Ǹ� ����
					hero.kill++;
					hero.money += enemy.get(0).money;

					switch (enemy.get(0).name) { // ������ ���

					case "���鸵":
						deathMusic = new Music("���鸵����.mp3", false);
						deathMusic.start();
						break;

					case "���κ�":
						deathMusic = new Music("���κ�����.mp3", false);
						deathMusic.start();
						break;

					case "���۸�":
						deathMusic = new Music("���۸�����.mp3", false);
						deathMusic.start();
						break;

					case "����":
						deathMusic = new Music("��������.mp3", false);
						deathMusic.start();
						break;

					case "����":
						deathMusic = new Music("��������.mp3", false);
						deathMusic.start();
						break;

					case "�����":
						deathMusic = new Music("���������.mp3", false);
						deathMusic.start();
						break;

					case "���":
						deathMusic = new Music("�������.mp3", false);
						deathMusic.start();
						break;

					case "����(boss)":
						deathMusic = new Music("��������.mp3", false);
						deathMusic.start();
						break;

					default:
						break;
					}
					
					if (enemy.get(0).name == "����(boss)") {
						killBoss = true;
					}

					enemy.remove(0);
				}

			}

			// ���÷��� ����
			if (hero.unit[i] != null && enemy.size() > 0 && hero.unit[i].range >= enemy.get(0).position
					&& hero.unit[i].splash == true) {

				for (int j = 0; j < enemy.size(); j++) {
					if ((enemy.get(j).position) <= (hero.unit[i].range)) {
						enemy.get(j).hp -= (hero.unit[i].attack() - enemy.get(0).armor); // ����
					}
				}

				switch (hero.unit[i].name) { // ������ ���

				case "���̾��":
					attackMusic = new Music("���̾�������.mp3", false);
					attackMusic.start();
					break;

				case "�������̾��":
					attackMusic = new Music("���̾�������.mp3", false);
					attackMusic.start();
					break;

				case "��ũ":
					attackMusic = new Music("��ũ������.mp3", false);
					attackMusic.start();
					break;

				default:
					break;
				}

				for (int j = 0; j < enemy.size(); j++) {
					if (enemy.get(j).hp <= 0) { // �� ü���� 0���ϰ� �Ǹ� ����
						hero.kill++;
						hero.money += enemy.get(j).money;

						switch (enemy.get(j).name) { // ������ ���

						case "���鸵":
							deathMusic = new Music("���鸵����.mp3", false);
							deathMusic.start();
							break;

						case "���κ�":
							deathMusic = new Music("���κ�����.mp3", false);
							deathMusic.start();
							break;

						case "���۸�":
							deathMusic = new Music("���۸�����.mp3", false);
							deathMusic.start();
							break;

						case "����":
							deathMusic = new Music("��������.mp3", false);
							deathMusic.start();
							break;

						case "����":
							deathMusic = new Music("��������.mp3", false);
							deathMusic.start();
							break;

						case "�����":
							deathMusic = new Music("���������.mp3", false);
							deathMusic.start();
							break;

						case "���":
							deathMusic = new Music("�������.mp3", false);
							deathMusic.start();
							break;

						case "����(boss)":
							deathMusic = new Music("��������.mp3", false);
							deathMusic.start();
							break;

						default:
							break;
						}
						
						if (enemy.get(j).name == "����(boss)") {
							killBoss = true;
						}

						enemy.remove(j);
					}
				}
			}
		}
	}

	public void move() { // �� ���� �̵�

		if (enemy.size() > 0) {
			for (int i = 0; i < enemy.size(); i++) {
				if (enemy.get(i).position > 1) {
					enemy.get(i).position -= 1;
				}
			}
		}
	}

	public void enemyCreate() { // �� ���� ����

		randomEnemy = (int) (Math.random() * 3 + 1); // �� ���� ���� ����� ��

		switch (round) {

		case 1: // ���� 1 = ���鸵

			if (roundFirst[round - 1]) { // �� ���� ����

				if (randomEnemy == 1) {
					enemy.add(new Broodling());
				} else if (randomEnemy == 2) {
					enemy.add(new Broodling());
				} else {
					enemy.add(new Broodling());
					enemy.add(new Broodling());
				}

			} else { // ������ ó�� ���� �� ������ ���

				createMusic = new Music("���鸵.mp3", false);
				createMusic.start();
				Print.notice = "�����ϼ���! �� �θ��鸵�� �ĵ��ɴϴ�.";
				roundFirst[round - 1] = true;

			}
			break;

		case 2: // ���� 2 = ���κ�

			if (roundFirst[round - 1]) { // �� ���� ����

				if (randomEnemy == 1) {
					enemy.add(new Probe());
				} else if (randomEnemy == 2) {
					enemy.add(new Probe());
				} else {
					enemy.add(new Probe());
					enemy.add(new Probe());
				}

			} else { // ������ ó�� ���� �� ������ ���

				createMusic = new Music("���κ�.mp3", false);
				createMusic.start();
				Print.notice = "�����ϼ���! �� ���κ갡 �ĵ��ɴϴ�.";
				roundFirst[round - 1] = true;

			}
			break;

		case 3: // ���� 3 = ���۸�

			if (roundFirst[round - 1]) { // �� ���� ����

				if (randomEnemy == 1) {
					enemy.add(new Zergling());
				} else if (randomEnemy == 2) {
					enemy.add(new Zergling());
					enemy.add(new Zergling());
				} else {
					enemy.add(new Zergling());
					enemy.add(new Zergling());
				}

			} else { // ������ ó�� ���� �� ������ ���

				createMusic = new Music("���۸�.mp3", false);
				createMusic.start();
				Print.notice = "�����ϼ���! �� ���۸��� �ĵ��ɴϴ�.";
				roundFirst[round - 1] = true;

			}
			break;

		case 4: // ���� 4 = ����

			if (roundFirst[round - 1]) { // �� ���� ����

				if (randomEnemy == 1) {
					enemy.add(new Zealot());
				} else if (randomEnemy == 2) {
					enemy.add(new Zealot());
					enemy.add(new Zealot());
				} else {
					enemy.add(new Zealot());
					enemy.add(new Zealot());
				}

			} else { // ������ ó�� ���� �� ������ ���

				createMusic = new Music("����.mp3", false);
				createMusic.start();
				Print.notice = "�����ϼ���! �� ������ �ĵ��ɴϴ�.";
				roundFirst[round - 1] = true;

			}
			break;

		case 5: // ���� 5 = ����

			if (roundFirst[round - 1]) { // �� ���� ����

				if (randomEnemy == 1) {
					enemy.add(new Vulture());
				} else if (randomEnemy == 2) {
					enemy.add(new Vulture());
				} else {
					enemy.add(new Vulture());
					enemy.add(new Vulture());
				}

			} else { // ������ ó�� ���� �� ������ ���

				createMusic = new Music("����.mp3", false);
				createMusic.start();
				Print.notice = "�����ϼ���! �� ���İ� �ĵ��ɴϴ�.";
				roundFirst[round - 1] = true;

			}
			break;

		case 6: // ���� 6 = ����� + ���۸�

			if (roundFirst[round - 1]) { // �� ���� ����

				if (randomEnemy == 1) {
					enemy.add(new Zergling());
					enemy.add(new Zergling());
				} else if (randomEnemy == 2) {
					enemy.add(new Zergling());
					enemy.add(new Hydra());
				} else {
					enemy.add(new Hydra());
				}

			} else { // ������ ó�� ���� �� ������ ���

				createMusic = new Music("�����.mp3", false);
				createMusic.start();
				Print.notice = "�����ϼ���! �� ����� �ĵ��ɴϴ�.";
				roundFirst[round - 1] = true;

			}
			break;

		case 7: // ���� 7 = ��� + ����

			if (roundFirst[round - 1]) { // �� ���� ����

				if (randomEnemy == 1) {
					enemy.add(new Zealot());
					enemy.add(new Zealot());
				} else if (randomEnemy == 2) {
					enemy.add(new Zealot());
					enemy.add(new Dragon());
				} else {
					enemy.add(new Dragon());
				}

			} else { // ������ ó�� ���� �� ������ ���

				createMusic = new Music("���.mp3", false);
				createMusic.start();
				Print.notice = "�����ϼ���! �� ����� �ĵ��ɴϴ�.";
				roundFirst[round - 1] = true;

			}
			break;

		case 8: // ���� 8 = ��ĭ

			if (!roundFirst[round - 1]) { // �� ���� ����

				enemy.add(new Archon());

				createMusic = new Music("����.mp3", false);
				createMusic.start();
				Print.notice = "����(boss)�� �����߽��ϴ�.";
				roundFirst[round - 1] = true;

			}
			break;

		default:
			break;
		}
	}
}
