package Games.Queen8

def queenInit() : (Array[Array[String]], Int) = {
  var board = Array.ofDim[String](8,8)
  var turn = 1

  (board,turn)
}

def queenDrawer(state : (Array[Array[String]], Int)) : Unit = {

}

def queenController(move : String, state : (Array[Array[String]], Int)) : ((Array[Array[String]], Int),Boolean) = {
  var valid = true
  var board = state._1
  var turn = state._2

  ((board,turn), valid)
}





