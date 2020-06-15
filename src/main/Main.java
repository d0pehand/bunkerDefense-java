package main;

public class Main {

	//���α׷��� ���� ���� ��
	public static void main(String[] args) {

		Hero hero = new Hero(); // ���ΰ��� ���� ����
		InputKey inputKey = new InputKey(hero); // Ű���� �Է��� ���
		Action action = new Action(hero); // �� ����, �̵�, ������ ���
		Print print = new Print(hero, action); // ȭ������� ���

		print.start(); // ���� ȭ��

		// ������ ����
		hero.setDaemon(true);
		inputKey.setDaemon(true);
		action.setDaemon(true);

		hero.start();
		inputKey.start();
		action.start();

		// ����� ����
		Music bgmmusic;
		bgmmusic = new Music("bgm.mp3", true);
		bgmmusic.setDaemon(true);
		bgmmusic.start();

		// ���� ��..
		while (hero.hp > 0 && !action.killBoss) {
			print.draw();
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		action.isStop = true;
		bgmmusic.close(); // ����� ����
		print.end(action.killBoss); // ���� ȭ��

	}
}
