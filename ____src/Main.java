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


	private static final int MinimumChampions = 18;

	public static void main(String[] args){	
		window = new Window("Randomize!");
		listeners = new Listeners();

		SwingUtilities.invokeLater(new Runnable(){public void run(){
			window.setSize(864, 436);
			window.addKeyListener(listeners);
			window.addFocusListener(listeners);
			window.addWindowListener(listeners);
			window.addMouseWheelListener(listeners);
		}});


		Entries = Entry.ReadFromArray(LoadList());
		RemovedEntries = new ArrayList<Entry>();
		
		SwingUtilities.invokeLater(new Runnable(){public void run(){
			for (int i = 0; i < Entries.size(); i++) {
				window.add(Entries.get(i));
			}
		}});

		window.repaint();
	}




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
	}



	public static void Randomize() { momentum += 150; }
	public static void Update() {
		if (Champions == null) return;
		for (int i = 0; i < Champions.size(); i++)
		Champions.get(i).update();
	}

	public static void Repaint() {
		// Set the locations of visible Champions
		int visible = 18;
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
			
			int y = (int)((half - i + yValue) * Champion.height) + (scr_h / 2) + 0;
			Champions.get(index).y = scr_h - y;
			if (i == half)
				Champions.get(index).selected = true;
			else
				Champions.get(index).selected = false;

			Champions.get(index).affected = true;
		}
		

		// Assign Default Values for those unaffected
		Champion.width = win.getWidth();
		for (int i = 0; i < Champions.size(); i++) {
			if (Champions.get(i).affected) {
				Champions.get(i).affected = false;
				continue;
			}

			Champions.get(i).y = -500;
			Champions.get(i).selected = false;
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
			return Util.GenerateEmptyStringArray(MinimumChampions);
		}

		ArrayList<String> NameList = Util.CharArr_to_StrList(chars);
		NameList = Util.DupStrToMinSize(NameList, MinimumChampions);
		String[] NameArray = Util.StrList_to_StrArr(NameList);

		return NameArray;
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

		Champion last = RemovedChampions.get(RemovedChampions.size() - 1);
		for (int i = 0; i < Champions.size(); i++) {
			if (Champions.get(i).name == last.name) {
				if (Champions.get(i).isClone) {
					win.remove(Champions.get(i));
					RemovedChampions.add(Champions.get(i));
					Champions.remove(i);
					System.out.println("REMOVING: " + i);
					i--;
				}
			}
		}

		if (Champions.size() < MinimumChampions) {
			for (int i = 0; i < Champions.size(); i++) {
				win.remove(Champions.get(i));
			}

			Champions = Util.DupToMinSize(Champions, MinimumChampions);
			System.out.print("\033[H\033[2J");
			System.out.flush();
			for (int i = 0; i < Champions.size(); i++) {
				win.add(Champions.get(i));
			}
		}
		Repaint();

		for (int i = 0; i < Champions.size(); i++) { 
			System.out.println(i + ": " + Champions.get(i).name);
		}
		
		return;
	}
	public static void SetSize(int in) {
		Champion.height = 30 + (in * 30);
	}
	public static void Shuffle() {
		Collections.shuffle(Champions);
		Repaint();
	}
}