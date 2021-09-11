package kprint

import java.util.Vector
import kotlin.system.exitProcess

class Grid(_xNum : Int, _yNum : Int, _def : Char) {
    private var xSize : Int? = null
    private var ySize : Int? = null
    private var def : Char? = null
    private var grid = Vector<Vector<Tile>>()

    init {
        this.xSize = _xNum
        this.ySize = _yNum
        this.def = _def
        val temp = Vector<Vector<Tile>>()
        for (r in 0..this.ySize!!) {
            val line = Vector<Tile>()
            for (c in 0..this.xSize!!) {
                line.add(Tile(this.def!!))
            }
            temp.add(line)
        }
        //TODO find substitute for reverse "for" iteration loop

        var xv : Int = temp.size - 1
        while (true) {
            this.grid.add(temp[xv])
            xv -= 1
            if (xv < 0) break
        }
    }
    override fun toString(): String {
        val ret = StringBuilder()
        for (vec : Vector<Tile> in this.grid) {
            for (t : Tile in vec) { ret.append(t.toString()) }
            ret.append("\n")
        }
        return ret.toString()
    }

    fun insert(_change : Char, _xCord : Int, _yCord : Int) {
        try {
            if (_xCord !in 0..this.xSize!! && _yCord !in 0..this.ySize!!) {
                val errMessage : String = when (_xCord !in 0..this.xSize!!) {
                    true -> "Value of x cord is out of bounds;; x: $_xCord      @G-I1"
                    false -> "Value of y cord is out of bounds;; y: $_yCord     @G-I1"
                }
                throw InvalidCoordinateException(errMessage)
            }
            this.grid[_xCord - 1][_yCord - 1].update(_change)
        }
        catch (e : Exception){
            e.printStackTrace()
            exitProcess(1)
        }
    }
    fun insert(_change : String, _xCord: Int, _yCord: Int) {
        try {
            if (_xCord !in 0..this.xSize!! || _yCord !in 0..this.ySize!!) {
                val errMessage = when (_xCord !in 0..this.xSize!!) {
                    true -> "Value of x cord is out of bounds;; x: $_xCord      @G-I2"
                    false -> "Value of y cord is out of bounds;; y: $_yCord     @G-I2"
                }
                throw InvalidCoordinateException(errMessage)
            }
            if (_change.length > (this.xSize!!) - _xCord) {
                throw InvalidStringSizeExeption("Message size is too large to fit within given grid space;;     @G-I2")
            }
            var chCount = 0
            for (ch : Char in _change.toCharArray()) {

                this.grid[_yCord - 1 + this.ySize!!][(_xCord - 1) + chCount].update(ch)
                chCount += 1
            }
        }
        catch (e : Exception) {
            e.printStackTrace()
            exitProcess(1)
        }
    }
    fun insert(_change : Int, _xCord : Int, _yCord : Int) {
        insert(_change.toString(), _xCord, _yCord)
    }
}
class Tile(_data: Char) {
    private var data : Char? = null
    private var tileNumber : Int? = null
    companion object { var tileCount : Int = 1 }

    init {
        this.data = _data
        this.tileNumber = tileCount
        tileCount += 1
    }
    override fun toString() : String {
        //return this.tileNumber.toString() + " "    //for debugging
        return data.toString()
    }

    fun update(_change : Char) {
        try { //TODO this is a potential solution to the issues, also might be an issue
            this.data = _change
        }
        catch (e : Exception) {
            e.printStackTrace()
            exitProcess(1)
        }
    }
}
