package main;

import java.util.Scanner;

//�Է¹��� Ű�� �����ϴ� Ŭ����
//1.����â 2.����â 3.����â 	�������� ���� �Է¹��� Ű�� �����Ѵ�.
public class InputKey extends Thread {

	Hero hero;
	Music music;

	Scanner scanner = new Scanner(System.in);

	String select; // �Է� ��
	static int menuType = 1; // 1.����â 2.����â 3.����â
	
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

					// �Է� ���� ���� ó��
					switch (select) {

					// ���� ����
					case "1":
					case "11":

						createCommand = true;
						command = "���� ����";

						break;

					// ���̾�� ����
					case "2":
					case "22":

						createCommand = true;
						command = "���̾�� ����";
						
						break;

					// ��Ʈ ����
					case "3":
					case "33":

						createCommand = true;
						command = "��Ʈ ����";
						
						break;

					// scv ���� Ű
					case "4":
					case "44":

						if (hero.money > 250) {
							hero.money -= 250;
							hero.scv++;
							music = new Music("scv.mp3", false);
							music.start();
							Print.notice = "scv�� �߰� �Ǿ����ϴ�.";
						} else {
							music = new Music("�̳׶�����.mp3", false);
							music.start();
							Print.notice = "���� �����մϴ�.";
						}
						break;

					// ���׷��̵� Ű
					case "q":
					case "qq":
					case "Q":
					case "QQ":
					case "��":
					case "����":

						if (hero.money > (10 + Hero.upgrade * 10)) {
							hero.money -= 10 + Hero.upgrade * 10;
							Hero.upgrade++;

							music = new Music("���ۼ���.mp3", false);
							music.start();
							Print.notice = "���׷��̵� �Ϸ�.";

						} else {
							music = new Music("�̳׶�����.mp3", false);
							music.start();
							Print.notice = "���� �����մϴ�.";
						}
						break;

					// ����� ���� Ű
					case "w":
					case "ww":
					case "W":
					case "WW":
					case "��":
					case "����":

						if (hero.money > (100 + hero.productivity * 100)) {
							hero.money -= 100 + hero.productivity * 100;
							hero.productivity++;

							music = new Music("��������.mp3", false);
							music.start();
							Print.notice = "����� ���� �Ϸ�.";

						} else {
							music = new Music("�̳׶�����.mp3", false);
							music.start();
							Print.notice = "���� �����մϴ�.";
						}
						break;

					// ����â���� �̵� Ű
					case "e":
					case "ee":
					case "E":
					case "EE":
					case "��":
					case "����":

						menuType = 2;
						break;

					// ����â���� �̵� Ű
					case "r":
					case "rr":
					case "R":
					case "RR":
					case "��":
					case "����":

						menuType = 3;
						break;
						
						// ����� ���� Ű
					case "t":
					case "tt":
					case "T":
					case "TT":
					case "��":
					case "����":

						if (hero.steamPack > 0) {
							hero.steamPack -= 1;
							
							music = new Music("������.mp3", false);
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

					// �Է� ���� ���� ó��
					switch (select) {

					// �������� ����
					case "1":
					case "11":
						combinationCommand = true;
						command = "�������� ����";
						break;

					// ���� ���̾�� ����
					case "2":
					case "22":
						combinationCommand = true;
						command = "�������̾�� ����";

						break;
					// ������Ʈ ����
					case "3":
					case "33":
						combinationCommand = true;
						command = "������Ʈ ����";
						
						break;

					// �񸮾� ����
					case "4":
					case "44":
						combinationCommand = true;
						command = "�񸮾� ����";

						break;

					// ��ũ ����
					case "5":
					case "55":
						combinationCommand = true;
						command = "��ũ ����";
						
						break;

					// ����â����
					case "q":
					case "qq":
					case "Q":
					case "QQ":
					case "��":
					case "����":

						menuType = 1;
						break;

					default:
						break;
					}

				}

				else if (menuType == 3) {

					// �Է� ���� ���� ó��
					switch (select) {

					// ���� ����
					case "1":
					case "11":
						removeCommand = true;
						command = "���� ����";

						break;

					// ���̾�� ����
					case "2":
					case "22":
						removeCommand = true;
						command = "���̾�� ����";

						break;

					// �񸮾� ����
					case "4":
					case "44":
						removeCommand = true;
						command = "�񸮾� ����";

						break;

					// ��ũ ����
					case "5":
					case "55":
						removeCommand = true;
						command = "��ũ ����";
						
						break;

					// ����â����
					case "q":
					case "qq":
					case "Q":
					case "QQ":
					case "��":
					case "����":

						menuType = 1;
						break;

					// �������� ����
					case "w":
					case "ww":
					case "W":
					case "WW":
					case "��":
					case "����":
						removeCommand = true;
						command = "�������� ����";

						break;

					// �������̾�� ����
					case "e":
					case "ee":
					case "E":
					case "EE":
					case "��":
					case "����":
						removeCommand = true;
						command = "�������̾�� ����";

						break;

					// ������Ʈ ����
					case "r":
					case "rr":
					case "R":
					case "RR":
					case "��":
					case "����":
						removeCommand = true;
						command = "������Ʈ ����";

						break;

					default:
						break;
					}

				}
			select = scanner.next(); // �Է� ������ ���� ���
		}

	}

}
