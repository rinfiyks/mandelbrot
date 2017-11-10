import scalafx.scene.paint.Color

case class Pixel(x: Int, y: Int)

case class ComplexPoint(pixel: Pixel, complex: Complex) {

  def getColour(maxIterations: Int): Color = {
    val maxColour = 255

    @annotation.tailrec
    def go(z: Complex = Complex(0, 0), iteration: Int = 0): Color = {
      if (iteration > maxIterations) return Color.rgb(0, 0, 0) // Black
      val next: Complex = (z * z) + complex
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

case class ComplexPlaneSection(complexPoints: Seq[ComplexPoint])

object ComplexPlaneSection {

  def apply(complexPoints: Seq[ComplexPoint]): ComplexPlaneSection = new ComplexPlaneSection(complexPoints)

  def apply(centreX: Double, centreY: Double, width: Int, height: Int, zoom: Double): ComplexPlaneSection = {
    val complexPoints = Seq.tabulate[ComplexPoint](width, height) { (w, h) =>
      val x: Double = centreX + zoom * (w.toDouble / width - 0.5)
      val y: Double = centreY + zoom * (0.5 - h.toDouble / height) * height.toDouble / width
      ComplexPoint(Pixel(w, h), Complex(x, y))
    }.flatten
    ComplexPlaneSection(complexPoints)
  }

}