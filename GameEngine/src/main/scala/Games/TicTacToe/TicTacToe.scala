package Games.TicTacToe
import GUI.*
import java.awt.{Color, Font, GridLayout}
import javax.swing.{JButton, JFrame, JLabel, SwingConstants}

def ticTacToeInit() : (Array[Array[String]], Int) = {
  val board = Array.ofDim[String](4,4)
  val turn = 1
  // initialize board
  for (i <- 1 until  4) {
    for (j <- 1 until  4) {
      board(i)(j) = " "
    }
  }
  (board,turn)
}

def ticTacToeDrawer(state : (Array[Array[String]], Int)) : Unit = {

  val mainFrame: JFrame = getMainFrame("Games")
  val containerLabel = newLabel(0, 0, 1100, 800, "./pics/game.png")
  mainFrame.add(containerLabel)
  val innerLabel = newLabel(165, 50, 700, 650, "")
  innerLabel.setLayout(new GridLayout(4, 4, 2, 2))
  val grid: Array[Array[JLabel]] = Array.ofDim[JLabel](4, 4)
  drawBoard(grid,state)
  for (i <- 0 until 4; j <- 0 until 4)
    innerLabel.add(grid(i)(j))

  containerLabel.add(innerLabel)
  mainFrame.setVisible(true)

}

def drawBoard(grid : Array[Array[JLabel]], state : (Array[Array[String]], Int)) : Unit = {
  grid(0)(0) = newBoardCell("",null,Color.LIGHT_GRAY,false,20)

  for(i <- 1 until 4)
    grid(i)(0) = newBoardCell(i.toString,null,Color.LIGHT_GRAY,false,20)

  for(i <- 1 until 4)
    grid(0)(i) = newBoardCell((i - 1 + 97).toChar.toString,null,Color.LIGHT_GRAY,false,20)

  for(i <- 1 until 4; j <- 1 until 4) {
    grid(i)(j) = newBoardCell(state._1(i)(j).charAt(0).toString,null,new Color(20,25,25),true,50)

  }
}


def ticTacToeController(move : String, state : (Array[Array[String]], Int)) : ((Array[Array[String]], Int),Boolean) = {
  val valid = true
  val board = state._1
  val turn = state._2
  val (x, y) = getPoint(move)
  if ( checkInput(move) && board(x)(y) == " " ) {
    if (turn % 2 == 1)
      board(x)(y) = "X"
    else
      board(x)(y) = "O"
  }
  else
    return ((board,turn), !valid)
  ((board,turn), valid)
}

def checkInput(move : String) : Boolean = move.length == 2 && move.charAt(0) > '0' && move.charAt(0) < '4' && move.charAt(1) >= 'a' && move.charAt(1) <= 'c'
def getPoint(move : String) : (Int,Int) ={
  if(checkInput(move))
    return (move.charAt(0).toInt - 48,move.charAt(1) - 96)
(-1,-1)
}

