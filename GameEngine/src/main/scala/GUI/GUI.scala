package GUI

import java.awt.{Color, Dimension, Font, Frame, Graphics, Graphics2D, RenderingHints, Window}
import javax.swing.{ImageIcon, JButton, JFrame, JLabel, JPanel, JTextField, SwingConstants, WindowConstants}
import scala.swing.{Component, Dimension}

def newFrame(text : String, width : Int, height : Int): JFrame = {
  val frame: JFrame = new JFrame()
  frame.setTitle(text)
  frame.setSize(width, height)
  frame.setLocationRelativeTo(null)
  frame.setResizable(false)
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  frame.setIconImage(new ImageIcon("./pics/logo.png").getImage)
  frame
}

def newCircleCell(opaque : Boolean, color : Color) : JPanel = {
  val panel = new JPanel() {
    override def paintComponent(g: Graphics): Unit = {
      super.paintComponent(g)
      val g2d = g.asInstanceOf[Graphics2D]
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      val x = (getWidth - 100) / 2 + 18
      val y = (getHeight - 100) / 2 + 18
      g2d.setColor(color)
      g2d.fillOval(x, y, 60, 60)
    }
  }
  panel.setBackground(new Color(60,60,80,120))
  panel.setOpaque(opaque)
  panel
}

def newLabel(x_ : Int, y_ : Int, width : Int, height : Int, image : String) : JLabel = {
  val label = new JLabel()
  label.setIcon(new ImageIcon(image))
  label.setBounds(x_, y_, width, height)
  label
}

def newPanel(text : String) : JPanel = {
  val panel = new JPanel()
  panel.setOpaque(false)
  val label = new JLabel();
  label.setText(text);
  label.setForeground(new Color(240,240,240))
  label.setFont(new Font("Monospace", Font.BOLD, 30))
  panel.add(label);
  panel
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

def newBoardCell(text : String, backgroundColor : Color, foregroundColor : Color, opaque : Boolean, fontSize : Int) : JLabel = {
  val label = new JLabel()
  label.setText(text)
  label.setHorizontalAlignment(SwingConstants.CENTER)
  label.setVerticalAlignment(SwingConstants.CENTER)
  label.setForeground(foregroundColor)
  label.setBackground(backgroundColor)
  label.setFont(new Font("Monospace", Font.TYPE1_FONT, fontSize))
  label.setOpaque(opaque)
  label
}

def getMainFrame(text : String) : JFrame = {
  val openWindows: Array[Window] = Window.getWindows
  var frame : JFrame = null
  for (window <- openWindows) {
    window match {
      case f: javax.swing.JFrame if f.getTitle == text =>
        frame = f
      case _ =>
    }
  }
  frame.getContentPane.removeAll()
  frame
}

