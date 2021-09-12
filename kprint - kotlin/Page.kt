package kprint

import kotlin.system.exitProcess

class Page(_xVal : Int = 180, _yVal : Int = 30, _default : Char = ' ', _fmbS : Boolean = false) {
    private var grid : Grid = when (_xVal < 1 || _yVal < 1) {
        true -> issueCreatingGrid();
        false -> Grid(_xVal, _yVal, _default, _fmbS)
    }
    private val standard : Char = _default
    private var xVal : Int = _xVal
    private var yVal : Int = _yVal
    companion object {
        val _defX = 180
        val _defY = 30
        val _defC = ' '
    }

    init {

    }
    private fun issueCreatingGrid() : Grid {
        try {
            val errMsg = when (this.xVal < 1) {
                true -> when (this.yVal < 1) {
                    true -> StringBuilder().append("Value of X and Y sizes are invalid\n")
                    false -> StringBuilder().append("Value of X size is invalid\n")
                }
                false -> StringBuilder().append("Value of Y size is invalid\n")
            }
            errMsg.append("X size: ${this.xVal},Y size: ${this.yVal}\n")
            errMsg.append("Line: 32, Error code: @Page.issueCreatingGrid -> invoked on Line: 7")
            throw InvalidSizeException(errMsg.toString())
        }
        catch (e : Exception) {
            e.printStackTrace()
            exitProcess(1)
        }
    }
}
