package backend

import akka.actor.{Actor, ActorRef}
import server._


class GameActor extends Actor {

  var players: Map[String, ActorRef] = Map()

  val game: Game = new Game()

  override def receive: Receive = {
    case message: AddPlayer => game.addPlayer(message.username)
    case message: RemovePlayer => game.delPlayer(message.username)
    case message: PlayerText => game.playerText(message.username, message.text)
    case message: DisplayName => game.displayName(message.username, message.name)

    case UpdateGame => game.update()
    case SendGameState => sender() ! GameState(game.gameState())
    case UpdateGameState => game.checkIfChangeState()
  }
}
