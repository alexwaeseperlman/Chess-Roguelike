package chessroguelike.textRenderer;

/**
 * A simple class to represent colors
 * It has some common colors built in
 **/
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

  // This is the ANSI escape sequence to reset current coloring
  public static final String RESET = "\u001b[0m";
  
  final int r, g, b;
   
  /**
   * Construct a color based on RGB components
   **/
  public Color(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * Generates an ANSI escape sequence to print text of this color
   * @param bg A flag representing whether or not the 
               escape code should generate foreground or background colors
   **/
  public String escapeCode(boolean bg) {
    String out = "\033[";
    if (bg) out += "48";
    else out += "38";
    out += String.format(";2;%d;%d;%dm", r, g, b);
    
    return out;
  }

  /**
   * @param c The char that should have color applied to it
   * @param fg Foreground color
   * @param bg Background color
   * @returns A string of char c with the fg and bg colors applied to it
   **/
  public static String color(char c, Color fg, Color bg) {
    return color(String.valueOf(c), fg, bg);
  }

  /**
   * @param s The string that should have color applied to it
   * @param fg Foreground color
   * @param bg Background color
   * @returns The inputted string with the fg and bg colors applied to it
   **/
  public static String color(String s, Color fg, Color bg) {
    return fg.escapeCode(false) + bg.escapeCode(true) + s + RESET;
  }

  /**
   * @param other The color to compare to
   * @returns Whether or not these colors are exactly equal
   **/
  public boolean equals(Color other) {
	  return r == other.r && g == other.g && b == other.b;
  }
}
