import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.ArrayList;

public class Champion extends JPanel {

    public static int width = 850;
    public static int height = 80;
    public int x = 0;
    public int y = -200;
    public boolean selected = false;
    public boolean affected = false;
    public static boolean moving = false;


    public String name;
    public String title;
    BufferedImage IMAGE;


    public Champion(String name, String title){
        super();
        this.name = name;
        this.title = title;

        this.setLayout(null);
        this.setBackground(Schemes.grey3);
        this.setBounds(x, y, width, height);

        try {
            IMAGE = Champion.LoadImage("Icons/" + FixNameForIcon(name));
        } catch (IOException e) {
            System.out.println("Failed: " + FixNameForIcon(name));

            try {
                IMAGE = Champion.LoadImage("Icons/None.png");
            } catch (IOException e2) { System.out.println("  Failed: None.png");}
        }
    }


    public static ArrayList<Champion> ConvertStringArray(String[] in) {
		ArrayList<Champion> Champions = new ArrayList<Champion>();
        
		for (int i = 0; i < in.length; i++) {
            Champions.add(ConvertString(in[i]));
		}

        return Champions;
    }

    public static Champion ConvertString(String in) {
        String name = in;
        String title = "";

        if (in.split(", ").length > 1) {
            name = in.split(", ")[0];
            title = in.split(", ")[1];
        }        
        return new Champion(name, title);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        // Name
        g2.setColor(Schemes.gold);
        if (height < 91) g2.setFont(Schemes.titleFont_1);
        else if (height < 121) g2.setFont(Schemes.titleFont_2);
        else g2.setFont(Schemes.titleFont_3);

        if (height < 61) g2.drawString(name, height + 5, height - 10);
        else g2.drawString(name, height + 5, height / 2);


        // Title
        if (height < 91) g2.setFont(Schemes.descFont_1);
        else if (height < 121) g2.setFont(Schemes.descFont_2);
        else g2.setFont(Schemes.descFont_3);

        if (height > 60) {
            g2.setColor(Schemes.gold2);
            g2.drawString(title, height + 5, height - 14);
        }

        // Icon
        g2.drawImage(IMAGE, 0, 0, height, height, null);


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




    public static String FixNameForIcon(String in) {
        String name = in;
        // Dr.Mundo
        name = name.replace(".","");
        
        // Asol
        name = name.replace(" ", "");
        
        // BelVeth, ChoGath, KSante, KaiSa, KhaZix, KogMaw,
        // RekSai, VelKoz
        name = name.replace("'", "");
        
        return name + ".png";
    }


    public static BufferedImage LoadImage(String in) throws IOException {
        File imageFile = new File(in);
        BufferedImage imageBuff = ImageIO.read(imageFile);
        
        return imageBuff;
    }
}
