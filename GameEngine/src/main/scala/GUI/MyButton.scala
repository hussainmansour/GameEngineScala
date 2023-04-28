package GUI

import javax.swing.JButton
import java.awt.{Color, Font}
import java.awt.event.{MouseAdapter, MouseEvent}
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints

class MyButton(var text : String, var x_ : Int, var y_ : Int) extends JButton{

  var color: Color = Color.LIGHT_GRAY
  var colorOver = new Color(93, 17, 57,50)
  var colorClick = new Color(96, 90, 98)
  var borderColor = new Color(68, 61, 98)
  var over = false
  var radius = 0

  this.setText(text)
  this.setColor(color)
  this.setBorderColor(borderColor)
  this.setColorClick(borderColor)
  this.setBackground(borderColor)
  this.setColorOver(colorOver)
  this.setForeground(color)
  this.setBorder(null)
  this.setFocusable(false)
  this.setFont(new Font("Monospace", Font.BOLD, 30))
  this.setContentAreaFilled(false)
  this.setBounds(x_,y_,250,120)
  this.setRadius(50)

  this.addMouseListener(new MouseAdapter() {
    override def mouseEntered(me: MouseEvent ): Unit = {
      setBackground(colorOver)
      over = true
    }

    override def mouseExited(me: MouseEvent): Unit = {
      setBackground(borderColor)
      over = false
    }

    override def mousePressed(me: MouseEvent): Unit = {
      setBackground(colorClick)
    }

    override def mouseReleased(me: MouseEvent): Unit = {
      if (over) setBackground(colorOver)
      else setBackground(color)
    }
  })


  def isOver: Boolean = over

  def setOver(over: Boolean): Unit = {
    this.over = over
  }

  def setColor(color: Color): Unit = {
    this.color = color
    setBackground(color)
  }

  def getColorOver: Color = colorOver

  def setColorOver(colorOver: Color): Unit = {
    this.colorOver = colorOver
  }

  def getBorderColor: Color = borderColor

  def getColorClick: Color = colorClick

  def setColorClick(colorClick: Color): Unit = {
    this.colorClick = colorClick
  }

  def setBorderColor(borderColor: Color): Unit = {
    this.borderColor = borderColor
  }

  def getRadius: Int = radius

  def setRadius(radius: Int): Unit = {
    this.radius = radius
  }

  override protected def paintComponent(graphics: Graphics): Unit = {
    val g2 = graphics.asInstanceOf[Graphics2D]
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    //  Paint Border
    g2.setColor(borderColor)
    g2.fillRoundRect(0, 0, getWidth, getHeight, radius, radius)
    g2.setColor(getBackground)
    //  Border set 2 Pix
    g2.fillRoundRect(2, 2, getWidth - 4, getHeight - 4, radius, radius)
    super.paintComponent(graphics)
  }

}
