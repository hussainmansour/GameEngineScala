package Games.Sudoku
import scala.util.Random

val boardSize = 9
val subGridSize = 3

def generateBoard(): Array[Array[Int]] = {
  val board = Array.ofDim[Int](boardSize, boardSize)
  fillBoard(board)
  board
}

def fillBoard(board: Array[Array[Int]]): Boolean = {
  val emptyCell = findEmptyCell(board)
  if (emptyCell.isEmpty) {
    true
  } else {
    val (row, col) = emptyCell.get
    val numbers = Random.shuffle(1 to boardSize).toArray
    numbers.exists { num =>
      if (isValidMove(board, row, col, num)) {
        board(row)(col) = num
        if (fillBoard(board)) {
          true
        } else {
          board(row)(col) = 0
          false
        }
      } else {
        false
      }
    }
  }
}

def findEmptyCell(board: Array[Array[Int]]): Option[(Int, Int)] = {
  for {
    row <- 0 until boardSize
    col <- 0 until boardSize
    if board(row)(col) == 0
  } return Some((row, col))
  None
}

def isValidMove(board: Array[Array[Int]], row: Int, col: Int, num: Int): Boolean = {
  !usedInRow(board, row, num) && !usedInColumn(board, col, num) && !usedInSubGrid(board, row - row % subGridSize, col - col % subGridSize, num)
}

def usedInRow(board: Array[Array[Int]], row: Int, num: Int): Boolean = {
  board(row).contains(num)
}

def usedInColumn(board: Array[Array[Int]], col: Int, num: Int): Boolean = {
  board.exists(row => row(col) == num)
}

def usedInSubGrid(board: Array[Array[Int]], startRow: Int, startCol: Int, num: Int): Boolean = {
  for {
    row <- 0 until subGridSize
    col <- 0 until subGridSize
  } {
    if (board(row + startRow)(col + startCol) == num) {
      return true
    }
  }
  false
}

def printBoard(board: Array[Array[Int]]): Unit = {
  for (row <- board) {
    println(row.mkString(" "))
  }
}

def main(args: Array[String]): Unit = {
  val board = generateBoard()
  printBoard(board)
}

