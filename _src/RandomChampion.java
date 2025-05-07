import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.JFrame;


public class RandomChampion{

	static JFrame win;
	static ArrayList<Champion> Champions;
	static ArrayList<Champion> RemovedChampions;
	static double scroll_value = 0.5;

	static double momentum = 0;
	public static void AddMomentum(double in) { momentum += in; }


	private static final int MinimumChampions = 18;

	public static void main(String[] args){
		SetupWindow();
		SetupChampions();
		AddChampionsToWindow();
		Repaint();
	}



	private static void SetupWindow() {
		win = new JFrame("Random League of Legends Champion!");
		win.setLayout(null);
		win.setDefaultCloseOperation(3);
		win.setBounds(0, 0, 864, 436);
		win.setLocationRelativeTo(null);
		win.getContentPane().setBackground(Schemes.grey3);

		win.addMouseWheelListener(new Listeners.MouseL());
		win.addKeyListener(new Listeners.KeyL());
		win.addFocusListener(new Listeners.FocusL());
		win.addWindowListener(new Listeners.WindowL());

		win.setVisible(true);
	}
	private static void SetupChampions() {
		String[] lines = LoadList();

		Champions = new ArrayList<Champion>();
		RemovedChampions = new ArrayList<Champion>();

		Champions = Champion.ConvertStringArray(lines);
	}
	private static void AddChampionsToWindow() {
		for (int i = 0; i < Champions.size(); i++) {
			win.add(Champions.get(i));
		}
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
		NameList = Util.<String>DupToMinSize(NameList, MinimumChampions);
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

		if (Champions.size() < MinimumChampions) {
			for (int i = 0; i < Champions.size(); i++) {
				win.remove(Champions.get(i));
			}

			Champions = Util.<Champion>DupToMinSize(Champions, MinimumChampions);
		}
		return;
	}
	public static void SetSize(int in) {
		Champion.height = 30 + (in * 30);
	}
}