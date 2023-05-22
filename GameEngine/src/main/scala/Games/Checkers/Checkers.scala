package Games.Checkers
import GUI.*
import java.awt.{Color, Font, GridLayout}
import javax.swing.{JButton, JFrame, JLabel, SwingConstants}

def checkersInit() : (Array[Array[String]], Int) = {
  val board = Array.ofDim[String](9, 9)
  for (i <- 1 until 9; j <- 1 until 9) {
    (i % 2, j % 2) match {
      case (0, 0) => board(i)(j) = "w "
      case (1, 1) => board(i)(j) = "w "
      case (1, 0) => board(i)(j) = "b "
      case (0, 1) => board(i)(j) = "b "
    }
    if(i < 4 && board(i)(j) == "b ")
      board(i)(j) = board(i)(j).updated(1,0x26C0)
    else if(i > 5 && board(i)(j) == "b ")
      board(i)(j) = board(i)(j).updated(1,0x26C2)
  }
  (board, 1)
}

def checkersDrawer(state : (Array[Array[String]], Int)) : Unit = {

  val mainFrame: JFrame = getMainFrame("Games")
  val containerLabel = newLabel(0, 0, 1100, 800, "./pics/game.png")
  mainFrame.add(containerLabel)
  val innerLabel = newLabel(165, 50, 700, 650, "")
  innerLabel.setLayout(new GridLayout(9, 9, 2, 2))
  val grid: Array[Array[JLabel]] = Array.ofDim[JLabel](9, 9)
  drawBoard(grid, state)
  for (i <- 0 until 9; j <- 0 until 9)
    innerLabel.add(grid(i)(j))
  containerLabel.add(innerLabel)
  mainFrame.setVisible(true)
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

def checkersController(move : String, state : (Array[Array[String]], Int)) : ((Array[Array[String]], Int),Boolean) = {
  val valid = true
  val board = state._1
  val turn = state._2
  if(checkInput(move)) {
    val p = getPoint(move)
    val x1 = p._1
    val y1 = p._2
    val x2 = p._3
    val y2 = p._4
    if(validate(board,x1,y1,x2,y2,turn)){
      board(x2)(y2) = board(x1)(y1)
      board(x1)(y1) = "b "
      if(board(x2)(y2).charAt(1) == 0x26C0 && x2 == 8)
        board(x2)(y2) = board(x2)(y2).updated(1,0x26C1)
      else if(board(x2)(y2).charAt(1) == 0x26C2 && x2 == 1)
        board(x2)(y2) = board(x2)(y2).updated(1,0x26C3)
      return ((board,turn), valid)
    }
    else if(checkJump(board,x1,y1,x2,y2,turn)){
      board(x2)(y2) = board(x1)(y1)
      board(x1)(y1) = "b "
      board(math.abs(x1+x2)/2)(math.abs(y1+y2)/2) = "b "
      if(board(x2)(y2).charAt(1) == 0x26C0 && x2 == 8)
        board(x2)(y2) = board(x2)(y2).updated(1, 0x26C1)
      else if(board(x2)(y2).charAt(1) == 0x26C2 && x2 == 1)
        board(x2)(y2) = board(x2)(y2).updated(1, 0x26C3)
      return ((board,turn), valid)
    }
  }
  ((board,turn), !valid)
}

def validate(board : Array[Array[String]], x : Int, y : Int, dirX : Int, dirY : Int, turn : Int) : Boolean = {
  //check for player try to move opponent's piece
  if((board(x)(y).charAt(1) == 0x26C2 || board(x)(y).charAt(1) == 0x26C3)&& turn % 2 == 0) {
    println("here1")
    return false
  }
  //check for player try to move opponent's piece
  if((board(x)(y).charAt(1) == 0x26C0 || board(x)(y).charAt(1) == 0x26C1)&& turn % 2 == 1) {
    println("here2")
    return false
  }
  if(board(dirX)(dirY).charAt(1) != ' ') {
    println("here3")
    return false
  }
  // check for player try to move piece in wrong direction
  //ordinary move
  if(board(x)(y).charAt(1) == 0x26C0 && (dirX - x <= 0 || dirY - y > 1 || dirY - y < -1 || dirY == y)) {
    println("here4")
    return false
  }
  //ordinary move
  if (board(x)(y).charAt(1) == 0x26C2 && (dirX - x >= 0 || dirY - y > 1 || dirY - y < -1 || dirY == y)) {
    println("here5")
    return false
  }

  //king move
  if(board(x)(y).charAt(1) == 0x26C1 && (math.abs(dirX - x) != 1 || math.abs(dirY - y) != 1)) {
    println("here6")
    return false
  }
  //king move
  if(board(x)(y).charAt(1) == 0x26C3 && (math.abs(dirX - x) != 1 || math.abs(dirY - y) != 1)) {
    println("here7")
    return false
  }

  true
}

// check for jumping over opponent's piece
def checkJump(board : Array[Array[String]], x : Int, y : Int, dirX : Int, dirY : Int, turn : Int) : Boolean = {
  if(board(dirX)(dirY).charAt(1) != ' ' || board((x+dirX)/2)(math.abs(y+dirY)/2).charAt(1) == ' ') {
    return false
  }
  // check for player try to move piece in wrong direction
  if(board(x)(y).charAt(1) == 0x26C0 && (math.abs(x-dirX) != 2  || math.abs(y-dirY) != 2 || board((x+dirX)/2)(math.abs(y+dirY)/2).charAt(1) == 0x26C0 || board((x+dirX)/2)(math.abs(y+dirY)/2).charAt(1) == 0x26C1)) {
    println("here6")
    return false
  }

  if(board(x)(y).charAt(1) == 0x26C2 && (math.abs(x-dirX) != 2 || math.abs(y-dirY) != 2 || board((x+dirX)/2)(math.abs(y+dirY)/2).charAt(1) == 0x26C2 || board((x+dirX)/2)(math.abs(y+dirY)/2).charAt(1) == 0x26C3)) {
    println("here7")
    return false
  }

  //king move
  if(board(x)(y).charAt(1) == 0x26C1 && (math.abs(x-dirX) != 2 || math.abs(y-dirY) != 2 || board((x+dirX)/2)(math.abs(y+dirY)/2).charAt(1) == 0x26C0 || board((x+dirX)/2)(math.abs(y+dirY)/2).charAt(1) == 0x26C1)) {
    println("here8")
    return false
  }
  //king move
  if(board(x)(y).charAt(1) == 0x26C3 && (math.abs(x-dirX) != 2 || math.abs(y-dirY) != 2 || board((x+dirX)/2)(math.abs(y+dirY)/2).charAt(1) == 0x26C2 || board((x+dirX)/2)(math.abs(y+dirY)/2).charAt(1) == 0x26C3)) {
    println("here9")
    return false
  }

  true
}

def checkInput(move : String) : Boolean = move.length == 4 && (move.charAt(0) > '0' && move.charAt(0) < '9' && move.charAt(1) >= 'a' && move.charAt(1) <= 'h') &&
                                                              (move.charAt(2) > '0' && move.charAt(2) < '9' && move.charAt(3) >= 'a' && move.charAt(3) <= 'h')

def getPoint(move : String) : (Int,Int,Int,Int) = (move.charAt(0).toInt - 48, move.charAt(1) - 96, move.charAt(2).toInt - 48, move.charAt(3) - 96)


