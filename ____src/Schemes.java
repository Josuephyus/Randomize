package src;

import java.awt.Color;
import java.awt.Font;

public class Schemes {
    public static final Font font_err = new Font("Arial", Font.BOLD, 70);
    public static final Font font_title1 = new Font("Arial", Font.BOLD, 40);
    public static final Font font_add1 = new Font("Arial", Font.PLAIN, 25);
    public static final Font font_title2 = new Font("Arial", Font.BOLD, 60);
    public static final Font font_add2 = new Font("Arial", Font.PLAIN, 35);
    public static final Font font_title3 = new Font("Arial", Font.BOLD, 80);
    public static final Font font_add3 = new Font("Arial", Font.PLAIN, 45);

    public static Color color_background = new Color(30, 35, 40);
    public static Color color_title = new Color(200, 155, 60);
    public static Color color_additional = new Color(120, 90, 40);
    public static Color color_movingBorder = new Color(160, 155, 140);
    public static Color color_selectedBorder = new Color(10, 200, 185);
    
    public static void ReadFrom(String in) {
        FileHandler.FileToStringArray(in);

    }

    public static final Color seeThrough = new Color(0,0,0,0);
}
