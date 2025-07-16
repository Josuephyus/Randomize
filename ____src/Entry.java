package src;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Font;

import java.util.ArrayList;

public class Entry {

    private static final int STATE_1_HEIGHT = 50;
    private static final int STATE_2_HEIGHT = 80;
    private static final int STATE_3_HEIGHT = 110;
    private static final int STATE_4_HEIGHT = 140;

    private static int y;
    private static int height;
    private static Font font_title;
    private static Font font_additional;

    public static boolean moving;

    private int y = -1;
    public boolean selected = false;
    public boolean affected = false;

    public Entry() {
        super();
        this.setLayout(null);
        this.setBounds(0, 0, 0, 0);
        this.setBackground(Schemes.grey3);
    }
    public static void setSize(int in) {
        switch (in) {
            case 1:
                height = STATE_1_HEIGHT;
                font_title = Schemes.titleFont_1;
                break;
            case 2:
                height = STATE_2_HEIGHT;
                font_title = Schemes.titleFont_1;
                font_additional = Schemes.addFont_1;
                break;
            case 3:
                height = STATE_3_HEIGHT;
                font_title = Schemes.titleFont_2;
                font_additional = Schemes.addFont_2;
                break;
            case 4:
                height = STATE_4_HEIGHT;
                font_title = Schemes.titleFont_3;
                font_additional = Schemes.addFont_3;
                break;
        }
    }

    
    private static BufferedImage icon;
    private static String title, additional;
    public void setData(String[] in) {
        if (in.length == 1) {
            Entry result = new Entry();
            result.setData(in[0], "");
            return result;
        }
        if (in.length > 1) {
            Entry result = new Entry();
            result.setData(in[0], in[1]);
            return result;
        }
    }
    public void setData(String _title, String _additional) {
        icon = LoadImage(_title);

        title = _title;
        additional = _additional;
    }
    public String[] getData() {
        return new String[]{ title, additional };
    }


    public static ArrayList<Entry> ReadFromArray(String[] arr) {
        ArrayList<Entry> result = new ArrayList<>();
        for (int i = 0; i < in.length; i++) {
            result.add(Entry.ReadFromString(in[i]));
        }
        return result;
    }
    public static Entry ReadFromString(String in) {
        Entry result = new Entry();
        result.setData(in.split(", "));
        return result;
    }


    public Entry clone() {
        Entry result = new Entry();
        result.setData(this.getData());
        return result;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        
        if (height != STATE_1_HEIGHT) {
            g2.setColor(Schemes.gold);
            g2.setFont(font_title);
            g2.drawString(title, height + 5, height / 2);
            g2.setColor(Schemes.gold2);
            g2.setFont(font_additional);
            g2.drawString(additional, height + 5, height - 14);
        } else {
            g2.setColor(Schemes.gold);
            g2.setFont(font_title);
            g2.drawString(title, height + 5, height - 10);
        }
        g2.drawImage(image, 0, 0, height, height, null);


        // Border
        if (selected) {
            if (moving) g2.setColor(Schemes.blue2);
            else g2.setColor(Schemes.gold);

            g2.setStroke(new BasicStroke(2));
            g2.drawRect(2, 2, this.getWidth() - 18, height - 4);
        }
    }
    public void update() {
        this.setBounds(x, y, width, height);
    }


    private static BufferedImage LoadImage(String in) {
        in = in.replace(" ","");
        in = in.replace(".","");
        in = in.replace("'","");

        try { // Try to find in "images"
            String dir = "images/" + in + ".png";
            BufferedImage img = ImageIO.read(new File(dir));

            return img;
        } catch (Exception e) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
    }
}
