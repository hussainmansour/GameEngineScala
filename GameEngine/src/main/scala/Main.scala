import javax.swing._
import scala.swing._
import java.awt.event.*
import GameEngine.*
import Games.Queen8.*
import Games.Checkers.*
import Games.Chess.*
import Games.Connect4.*
import Games.Sudoku.*
import Games.TicTacToe.*
import MainMenu.*
import GUI.*
import org.jpl7._

@main
def main(): Unit = {

  val label = mainMenu()
  for (comp <- label.getComponents) {
    comp match {
      case button : JButton =>
        button.addActionListener((_ : ActionEvent) => {
          button.getText match {
            case "Queen-8" => startGame(queenInit, queenDrawer,queenController)
            case "Chess" => startGame(chessInit, chessDrawer, chessController)
            case "Checkers" => startGame(checkersInit, checkersDrawer, checkersController)
            case "Connect-4" => startGame(connect4Init, connect4Drawer, connect4Controller)
            case "Sudoku" => startGame(sudokuInit, sudokuDrawer, sudokuController)
            case "Tic-Tac-Toe" => startGame(ticTacToeInit, ticTacToeDrawer, ticTacToeController)
          }
        })
      case _ =>
    }
  }

}

