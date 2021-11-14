package chessroguelike.textRenderer;

public class Color {
  public static final Color 
  // standard console colors
  WHITE = new Color(255, 255, 255),
  BLACK = new Color(0, 0, 0),

  BLUE = new Color(0, 0, 255),
  CYAN = new Color(0, 255, 255),
  GREEN = new Color(0, 255, 0),
  PURPLE = new Color(160, 32, 240),
  RED = new Color(255, 0, 0), 
  YELLOW = new Color(255, 255, 0);
    
  public static final String RESET = "\u001b[0m";
  
  final int r, g, b;
   
  public Color(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public String escapeCode(boolean bg) {
    String out = "\033[";
    if (bg) out += "48";
    else out += "38";
    out += String.format(";2;%d;%d;%dm", r, g, b);
    
    return out;
  }

  public static String color(char c, Color fg, Color bg) {
    return color(String.valueOf(c), fg, bg);
  }
  public static String color(String s, Color fg, Color bg) {
    return fg.escapeCode(false) + bg.escapeCode(true) + s + RESET;
  }

  public boolean equals(Color other) {
	  return r == other.r && g == other.g && b == other.b;
  }
}
