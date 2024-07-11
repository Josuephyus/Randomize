import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class RandomChampion{

	static Champion[] championValues;

	static JFrame win = new JFrame();
	static JLayeredPane scr = new JLayeredPane();

	static Boolean done;

	static Double yLevel = 0.5;

	public static void main(String[] args){
		String[] champions = loadChampions();
		
		win.setUndecorated(true);
		win.setDefaultCloseOperation(3);
		win.setLayout(null);
		scr.setBounds(0,0,850,400);
		scr.setBackground(Schemes.grey3);
		scr.setLayout(null);
		JPanel champs = new JPanel();
		champs.setBounds(0,0,scr.getWidth(),scr.getHeight());
		champs.setLayout(null);
		win.add(scr);

		championValues = new Champion[champions.length];
		for (int i = 0; i < champions.length; i++){
			if (champions[i].split(", ").length > 1){
				championValues[i] = new Champion(champions[i].split(", ")[0], champions[i].split(", ")[1]);
			} else {
				championValues[i] = new Champion(champions[i]);
			}
			championValues[i].display.setBounds(-999, -999, Champion.width, Champion.height);
			champs.add(championValues[i].display, 0);
		} scr.add(champs, 0);
		
		scroll(0);

		win.pack();
		win.setSize(scr.getWidth(),scr.getHeight());
		win.setLocationRelativeTo(null);
		win.addMouseWheelListener(new MouseWheelListener(){
			public void mouseWheelMoved(MouseWheelEvent e){
				RandomChampion.scroll(e.getWheelRotation());
			}
		});
		win.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != 32)return;
				Thread t = new Thread(new Runnable(){
					public void run(){
						try {
							for (int i = 0; i < new Random().nextInt(championValues.length); i++){
								scroll(150);
								Thread.sleep(16);
							}
							for (int i = 150; i >55; i -= 2){
								scroll(i);
								Thread.sleep(16);
							}
							for (int i = 55; i > 10; i--){
								scroll(i);
								Thread.sleep(16);
								scroll(i);
								Thread.sleep(16);
							}
							for (int i = 10; i > 0; i--){
								for (int o = 0; o < ((10 - i) * 2) + 3; o++){
									scroll(i);
									Thread.sleep(16);
								}
							} scroll(0);
							return;
						} catch (InterruptedException e){

						}
					}
				}); t.start(); System.out.println("Randomizing!");
			}
			public void keyPressed(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		win.setVisible(true);
	}

	public static void scroll(int howMuch){
		double move;
		
		if (howMuch > 150) howMuch = 150;
		if (howMuch < -150) howMuch = -150;

		move = ((double)howMuch  * 4)/ Champion.height;

		yLevel += move;
		if (yLevel >= championValues.length)yLevel = 0.0 + Math.abs(yLevel - championValues.length);
		if (yLevel < 0)yLevel = (double)championValues.length - Math.abs(yLevel);

		Integer BufferRange = 18;
		for (int i = 0; i < BufferRange; i++){
			Integer index = (int)Math.round(yLevel - 0.5) + i - (BufferRange / 2);
			if (index < 0){index += championValues.length;}
			if (index > championValues.length - 1){index -= championValues.length;}
			championValues[index].display.setBounds(0, (int)(200 - (120 * (yLevel - Math.round(yLevel - 0.5) + (BufferRange/2) - i))), Champion.width, Champion.height);
			if (i == BufferRange/2){
				championValues[index].display.setBorder(BorderFactory.createLineBorder(((move != 0) ? Schemes.gold2 : Schemes.blue2), 3));
			} else {
				championValues[index].display.setBorder(BorderFactory.createLineBorder(Schemes.seeThrough));
			}
		}
		scr.repaint();
	}

	public static String[] loadChampions(){
		File ChampionListTxt = new File("thisList.txt");
		try {
			FileReader ChampionListReader = new FileReader(ChampionListTxt);
			char[] characters = new char[(int)ChampionListTxt.length()];
			ChampionListReader.read(characters);
			ChampionListReader.close();

			String bigString = "";
			for (int i = 0; i < characters.length; i++){
				if (characters[i] != 0){
					bigString += ("" + characters[i]);
				} else {
					i = characters.length;
				}
			}

			String[] smallStrings = bigString.split(((char)13 + "" + (char)10));
			return smallStrings;

		} catch (IOException e){}
		return new String[]{};
	}
}