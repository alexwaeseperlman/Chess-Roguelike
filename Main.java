import TextRenderer.*;
import Inputs.*;

class Main {
  public static void main(String[] args) {
    Rect r1 = new Rect(2, 3, 3, 3, 0);
    Point r2 = new Point(new Glyph('A', Color.WHITE, Color.BLACK), 4, 3, 2);
    
    Text t = new Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla non ultricies turpis, eu molestie turpis. Mauris vitae augue ultrices, ultrices felis quis, facilisis arcu. Duis ac lacinia tellus. In massa sapien, accumsan a velit ut, hendrerit sollicitudin mi. In auctor purus est. Sed dictum turpis odio, non euismod lorem eleifend dapibus. Donec elementum velit finibus urna commodo fermentum.", 15, 0, 20);
    t.fg = Color.RED;
    t.layer = 1;

    Renderer render = new Renderer(40, 20); 
    Input inputs = new Input(System.in);

    inputs.addListener(new Input.Listener() {
        @Override
        public void keyPressed(char c) {
            switch (c) {
                case 'h': r2.x--; break;
                case 'l': r2.x++; break;
                case 'k': r2.y--; break;
                case 'j': r2.y++; break;
            }
			t.content = String.format("%d, %d", r2.x, r2.y);
            render.refresh();
            if (c == '{') {
                Renderer.close();
                inputs.close();
            }
        }
    });

    render.objects.add(r1);
    render.objects.add(r2);
    render.objects.add(t);
    render.refresh();
//    render.refresh();
    inputs.open();
  }
  public static void wait(int ms) {
    try {
      Thread.sleep(ms);
    }
    catch(InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }
}
