package kprint

import kotlin.system.exitProcess

class Grid(_xNum : Int = 180, _yNum : Int = 30, _def : Char = ' ', _mFB : Boolean = false) {
    private var xSize : Int? = null
    private var ySize : Int? = null
    private var def : Char? = null
    private var grid : MutableList<MutableList<Tile>> = mutableListOf()
    private val formatAssist : FormatBar? = when(_mFB) {
        true -> FormatBar(_xNum * 2)
        false -> null
    }
    private var hasFormatAssist : Boolean = _mFB
    companion object {
        var defX = 180
        var defY = 30
        var defC = ' '
    }

    init {
        try {
            if (_xNum < 1 || _yNum < 1) {
                val errMsg = StringBuilder()
                errMsg.append("Value of size cords are invalid\nX size: $_xNum, Y size: $_yNum\n")
                errMsg.append("Line 27, Error code: @Grid.init")
                throw InvalidSizeException(errMsg.toString())
            }
            this.xSize = _xNum
            this.ySize = _yNum
        }
        catch (e : Exception) {
            e.printStackTrace()
            exitProcess(1)
        }

        this.def = _def
        this.hasFormatAssist = _mFB
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
        if (this.hasFormatAssist) {
            ret.append(this.formatAssist).append("\n")
        }
        for (vec : MutableList<Tile> in this.grid) {
            for (t : Tile in vec) { ret.append(t.toString()) }
            ret.append("\n")
        }
        return ret.toString()
    }

    fun insert(_change : Char, _xCord : Int, _yCord : Int) {
        try {
            if (_xCord !in 1..this.xSize!! || _yCord !in 0..this.ySize!!) {
                val errMessage = when (_xCord !in 0..this.xSize!!) {
                    true -> StringBuilder().append("Value of x cord is out of bounds\n")
                    false -> StringBuilder().append("Value of y cord is out of bounds\n")
                }
                errMessage.append("X size: $_xCord, Y size: $_yCord\nLine 68, Error code: @Grid.insert_char")
                throw InvalidCoordinateException(errMessage.toString())
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
                val errMsg = when (_xCord !in 0..this.xSize!!) {
                    true -> StringBuilder().append("Value of x cord is out of bounds\n")
                    false -> StringBuilder().append("Value of y cord is out of bounds\n")
                }
                errMsg.append("X value: $_xCord, Y value: $_yCord\nLine: 85, Error code: @Grid.insert_string")
                throw InvalidCoordinateException(errMsg.toString())
            }
            if (_xCord - 1 + _change.length > this.xSize!!) {
                val errMsg = StringBuilder().append("Message size is too large to fit within given grid space\n")
                errMsg.append("Message size: ${_change.length}, Available space: ${this.xSize!! - (_xCord - 1)}\n")
                errMsg.append("Line: 91, Error code: @Grid.insert_string")
                throw InvalidStringSizeException(errMsg.toString())
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
