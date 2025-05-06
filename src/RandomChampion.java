import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.JFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class RandomChampion{

	static Champion[] championValues;

	static JFrame win;
	static double yLevel = 0.5;

	public static void main(String[] args){
		
		win = new JFrame("Random League of Legends Champion!");
		win.setLayout(null);
		win.setDefaultCloseOperation(3);
		win.setBounds(0, 0, 864, 436);
		win.setLocationRelativeTo(null);
		win.getContentPane().setBackground(Schemes.grey3);


		String[] champions = loadChampions();
		championValues = new Champion[champions.length];
		for (int i = 0; i < champions.length; i++) {
			String name = champions[i];
			String title = "...";

			if (champions[i].split(", ").length > 1) {
				name = champions[i].split(", ")[0];
				title = champions[i].split(", ")[1];
			}
			
			championValues[i] = new Champion(name, title);
			win.add(championValues[i]);
		}
		
		win.addMouseWheelListener(mouL);
		win.addKeyListener(keyL);

		Scroll(0);
		win.setVisible(true);
	}


	public static MouseWheelListener mouL = new MouseWheelListener(){
		public void mouseWheelMoved(MouseWheelEvent e) {
			Scroll(e.getWheelRotation());
		}
	};

	public static KeyListener keyL = new KeyListener(){
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() != 32) {
				if (e.getKeyCode() > 64 && e.getKeyCode() < 91) {
					byte code = (byte)(e.getKeyCode());
					for (int i = 0; i < championValues.length; i++) {
						if (championValues[i].name.getBytes()[0] >= code) {
							yLevel = i;
							Scroll(0);
							break;
						}
					}
				}
				return;
			}

			Thread t = new Thread(Scroll_On_Space);
			t.start();
			System.out.println("Randomizing!");
		}
		public void keyPressed(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};
	public static Runnable Scroll_On_Space = new Runnable(){
		public void run(){
			try {
				int sleep = 8;
				AutoScroll(149, new Random().nextInt(championValues.length) * 2, sleep);
				AutoScroll_Decrease(150 * 2, 55, -2, sleep);
				AutoScroll_Decrease(55 * 2, 10, -1, sleep);
				AutoScroll_Slow(sleep);
				Scroll(0);
				return;
			} catch (InterruptedException e){}
		}
	};

	public static void AutoScroll(int howMuch, int count, int sleep) throws InterruptedException {
		for (int i = 0; i < count; i++) {
			Scroll(howMuch);
			Thread.sleep(sleep);
		}
	}
	public static void AutoScroll_Decrease(int start, int val, int change, int sleep) throws InterruptedException {
		for (int i = start; i > val; i += change){
			Scroll(i);
			Thread.sleep(sleep);
		}
	}
	public static void AutoScroll_Slow(int sleep) throws InterruptedException {
		for (int i = 10; i > 0; i--){
			for (int o = 0; o < ((10 - i) * 2) + 3; o++){
				Scroll(i);
				Thread.sleep(sleep);
			}
		}
	}
	public static void Scroll(int howMuch){


		// Check if movement
		if (howMuch == 0)
			Champion.moving = false;
		else
			Champion.moving = true;


		// Limit Momentum
		double move = (double)howMuch;
		if (move > 299) move = 299;
		if (move < -299) move = -299;


		// Apply Momentum
		move *= 4.0f / Champion.height;
		yLevel += move;


		// Flow into the next direction
		if (yLevel >= championValues.length)
			yLevel = 0.0 + Math.abs(yLevel - championValues.length);
		if (yLevel < 0)
			yLevel = (double)championValues.length - Math.abs(yLevel);


		// Set the locations of visible Champions
		int visible = 10;
		int half = (visible / 2) - 1;
		int scr_h = win.getHeight();
		int yRound = (int)Math.round(yLevel - 0.5f);
		double yValue = yLevel - yRound;
		for (int i = 0; i < visible; i++){
			int index = yRound + i - half;

	
			if (index < 0)
				index += championValues.length;
			if (index > championValues.length - 1)
				index -= championValues.length;
			
			int y = (int)((half - i + yValue) * 120) + (scr_h / 2) + 10;
			championValues[index].y = scr_h - y;
			if (i == half) {
				championValues[index].selected = true;
			} else {
				championValues[index].selected = false;
			}

			championValues[index].affected = true;
		}
		

		// Assign Default Values for those unaffected
		Champion.width = win.getWidth();
		for (int i = 0; i < championValues.length; i++) {
			if (championValues[i].affected) {
				championValues[i].affected = false;
				championValues[i].update();
				continue;
			}

			championValues[i].y = -500;
			championValues[i].selected = false;
			championValues[i].update();
		}


		// Repaint the window
		win.repaint();
	}


	// Convert the 'thisList.txt' into the list of chosen champions
	public static String[] loadChampions(){
		File ChampionListTxt = new File("thisList.txt");
		try {
			FileReader ChampionListReader = new FileReader(ChampionListTxt);
			char[] characters = new char[(int)ChampionListTxt.length()];
			ChampionListReader.read(characters);
			ChampionListReader.close();


			ArrayList<String> list = new ArrayList<String>();
			String line = "";
			for (int i = 0; i < characters.length; i++) {
				if (characters[i] == (char)10) {
					list.add(line);
					line = new String();
				} else {
					line += characters[i];
				}
			}
			System.out.println("COUNT: " + list.size());

			
			String[] arr = new String[list.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = list.get(i);
			}
			
			return arr;

		} catch (IOException e){
			System.out.println("ERR: "); e.printStackTrace();
		}
		
		return new String[]{};
	}
}