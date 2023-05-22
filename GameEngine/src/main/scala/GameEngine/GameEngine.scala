package GameEngine

import GUI.*
import org.jpl7._

import java.awt.FlowLayout
import javax.swing.JFrame

  def startGame(initGame : () => (Array[Array[String]], Int),
                drawGame : ((Array[Array[String]], Int)) => Unit,
                controlGame : (String, (Array[Array[String]], Int)) => ((Array[Array[String]], Int), Boolean)) : Unit = {

    val inputFrame = newFrame("Input",600,150)
    inputFrame.setLocation(1400,500)
    val inputButton = newButton("Enter")
    val solveButton = newButton("Solve")
    val inputField = newTextField()
    inputFrame.setLayout(new FlowLayout())
    inputFrame.add(inputField)
    inputFrame.add(inputButton)
    inputFrame.add(solveButton)
    inputFrame.setVisible(true)

    val state = initGame()
    drawGame(state)
    var board = state._1
    var turn = state._2

    inputButton.addActionListener(_ => {
      val (newState, valid) = controlGame(inputField.getText, (board,turn))
      if(valid) {
        drawGame(newState)
        board = newState._1
        turn += 1
        inputField.setText("")
      } else {
        inputField.setText("Invalid input")
      }
    })

//    solveButton.addActionListener(_ => {
//      val q1 = new Query("consult('D:/Github Repos/GameEngineScala/GameEngine/src/main/scala/8Queen.pl')")
//      println("consult "+(if (q1.hasSolution) "succesful" else "failed"))
//      val q = new Query("n_queens(8,Q).")
//      println(q.hasSolution)
//
//      val sol = q.oneSolution().get("Q").toString
//      println(sol)
//    })
    solveButton.addActionListener(_ => {
      val q1 = new Query("consult('D:/Github Repos/GameEngineScala/GameEngine/src/main/scala/Sudoku.pl')")
      println("consult " + (if (q1.hasSolution) "succesful" else "failed"))
      val q = new Query("Rows = [[6,_,2,3,1,4,7,8,9],[_,_,8,2,_,_,_,5,3],[_,7,3,_,8,5,2,6,4],[_,_,5,_,9,8,_,_,6],[7,_,6,_,_,2,_,9,5],[_,_,9,6,_,3,8,7,1],[_,9,_,_,_,_,5,3,2],[_,3,_,8,2,9,6,_,7],[2,6,7,5,3,_,_,4,8]],sudoku(Rows), maplist(label, Rows)."
      )
      println(q.hasSolution)

      val sol = q.oneSolution().get("Rows").toString
      println(sol)
    })
  }





