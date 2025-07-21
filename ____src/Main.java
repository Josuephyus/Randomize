package src;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

	private static Window window;
	private static Display display;
	private static Listeners listeners;

	public static void main(String[] args){	
		Schemes.Reset();
		display = new Display();
		listeners = new Listeners();

		SwingUtilities.invokeLater(new Runnable(){public void run(){
			window = new Window("Randomize!");
			window.setSize(864, 436);
			window.addKeyListener(listeners);
			window.addFocusListener(listeners);
			window.addWindowListener(listeners);
			window.addMouseWheelListener(listeners);
			window.add(display);
			window.repaint();
		}});
	}


	public static boolean rolling() {
		return display.moving;
	}
	public static void AddMomentum(int in) {
		display.addMomentum(in);
	}
	public static void ReduceMomentum(float delta) {
		display.reduceMomentum(delta);
	}
	public static void Randomize() {
		display.randomize();
	}
	public static void Repaint() {
		window.repaint();
	}
	public static void OpenList() {
		display.openList();
	}
	public static void SaveList(String in) {
		display.saveList(in);
	}
	public static void Search(byte in) {
		display.search(in);
	}
	public static void OneUp() {
		display.move(-1);
	}
	public static void OneDown() {
		display.move(1);
	}
	public static void Undo() {
		display.undo();
	}
	public static void Refresh() {
		Schemes.Reset();
		display.openList();
	}
	public static void RemoveSelected() {
		display.removeSelected();
	}
	public static void SetSize(int in) {
		display.setSize(in);
	}
	public static void Shuffle() {
		display.shuffle();
	}
}