package kprint;

public class Datapoint {
    private String data;
    final String type;
    private String last;
    final int xloc;
    final int yloc;
    final String name;

    public Datapoint(String _name, int _data, int _xl, int _yl) {
        this.xloc = _xl;
        this.yloc = _yl;
        this.data = String.valueOf(_data);
        this.type = "int";
        this.name = _name;
    }
    public Datapoint(String _name, String _data, int _xl, int _yl) {
        this.xloc = _xl;
        this.yloc = _yl;
        this.data = _data;
        this.type = "string";
        this.name = _name;
    }

    public void update(int _change) {
        this.data = String.valueOf(_change);
    }
    public void update (String _change) {
        this.data = _change;
    }
    public void setLast(String _saved) {
        this.last = _saved;
    }

    public boolean isInt() {
        return this.type.equals("int");
    }
    public int getX() {
        return this.xloc;
    }
    public int getY() {
        return this.yloc;
    }

    public String get() {
        return this.data;
    }
    public String getName() {
        return this.name;
    }
    public String getLast() {
        return this.last;
    }
}