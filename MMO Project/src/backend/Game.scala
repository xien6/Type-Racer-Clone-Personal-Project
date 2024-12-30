package backend

import backend.gameStates.{GameState, noGame}
import play.api.libs.json.{JsValue, Json}

import scala.collection.mutable.ListBuffer
import scala.io.Source

class Game {

  // global variables
  var GameState: GameState = new noGame(this)

  var text: String = "Starting game in 5 seconds..."
  var players: Map[String, Player] = Map()
  var leaderBoard: String = ""

  var initialTime: Long = System.nanoTime()
  var currentTime: Long = System.nanoTime()

  /** API METHODS FOR:
    * GameActor
    */
  // adds a player
  def addPlayer(username: String): Unit = {
    this.players += (username -> new Player(username))
  }

  // changes username
  def displayName(username: String, displayName: String): Unit = {
    this.players(username).displayName = displayName
  }

  // remove a player
  def delPlayer(username: String): Unit = {
    this.players -= username
  }

  // update the game
  def update(): Unit = {
    this.GameState.update()
  }

  // state of the game
  def gameState(): String = {
    val gameState: Map[String, JsValue] = Map(
      "text" -> Json.toJson(this.text),
      "players" -> Json.toJson(this.players.map({ case (k,v) => Json.toJson(Map(
        "username" -> Json.toJson(v.name),
        "displayName" -> Json.toJson(v.displayName),
        "input" -> Json.toJson(v.input),
        "inputCheck" -> Json.toJson(v.inputCheck),
        "inputComplete" -> Json.toJson(v.inputComplete),
        "WPM" -> Json.toJson(v.WPM))) })),
      "leaderBoard" -> Json.toJson(this.leaderBoard)
    )
    Json.stringify(Json.toJson(gameState))
  }

  /** REGULAR METHODS FOR:
    * updating the game
    */
  // updates player text
  def playerText(username: String, text: String): Unit = {
    players(username).input = text
  }

  // checks if state of the game should be changed
  def checkIfChangeState(): Unit = {
    this.GameState.checkIfChangeState()
  }

  // changes state of game
  def changeState(): Unit = {
    this.GameState.changeState()
  }

  // takes json filename from server and parses it into something the model can use.
  def parseJson(json: String): Unit = {
    // parse input
    val parsed: JsValue = Json.parse(Source.fromFile(json).mkString)
    val newText: String = (parsed \ "text").as[String].replace("&quot;", "'")

    // parse everything into usable format. exception is users which will be the keys in leaderboard.
    text = newText

  }

  // update each player
  def updatePlayer(): Unit = {
    for (player <- this.players) {
      player._2.checkWPM(this.initialTime, this.currentTime)
      player._2.checkTyping(this.text)
    }
  }

  // update the leaderBoard
  def updateLeaderBoard(): Unit = {
    var compare: ListBuffer[List[String]] = ListBuffer()

    for (player <- this.players) {
      compare += List(player._2.displayName, player._2.WPM.toString)
    }

    compare.sortWith((a: List[String], b: List[String]) => a(1).toInt > b(1).toInt)
    this.leaderBoard = ""

    for (i <- compare.toList) {
      this.leaderBoard += i.head + " : " + i(1) + " WPM" + "\n"
    }
  }
}
