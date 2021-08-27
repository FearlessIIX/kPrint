import java.util.Vector;

public class Engine {
    public static void clear(int amount) {
        StringBuilder str = new StringBuilder();
        str.append("\n".repeat(Math.max(0, amount)));
        System.out.println(str.toString());

    }
    public static void main(String[] args) throws InterruptedException {
        Grid grid = new Grid(180, 30);
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        System.out.println(grid);
        grid.insert('#', 5, 1);
        Thread.sleep(1000);
        clear(40);
        System.out.println(grid);
        grid.insert(4, 10, 1);
        Thread.sleep(1000);
        clear(40);
        System.out.println(grid);
        grid.insert("string", 15, 1);
        Thread.sleep(1000);
        clear(40);
        System.out.println(grid);
        grid.insert(1001, 20, 1);
        Thread.sleep(1000);
        clear(40);
        System.out.println(grid);
        Thread.sleep(5000);

    }
}
class Grid {
    private int x;
    private int y;
    private boolean binder = false;
    private Vector<Vector<Tile>> grid = new Vector<>();
   public Grid(int x_num, int y_num) {
       this.x = x_num;
       this.y = y_num;
       Vector<Vector<Tile>> temp = new Vector<>();
       for (int r = 0; r < y_num; r++) {  //creation loop  [[1, 2], [3, 4], [5, 6]]
           Vector<Tile> line = new Vector<>();
           for (int c = 0; c < x_num; c++) {
                line.add(new Tile());
           }
           grid.add(line);
       }
       /*for (int i = temp.size() - 1; i >= 0; i--) {  //reversal loop [[5, 6], [3, 4], [1, 2]]
           grid.add(temp.get(i));
       }*/
   }
   public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Vector<Tile> vec : this.grid) {
            for (Tile t : vec) {
                if (this.binder) ret.append(t.toString()).append(" ");
                else ret.append(t.toString());
            }
            ret.append("\n");
        }
        return ret.toString();
   }
   public void insert(char change, int x_cord, int y_cord) {
        try {
            if ((x_cord > 0 && x_cord <= this.x) && (y_cord > 0 && y_cord <= this.y)) {
                int selector = this.y - y_cord;
                this.grid.get(selector).get(x_cord - 1).update(change);
            }
        }
        catch (Exception e) {
            //TODO: add something here to pause/ interrupt the execution of any active Threads
            System.out.println("*** SOMETHING WENT WRONG!!!");
            e.printStackTrace();
        }
   }
   public void insert(String change, int x_cord, int y_cord) {
       try {
           if ((x_cord > 0 && x_cord <= this.x) && (y_cord > 0 && y_cord <= this.y)) {
                if (x_cord + change.length() <= this.x) {
                    int selector = this.y - y_cord;
                    Vector<Tile> line = this.grid.get(selector);
                    int current = x_cord;
                    for (char ch : change.toCharArray()) {
                        line.get(current - 1).update(ch);
                        current++;
                    }
                }
           }
       }
       catch (Exception e) {
           //TODO: add something here to pause/ interrupt the execution of any active Threads
           System.out.println("*** SOMETHING WENT WRONG!!!");
           e.printStackTrace();
       }
   }
   public void insert(int change, int x_cord, int y_cord) {
       insert(String.valueOf(change), x_cord, y_cord);
   }
}
class Tile {
    private char data;
    private int tile_number;
    static int count = 1;
    public Tile() {
        this.data = '-';   //this is used to set the default background for the Screen **MUST BE A CHARACTER**
        this.tile_number = count;
        count++;
    }
    public void update(char change) {this.data = change;}
    public void update(String change) {this.data = change.charAt(0);}
    public String toString() {
        return String.valueOf(this.data);
    }
}
