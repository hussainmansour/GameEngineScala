package Games.Sudoku

def sudokuInit() : (Array[Array[String]], Int) = {
  var board = Array.ofDim[String](8,8)
  var turn = 1
  // initialize board
  (board,turn)
}

def sudokuDrawer(state : (Array[Array[String]], Int)) : Unit = {

}

def sudokuController(move : String, state : (Array[Array[String]], Int)) : ((Array[Array[String]], Int),Boolean) = {
  var valid = true
  var board = state._1
  var turn = state._2

  ((board,turn), valid)
}


