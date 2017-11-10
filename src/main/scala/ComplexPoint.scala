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
        val r = triangle(maxIterations * math.log(1 + iteration.toDouble / maxIterations))
        val g = triangle(maxIterations * math.log(1 + iteration.toDouble / maxIterations) * 2)
        val b = triangle(maxIterations * math.log(1 + iteration.toDouble / maxIterations) * 3)
        return Color.rgb(r, g, b)
      }
      go(next, iteration + 1)
    }

    // Makes the modulus result triangle-shaped /\/\/\ rather than sawtooth-shaped /|/|/|
    // for a smoother colour transition
    def triangle(i: Double): Int = maxColour - math.abs(i.toInt % (2 * maxColour) - maxColour)

    go()
  }

}
