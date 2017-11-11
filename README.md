# Mandelbrot Set Explorer
Program to draw and explore the Mandelbrot set.
Written in Scala, using [ScalaFX](https://github.com/scalafx/scalafx) for UI and [Scala.Rx](https://github.com/lihaoyi/scala.rx) as an FRP framework.

## Usage
| Key | Action |
| --- | --- |
| Left click | Zoom in 4x |
| Right click | Zoom out 4x |
| . (period) | Double the max number of iterations |
| , (comma) | Halve the max number of iterations |
| D or R | Redraw (press this after you are finished resizing the window) |
| S | Pop up a save file dialog |
| Q | Quit |

## Sample Images
![[600 x 480] [-0.75 + 0.0i] [z: 3.0] [i: 25600]](https://raw.githubusercontent.com/rinfiyks/mandelbrot/master/images/0.png)
![[600 x 480] [-1.432491459281882 + 5.453948373906313E-4i] [z: 1.7462298274040222E-10] [i: 25600]](https://raw.githubusercontent.com/rinfiyks/mandelbrot/master/images/1.png)
![[600 x 480] [0.14054687500000002 + 0.6399999999999999i] [z: 0.046875] [i: 25600]](https://raw.githubusercontent.com/rinfiyks/mandelbrot/master/images/2.png)
![[600 x 480] [-1.999774051417189 + 1.517357305809755E-8i] [z: 6.984919309616089E-10] [i: 25600]](https://raw.githubusercontent.com/rinfiyks/mandelbrot/master/images/3.png)

