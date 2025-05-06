import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class Champion extends JPanel {

    public static int width = 850;
    public static final int height = 120;
    public int x = 0;
    public int y = -200;
    public boolean selected = false;
    public boolean affected = false;
    public static boolean moving = false;

    JLabel LABEL_Icon;

    public String name;
    public Champion(String name, String title){
        super();
        this.name = name;

        setLayout(null);
        setBackground(Schemes.grey3);
        setForeground(Schemes.gold);
        setBounds(x, y, width, height);

        JLabel LABEL_Name = new JLabel(name.toUpperCase());
        LABEL_Name.setBounds(130, 5, width, 80);
        LABEL_Name.setFont(Schemes.titleFont);
        LABEL_Name.setForeground(Schemes.gold);
        this.add(LABEL_Name);

        JLabel LABEL_Title = new JLabel(title);
        LABEL_Title.setBounds(130, 85, width, 30);
        LABEL_Title.setFont(Schemes.descFont);
        LABEL_Title.setForeground(Schemes.gold2);
        this.add(LABEL_Title);

        LABEL_Icon = new JLabel();
        try {
            File imageFile = new File("Icons/" + FixNameForIcon(name));
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            ImageIcon imageIcon = new ImageIcon(bufferedImage);

            LABEL_Icon.setIcon(imageIcon);
        } catch (IOException e) { System.out.println("Failed: " + FixNameForIcon(name)); }
        LABEL_Icon.setBounds(0, 0, 120, 120);
        this.add(LABEL_Icon);
    }



    public void paintComponent(Graphics g) {
        this.update();
        super.paintComponent(g);

        if (selected) {
            if (moving)
                g.setColor(Schemes.blue2);
            else
                g.setColor(Schemes.gold);

            ((Graphics2D)g).setStroke(new BasicStroke(2));
            g.drawRect(2, 2, this.getWidth() - 18, height - 4);
        }
    }


    public void update() {
        this.setBounds(x, y, width, height);

        if (selected) {
            this.LABEL_Icon.setBounds(4, 4, 120, 112);
        } else {
            this.LABEL_Icon.setBounds(0, 0, 120, 120);
        }
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
}
