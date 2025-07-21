package src;

import java.io.File;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageLoader {
    public static ArrayList<LoadedImage> LoadedImages = new ArrayList<LoadedImage>(){
        {
            this.add(
                new LoadedImage(
                    "", new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
                )
            );
        }
    };

    public static int preload(String in) {
        in = in.replace(" ","");
        in = in.replace(".","");
        in = in.replace("'","");

        for (int i = 0; i < LoadedImages.size(); i++) {
            if (LoadedImages.get(i).src.equals(in)) {
                return i;
            }
        }

        String name = "images/" + in + ".png";
        File file = new File(name);
        try {
            LoadedImage loaded = new LoadedImage(in, ImageIO.read(file));
            LoadedImages.add(loaded);
            return LoadedImages.size() - 1;
        } catch (Exception e) {
            return 0;
        }
    }
    public static BufferedImage get(int in) { 
        return LoadedImages.get(in).img;
    }

    public static class LoadedImage {
        public String src;
        public BufferedImage img;
        public LoadedImage(String src, BufferedImage img) {
            this.src = src; this.img = img;
        }
    }
}