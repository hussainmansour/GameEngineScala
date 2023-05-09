package Games.Queen8
import GUI.*
import java.awt.{Color, Font, GridLayout}
import javax.swing.{JButton, JFrame, JLabel, SwingConstants}

def queenInit() : (Array[Array[String]], Int) = {
  val board = Array.ofDim[String](9,9)
  for(i <- 1 until 9; j <- 1 until 9) {
    (i % 2, j % 2) match {
      case (0,0) => board(i)(j) = "w "
      case (1,1) => board(i)(j) = "w "
      case (1,0) => board(i)(j) = "b "
      case (0,1) => board(i)(j) = "b "
    }
  }
  (board,1)
}

def queenDrawer(state : (Array[Array[String]], Int)) : Unit = {

  val mainFrame : JFrame = getMainFrame("Games")
  val containerLabel = newLabel(0,0,1100,800,"./pics/game.png")
  mainFrame.add(containerLabel)
  val innerLabel = newLabel(165,50,700,650,"")
  innerLabel.setLayout(new GridLayout(9,9,2,2))
  val grid : Array[Array[JLabel]] = Array.ofDim[JLabel](9,9)
  drawBoard(grid,state)

  for(i <- 0 until 9; j <- 0 until 9)
    innerLabel.add(grid(i)(j))

  containerLabel.add(innerLabel)
  mainFrame.setVisible(true)

}

def queenController(move : String, state : (Array[Array[String]], Int)) : ((Array[Array[String]], Int),Boolean) = {

  if(!checkInput(move) )
    return ((state._1,state._2), false)

  val board = state._1
  val (x,y) = getPoint(move)
  var valid = true
  val offset = List((1,0),(-1,0),(0,1),(0,-1),(1,1),(-1,-1),(-1,1),(1,-1))

  for(pair <- offset)
      valid &&= validate(board,x,y,pair._1,pair._2)

  if(board(x)(y).charAt(1) == '♛')
    board(x)(y) = board(x)(y).updated(1,' ')
  else if(valid && board(x)(y).charAt(1) != '♛')
    board(x)(y) = board(x)(y).updated(1,'♛')

  ((board,state._2), valid)
}

def drawBoard(grid : Array[Array[JLabel]], state : (Array[Array[String]], Int)) : Unit = {
  grid(0)(0) = newBoardCell("",null,Color.LIGHT_GRAY,false,20)

  for(i <- 1 until 9)
    grid(i)(0) = newBoardCell(i.toString,null,Color.LIGHT_GRAY,false,20)

  for(i <- 1 until 9)
    grid(0)(i) = newBoardCell((i - 1 + 97).toChar.toString,null,Color.LIGHT_GRAY,false,20)

  for(i <- 1 until 9; j <- 1 until 9) {
    grid(i)(j) = newBoardCell(state._1(i)(j).charAt(1).toString,null,new Color(20,25,25),true,50)
    state._1(i)(j).charAt(0) match {
      case 'w' => grid(i)(j).setBackground(Color.WHITE)
      case 'b' => grid(i)(j).setBackground(new Color(68, 61, 98))
    }
  }
}

def validate(board : Array[Array[String]], x : Int, y : Int, dirX : Int, dirY : Int) : Boolean = {
  var valid = true
  var (i,j) = (x,y)
  while((i + dirX > 0) && (j + dirY > 0) && (i + dirX < 9) && (j + dirY < 9)) {
    if(board(i + dirX)(j + dirY).charAt(1) == '♛')
      valid = false
    i += dirX
    j += dirY
  }
  valid
}

def checkInput(move : String) : Boolean = move.length == 2 && move.charAt(0) > '0' && move.charAt(0) < '9' && move.charAt(1) >= 'a' && move.charAt(1) <= 'h'

def getPoint(move : String) : (Int,Int) = (move.charAt(0).toInt - 48,move.charAt(1) - 96)