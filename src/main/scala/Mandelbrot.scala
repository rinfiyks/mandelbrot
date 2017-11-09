import scalafx.scene.paint.Color

case class RGBColour(r: Int, g: Int, b: Int) {
  def toScalaFXColor: Color = Color.rgb(r, g, b)
}

case class Pixel(x: Int, y: Int)

/*
MCanvas needs renaming, and should probably be a list/array of (Pixel, Complex)
the code to work out the colour should be elsewhere.
1. what should a class of (Pixel, Complex) be called?
  ComplexPoint?
2. and what is a list of them called?
  lattice/grid/section...
*/
case class MCanvas(pixels: Array[Array[RGBColour]])

object MCanvas {
  def apply(pixels: Array[Array[RGBColour]]): MCanvas = new MCanvas(pixels)

  def apply(centreX: Double, centreY: Double, width: Int, height: Int, zoom: Double, maxIterations: Int): MCanvas = {
    val matrix = Array.tabulate[RGBColour](width, height) { (w, h) =>
      val x = centreX + zoom * (w.toDouble / width - 0.5)
      val y = centreY + zoom * (h.toDouble / height - 0.5) * height.toDouble / width
      getColour(Complex(x, y), maxIterations) // bad - getColour shouldn't be coupled into this class
    }

    MCanvas(matrix)
  }

  private def getColour(c: Complex, maxIterations: Int): RGBColour = {
    val maxColour = 255

    @annotation.tailrec
    def go(z: Complex = Complex(0, 0), iteration: Int = 0): RGBColour = {
      if (iteration > maxIterations) return RGBColour(0, 0, 0) // Black
      val next: Complex = (z * z) + c
      if (next.absSquared > 4) {
        val r = iteration % maxColour
        val g = (iteration * 2) % maxColour
        val b = (iteration * 3) % maxColour
        return RGBColour(r, g, b)
      }
      go(next, iteration + 1)
    }

    go()
  }

}