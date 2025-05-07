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
	static Champion[] Champions;
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
		Champions = Champion.ConvertStringArray(lines);
		for (int i = 0; i < Champions.length; i++) {
			win.add(Champions[i]);
		}


		// Repaint
		Repaint();
	}


	public static WindowAdapter winL = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			SaveList();
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

		if (Champions == null) { return; }
		scroll_value %= Champions.length;
		if (scroll_value < 0) scroll_value += Champions.length;

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

	public static KeyListener keyL = new KeyListener(){
		public void keyReleased(KeyEvent e) {
			byte code = (byte)(e.getKeyCode());


			// IF (Space) Randomize
			if (code == 32) {
				momentum += 150;
				return;
			}


			// IF (Letter) Search that letter
			if (code > 64 && code < 91) {
				for (int i = 0; i < Champions.length; i++) {
					if (Champions[i].name.getBytes()[0] >= code) {
						scroll_value = i;
						break;
					}
				}
				return;
			}


			// IF (Arrow) Move
			if (code == 38) { // UP
				scroll_value -= 1;
				return;
			}
			if (code == 40) { // DOWN
				scroll_value += 1;
				return;
			}
			// Left: 37
			// Right: 39

			
			// IF (Enter) Refresh list
			if (code == 10) {
				for (int i = 0; i < Champions.length; i++) {
					win.remove(Champions[i]);
				}
				String[] lines = LoadList();
				Champions = Champion.ConvertStringArray(lines);
				for (int i = 0; i < Champions.length; i++) {
					win.add(Champions[i]);
				}
			}
		}
		public void keyPressed(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};


	public static void Repaint() {
		// Set the locations of visible Champions
		int visible = 10;
		int half = (visible / 2) - 1;
		int scr_h = win.getHeight();
		int yRound = (int)Math.round(scroll_value - 0.5f);
		double yValue = scroll_value - yRound;

		if (Champions == null) return;
		for (int i = 0; i < visible; i++){
			int index = yRound + i - half;

			if (index < 0) index += Champions.length;
			if (index > Champions.length - 1) index %=  Champions.length;
			
			int y = (int)((half - i + yValue) * 120) + (scr_h / 2) + 10;
			Champions[index].y = scr_h - y;
			if (i == half) {
				Champions[index].selected = true;
			} else {
				Champions[index].selected = false;
			}

			Champions[index].affected = true;
		}
		

		// Assign Default Values for those unaffected
		Champion.width = win.getWidth();
		for (int i = 0; i < Champions.length; i++) {
			if (Champions[i].affected) {
				Champions[i].affected = false;
				Champions[i].update();
				continue;
			}

			Champions[i].y = -500;
			Champions[i].selected = false;
			Champions[i].update();
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

	public static void SaveList() {
		
		File closeFile = new File("close.txt");

		try {
			FileWriter closeWriter = new FileWriter(closeFile);
			for (int i = 0; i < Champions.length; i++) {
				closeWriter.write(Champions[i].name);
				closeWriter.write(", ");
				closeWriter.write(Champions[i].title);
				closeWriter.write("\n");
			}
			closeWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}