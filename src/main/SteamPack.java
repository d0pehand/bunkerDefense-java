package main;

//�÷��̾ T.������ Ű�� �������� ����Ǵ� ������
public class SteamPack extends Thread {

	Hero hero;

	public SteamPack(Hero hero) {
		this.hero = hero;
	}

	public void run() {

		hero.steamPackNow = true; // ���� ������ �������� �Ǵ�

		for (int i = 0; i < 8; i++) { // ������ ȿ�� ����
			if ((hero.unit[i] != null) && (hero.unit[i].name == "����" || hero.unit[i].name == "��������"
					|| hero.unit[i].name == "���̾��" || hero.unit[i].name == "�������̾��")) {
				hero.unit[i].attack = 20;
				hero.unit[i].plusAttack = 4;
			}
		}

		try {
			Thread.sleep(10000); // ������ ���ӽð� = 10��
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		hero.steamPackNow = false;

		for (int i = 0; i < 8; i++) { // ������ ȿ�� Ǯ��
			if ((hero.unit[i] != null) && (hero.unit[i].name == "����" || hero.unit[i].name == "��������")) {
				hero.unit[i].attack = 10;
				hero.unit[i].plusAttack = 2;
			}
		}

	}
}
