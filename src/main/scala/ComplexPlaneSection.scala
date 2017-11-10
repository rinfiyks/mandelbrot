case class Location(point: Complex, zoom: Double)

case class PlaneDimensions(width: Int, height: Int)

case class ComplexPlaneSection(complexPoints: Seq[ComplexPoint])

object ComplexPlaneSection {

  def apply(complexPoints: Seq[ComplexPoint]): ComplexPlaneSection = new ComplexPlaneSection(complexPoints)

  def apply(location: Location, dim: PlaneDimensions): ComplexPlaneSection = {
    val complexPoints = Seq.tabulate[ComplexPoint](dim.width, dim.height) { (w, h) =>
      val x: Double = location.point.re + location.zoom * (w.toDouble / dim.width - 0.5)
      val y: Double = location.point.im + location.zoom * (0.5 - h.toDouble / dim.height) * dim.height.toDouble / dim.width
      ComplexPoint(Pixel(w, h), Complex(x, y))
    }.flatten
    ComplexPlaneSection(complexPoints)
  }

}