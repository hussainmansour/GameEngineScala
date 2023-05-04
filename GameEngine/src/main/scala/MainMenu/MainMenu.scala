package MainMenu
import GUI._
import java.awt.GridLayout
import javax.swing._

def mainMenu() : JLabel = {
  val mainFrame = newFrame("Games", 1100, 800)
  val containerLabel = newLabel(0,0,1100,800,"./pics/background.png")
  mainFrame.add(containerLabel)
  val innerLabel = newLabel(250,210,600,500,"")
  innerLabel.setLayout(new GridLayout(3,2,200,80))
  innerLabel.add(newButton("Chess"))
  innerLabel.add(newButton("Checkers"))
  innerLabel.add(newButton("Queen-8"))
  innerLabel.add(newButton("Connect-4"))
  innerLabel.add(newButton("Tic-Tac-Toe"))
  innerLabel.add(newButton("Sudoku"))
  containerLabel.add(innerLabel)
  mainFrame.setVisible(true)
  innerLabel
}

