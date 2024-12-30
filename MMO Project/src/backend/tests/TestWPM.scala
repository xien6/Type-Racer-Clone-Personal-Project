package backend.tests

import org.scalatest._
import backend.{Game, Player}

class TestWPM extends FunSuite {

  // testing if WPM is outputting correctly

  test("testing sentence with all letters of alphabet") {
    // most notable for testing functionality
    val game = new Game
    val player = new Player("Test")
    val json: String = "src/backend/tests/jsontests/test0.json"
    game.parseJson(json)
    player.input = game.text

    // text is 44 words long, delta time is 44 seconds long.
    // 44 words / ((44 seconds) * (1 minute / 60 seconds)) = 60 wpm
    game.initialTime = 0
    var currentTime: Long = (44 * math.pow(10,9)).toLong
    player.checkWPM(game.initialTime, currentTime)
    assert(player.WPM == 60)

    // text is 44 words long, delta time is 66 seconds long.
    // 44 words / ((66 seconds) * (1 minute / 60 seconds)) = 40 wpm
    currentTime =  (66 * math.pow(10,9)).toLong
    player.checkWPM(game.initialTime, currentTime)
    assert(player.WPM == 40)

    // text is 44 words long, delta time is 44 seconds long.
    // 44 words / ((88 seconds) * (1 minute / 60 seconds)) = 30 wpm
    currentTime =  (88 * math.pow(10,9)).toLong
    player.checkWPM(game.initialTime, currentTime)
    assert(player.WPM == 30)
  }

  test("testing sentence with clean version of navy seals copypasta") {
    // most notable for testing how big numbers skew scala rounding
    val game = new Game
    val player = new Player("test")
    val json: String = "src/backend/tests/jsontests/test1.json"
    game.parseJson(json)
    player.input = game.text

    // text is 1592 words long, delta time is 1592 seconds long.
    // 1592 words / ((1592 seconds) * (1 minute / 60 seconds)) = 60 wpm
    game.initialTime = 0
    var currentTime: Long = (1592 * math.pow(10,9)).toLong
    player.checkWPM(game.initialTime, currentTime)
    assert(player.WPM == 60)

    // text is 1592 words long, delta time is 2928 seconds long.
    // 1592 words / ((2928 seconds) * (1 minute / 60 seconds)) = 40 wpm
    game.initialTime = 0
    currentTime = (2388 * math.pow(10,9)).toLong
    player.checkWPM(game.initialTime, currentTime)
    assert(player.WPM == 40)

    // text is 1592 words long, delta time is 1592 seconds long.
    // 1592 words / ((1592 seconds) * (1 minute / 60 seconds)) = 60 wpm
    game.initialTime = 0
    currentTime = (3184 * math.pow(10,9)).toLong
    player.checkWPM(game.initialTime, currentTime)
    assert(player.WPM == 30)
  }

  test("testing sentence with a lotta numbers") {
    // most notable for having a decimal time
    val game = new Game
    val player = new Player("test")
    val json: String = "src/backend/tests/jsontests/test2.json"
    game.parseJson(json)
    player.input = game.text

    // text is 425 words long, delta time is 425 seconds long.
    // 425 words / ((425 seconds) * (1 minute / 60 seconds)) = 60 wpm
    game.initialTime = 0
    var currentTime: Long = (425 * math.pow(10,9)).toLong
    player.checkWPM(game.initialTime, currentTime)
    assert(player.WPM == 60)

    // text is 425 words long, delta time is 637.5 seconds long.
    // 425 words / ((425 seconds) * (1 minute / 60 seconds)) = 40 wpm
    currentTime =  (637.5 * math.pow(10,9)).toLong
    player.checkWPM(game.initialTime, currentTime)
    assert(player.WPM == 40)

    // text is 425 words long, delta time is 425 seconds long.
    // 425 words / ((425 seconds) * (1 minute / 60 seconds)) = 60 wpm
    currentTime =  (850 * math.pow(10,9)).toLong
    player.checkWPM(game.initialTime, currentTime)
    assert(player.WPM == 30)
  }

}
