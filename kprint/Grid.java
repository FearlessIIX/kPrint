package kprint;

import java.util.Vector;

public class Grid {
    final int x;
    final int y;
    private Vector<Vector<Tile>> grid = new Vector<>();
    public Grid(int x_num, int y_num, char def) {
        this.x = x_num;
        this.y = y_num;
        Vector<Vector<Tile>> temp = new Vector<>();
        for (int r = 0; r < y_num; r++) {
            Vector<Tile> line = new Vector<>();
            for (int c = 0; c < x_num; c++) {
                line.add(new Tile(def));
            }
            grid.add(line);
        }
    }
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Vector<Tile> vec : this.grid) {
            for (Tile t : vec) {
                ret.append(t.toString());
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
    //TODO whenever this part recognizes that there will be overflow. it instead decides not to add anything at all
    public void insert(String change, int x_cord, int y_cord) {
        try {
            if ((x_cord > 0 && x_cord <= this.x) && (y_cord > 0 && y_cord <= this.y)) {
                if (x_cord - 1 + change.length() <= this.x) {
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
    public void createNewGrid(Vector<Vector<Tile>> _vec) {
        int line = 0;
        for (Vector<Tile> v : _vec) {
            int count = 0;
            for (Tile t : v) {
                this.grid.get(line).set(count, t);
                count++;
            }
            line++;
        }
    }
    public Vector<Vector<Tile>> contents() {
        return this.grid;
    }
}
class Tile {
    private char data;
    final int tile_number;
    static int count = 1;
    public Tile(char fill) {
        this.data = fill;   //this is used to set the default background for the Screen **MUST BE A CHARACTER**
        this.tile_number = count;
        count++;
    }
    public void update(char change) {this.data = change;}
    //public void update(String change) {this.data = change.charAt(0);}
    public String toString() {
        return String.valueOf(this.data);
    }
}