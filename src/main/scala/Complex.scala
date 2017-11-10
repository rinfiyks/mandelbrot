case class Complex(re: Double, im: Double) {

  def +(x: Complex): Complex = {
    val realPart: Double = this.re + x.re
    val imaginaryPart: Double = this.im + x.im
    Complex(realPart, imaginaryPart)
  }

  def *(x: Complex): Complex = {
    val realPart: Double = this.re * x.re - this.im * x.im
    val imaginaryPart: Double = this.re * x.im + this.im * x.re
    Complex(realPart, imaginaryPart)
  }

  def absSquared: Double = re * re + im * im

  def abs: Double = math.sqrt(absSquared)

  override def toString: String = s"$re + ${im}i"

}
