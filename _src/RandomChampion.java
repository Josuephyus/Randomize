import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.JFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class RandomChampion{

	static JFrame win;
	static ArrayList<Champion> Champions;
	static ArrayList<Champion> RemovedChampions;
	static double scroll_value = 0.5;
	static double momentum = 0;

	public static void main(String[] args){
		
		// Setup Window
		win = new JFrame("Random League of Legends Champion!");
		win.setLayout(null);
		win.setDefaultCloseOperation(3);
		win.setBounds(0, 0, 864, 436);
		win.setLocationRelativeTo(null);
		win.getContentPane().setBackground(Schemes.grey3);

		win.addMouseWheelListener(mouL);
		win.addKeyListener(keyL);
		win.addFocusListener(focL);
		win.addWindowListener(winL);

		win.setVisible(true);


		// Setup Champion Panels and add them to the window
		String[] lines = LoadList();
		RemovedChampions = new ArrayList<Champion>();
		Champions = Champion.ConvertStringArray(lines);
		for (int i = 0; i < Champions.size(); i++) {
			win.add(Champions.get(i));
		}


		// Repaint
		Repaint();
	}


	public static WindowAdapter winL = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			SaveList("close.txt");
		}
	};
	public static MouseWheelListener mouL = new MouseWheelListener(){
		public void mouseWheelMoved(MouseWheelEvent e) { momentum += e.getWheelRotation(); }
	};
	public static FocusListener focL = new FocusListener() {
		Thread t;
		boolean run;
		public void focusGained(FocusEvent e) {
			run = true;

			t = new Thread() {
				public void run() { 
					while (run) {
						ReduceMomentum();
						Repaint();
						try { Thread.sleep(10); }
						catch (InterruptedException e) { e.printStackTrace(); }
					}
				}
			}; t.start();
		}

		public void focusLost(FocusEvent e) {
			run = false;
		}
	};
	public static void ReduceMomentum() {
		scroll_value += (momentum * 4.0 / Champion.height);

		if (Champions == null) return;
		if (Champions.size() == 0) return;

		scroll_value %= Champions.size();
		if (scroll_value < 0) scroll_value += Champions.size();

		if (momentum > 1000) momentum = 1000.0;
		else if (momentum > 150) momentum -= 50.0 / 60.0;
		else if (momentum > 55) momentum -= 30.0 / 60.0;
		else if (momentum > 25) momentum -= 10.0 / 60.0;
		else momentum -= 3.0 / 60.0;
		
		
		if (momentum < 0) momentum = 0;
		if (momentum == 0) Champion.moving = false;
		else Champion.moving = true;

		Repaint();
	}


	static final byte SPACE = 32, ENTER = 10, BACKSPACE = 8;
	static final byte UP = 38, DOWN = 40, LEFT = 37, RIGHT = 39;
	static final byte ALPHABET_START = 64, ALPHABET_END = 91;
	static final byte ONE = 49, TWO = 50, THREE = 51, FOUR = 52;
	public static KeyListener keyL = new KeyListener(){
		public void keyReleased(KeyEvent e) {
			byte code = (byte)(e.getKeyCode());

			if (code == SPACE) Randomize();
			if (code > ALPHABET_START && code < ALPHABET_END) Search(code);
			if (code == UP) OneUp();
			if (code == DOWN) OneDown();
			if (code == LEFT) Undo();
			if (code == RIGHT) SaveList("export.txt");
			if (code == ENTER) Refresh();
			if (code == BACKSPACE) RemoveSelected();
			
			if (code == ONE) SetSize(1);
			if (code == TWO) SetSize(2);
			if (code == THREE) SetSize(3);
			if (code == FOUR) SetSize(4);


			// IF (Tab) Save
			if (code > 48 && code < 53) {
				int num = (code - 48) + 1;
				Champion.height = num * 30;
				return;
			}
		}
		public void keyPressed(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};


	public static void Randomize() { momentum += 150; }

	public static void Repaint() {
		// Set the locations of visible Champions
		int visible = 10;
		int half = (visible / 2) - 1;
		int scr_h = win.getHeight();
		int yRound = (int)Math.round(scroll_value - 0.5f);
		double yValue = scroll_value - yRound;

		if (Champions == null) return;
		if (Champions.size() == 0) return;

		for (int i = 0; i < visible; i++){
			int index = yRound + i - half;

			if (index < 0) index += Champions.size();
			if (index > Champions.size() - 1) index %=  Champions.size();
			
			int y = (int)((half - i + yValue) * Champion.height) + (scr_h / 2) + 10;
			Champions.get(index).y = scr_h - y;
			if (i == half) {
				Champions.get(index).selected = true;
			} else {
				Champions.get(index).selected = false;
			}

			Champions.get(index).affected = true;
		}
		

		// Assign Default Values for those unaffected
		Champion.width = win.getWidth();
		for (int i = 0; i < Champions.size(); i++) {
			if (Champions.get(i).affected) {
				Champions.get(i).affected = false;
				Champions.get(i).update();
				continue;
			}

			Champions.get(i).y = -500;
			Champions.get(i).selected = false;
			Champions.get(i).update();
		}

		// Repaint the window
		win.repaint();
	}


	// Convert the file "list.txt" into a String[]
	// based on the newline character (aka: (char)10)
	// TODO: Make it accept (CRLF)
	public static String[] LoadList(){

		// Convert the File into a char[]
		char[] chars;
		try {
			File ListFile = new File("open.txt");

			chars = new char[(int)ListFile.length()];
			FileReader ListReader = new FileReader(ListFile);
			ListReader.read(chars);
			ListReader.close();
		} catch (IOException e) {
			System.out.println("Failed to read: 'open.txt'");

			String[] failure = new String[]{
				"None", "None", "None", "None", "None",
				"None", "None", "None", "None", "None"
			};
			return failure;
		}


		// Convert the char[] into a ArrayList<String> based on
		// 'newline' characters
		char newline = (char)10;
		String line = "";
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == newline) {
				list.add(line);
				line = new String();
			} else {
				line += chars[i];
			}
		}


		// Extend the list to at least 10;
		if (list.size() < 10) {
			ArrayList<String> oldList = list;
			list = new ArrayList<String>();
			for (int i = 0; i < list.size() * 10; i++) {
				int index = i % list.size();
				list.add(oldList.get(index));
			}
		}


		// Convert the ArrayList<String> to String[]
		String[] arr = new String[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i);
		}


		// Return the String[]
		return arr;
	}

	public static void SaveList(String in) {
		
		if (Champions == null) return;
		if (Champions.size() == 0) return;

		File file = new File(in);
		try {
			FileWriter writer = new FileWriter(file);
			for (int i = 0; i < Champions.size(); i++) {
				writer.write(Champions.get(i).name);
				writer.write(", ");
				writer.write(Champions.get(i).title);
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void Search(byte in) {
		for (int i = 0; i < Champions.size(); i++) {
			Champion tempChamp = Champions.get(i);
			if (tempChamp.name.getBytes()[0] >= in) {
				scroll_value = i;
				break;
			}
		}
		return;
	}

	public static void OneUp() { scroll_value -= 1; }
	public static void OneDown() { scroll_value += 1; }
	public static void Undo() {
		if (RemovedChampions.size() == 0) return;

		Champion tempChamp = RemovedChampions.get(RemovedChampions.size() - 1);
		for (int i = 0; i < Champions.size(); i++) {
			String name = Champions.get(i).name;
			if (name.compareTo(tempChamp.name) > 0) {
				Champions.add(i, tempChamp);
				RemovedChampions.remove(RemovedChampions.size() - 1);
				win.add(tempChamp);
				return;
			}
		}
		Champions.add(tempChamp);
		win.add(tempChamp);
		return;
	}
	public static void Refresh() {
		for (int i = 0; i < Champions.size(); i++) {
			win.remove(Champions.get(i));
		}
		String[] lines = LoadList();
		Champions = Champion.ConvertStringArray(lines);
		for (int i = 0; i < Champions.size(); i++) {
			win.add(Champions.get(i));
		}
		return;
	}
	public static void RemoveSelected() {
		win.remove(Champions.get((int)scroll_value));
		RemovedChampions.add(Champions.get((int)scroll_value));
		Champions.remove((int)scroll_value);
		return;
	}
	public static void SetSize(int in) {
		Champion.height = 30 + (in * 30);
		for (int i = 0; i < Champions.size(); i++) {
			Champions.get(i).Rescale();
		}
		for (int i = 0; i < RemovedChampions.size(); i++) {
			Champions.get(i).Rescale();
		}
	}
}