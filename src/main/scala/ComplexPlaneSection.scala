case class Pixel(x: Int, y: Int)

case class ComplexPoint(pixel: Pixel, complex: Complex)

case class ComplexPlaneSection(complexPoints: Seq[ComplexPoint])

object ComplexPlaneSection {

  def apply(complexPoints: Seq[ComplexPoint]): ComplexPlaneSection = new ComplexPlaneSection(complexPoints)

  def apply(centreX: Double, centreY: Double, width: Int, height: Int, zoom: Double): ComplexPlaneSection = {
    val complexPoints = Seq.tabulate[ComplexPoint](width, height){ (w, h) =>
      val x: Double = centreX + zoom * (w.toDouble / width - 0.5)
      val y: Double = centreY + zoom * (h.toDouble / height - 0.5) * height.toDouble / width
      ComplexPoint(Pixel(w, h), Complex(x, y))
    }.flatten
    ComplexPlaneSection(complexPoints)
  }

}