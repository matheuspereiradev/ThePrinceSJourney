package com.matheus.game;

import java.applet.Applet;
import java.applet.AudioClip;


public class Sons {

	private AudioClip song;
	public static final Sons musica=new Sons("/Windless Slopes.wav");
	public static final Sons danoSong=new Sons("/hurt.wav");
	public static final Sons danoInimigoSong=new Sons("/dano_inimigo.wav");
	public static final Sons bipMenu=new Sons("/bip.wav");
	public static final Sons vidaSong=new Sons("/vida.wav");
	public static final Sons tiroSong=new Sons("/tiro.wav");
	public static final Sons naoPodeSong=new Sons("/nao_pode.wav");
	public static final Sons proxFase=new Sons("/nextLevel.wav");
	

	public Sons(String path) {
		try {
			song = Applet.newAudioClip(getClass().getResource(path));
		} catch (Throwable e) {
			System.out.println(e);
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					song.play();
				}
			}.start();
		} catch (Throwable e) {
			System.out.println(e);
		}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					song.loop();
				}
			}.start();
		} catch (Throwable e) {
			System.out.println(e);
		}
	}
	
	public void stop() {
		try {
			new Thread() {
				public void run() {
					song.stop();;
				}
			}.start();
		} catch (Throwable e) {
			System.out.println(e);
		}
	}
	
}
