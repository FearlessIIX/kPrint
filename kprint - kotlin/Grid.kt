package kprint

import kotlin.system.exitProcess

class Grid(_xNum : Int, _yNum : Int, _def : Char) {
    private var xSize : Int? = null
    private var ySize : Int? = null
    private var def : Char? = null
    private var grid : MutableList<MutableList<Tile>> = mutableListOf()

    init {
        this.xSize = _xNum
        this.ySize = _yNum
        this.def = _def
        for (r in 0 until this.ySize!!) {
            val line : MutableList<Tile> = mutableListOf()
            for (c in 0 until this.xSize!!) {
                line.add(Tile(this.def!!))
            }
            this.grid.add(line)
        }
        this.grid.reverse()
    }
    override fun toString(): String {
        val ret = StringBuilder()
        for (vec : MutableList<Tile> in this.grid) {
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
            this.grid[this.ySize!! - _yCord][_xCord - 1].update(_change)
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
            if (_xCord - 1 + _change.length > this.xSize!!) {
                throw InvalidStringSizeExeption("Message size is too large to fit within given grid space;;     @G-I2")
            }
            val line: MutableList<Tile> = this.grid[this.ySize!! - _yCord]
            var current = _xCord  - 1
            for (ch : Char in _change.toCharArray()) {
                line[current].update(ch)
                current++
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
