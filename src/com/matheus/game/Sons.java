package com.matheus.game;

import java.applet.Applet;
import java.applet.AudioClip;


public class Sons {

	private AudioClip song;
	public static final Sons musica=new Sons("/music/Windless Slopes.wav");
	public static final Sons danoSong=new Sons("/music/hurt.wav");
	public static final Sons danoInimigoSong=new Sons("/music/dano_inimigo.wav");
	public static final Sons bipMenu=new Sons("/music/bip.wav");
	public static final Sons vidaSong=new Sons("/music/vida.wav");
	public static final Sons tiroSong=new Sons("/music/tiro.wav");
	public static final Sons naoPodeSong=new Sons("/music/nao_pode.wav");
	public static final Sons proxFase=new Sons("/music/nextLevel.wav");
	

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
		song.stop();
	}
	
}
