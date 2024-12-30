package backend.gameStates

import backend.{Game, Player}
import server.API

class noGame(game: Game) extends GameState {

  override def update(): Unit = {

  }

  override def checkIfChangeState(): Unit = {
    if (game.players.nonEmpty) {
      // get new API
      val API: API = new API
      API.getLines()
      game.parseJson("jokes.json")

      // make countdown
      changeState()
    }
  }

  override def changeState(): Unit = {
    Thread.sleep(5000)

    for (player <- game.players) {
      player._2.inputCheck = false
      player._2.inputComplete = false
      player._2.WPM = 0
    }

    game.initialTime = System.nanoTime()
    game.GameState = new yesGame(game)
  }

}
