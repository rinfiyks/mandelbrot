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
