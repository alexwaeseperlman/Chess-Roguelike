import TextRenderer.*;
import Inputs.*;

class Main {
  public static void main(String[] args) {
    Rect r1 = new Rect(2, 3, 3, 3, 0);
    Rect r2 = new Rect(6, 4, 3, 3, 0);
    Renderer render = new Renderer(10, 10); 
    Input inputs = new Input(System.in);

    inputs.addListener(new Input.Listener() {
        @Override
        public void keyPressed(char c) {
            System.out.println(c);
            r1.x++;
            render.refresh();
            render.refreshScreen();
            if (c == 'X') {
                Renderer.close();
                inputs.close();
            }
        }
    });
    inputs.open();

    render.objects.add(r1);
    render.objects.add(r2);
    render.refresh();
    render.refreshScreen();
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
