package chessroguelike.textRenderer;
import java.util.ArrayList;

/**
 * A render object for drawing text with simple line wrapping
 **/
public class Text implements RenderObject {
    // If the delimiter is set it signals that lines can only wrap
    // in places where its present.
    // This allows users to prevent lines from wrapping mid-word
    public String content, delimiter;
    public int width, layer;
    public Color fg = Color.WHITE, bg = Color.BLACK;
    /**
     * Construct a text object with given text and width
     * @param width The maximum width of a line before it wraps
     **/
	public Text(String s, int width, int layer) {
		this(s, width, " ", layer, Color.WHITE, Color.BLACK);
	}
    /** 
     * Construct a text object with custom text, width, delimiter, layer, and color
     * @param width The maximum width of aline before it wraps
     * @param s The contents that should be rendered
     * @param delimeter A string that restricts the point where line breaks can be added
     * @param fg Foreground {@link Color}
     * @param bg Background {@link Color}
     **/
    public Text(String s, int width, String delimeter, int layer, Color fg, Color bg) {
        this.content = s;
        this.width = width;
        this.delimiter = delimeter;
        this.fg = fg;
        this.bg = bg;
        this.layer = layer;
    }
    /**
     * Construct a text object with unspecified layer
     **/
    public Text(String s, int width) {
        this(s, width, " ", 3, Color.WHITE, Color.BLACK);
    }
    /**
     * Construct a text object without wrapping. Maximum length is set to the contents length
     **/
    public Text(String s) {
        this(s, s.length(), " ", 3, Color.WHITE, Color.BLACK);
    }

    public ArrayList<Pixel> draw() {
        ArrayList<Pixel> out = new ArrayList<Pixel>();

        // Number of columns in the current row
        int cols = 0;
        // Number of rows that have been drawn
        int row = 0;
        for (String w : content.split(delimiter)) {
            // Decide when to wrap text
            if (cols + w.length()+delimiter.length() > width) {
                cols = 0;
                row++;
            }

            // Add all the characters from this step.
            // It can be more than one because the delimiter
            // can be multiple characters
            for (int i = 0; i < (delimiter+w).length(); i++) {
                Glyph c = new Glyph((delimiter+w).charAt(i), fg, bg);
                out.add(new Pixel(c, cols, row, layer));
                cols++;
            }
        }
        return out;
    }

}
