import scalafx.Includes._
import scalafx.application.{JFXApp, Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.scene.layout.BorderPane

object Stage extends JFXApp {

  private val canvas: Canvas = new Canvas()
  private val gc: GraphicsContext = canvas.graphicsContext2D

  stage = new PrimaryStage {
    title.value = "Mandelbrot"
    scene = new Scene(800, 600) {
      val borderPane: BorderPane = new BorderPane {
        center = canvas
      }
      root = borderPane

      onKeyPressed = (k: KeyEvent) => k.code match {
        case KeyCode.Space => draw()
        case KeyCode.Q => Platform.exit()
        case _ =>
      }

      onMouseClicked = (e: MouseEvent) => println(e)

      // Binds the canvas size to the window size
      canvas.width <== borderPane.width
      canvas.height <== borderPane.height
    }
  }

  private def draw(): Unit = {
    println("Drawing")
    val start = System.currentTimeMillis()
    val c = MCanvas(-0.75, 0, canvas.width.value.toInt, canvas.height.value.toInt, 3.0, 200)

    for {
      i <- 0 until canvas.width.value.toInt
      j <- 0 until canvas.height.value.toInt
    } {
      gc.fill = c.pixels(i)(j).toScalaFXColor
      gc.fillRect(i, j, 1, 1)
    }
    val end = System.currentTimeMillis()
    println(s"Duration = ${end - start} ms")
  }
}
