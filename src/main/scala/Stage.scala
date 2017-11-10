import scalafx.Includes._
import scalafx.application.{JFXApp, Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.image.PixelWriter
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color

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

    val section = ComplexPlaneSection(-0.75, 0, canvas.width.value.toInt, canvas.height.value.toInt, 3.0)

    section.complexPoints.foreach { complexPoint =>
      val pw: PixelWriter = gc.pixelWriter
      pw.setColor(complexPoint.pixel.x, complexPoint.pixel.y, getColour(complexPoint.complex, 100))
    }

    val end = System.currentTimeMillis()
    println(s"Duration = ${end - start} ms")
  }

  // TODO move into its own class/object
  private def getColour(c: Complex, maxIterations: Int): Color = {
    val maxColour = 255

    @annotation.tailrec
    def go(z: Complex = Complex(0, 0), iteration: Int = 0): Color = {
      if (iteration > maxIterations) return Color.rgb(0, 0, 0) // Black
      val next: Complex = (z * z) + c
      if (next.absSquared > 4) {
        val r = iteration % maxColour
        val g = (iteration * 2) % maxColour
        val b = (iteration * 3) % maxColour
        return Color.rgb(r, g, b)
      }
      go(next, iteration + 1)
    }

    go()
  }
}
