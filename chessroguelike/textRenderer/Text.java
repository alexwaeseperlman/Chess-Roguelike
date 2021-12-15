package chessroguelike.textRenderer;
import java.util.ArrayList;

public class Text implements RenderObject {
    public String content, delimiter;
    public int width, layer;
    public Color fg = Color.WHITE, bg = Color.BLACK;
	public Text(String s, int width, int layer) {
		this(s, width, " ", layer, Color.WHITE, Color.BLACK);
	}
    public Text(String s, int width, String delimeter, int layer, Color fg, Color bg) {
        this.content = s;
        this.width = width;
        this.delimiter = delimeter;
        this.fg = fg;
        this.bg = bg;
        this.layer = layer;
    }
    public Text(String s, int width) {
        this(s, width, " ", 3, Color.WHITE, Color.BLACK);
    }
    public ArrayList<Pixel> draw() {
        ArrayList<Pixel> out = new ArrayList<Pixel>();

        int cols = 0;
        int row = 0;
        for (String line : content.split("\n")){
            for (String w : line.split(delimiter)) {
                if (cols + w.length() + delimiter.length() > width) {
                    cols = 0;
                    row++;
                }

                for (int i = 0; i < (delimiter+w).length(); i++) {
                    
                    Glyph c = new Glyph((w + delimiter).charAt(i), fg, bg);
                    out.add(new Pixel(c, cols, row, layer));
                    cols++;
                }
            }
            cols = 0;
            row ++;
        }
        
        return out;
    }

}
