package kprint

import kotlin.system.exitProcess

class Page(_xVal : Int = 180, _yVal : Int = 30, _default : Char = ' ', _fmbS : Boolean = false) {
    private val grid : Grid = createGrid(_xVal, _yVal, _default, _fmbS)
    private val standard : Char = _default
    private var xVal : Int = _xVal
    private var yVal : Int = _yVal
    companion object {
        val _defX = 180
        val _defY = 30
        val _defC = ' '
    }

    private fun createGrid(_x : Int, _y : Int, _c : Char, _fbs : Boolean) : Grid {
        try {
            if (_x < 1 || _y < 1) {
                val errMsg = when (_x < 1) {
                    true -> when (_y < 1) {
                        true -> StringBuilder().append("Value of X and Y sizes are invalid\n")
                        false -> StringBuilder().append("Value of X size is invalid\n")
                    }
                    false -> StringBuilder().append("Value of Y size is invalid\n")
                }
                errMsg.append("X size: ${_x},Y size: ${_y}\n")
                errMsg.append("Line: 28, Error code: @Page.createGrid") //TODO this line val may change
                throw InvalidSizeException(errMsg.toString())
            }
        }
        catch (e : Exception) {
            e.printStackTrace()
            exitProcess(1)
        }
        return Grid(_x, _y, _c, _fbs)
    }
}
