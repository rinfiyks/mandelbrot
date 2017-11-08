import org.scalatest.{Matchers, PropSpec}
import org.scalatest.prop.PropertyChecks

class ComplexPropSpec extends PropSpec with PropertyChecks with Matchers {

  property("Addition") {
    forAll { (reX: Double, imX: Double, reY: Double, imY: Double) =>
      val x = Complex(reX, imX)
      val y = Complex(reY, imY)
      val z = x + y
      z.re should be (reX + reY)
      z.im should be (imX + imY)
    }
  }

  property("0 + 0i is the additive identity") {
    val zero = Complex(0.0, 0.0)
    forAll { (re: Double, im: Double) =>
      val x = Complex(re, im)
      x + zero should be(x)
    }
  }

  property("Multiplication") {
    forAll { (reX: Double, imX: Double, reY: Double, imY: Double) =>
      val x = Complex(reX, imX)
      val y = Complex(reY, imY)
      val z = x * y
      z.re should be (reX * reY - imX * imY)
      z.im should be (reX * imY + imX * reY)
    }
  }

  property("1 + 0i is the multiplicative identity") {
    val one = Complex(1.0, 0.0)
    forAll { (re: Double, im: Double) =>
      val x = Complex(re, im)
      x * one should be(x)
    }
  }

  property("Abs should be positive") {
    forAll { (re: Double, im: Double) =>
      Complex(re, im).abs should be >= 0.0
    }
  }

  property("Abs squared should be positive") {
    forAll { (re: Double, im: Double) =>
      Complex(re, im).absSquared should be >= 0.0
    }
  }

}
