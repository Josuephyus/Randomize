package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

	private static Window window;
	private static Listeners listeners;
	private static ArrayList<Entry> Entries;
	private static ArrayList<Entry> RemovedEntries;

	static double scroll_value = 0.5;

	static double momentum = 0;
	public static void AddMomentum(double in) { momentum += in; }


	private static final int MinimumEntries = 18;

	public static void main(String[] args){	
		listeners = new Listeners();

		SwingUtilities.invokeLater(new Runnable(){public void run(){
			window = new Window("Randomize");
			window.add(display);
			window.setSize(864, 436);
			window.addKeyListener(listeners);
			window.addFocusListener(listeners);
			window.addWindowListener(listeners);
			window.addMouseWheelListener(listeners);
			window.repaint();
		}});

		display = new Display();

		SwingUtilities.invokeLater(new Runnable(){public void run(){
			window.add(display);
		}});
	}


	public static void ReduceMomentum() {
		scroll_value += (momentum / 20.0f);

		if (Entries == null) return;
		if (Entries.size() == 0) return;

		scroll_value %= Entries.size();
		if (scroll_value < 0) scroll_value += Entries.size();

		if (momentum > 1000) momentum = 1000.0;
		else if (momentum > 150) momentum -= 50.0 / 60.0;
		else if (momentum > 55) momentum -= 30.0 / 60.0;
		else if (momentum > 25) momentum -= 10.0 / 60.0;
		else momentum -= 3.0 / 60.0;
		
		
		if (momentum < 0) momentum = 0;
		if (momentum == 0) Entries.moving = false;
		else Entries.moving = true;
	}



	public static void Randomize() { momentum += 150; }
	public static void Update() {
		if (Entries == null) return;
		for (int i = 0; i < Entries.size(); i++)
		Entries.get(i).update();
	}

	public static void Repaint() {
		int visible = 18;
		int half = (visible / 2) - 1;
		int scr_h = win.getHeight();
		int yRound = (int)Math.round(scroll_value - 0.5f);
		double yValue = scroll_value - yRound;

		if (Entries == null) return;
		if (Entries.size() == 0) return;

		for (int i = 0; i < visible; i++){
			int index = yRound + i - half;

			if (index < 0) index += Entries.size();
			if (index > Entries.size() - 1) index %= Entries.size();
			
			int y = (int)((half - i + yValue) * 80) + (scr_h / 2) + 0;
			Entries.get(index).y = scr_h - y;
			if (i == half)
				Entries.get(index).selected = true;
			else
				Entries.get(index).selected = false;

			Entries.get(index).affected = true;
		}
		

		// Assign Default Values for those unaffected
		Champion.width = win.getWidth();
		for (int i = 0; i < Entries.size(); i++) {
			if (Entries.get(i).affected) {
				Entries.get(i).affected = false;
				continue;
			}

			Entries.get(i).y = -500;
			Entries.get(i).selected = false;
		}

		// Repaint the window
		win.repaint();
	}


	// Convert the file "list.txt" into a String[]
	// based on the newline character (aka: (char)10)
	// TODO: Make it accept (CRLF)
	public static String[] LoadList(){

		char[] chars = Util.ReadFile("open.txt");
		if (chars == null) {
			return Util.GenerateEmptyStringArray(MinimumEntries);
		}

		ArrayList<String> NameList = Util.CharArr_to_StrList(chars);
		NameList = Util.DupStrToMinSize(NameList, MinimumEntries);
		String[] NameArray = Util.StrList_to_StrArr(NameList);

		return NameArray;
	}

	public static void SaveList(String in) {
		
		if (Entries == null) return;
		if (Entries.size() == 0) return;

		File file = new File(in);
		try {
			FileWriter writer = new FileWriter(file);
			for (int i = 0; i < Entries.size(); i++) {
				writer.write(Entries.get(i).name);
				writer.write(", ");
				writer.write(Entries.get(i).title);
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void Search(byte in) {
		for (int i = 0; i < Entries.size(); i++) {
			Champion tempChamp = Entries.get(i);
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
		if (RemovedEntries.size() == 0) return;

		Champion tempChamp = RemovedEntries.get(RemovedEntries.size() - 1);
		for (int i = 0; i < Entries.size(); i++) {
			String name = Entries.get(i).name;
			if (name.compareTo(tempChamp.name) > 0) {
				Entries.add(i, tempChamp);
				RemovedEntries.remove(RemovedEntries.size() - 1);
				win.add(tempChamp);
				return;
			}
		}
		Entries.add(tempChamp);
		win.add(tempChamp);
		return;
	}
	public static void Refresh() {
		for (int i = 0; i < Entries.size(); i++) {
			win.remove(Entries.get(i));
		}
		String[] lines = LoadList();
		Entries = Champion.ConvertStringArray(lines);
		for (int i = 0; i < Entries.size(); i++) {
			win.add(Entries.get(i));
		}
		return;
	}
	public static void RemoveSelected() {
		win.remove(Entries.get((int)scroll_value));
		RemovedEntries.add(Entries.get((int)scroll_value));
		Entries.remove((int)scroll_value);

		Champion last = RemovedEntries.get(RemovedEntries.size() - 1);
		for (int i = 0; i < Entries.size(); i++) {
			if (Entries.get(i).name == last.name) {
				if (Entries.get(i).isClone) {
					win.remove(Entries.get(i));
					RemovedEntries.add(Entries.get(i));
					Entries.remove(i);
					System.out.println("REMOVING: " + i);
					i--;
				}
			}
		}

		if (Entries.size() < MinimumEntries) {
			for (int i = 0; i < Entries.size(); i++) {
				win.remove(Entries.get(i));
			}

			Entries = Util.DupToMinSize(Entries, MinimumEntries);
			System.out.print("\033[H\033[2J");
			System.out.flush();
			for (int i = 0; i < Entries.size(); i++) {
				win.add(Entries.get(i));
			}
		}
		Repaint();

		for (int i = 0; i < Entries.size(); i++) { 
			System.out.println(i + ": " + Entries.get(i).name);
		}
		
		return;
	}
	public static void SetSize(int in) { Entry.setSize(in); }
	public static void Shuffle() {
		Collections.shuffle(Entries);
		Repaint();
	}
}