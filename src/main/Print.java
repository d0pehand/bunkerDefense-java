package main;

//ȭ���� �׷��ִ� Ŭ����
public class Print {

	Hero hero;
	Action action;
	int count;

	static String notice = ""; // �˸� ȭ�鿡 ǥ���� ����

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

	public void draw() { // ���� �� 0.25�ʸ��� �ݺ�
		clear();
		gameScrean(); // ����ȭ���� �׷��ش�.
		menuScrean(); // �޴������ �׷��ش�.
	}

	public void start() {
		Music startMusic;

		for (int i = 0; i < 3; i++) {
			clear();
			System.out.println("������Bunker Defence - Made By S.J��������������������������������������������������������");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                       Game Start                        ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
		}

		for (int i = 0; i < 3; i++) {
			clear();
			startMusic = new Music("ī��Ʈ�ٿ�.mp3", false);
			startMusic.start();

			System.out.println("������Bunker Defence - Made By S.J��������������������������������������������������������");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                       Game Start                        ��");
			System.out.println("��                                                         ��");
			System.out.println("��                           " + (3 - i) + "                             ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			// 1000 = 1�� �Ͻ�����
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		startMusic = new Music("����.mp3", false);
		startMusic.start();

		clear();
		System.out.println("������Bunker Defence - Made By S.J��������������������������������������������������������");
		System.out.println("��                                                         ��");
		System.out.println("��                                                         ��");
		System.out.println("��                                                         ��");
		System.out.println("��                       Game Start                        ��");
		System.out.println("��                                                         ��");
		System.out.println("��                           " + 0 + "                             ��");
		System.out.println("��                                                         ��");
		System.out.println("��                                                         ��");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void end(boolean killBoss) { // ������ �׿��� ���´°� ü���� ��Ƽ� �й��Ͽ��°�

		Music endMusic;

		if (killBoss) {
			clear();
			System.out.println("������Bunker Defence - Made By S.J��������������������������������������������������������");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                         Win!                            ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");

			endMusic = new Music("�¸���.mp3", false);
			endMusic.start();

		} else {
			clear();
			System.out.println("������Bunker Defence - Made By S.J��������������������������������������������������������");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                         Defeat!                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");
			System.out.println("��                                                         ��");

			endMusic = new Music("�й���.mp3", false);
			endMusic.start();
		}

	}

	public void gameScrean() { // ����ȭ���� �׷��ش�.

		System.out.println("��������Ŀ ���潺��������������������������������������������������������������������������������������������������");
		System.out.print("money : " + hero.money + "  ");
		System.out.print("    upgrade : " + Hero.upgrade);
		System.out.println("    productivity : " + hero.productivity);
		System.out.print(" kill : " + hero.kill);

		// ���� ǥ��
		System.out.print("	 Round : " + action.round);

		// waveTime RestTime ���� �ð� ǥ��
		if ((action.time % (action.wave + action.rest)) < action.wave) {
			System.out.println("	WaveTime : " + (action.wave - (action.time % (action.wave + action.rest))));
		} else {
			System.out.println(
					"	RestTime : " + (action.wave + action.rest - (action.time % (action.wave + action.rest))));
		}

		System.out.println("");

		// �� ���� ���� ǥ��
		if (action.enemy.size() > 0) {
			boolean[] enemyInfo = new boolean[8];

			// �� ���� ��ȸ
			for (int i = 0; i < action.enemy.size(); i++) {
				switch (action.enemy.get(i).name) {
				case "���鸵":
					enemyInfo[0] = true;
					break;

				case "���κ�":
					enemyInfo[1] = true;
					break;

				case "���۸�":
					enemyInfo[2] = true;
					break;

				case "����":
					enemyInfo[3] = true;
					break;

				case "����":
					enemyInfo[4] = true;
					break;

				case "�����":
					enemyInfo[5] = true;
					break;

				case "���":
					enemyInfo[6] = true;
					break;

				case "����(boss)":
					enemyInfo[7] = true;
					break;

				default:
					break;
				}

			}

			// �� ���� ���
			if (enemyInfo[0]) {
				System.out.println("info : ���鸵   ���ݷ� 4   ���� 0   ü�� 40   ��Ÿ� 1   �� 3");
			}
			if (enemyInfo[1]) {
				System.out.println("info : ���κ�   ���ݷ� 10   ���� 4   ü�� 40   ��Ÿ� 1   �� 4");
			}
			if (enemyInfo[2]) {
				System.out.println("info : ���۸�   ���ݷ�10   ���� 0   ü�� 100   ��Ÿ� 1   �� 5");
			}
			if (enemyInfo[3]) {
				System.out.println("info : ����   ���ݷ� 20   ���� 2   ü�� 250   ��Ÿ� 1   �� 5");
			}
			if (enemyInfo[4]) {
				System.out.println("info : ����   ���ݷ� 10   ���� 0   ü�� 120   ��Ÿ� 2   �� 6");
			}
			if (enemyInfo[5]) {
				System.out.println("info : �����   ���ݷ� 20   ���� 0   ü�� 200   ��Ÿ� 2   �� 7");
			}
			if (enemyInfo[6]) {
				System.out.println("info : ���   ���ݷ� 10   ���� 20   ü�� 350   ��Ÿ� 3   �� 10");
			}
			if (enemyInfo[7]) {
				System.out.println("info : ����(boss)   ���ݷ� 40   ���� 100   ü�� 1000000   ��Ÿ� 2");
			}

			// �� ���� �� ü�� ǥ��
			if (action.enemy.size() > 0) {
				System.out.println("");
				int firstEnemyHp = action.enemy.get(0).hp;
				if (firstEnemyHp < 0) {
					firstEnemyHp = 0;
				}
				System.out.println("		" + action.enemy.get(0).name + "   ü�� " + firstEnemyHp);
			} else {
				System.out.println("");
				System.out.println("");
			}

		} else { // �� ������ ���ٸ�(�׷����� ��������)
			System.out.println("");
			System.out.println("");
		}

		System.out.println("");
		System.out.println("");

		// ��ġ�� �� ���� ���� ���
		int[] enemycount = new int[8];
		if (action.enemy.size() > 0) {
			for (int i = 0; i < 6; i++) {

				for (int j = 0; j < 8; j++) {
					enemycount[j] = 0;
				}

				for (int j = 0; j < action.enemy.size(); j++) {

					if (action.enemy.get(j).position == 6 - i) {
						switch (action.enemy.get(j).name) {
						case "���鸵":
							enemycount[0]++;
							break;

						case "���κ�":
							enemycount[1]++;
							break;

						case "���۸�":
							enemycount[2]++;
							break;

						case "����":
							enemycount[3]++;
							break;

						case "����":
							enemycount[4]++;
							break;

						case "�����":
							enemycount[5]++;
							break;

						case "���":
							enemycount[6]++;
							break;

						case "����(boss)":
							enemycount[7]++;
							break;

						default:
							break;
						}
					}
				}

				String n = "		";
				if (enemycount[0] > 0) {
					n += "���鸵 x" + enemycount[0] + " ";
				}
				if (enemycount[1] > 0) {
					n += "���κ� x" + enemycount[1] + " ";
				}
				if (enemycount[2] > 0) {
					n += "���۸� x" + enemycount[2] + " ";
				}
				if (enemycount[3] > 0) {
					n += "���� x" + enemycount[3] + " ";
				}
				if (enemycount[4] > 0) {
					n += "���� x" + enemycount[4] + " ";
				}
				if (enemycount[5] > 0) {
					n += "����� x" + enemycount[5] + " ";
				}
				if (enemycount[6] > 0) {
					n += "��� x" + enemycount[6] + " ";
				}
				if (enemycount[7] > 0) {
					n += "����(boss) x" + enemycount[7] + " ";
				}

				System.out.println(n);

			}
		} else { // �� ������ ���� ���
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
		}

		// ��Ŀ, ���� UI
		System.out.println("		������������������������������������			");
		if (hero.steamPack > 0) {
			System.out.println("		��      scv " + hero.scv + "     ��      	T.������" + hero.steamPack);
		} else {
			System.out.println("		��      scv " + hero.scv + "     ��");
		}

		System.out.println("		��                ��			");

		System.out.println("		    ��Ŀ : " + hero.hp + "/" + hero.maxhp);
		System.out.println("");

		count = 0; // �Ʊ� ������ ���� ����
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

		// �Ʊ� ���� �������� ���� ��ȸ
		int[] unitCount = new int[8];

		for (int i = 0; i < hero.unit.length; i++) {

			if (hero.unit[i] != null) {
				switch (hero.unit[i].name) {
				case "����":
					unitCount[0] += 1;
					break;

				case "���̾��":
					unitCount[1] += 1;
					break;

				case "��Ʈ":
					unitCount[2] += 1;
					break;

				case "��������":
					unitCount[3] += 1;
					break;

				case "�������̾��":
					unitCount[4] += 1;
					break;

				case "������Ʈ":
					unitCount[5] += 1;
					break;

				case "�񸮾�":
					unitCount[6] += 1;
					break;

				case "��ũ":
					unitCount[7] += 1;
					break;

				default:
					break;
				}
			}
		}

		// �Ʊ� ���� ���� ���
		if (unitCount[0] > 0) {
			if (hero.steamPackNow) {
				System.out.println("    ���� : ���ݷ� " + (20 + Hero.upgrade * 4) + "   ��Ÿ� 2   x" + unitCount[0]);
			} else {
				System.out.println("    ���� : ���ݷ� " + (10 + Hero.upgrade * 2) + "   ��Ÿ� 2   x" + unitCount[0]);
			}
		}
		if (unitCount[1] > 0) {
			if (hero.steamPackNow) {
				System.out.println(
						"    ���̾�� : ���ݷ� " + (80 + Hero.upgrade * 8) + "   ��Ÿ� 1   x" + unitCount[1] + "   ���÷���");
			} else {
				System.out.println(
						"    ���̾�� : ���ݷ� " + (40 + Hero.upgrade * 4) + "   ��Ÿ� 1   x" + unitCount[1] + "   ���÷���");
			}
		}
		if (unitCount[2] > 0) {
			System.out.println("    ��Ʈ : ���ݷ� " + (100 + Hero.upgrade * 10) + "   ��Ÿ� 4   x" + unitCount[2]);
		}
		if (unitCount[3] > 0) {
			if (hero.steamPackNow) {
				System.out.println("    ���� ���� : ���ݷ� " + (80 + Hero.upgrade * 16) + "   ��Ÿ� 2   x" + unitCount[3]);
			} else {
				System.out.println("    ���� ���� : ���ݷ� " + (40 + Hero.upgrade * 8) + "   ��Ÿ� 2   x" + unitCount[3]);
			}
		}
		if (unitCount[4] > 0) {
			if (hero.steamPackNow) {
				System.out.println(
						"    ���� ���̾�� : ���ݷ� " + (160 + Hero.upgrade * 20) + "   ��Ÿ� 1   x" + unitCount[4] + "   ���÷���");
			} else {
				System.out.println(
						"    ���� ���̾�� : ���ݷ� " + (80 + Hero.upgrade * 10) + "   ��Ÿ� 1   x" + unitCount[4] + "   ���÷���");
			}
		}
		if (unitCount[5] > 0) {
			System.out.println("    ���� ��Ʈ : ���ݷ� " + (200 + Hero.upgrade * 20) + "   ��Ÿ� 4   x" + unitCount[5]);
		}
		if (unitCount[6] > 0) {
			System.out.println("    �񸮾� : ���ݷ� " + (100 + Hero.upgrade * 25) + "   ��Ÿ� 3   x" + unitCount[6]);
		}
		if (unitCount[7] > 0) {
			System.out.println(
					"    ��ũ : ���ݷ� " + (100 + Hero.upgrade * 10) + "   ��Ÿ� 4   x" + unitCount[7] + "/3" + "   ���÷���");
		}

		System.out.println("����������������������������������������������������������������������������������������������������������������������");
		System.out.println("	�˸� : " + notice);
		System.out.println("����������������������������������������������������������������������������������������������������������������������");

	}

	public void menuScrean() { // �޴� ȭ���� �׷��ش�.

		if (InputKey.menuType == 1) { // ���� â
			System.out.println("������ �ൿ�� ������ �ּ���.");

			System.out.print("	1. ���� ����      | 80��  ");
			System.out.println("		Q. ���ݷ� ��   | " + (10 + hero.upgrade * 10) + "��");
			System.out.print("	2. ���̾�� ����| 200��");
			System.out.println("		W. ����� ��   | " + (hero.productivity * 100 + 100) + "��");
			System.out.print("	3. ��Ʈ ����   | 500��");
			System.out.println("		E. ����â���� | ");
			System.out.print("	4. scv ����    | 250��");
			System.out.println("		R. ����â���� | ");

			System.out.println("");
			System.out.println("");
		}

		if (InputKey.menuType == 2) { // ���� â
			System.out.println("������ �ൿ�� ������ �ּ���. | Q. �ڷΰ���");

			System.out.println("	1. ���� ���� ����      | ���� x4           ");
			System.out.println("	2. ���� ���̾�� ����| ���̾�� x2        ");
			System.out.println("	3. ���� ��Ʈ ����   | ��Ʈ x2         ");
			System.out.println("	4. �񸮾� ����          | ��Ʈ + ���� ����      ");
			System.out.println("	5. ��ũ ����             | ��Ʈ + ���� ���̾��");

			System.out.println("");
			System.out.println("");
		}

		if (InputKey.menuType == 3) { // ���� â
			System.out.println("������ �ൿ�� ������ �ּ���. | Q. �ڷΰ���");

			System.out.println("	1. ���� ����            | W. ���� ���� ����         ");
			System.out.println("	2. ���̾�� ����      | E. ���� ���̾�� ����   ");
			System.out.println("	3. ��Ʈ ����         | R. ���� ��Ʈ ����      ");
			System.out.println("	4. �񸮾� ����         |                 ");
			System.out.println("	5. ��ũ ����            |                 ");

			System.out.println("");
			System.out.println("");
		}
	}
}
