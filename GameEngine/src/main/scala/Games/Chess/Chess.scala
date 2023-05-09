package Games.Chess
import GUI.*
import java.awt.{Color, GridLayout}
import javax.swing.{JFrame, JLabel}

def chessInit() : (Array[Array[String]], Int) = {
  val board: Array[Array[String]] = Array(
    Array("   ","   ","   ","   ","   ","   ","   ","   ","   "),
    Array("   ","w♜b","b♞b","w♝b","b♛b","w♚b","b♝b","w♞b","b♜b"),
    Array("   ","b♟b","w♟b","b♟b","w♟b","b♟b","w♟b","b♟b","w♟b"),
    Array("   ","w  ","b  ","w  ","b  ","w  ","b  ","w  ","b  "),
    Array("   ","b  ","w  ","b  ","w  ","b  ","w  ","b  ","w  "),
    Array("   ","w  ","b  ","w  ","b  ","w  ","b  ","w  ","b  "),
    Array("   ","b  ","w  ","b  ","w  ","b  ","w  ","b  ","w  "),
    Array("   ","w♙w","b♙w","w♙w","b♙w","w♙w","b♙w","w♙w","b♙w"),
    Array("   ","b♖w","w♘w","b♗w","w♕w","b♔w","w♗w","b♘w","w♖w")
  )
  (board,1)
}

def chessDrawer(state : (Array[Array[String]], Int)) : Unit = {
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

def chessController(move : String, state : (Array[Array[String]], Int)) : ((Array[Array[String]], Int),Boolean) = {
  val board = state._1
  val turn = state._2
  val input = move.split(" ")

  if(input.length != 2 || input(0).length != 2 || input(1).length != 2)
    return ((board,state._2), false)

  val (x1,y1) = getPoint(input(0))
  val (x2,y2) = getPoint(input(1))

  if(!(checkInput(input(0)) && checkInput(input(1))) || !isYourTurn((x1,y1), state) || state._1(x1)(y1).charAt(1) == ' ')
    return ((board,state._2), false)

  val piece : String = getPiece(board(x1)(y1).charAt(1))
  if(!validateAndUpdate(getPieceValidation(piece), (x1,y1), (x2,y2), board))
    return ((board,turn), false)

  ((board,turn), true)
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

def validateAndUpdate(pieceValidation: ((Int,Int), (Int,Int)) => Boolean,
                      from: (Int,Int), to: (Int,Int),
                      board: Array[Array[String]]) : Boolean = {

  if(board(from._1)(from._2).charAt(2) == board(to._1)(to._2).charAt(2))
    return false

  val piece : String = getPiece(board(from._1)(from._2).charAt(1))
  val valid = pieceValidation(from,to)

  if(!valid && piece != "WhitePawn" && piece != "BlackPawn")
    return false

  if((piece == "WhitePawn" || piece == "BlackPawn") && !valid) {
    if(piece == "WhitePawn" && !diagonalWhitePawn(from,to,board))
      return false
    else if(piece == "BlackPawn" && !diagonalBlackPawn(from,to,board))
      return false
  }

  board(to._1)(to._2) = board(to._1)(to._2).updated(1,board(from._1)(from._2).charAt(1))
  board(to._1)(to._2) = board(to._1)(to._2).updated(2,board(from._1)(from._2).charAt(2))
  board(from._1)(from._2) = board(from._1)(from._2).updated(1,' ')
  board(from._1)(from._2) = board(from._1)(from._2).updated(2,' ')

  true
}

def diagonalWhitePawn(from: (Int,Int), to: (Int,Int),
                      board: Array[Array[String]]) : Boolean = {
  val offset = List((-1,1),(-1,-1))
  for(pair <- offset)
    if (from._1 + pair._1 > 0 && from._2 + pair._2 > 0 && from._1 + pair._1 < 9 && from._2 + pair._2 < 9) {
      if(board(to._1)(to._2).charAt(1) != ' ' && board(to._1)(to._2).charAt(2) == 'b')
        return true
    }
  false
}

def diagonalBlackPawn(from: (Int,Int), to: (Int,Int),
  board: Array[Array[String]]) : Boolean = {
  val offset = List((1,1),(1,-1))
  for(pair <- offset)
    if (from._1 + pair._1 > 0 && from._2 + pair._2 > 0 && from._1 + pair._1 < 9 && from._2 + pair._2 < 9) {
      if(board(to._1)(to._2).charAt(1) != ' ' && board(to._1)(to._2).charAt(2) == 'w')
        return true
    }
  false
}

def validateMove(x1 : Int, y1 : Int, x2 : Int, y2 : Int, dirX : Int, dirY : Int) : Boolean = {
  var (i,j) = (x1,y1)
  while((i + dirX > 0) && (j + dirY > 0) && (i + dirX < 9) && (j + dirY < 9)) {
    if((i + dirX, j + dirY) == (x2, y2))
      return true
    i += dirX
    j += dirY
  }
  false
}

def validateRook(from : (Int,Int), to : (Int,Int)) : Boolean = {
  val offset = List((1,0),(-1,0),(0,1),(0,-1))
  for(pair <- offset)
    if (validateMove(from._1,from._2,to._1,to._2,pair._1,pair._2))
      return true
  false
}

def validateBishop(from : (Int,Int), to : (Int,Int)) : Boolean = {
  val offset = List((1,1),(-1,-1),(-1,1),(1,-1))
  for(pair <- offset)
    if (validateMove(from._1,from._2,to._1,to._2,pair._1,pair._2))
      return true
  false
}

def validateQueen(from : (Int,Int), to : (Int,Int)) : Boolean = validateBishop(from,to) || validateRook(from,to)

def validateKnight(from : (Int,Int), to : (Int,Int)) : Boolean = {
  val offset = List((2,1),(2,-1),(-2,1),(-2,-1),(-1,2),(1,2),(-1,-2),(1,-2))
  for(pair <- offset)
    if (from._1 + pair._1 == to._1 && from._2 + pair._2 == to._2)
      return true
  false
}

def validateKing(from : (Int,Int), to : (Int,Int)) : Boolean = {
  val offset = List((1,1),(-1,-1),(-1,1),(1,-1),(-1,0),(1,0),(0,1),(0,-1))
  for(pair <- offset)
    if (from._1 + pair._1 == to._1 && from._2 + pair._2 == to._2)
      return true
  false
}

def validateWhitePawn(from : (Int,Int), to : (Int,Int)) : Boolean = {
  val offset = List((-1,0))
  for(pair <- offset)
    if (from._1 + pair._1 == to._1 && from._2 + pair._2 == to._2)
      return true
  if(from._1 == 7) {
    val offset = List((-2,0))
    for(pair <- offset)
      if (from._1 + pair._1 == to._1 && from._2 + pair._2 == to._2)
        return true
  }
  false
}

def validateBlackPawn(from : (Int,Int), to : (Int,Int)) : Boolean = {
  val offset = List((1,0))
  for(pair <- offset)
    if (from._1 + pair._1 == to._1 && from._2 + pair._2 == to._2)
      return true
  if(from._1 == 2) {
    val offset = List((2,0))
    for(pair <- offset)
      if (from._1 + pair._1 == to._1 && from._2 + pair._2 == to._2)
        return true
  }
  false
}

def getPieceValidation(piece : String) : ((Int,Int), (Int,Int)) => Boolean = piece match {
  case "Rook" => validateRook
  case "Knight" => validateKnight
  case "Bishop" => validateBishop
  case "Queen" => validateQueen
  case "King" => validateKing
  case "BlackPawn" => validateBlackPawn
  case "WhitePawn" => validateWhitePawn
}

def checkInput(move : String) : Boolean = move.length == 2 && move.charAt(0) > '0' && move.charAt(0) <= '8' && move.charAt(1) >= 'a' && move.charAt(1) <= 'h'

def getPoint(move : String) : (Int,Int) = (move.charAt(0).toInt - 48,move.charAt(1) - 96)

def isYourTurn(point : (Int, Int), state : (Array[Array[String]], Int)) : Boolean = state._2 % 2 match {
  case 1 => state._1(point._1)(point._2).charAt(2) == 'w'
  case 0 => state._1(point._1)(point._2).charAt(2) == 'b'
}

def getPiece(shape : Char) : String = shape match {
  case '♜' | '♖' => "Rook"
  case '♞' | '♘' => "Knight"
  case '♝' | '♗' => "Bishop"
  case '♛' | '♕' => "Queen"
  case '♚' | '♔' => "King"
  case '♟' => "BlackPawn"
  case '♙' => "WhitePawn"
}
