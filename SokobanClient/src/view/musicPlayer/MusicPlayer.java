package view.musicPlayer;

import java.io.File;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

public class MusicPlayer implements MediaPlayer {

	private AudioClip player;
	private volatile boolean stopMusic;
	@Override
	public void play(String filePath) {
		File soundtrack = new File(filePath);
		if (soundtrack.exists()) {
			Media media = new Media(soundtrack.toURI().toString());
			player = new AudioClip(media.getSource());
			Thread musicThread = new Thread(new Runnable() {

				@Override
				public void run() {
					while (!stopMusic) {
						if (!player.isPlaying())
							player.play();
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			});
			
			musicThread.start();
		
	}
		
	
	}
	
	public void stop(){
		
		stopMusic = true;
		player.stop();
		
		
	}
	
	
	

}
