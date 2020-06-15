package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

//사운드를 출력해주는 클래스. 
//Music(String name, boolean isLoop) : name = "mp3파일명.확장자" if(isLoop = true) 반복 실행 / if(isLoop = false) 한번 실행
public class Music extends Thread {
	Player player;
	boolean isLoop;
	File file;
	FileInputStream fis;
	BufferedInputStream bis;

	public Music(String name, boolean isLoop) {
		try {
			this.isLoop = isLoop;
			// 파일의 경로 "../music/" +
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