package GUI
import javax.swing._
import java.awt.event._

class Frame extends JFrame with ActionListener{

  this.setTitle("Games")
  this.setSize(1100, 800)
  this.setLocationRelativeTo(null)
  this.setResizable(false)
  this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  val image: ImageIcon = new ImageIcon(".\\pics\\logo.png")
  this.setIconImage(image.getImage)
  this.setVisible(true)
  var sudo = new MyButton("Sudoku",200,230)
  var chess = new MyButton("Chess",200,410)
  var checkers = new MyButton("Checkers",640,410)
  var queen = new MyButton("8-Queen",200,590)
  var connect = new MyButton("Connect-4",640,230)
  var tic = new MyButton("Tic-Tac-Toe",640,590)

  def mainScreen(): Unit = {

    val label = new JLabel
    label.setIcon(new ImageIcon(".\\pics\\background.png"))
    label.setBounds(0, 0, 1100, 800)
    this.sudo.addActionListener(this)
    this.chess.addActionListener(this)
    this.checkers.addActionListener(this)
    this.queen.addActionListener(this)
    this.connect.addActionListener(this)
    this.tic.addActionListener(this)

    label.add(this.sudo)
    label.add(this.chess)
    label.add(this.queen)
    label.add(this.connect)
    label.add(this.checkers)
    label.add(this.tic)

    this.add(label)
    this.setVisible(true)

  }

  override def actionPerformed(e: ActionEvent): Unit = {
    if (e.getSource == this.sudo) {
      println("sudoku")
    }
    if(e.getSource == this.chess) {
      println("chess")
    }
    if(e.getSource == this.checkers) {
      println("checkers")
    }
    if(e.getSource == this.tic) {
      println("tic")
    }
    if(e.getSource == this.queen) {
      println("queen")
    }
    if(e.getSource == this.connect) {
      println("connect")
    }
  }
}


