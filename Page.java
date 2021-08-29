public class Page {
    private Grid grid;
    private char standard;
    private int xval;
    private int yval;
    public Page(char _default) {
        //the default size for a Page. . Optimal size for fullscreen view on most monitors
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

    public void display() {
        System.out.println(this.grid);
    }
}
