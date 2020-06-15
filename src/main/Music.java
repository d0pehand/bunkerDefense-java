package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

//���带 ������ִ� Ŭ����. 
//Music(String name, boolean isLoop) : name = "mp3���ϸ�.Ȯ����" if(isLoop = true) �ݺ� ���� / if(isLoop = false) �ѹ� ����
public class Music extends Thread {
	Player player;
	boolean isLoop;
	File file;
	FileInputStream fis;
	BufferedInputStream bis;

	public Music(String name, boolean isLoop) {
		try {
			this.isLoop = isLoop;
			// ������ ��� "../music/" +
			file = new File(Main.class.getResource("../music/" + name).toURI());
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);

		} catch (Exception e) {

		}
	}

	public void close() {
		isLoop = false;
		player.close();
		this.interrupt();
	}

	@Override
	public void run() {
		do {
			try {
				player.play();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);

			} catch (Exception e) {
			}
		} while (isLoop);

	}

}