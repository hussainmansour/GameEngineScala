package GameEngine

import GUI.*

import java.awt.FlowLayout
import javax.swing.JFrame

  def startGame(initGame : () => (Array[Array[String]], Int),
                drawGame : ((Array[Array[String]], Int)) => Unit,
                controlGame : (String, (Array[Array[String]], Int)) => ((Array[Array[String]], Int), Boolean)) : Unit = {

    val inputFrame = newFrame("Input",400,100)
    val inputButton = newButton("Enter")
    val inputField = newTextField()
    inputFrame.setLayout(new FlowLayout())
    inputFrame.add(inputField)
    inputFrame.add(inputButton)
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

  }





