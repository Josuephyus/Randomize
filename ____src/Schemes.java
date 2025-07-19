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

    private static final Color color_default_background = new Color(40, 40, 40);
    private static final Color color_default_title = new Color(200, 200, 200);
    private static final Color color_default_additional = new Color(120, 120, 120);
    private static final Color color_default_movingBorder = new Color(140, 140, 140);
    private static final Color color_default_selectedBorder = new Color(220, 220, 220);
    public static Color color_background = color_default_background;
    public static Color color_title = color_default_title;
    public static Color color_additional = color_default_additional;
    public static Color color_movingBorder = color_default_movingBorder;
    public static Color color_selectedBorder = color_default_selectedBorder;
    
    public static void Reset() {
        ReadFrom("texts/colors.txt");   
    }
    public static void ReadFrom(String in) {
        String[] strings = FileHandler.FileToStringArray(in);

        if (strings.length != 5) {
            ResetToDefault();
            System.out.println(strings.length);
            return;
        }

        Color[] colors = new Color[5];
        for (int i = 0; i < 5; i++) {
            String line = strings[i];
            String[] values = line.split(" ");
            if (values.length != 3) {
                ResetToDefault();
                System.out.println(line + ", " + values.length);
                return;
            }

            try {
                int[] rgb = new int[3];
                for (int j = 0; j < 3; j++) {
                    rgb[j] = Integer.parseInt(values[j]);
                }
                colors[i] = new Color(rgb[0], rgb[1], rgb[2]);
            } catch (Exception e) {
                e.printStackTrace();
                ResetToDefault();
                return;
            }     
        }

        color_background = colors[0];
        color_title = colors[1];
        color_additional = colors[2];
        color_movingBorder = colors[3];
        color_selectedBorder = colors[4];
    }
    public static void ResetToDefault() {
        color_background = color_default_background;
        color_title = color_default_title;
        color_additional = color_default_additional;
        color_movingBorder = color_default_movingBorder;
        color_selectedBorder = color_default_selectedBorder;
    }
}