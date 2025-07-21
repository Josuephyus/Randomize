package src;

import java.util.ArrayList;

public class Entry {
    public int image;
    public String title, additional;
    public int prevIndex;

    private static final String separator = ", ";

    public Entry(String title, String additional) {
        this.image = ImageLoader.preload(title);
        this.title = title;
        this.additional = additional;
    }
    public Entry(String line) {
        String[] v = line.split(separator);
        if (v.length > 0) {
            this.image = ImageLoader.preload(v[0]);
            this.title = v[0];
        }
        if (v.length > 1) {
            this.additional = v[1];
        }
    }
    @Override public String toString() {
        if (additional != null) {
            return title + separator + additional;
        } else {
            return title;
        }
    }


    public static ArrayList<Entry> ReadFromArray(String[] arr) {
        ArrayList<Entry> result = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            result.add(new Entry(arr[i]));
        }
        return result;
    }
}
