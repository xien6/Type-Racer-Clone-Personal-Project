package backend.gameStates

import backend.Game

class yesGame(game: Game) extends GameState {

  override def checkIfChangeState(): Unit = {
    for (player <- game.players) {
      if (player._2.inputComplete) {
        game.text = player._2.displayName + " has won the game! Please clear your input box for the new game in 5 seconds..."
        game.changeState()
      }
    }
  }

  override def update(): Unit = {
    val time = System.nanoTime()
    game.updatePlayer()
    game.updateLeaderBoard()
    this.game.currentTime = time
  }

  override def changeState(): Unit = {
    game.GameState = new noGame(game)
  }

}
