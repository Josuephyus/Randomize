import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class Listeners {
    
    
    public static class WindowL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            RandomChampion.SaveList("close.txt");
        }
    }


    public static class MouseL implements MouseWheelListener {
        public void mouseWheelMoved(MouseWheelEvent e) {
            RandomChampion.AddMomentum(e.getWheelRotation());
        }
    }


    public static class FocusL implements FocusListener {
        Loop loop;
        public void focusGained(FocusEvent e) {
            loop = new Loop();
            loop.start();
        }
        public void focusLost(FocusEvent e) {
            loop.end();
        }

        public static class Loop extends Thread {
            private boolean active = true;
            public void end() { active = false;}

            public void run() {
                super.run();
                active = true;
                while (active) {
                    RandomChampion.ReduceMomentum();
                    RandomChampion.Repaint();
                    RandomChampion.Update();
                    
                    try { Thread.sleep(10); }
                    catch (Exception e) { e.printStackTrace(); }
                }
            }
        }
    }


    static final byte SPACE = 32, ENTER = 10, BACKSPACE = 8;
	static final byte UP = 38, DOWN = 40, LEFT = 37, RIGHT = 39;
	static final byte ALPHABET_START = 64, ALPHABET_END = 91;
	static final byte ONE = 49, TWO = 50, THREE = 51, FOUR = 52;
    static final byte TILDE = (byte)KeyEvent.VK_BACK_QUOTE;
	public static class KeyL implements KeyListener {
		public void keyReleased(KeyEvent e) {
			byte code = (byte)(e.getKeyCode());

			if (code == SPACE) RandomChampion.Randomize();
			if (code > ALPHABET_START && code < ALPHABET_END) RandomChampion.Search(code);
			if (code == UP) RandomChampion.OneUp();
			if (code == DOWN) RandomChampion.OneDown();
			if (code == LEFT) RandomChampion.Undo();
			if (code == RIGHT) RandomChampion.SaveList("export.txt");
			if (code == ENTER) RandomChampion.Refresh();
			if (code == BACKSPACE) RandomChampion.RemoveSelected();
			
			if (code == ONE) RandomChampion.SetSize(1);
			if (code == TWO) RandomChampion.SetSize(2);
			if (code == THREE) RandomChampion.SetSize(3);
			if (code == FOUR) RandomChampion.SetSize(4);
            if (code == TILDE) RandomChampion.Shuffle();
		}
		public void keyPressed(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};
}