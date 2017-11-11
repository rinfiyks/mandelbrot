import java.io.File
import javax.imageio.ImageIO

import rx._

import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}
import scalafx.embed.swing.SwingFXUtils
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.image.{PixelWriter, WritableImage}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseButton, MouseEvent}
import scalafx.scene.layout.BorderPane
import scalafx.stage.FileChooser

object Stage extends JFXApp {
  self =>

  private val initialWidth = 600
  private val initialHeight = 480

  private val canvas: Canvas = new Canvas()
  private val pw: PixelWriter = canvas.graphicsContext2D.pixelWriter

  private val dimensions = Var(PlaneDimensions(initialWidth, initialHeight))

  // The maximum number of iterations before assuming a point is in the set
  private val maxIterations = Var(100)

  // A location on the complex plane and a zoom level (smaller number means more zoomed in)
  private val location = Var(Location(Complex(-0.75, 0), 3.0))

  // A list of complex points along with which pixel they belong to given a location and a canvas
  private val complexPlaneSection: Rx[ComplexPlaneSection] = Rx {
    ComplexPlaneSection(location(), dimensions())
  }

  // Create some on-change triggers that will draw the set to the canvas
  maxIterations.triggerLater(draw())
  complexPlaneSection.triggerLater(draw())

  stage = new PrimaryStage {
    title.value = "Mandelbrot"
    scene = new Scene(initialWidth, initialHeight) {
      val borderPane: BorderPane = new BorderPane {
        center = canvas
      }
      root = borderPane

      onKeyPressed = (k: KeyEvent) => k.code match {
        case KeyCode.Period => maxIterations() = maxIterations.now * 2
        case KeyCode.Comma => maxIterations() = maxIterations.now / 2
        case KeyCode.D | KeyCode.R =>
          val previousDimensions = dimensions.now
          dimensions() = PlaneDimensions(canvas.width.value.toInt, canvas.height.value.toInt)
          if (previousDimensions == dimensions.now) draw()
        case KeyCode.Q => Platform.exit()
        case KeyCode.S => saveImage
        case _ =>
      }

      onMouseClicked = (e: MouseEvent) => {
        val point = complexPlaneSection.now.complexPoints.find(p => p.pixel.x == e.x.toInt && p.pixel.y == e.y.toInt).get
        val newRe = point.complex.re
        val newIm = point.complex.im
        val newZoom = e.button match {
          case MouseButton.Primary => location.now.zoom / 4
          case MouseButton.Secondary => location.now.zoom * 4
          case _ => location.now.zoom
        }
        location() = Location(Complex(newRe, newIm), newZoom)
      }

      // Binds the canvas size to the window size
      canvas.width <== borderPane.width
      canvas.height <== borderPane.height
    }
  }

  private def draw(): Unit = {
    val start = System.currentTimeMillis()

    println(s"[${dimensions.now.width} x ${dimensions.now.height}] [${location.now.point}] [z: ${location.now.zoom}] [i: ${maxIterations.now}]")
    complexPlaneSection.now.complexPoints.foreach { complexPoint =>
      pw.setColor(complexPoint.pixel.x, complexPoint.pixel.y, complexPoint.getColour(maxIterations.now))
    }

    val end = System.currentTimeMillis()
    println(s"Duration = ${end - start} ms")
    println()
  }

  private def saveImage(): Unit = {
    val fileChooser: FileChooser = new FileChooser()
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"))
    val file = fileChooser.showSaveDialog(self.stage)
    if (file == null) return // needed if you click cancel on the dialog
    val fileWithExt: File = new File(file.getAbsolutePath + ".png")
    val image = new WritableImage(dimensions.now.width, dimensions.now.height)
    canvas.snapshot(null, image)
    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", fileWithExt)
  }

}
