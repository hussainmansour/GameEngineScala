package Games.Connect4
import GUI.*

import java.awt.{Color, Font, GridLayout}
import javax.swing.{JButton, JFrame, JLabel, SwingConstants}
import scala.swing._

import javax.swing._
import java.awt._
import java.awt.{Color, Graphics2D}
import javax.swing.{JFrame, JLabel}
import scala.swing.Swing._
import scala.math.{cos, sin, Pi}


def connect4Init() : (Array[Array[String]], Int) = {
  val board = Array.ofDim[String](7,7)
  val turn = 1
  // initialize board
  for (i <- 0 until 7) {
    for (j <- 0 until 7) {
      board(i)(j) = " "
    }
  }
  (board,turn)
}

def connect4Drawer(state : (Array[Array[String]], Int)) : Unit = {

  val mainFrame: JFrame = getMainFrame("Games")
  val containerLabel = newLabel(0, 0, 1100, 800, "./pics/game.png")
  mainFrame.add(containerLabel)
  val innerLabel = newLabel(165, 50, 700, 650, "")
  innerLabel.setBackground(new Color(68,68,69))
  innerLabel.setLayout(new GridLayout(7, 7, 0, 1))
  val grid: Array[Array[JPanel]] = Array.ofDim[JPanel](7, 7)
  drawBoard(grid, state)
  for (i <- 0 until 7; j <- 0 until 7)
    innerLabel.add(grid(i)(j))

  containerLabel.add(innerLabel)
  mainFrame.setVisible(true)
}

def drawBoard(grid : Array[Array[JPanel]], state : (Array[Array[String]], Int)) : Unit = {

  grid(0)(0) = newCircleCell(false,null)

  for (i <- 0 until 7)
    grid(0)(i) = newPanel((i + 97).toChar.toString)

  for (i <- 1 until 7; j <- 0 until 7) {
//    grid(i)(j) = newBoardCell("", null, new Color(20, 25, 25), true, 50)
    grid(i)(j) = newCircleCell(true,null)
    grid(i)(j).setOpaque(true)
    state._1(i)(j).charAt(0) match {
      case 'y' => grid(i)(j).setForeground(new Color(255,0,0,150))
      case 'b' => grid(i)(j).setForeground(new Color(0,50,255,150))
      case _ =>
    }
  }
}


//class CircleLabel(text: String, color: Color, size: Int) extends JLabel(text) {
//  setPreferredSize(new Dimension(size, size))
//
//  override def paintComponent(g: Graphics): Unit = {
//    super.paintComponent(g)
//    val g2d = g.asInstanceOf[Graphics2D]
//    g2d.setColor(color)
//    g2d.fillOval(0, 0, getWidth, getHeight)
//  }
//}
//def connect4Drawer(state: (Array[Array[String]], Int)): Unit = {
//  val mainFrame: JFrame = getMainFrame("Games")
//  val containerLabel = newLabel(0, 0, 1100, 800, "./pics/game.png")
//  mainFrame.add(containerLabel)
//  val innerPanel = new JPanel(new GridLayout(7, 7, 2, 2))
//  val grid: Array[Array[CircleLabel]] = Array.ofDim[CircleLabel](7, 7)
//  drawBoard(grid, state)
//  for (i <- 0 until 7; j <- 0 until 7)
//    innerPanel.add(grid(i)(j))
//
//  containerLabel.add(innerPanel)
//  mainFrame.pack()
//  mainFrame.setVisible(true)
//
//}

//def drawBoard(grid: Array[Array[CircleLabel]], state: (Array[Array[String]], Int)): Unit = {
//  grid(0)(0) = new CircleLabel("", Color.LIGHT_GRAY, 20)
//
//  for (i <- 0 until 7)
//    grid(0)(i) = new CircleLabel((97 + i).toChar.toString, Color.LIGHT_GRAY, 20)
//
//  for (i <- 1 until 7; j <- 0 until 7) {
//    grid(i)(j) = new CircleLabel("", new Color(20, 25, 25), 50)
//    state._1(i)(j).charAt(0) match {
//      case 'y' => grid(i)(j).setBackground(Color.YELLOW)
//      case 'b' => grid(i)(j).setBackground(Color.BLUE)
//      case _ =>
//    }
//  }
//  println("Drawer")
//}
//

def connect4Controller(move : String, state : (Array[Array[String]], Int)) : ((Array[Array[String]], Int),Boolean) = {
  val board = state._1
  val turn = state._2
  val valid = false
  if(checkInput(move)) {
    val x = getPoint(move)
    val (valid, y) = validate(board, x)
    if (valid) {
      if (turn % 2 == 1)
        board(y)(x) = "y"
      else
        board(y)(x) = "b"
      return ((board, turn), valid)
    }
    else
      return ((board, turn), valid)
  }
  ((board,turn), valid)
}

def checkInput(move : String) : Boolean = move.length == 1 && move.charAt(0) >='a' && move.charAt(0) <= 'g'
def getPoint(move : String) : Int = move.charAt(0) - 97

def validate(array: Array[Array[String]], j: Int): (Boolean, Int) = {

  for (i <- 6 to 1 by -1) {
    if (array(i)(j) == " ")
      return (true, i)
  }
  (false, 0)
}
