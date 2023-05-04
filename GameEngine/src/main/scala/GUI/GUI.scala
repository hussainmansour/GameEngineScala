package GUI

import java.awt.{Color, Dimension, Font}
import javax.swing.{ImageIcon, JButton, JFrame, JLabel, JTextField, WindowConstants}

def newFrame(text : String, width : Int, height : Int): JFrame = {
  val frame: JFrame = new JFrame()
  frame.setTitle(text)
  frame.setSize(width, height)
  frame.setLocationRelativeTo(null)
  frame.setResizable(false)
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  val image: ImageIcon = new ImageIcon("./pics/logo.png")
  frame.setIconImage(image.getImage)
  frame
}

def newLabel(x_ : Int, y_ : Int, width : Int, height : Int, image : String) : JLabel = {
  val label = new JLabel()
  label.setIcon(new ImageIcon(image))
  label.setBounds(x_, y_, width, height)
  label
}

def newButton(text: String): JButton = {
  val button: JButton = new JButton()
  button.setText(text)
  button.setBackground(new Color(68, 61, 98))
  button.setForeground(Color.LIGHT_GRAY)
  button.setBorder(null)
  button.setFocusable(false)
  button.setFont(new Font("Monospace", Font.BOLD, 30))
  button.setContentAreaFilled(true)
  button
}

def newTextField(): JTextField = {
  val inputField = new JTextField(10)
  inputField.setPreferredSize(new Dimension(300, 50))
  inputField.setFont(new Font("Monospace", Font.BOLD, 30))
  inputField
}

