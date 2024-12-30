package server


// Received by Multiple Types
case object SendGameState
case class GameState(gameState: String)


// Received by GameActor
case object UpdateGame
case object UpdateGameState
case class AddPlayer(username: String)
case class RemovePlayer(username: String)
case class PlayerText(username: String, text: String)
case class DisplayName(username: String, name: String)
