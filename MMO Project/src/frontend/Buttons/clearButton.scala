package frontend.Buttons

import backend.Game
import scalafx.Includes._
import scalafx.event.ActionEvent

class clearButton(game: Game) extends defaultButton(game) {
  text = "Clear Game"
  layoutX = 1560
  layoutY = 900
  onAction = (event: ActionEvent) => this.clear()

  def clear(): Unit = {
    // clears the game
  }

}