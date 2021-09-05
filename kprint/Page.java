package kprint;

import java.util.Vector;

public class Page {//TODO don't stop the user from overriding borders, as they may want to change them more precisely
    private Grid grid;
    private char standard;
    private int xval;
    private int yval;
    private Vector<Datapoint> dp = new Vector<>();
    public Page(char _default) {
        //the default size for a kPrint.Page. . Optimal size for fullscreen view on most monitors
        create(180, 30, _default);
    }
    public Page(int _xval, int _yval, char _default) {
        try {
            if (_xval < 2 || _yval < 2) {
                if (_xval < 2 && _yval < 2) throw new Exception("- invalid size parameters  both x and y values must be at least 2");
                if (_xval < 2) throw new Exception("- invalid size parameter  x value must be at least 2");
                else throw new Exception("- invalid size parameter  y value must be at least 2");
            }
            else create (_xval, _yval, _default);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    //Pages will always go by which one was refrenced first
    public Page(Page _p1, Page _p2, String _mode, char _default) {
        Vector<Vector<Tile>> gp1 = _p1.grid.contents();
        Vector<Vector<Tile>> gp2 = _p2.grid.contents();
        Vector<Vector<Tile>> send = new Vector<>();
        Vector<Tile> line;
        if (_mode.contains("left")) {
            this.xval = _p1.xval + _p2.xval;
            this.yval = Math.max(_p1.yval, _p2.yval);
            create(this.xval, this.yval, _default);
            if (_mode.equalsIgnoreCase("left-right")) {
                for (int s = 0; s < Math.min(_p1.yval, _p2.yval); s++) {
                    line = new Vector<>();
                    line.addAll(gp1.get(s));
                    line.addAll(gp2.get(s));
                    send.add(line);
                }
                for (int e = Math.min(_p1.yval, _p2.yval); e < this.yval; e++) {
                    line = new Vector<>();
                    if (_p1.yval > _p2.yval) line.addAll(gp1.get(e));
                    else line.addAll(gp2.get(e));
                    send.add(line);
                }
            }
            else if (_mode.equalsIgnoreCase("right-left")){
                for (int s = 0; s < Math.min(_p1.yval, _p2.yval); s++) {
                    line = new Vector<>();
                    line.addAll(gp2.get(s));
                    line.addAll(gp1.get(s));
                    send.add(line);
                }
                for (int e = Math.min(_p1.yval, _p2.yval); e < this.yval; e++) {
                    line = new Vector<>();
                    if (_p1.yval > _p2.yval) line.addAll(gp1.get(e));
                    else line.addAll(gp2.get(e));
                    send.add(line);
                }
            }

        }
        else {
            this.xval = Math.min(_p1.xval, _p2.xval);
            this.yval = _p1.yval + _p2.yval;
            create(this.xval, this.yval, _default);
            if (_mode.equalsIgnoreCase("top-bottom")) {
                send.addAll(gp1);
                send.addAll(gp2);
            }
            else if (_mode.equalsIgnoreCase("bottom-top")) {
                send.addAll(gp2);
                send.addAll(gp1);
            }
        }
        this.grid.createNewGrid(send);
        this.dp.addAll(_p1.dp);
        this.dp.addAll(_p2.dp);
    }

    private void create(int _xval, int _yval, char _default) {
        this.xval = _xval; this.yval = _yval;
        this.grid = new Grid(_xval, _yval, _default);
        this.standard = _default;
    }

    public void border(boolean _status) {
        if (_status) {
            cBorder('#');
        }
        else {
            cBorder(this.standard);
        }
    }
    public void border(boolean _status, boolean[] tblr) {
        if (_status) {
            cBorder('#', tblr);
        }
        else {
            cBorder(this.standard, tblr);
        }
    }
    public void border(boolean _status, char _bdr) {
        if (_status) {
            cBorder(_bdr);
        }
        else {
            cBorder(this.standard);
        }
    }
    public void border(boolean _status, char _bdr, boolean[] tblr) {
        if (_status) {
            cBorder(_bdr, tblr);
        }
        else {
            cBorder(this.standard, tblr);
        }
    }
    private void cBorder(char _fill) {
        grid.insert(String.valueOf(_fill).repeat(this.xval), 1, 1);
        grid.insert(String.valueOf(_fill).repeat(this.xval), 1, this.yval);
        for (int e = 1; e <= this.yval; e++) {
            grid.insert(_fill, 1, e);
            grid.insert(_fill, this.xval, e);
        }
    }
    private void cBorder(char _fill, boolean[] pr) {
        StringBuilder rule = new StringBuilder();
        switch (pr.length) {
            case 1 -> {
                rule.append("tblr");
            }
            case 2 -> {
                if (pr[0]) rule.append("tb");
                if (pr[1]) rule.append("lr");
            }
            case 3 -> {
                if (pr[0]) rule.append("t");
                if (pr[1]) rule.append("b");
                if (pr[2]) rule.append("lr");
            }
            case 4 -> {
                if (pr[0]) rule.append("t");
                if (pr[1]) rule.append("b");
                if (pr[2]) rule.append("l");
                if (pr[3]) rule.append("r");
            }
        }
        if (!(rule.indexOf("b") == -1)) grid.insert(String.valueOf(_fill).repeat(this.xval), 1, 1);
        if (!(rule.indexOf("t") == -1)) grid.insert(String.valueOf(_fill).repeat(this.xval), 1, this.yval);
        if (!(rule.indexOf("l") == -1) && !(rule.indexOf("r") == -1)) {
            for (int e = 1; e <= this.yval; e++) {
                grid.insert(_fill, 1, e);
                grid.insert(_fill, this.xval, e);
            }
        }
        else if (!(rule.indexOf("l") == -1)) {
            for (int e = 1; e <= this.yval; e++) {
                grid.insert(_fill, 1, e);
            }
        }
        else if (!(rule.indexOf("r") == -1)) {
            for (int e = 1; e <= this.yval; e++) {
                grid.insert(_fill, this.xval, e);
            }
        }
    }

    public void line(int _x1, int _y1, int _x2, int _y2) {
        cLine(_x1, _y1, _x2, _y2, '.');
    }
    public void line(int _x1, int _y1, int _x2, int _y2, char _fill) {
        cLine(_x1, _y1, _x2, _y2, _fill);
    }
    private void cLine(int _x1, int _y1, int _x2, int _y2, char _fill) {
        if (_y1 == _y2) this.grid.insert(String.valueOf(_fill).repeat(_x1 + _x2 - 1), _x1, _y1);
        else if (_x1 == _x2) {
            for (int e = _y1; e <= _y2; e++) this.grid.insert(String.valueOf(_fill), _x1, e);
        }
        else {
            //TODO make the algorithm for producing non normal lines
        }
    }

    public void box(int _x1, int _y1, int _x2, int _y2) {
        char _fill = '.';
        cBox(_x1, _y1, _x2, _y2, _fill);
    }
    public void box(int _x1, int _y1, int _x2, int _y2, char _fill) {
        cBox(_x1, _y1, _x2, _y2, _fill);
    }
    private void cBox(int _x1, int _y1, int _x2, int _y2, char _fill) {
        cLine(_x1, _y1, _x2, _y1, _fill);
        cLine(_x1, _y2, _x2, _y2, _fill);
        cLine(_x1, _y1, _x1, _y2, _fill);
        cLine(_x2, _y1, _x2, _y2, _fill);
    }

    public void insert(int n, int _xpos, int _ypos) {
        this.grid.insert(n, _xpos, _ypos);
    }
    public void insert(String s, int _xpos, int _ypos) {
        this.grid.insert(s, _xpos, _ypos);
    }
    public void insert(char c, int _xpos, int _ypos) {
        this.grid.insert(c, _xpos, _ypos);
    }

    private int readXy(String _target) {
            if (_target.equals("x")){
                return this.xval;
                //return _p.xval;
            }
            else {
                return this.yval;
                //return _p.yval;
            }
    }

    public void addDatapoint(Datapoint d, int _xval, int _yval) {
        if (d.isInt()) {
            insert(Integer.parseInt(d.get()), _xval, _yval);
        }
        else insert(d.get(), _xval, _yval);

        d.setLast(d.get());

        this.dp.add(d);
    }
    public void removeDatapoint(String name) {
        for (Datapoint d : this.dp) {
            if (d.getName().equals(name)) {
                String str = String.valueOf(this.standard).repeat(d.getLast().length());
                insert(str, d.getX(), d.getY());
                dp.remove(d);
                break;
            }
        }
    }
    public void insertDatapoint(Datapoint d) {
        String current = d.get();
        int spaces = d.getLast().length();
        String str = String.valueOf(this.standard).repeat(spaces);
        insert(str, d.getX(), d.getY());
        insert(current, d.getX(), d.getY());
        d.setLast(current);
    }
    public void display() {
        for (Datapoint d : this.dp) {
            if (!(d.get().equals(d.getLast()))) {
                insertDatapoint(d);
            }
        }
        System.out.println(this.grid);
    }
}