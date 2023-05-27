package Games.Sudoku
import GUI.*

import scala.util.Random
import java.awt.{Color, Font, GridLayout}
import javax.swing.{JFrame, JLabel}
import org.jpl7.*

def sudokuInit() : (Array[Array[String]], Int) = (generateRandomBoard(),1)

def sudokuDrawer(state : (Array[Array[String]], Int)) : Unit = {
  val mainFrame : JFrame = getMainFrame("Games")
  val containerLabel = newLabel(0,0,1100,800,"./pics/game.png")
  mainFrame.add(containerLabel)
  val solveButton = newButton("Solve")
  solveButton.setBounds(850,600,150,100)
  val innerLabel = newLabel(70,50,700,650,"")
  innerLabel.setLayout(new GridLayout(10,10,1,1))
  val grid : Array[Array[JLabel]] = Array.ofDim[JLabel](10,10)
  drawBoard(grid,state)

  for(i <- 0 until 10; j <- 0 until 10)
    innerLabel.add(grid(i)(j))

  containerLabel.add(solveButton)
  containerLabel.add(innerLabel)

  mainFrame.setVisible(true)

  solveButton.addActionListener(_ => {
    solveSudoku(state._1)
  })

}

def sudokuController(move : String, state : (Array[Array[String]], Int)) : ((Array[Array[String]], Int),Boolean) = {

  val board = state._1
  val input = move.split(" ")

  if(input.length != 2 || input(0).length != 2 || input(1).length != 1 || !checkInput(input(0),input(1)))
    return ((board,state._2), false)

  val (x,y) = getPoint(input(0))
  if(board(x)(y).charAt(0) != ' ' && board(x)(y).charAt(1) != 'M') {
    board(x)(y) = board(x)(y).updated(0,' ')
    return ((board,state._2), true)
  }

  var valid = true
  val offset = List((1,0),(-1,0),(0,1),(0,-1))
  for(pair <- offset)
    valid &&= validate(board,x,y,pair._1,pair._2,input(1))

  val (newX,newY) = getBox(x-1,y-1)
  valid &&= validateBox(board,newX,newY,input(1))

  if(valid) {
    val c = input(1).toCharArray
    board(x)(y) = board(x)(y).updated(0,c(0))
  }
  ((board,state._2), valid)
}

def drawBoard(grid : Array[Array[JLabel]], state : (Array[Array[String]], Int)) : Unit = {
  grid(0)(0) = newBoardCell("",null,Color.LIGHT_GRAY,false,20)

  for(i <- 1 until 10)
    grid(i)(0) = newBoardCell(i.toString,null,Color.LIGHT_GRAY,false,20)

  for(i <- 1 until 10)
    grid(0)(i) = newBoardCell((i - 1 + 97).toChar.toString,null,Color.LIGHT_GRAY,false,20)

  for(i <- 1 until 10; j <- 1 until 10)
    grid(i)(j) = newBoardCell(state._1(i)(j).charAt(0).toString,new Color(230,230,240), new Color(20,25,25),true,30)

}

def validate(board : Array[Array[String]], x : Int, y : Int, dirX : Int, dirY : Int, value : String) : Boolean = {
  var valid = true
  var (i,j) = (x,y)
  while((i + dirX > 0) && (j + dirY > 0) && (i + dirX < 10) && (j + dirY < 10)) {
    if(board(i + dirX)(j + dirY).charAt(0).toString == value)
      valid = false
    i += dirX
    j += dirY
  }
  valid
}

def validateBox(board : Array[Array[String]], x : Int, y : Int, value : String) : Boolean = {
  var valid = true
  val (row,col) : (Int,Int) = (x + 2, y + 2)
  for(i <- x to row; j <- y to col) {
    if(board(i)(j).charAt(0).toString == value)
      valid = false
  }
  valid
}

def generateRandomBoard() : Array[Array[String]] = {

  val random = new Random()
  val board = generateBoard()
  val grid = Array.ofDim[String](10, 10)

  for(i <- 0 to 9; j <- 0 to 9) {
    if(i == 0 || j == 0) {
      grid(i)(j) = "  "
    }
    else {
      grid(i)(j) = board(i - 1)(j - 1).toString.concat("M")
    }
  }

  val noOfCellsToBeRemoved = 81 - (random.nextInt(14) + 20)

  var i = 1
  while (i <= noOfCellsToBeRemoved) {
    val row = random.nextInt(9) + 1
    val col = random.nextInt(9) + 1
    if(grid(row)(col) != "  ") {
      grid(row)(col) = "  "
    } else {
      i -= 1
    }
    i += 1
  }
  grid
}

def getBox(x : Int, y : Int) : (Int, Int) = (x / 3 * 3 + 1, y / 3 * 3 + 1)

def checkInput(move : String, value : String) : Boolean = move.length == 2 && move.charAt(0) > '0' && move.charAt(0) <= '9' && move.charAt(1) >= 'a' && move.charAt(1) <= 'i'
                                                          && value.length == 1 && value >= "1" && value <= "9"
def getPoint(move : String) : (Int,Int) = (move.charAt(0).toInt - 48,move.charAt(1) - 96)

def solveSudoku(state : Array[Array[String]]) : Unit = {

  val q1 = new Query("consult('E:/CSED25/2nd Year/Second term/Programming Paradigms/Phase2/GameEngineScala/GameEngine/src/main/scala/Sudoku.pl')")
  println("consult " + (if (q1.hasSolution) "succesful" else "failed"))

  val input = initSolve(state)
  printf(input)

  val q = new Query(s"Rows = $input,sudoku(Rows), maplist(label, Rows).")
  println(q.hasSolution)

  if(!q.hasSolution) {
    println("No Solution exist!")
    val frame: JFrame = new JFrame()
    frame.setTitle("Solution")
    frame.setSize(350, 200)
    frame.setLocationRelativeTo(null)
    frame.setResizable(false)
    val noSolution = newLabel(25,5,100,100,null)
    noSolution.setText("No Solution exist!")
    noSolution.setFont(new Font("Monospace", Font.BOLD, 30))
    frame.add(noSolution)
    frame.setVisible(true)
    return
  }

  val sol = q.oneSolution().get("Rows").toString
  println(sol)

  val formattedSol = sol.substring(1, sol.length - 1)
  val rows = formattedSol.split(",")
  val allString = rows.mkString.replace("[", "").replace("]","").replace(" ","")

  for(i <- 0 until 9; j <- 0 until 9) {
    state(i + 1)(j + 1) = state(i + 1)(j + 1).updated(0,allString(9 * i + j))
  }
  
  sudokuDrawer((state,1))
}

def initSolve(state : Array[Array[String]]) : String = {
  val board = Array.ofDim[String](9, 9)
  for(i <- 1 to 9; j <- 1 to 9) {
    board(i - 1)(j - 1) = state(i)(j)
  }
  val input = "[" + board.map(row => "[" + row.map(element => if (element.trim.isEmpty) "_" else element).mkString(",") + "]").mkString(",").filter(_ != 'M') + "]"
  input
}