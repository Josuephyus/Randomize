package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class Listeners extends WindowAdapter implements MouseWheelListener, FocusListener, KeyListener{

    // Window Adapter
    public void windowClosing(WindowEvent e) {
        Main.SaveList("close.txt");
    }


    // Mouse Wheel Adapter
    public void mouseWheelMoved(MouseWheelEvent e) {
        Main.AddMomentum(e.getWheelRotation());
    }


    // Focus Listener
    Loop loop;
    public void focusGained(FocusEvent e) { loop = new Loop(); loop.start(); }
    public void focusLost(FocusEvent e) { loop.end(); }
    public static class Loop extends Thread {
        private boolean active = true;
        public void end() { active = false;}

        public void run() {
            long start = System.currentTimeMillis();
            super.run();
            active = true;
            while (active) {
                long end = System.currentTimeMillis();
                long delta = end - start;
                start = end;
                Main.ReduceMomentum((float)delta / 1000.0f);
                Main.Repaint();
                
                
                try { Thread.sleep(10); }
                catch (Exception e) { e.printStackTrace(); }
            }
        }
    }


    // Key Listener
    private static final byte SPACE = 32, ENTER = 10, BACKSPACE = 8;
	private static final byte UP = 38, DOWN = 40, LEFT = 37, RIGHT = 39;
	private static final byte ALPHABET_START = 64, ALPHABET_END = 91;
	private static final byte ONE = 49, TWO = 50, THREE = 51, FOUR = 52;
    private static final byte TILDE = (byte)KeyEvent.VK_BACK_QUOTE;
    public void keyReleased(KeyEvent e) {
        byte code = (byte)(e.getKeyCode());

        if (code == SPACE) Main.Randomize();
        if (code > ALPHABET_START && code < ALPHABET_END) Main.Search(code);
        if (code == UP) Main.OneUp();
        if (code == DOWN) Main.OneDown();
        if (code == LEFT) Main.Undo();
        if (code == RIGHT) Main.SaveList("export.txt");
        if (code == ENTER) Main.Refresh();
        if (code == BACKSPACE) Main.RemoveSelected();
        
        if (code == ONE) Main.SetSize(1);
        if (code == TWO) Main.SetSize(2);
        if (code == THREE) Main.SetSize(3);
        if (code == FOUR) Main.SetSize(4);
        if (code == TILDE) Main.Shuffle();
    }
    public void keyPressed(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}