package src;

import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Window extends JFrame {
    public Window(String in) { this(in, true); }
    public Window(String in, boolean close) {
        super(in);
        if (close) this.setDefaultCloseOperation(3);

        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void close() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}