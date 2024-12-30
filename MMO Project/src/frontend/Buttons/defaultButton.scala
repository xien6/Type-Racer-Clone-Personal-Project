package frontend.Buttons

import backend.Game
import scalafx.scene.control.Button

class defaultButton(game: Game) extends Button {
  val xScale = 1
  val yScale = 1
  minWidth = 100 * xScale
  minHeight = 100 * yScale
  style = "-fx-font: 30 ariel;"
}
