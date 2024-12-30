package versionControl.backend.tests

import org.scalatest._
import backend.{Game, Player}

class TestCheckTyping extends FunSuite {

  // tests if player is typing correctly given the text

  test("testing against submission with all letters") {
    val game = new Game
    val player = new Player("test")
    val json: String = "src/backend/tests/jsontests/test0.json"
    game.parseJson(json)

    // testing against the text The quick brown fox jumped over the lazy dog

    // testing clearly wrong submissions
    player.input = "imagine being a comp sci major in 2k19 LUL"
    player.checkTyping(game.text)
    assert(player.inputCheck == false)
    player.input = "not going in 40k debt for college LUL"
    player.checkTyping(game.text)
    assert(player.inputCheck == false)
    player.input = "pineapples on pizza are very good"
    player.checkTyping(game.text)
    assert(player.inputCheck == false)

    // testing against whole text and parts of text from beginning
    player.input = "The quick brown fox jumped over the lazy dog"
    player.checkTyping(game.text)
    assert(player.inputCheck == true)
    player.input = "The quick brown fox jumped"
    player.checkTyping(game.text)
    assert(player.inputCheck == true)
    player.input = "T"
    player.checkTyping(game.text)
    assert(player.inputCheck == true)

    // testing parts that don't start from the beginning
    player.input = "dog"
    player.checkTyping(game.text)
    assert(player.inputCheck == false)
    player.input = "over the lazy dog"
    player.checkTyping(game.text)
    assert(player.inputCheck == false)
    player.input = "quick brown fox jumped over the lazy dog"
    player.checkTyping(game.text)
    assert(player.inputCheck == false)

    // testing if some characters replaced with something else, or capitalization
    player.input = "The quick brown fox jumped over the lazy dog".replace("z", "a")
    player.checkTyping(game.text)
    assert(player.inputCheck == false)
    player.input = "tHe QuIcK bRoWn FoX jUmPs OvEr ThE lAzY dOg"
    player.checkTyping(game.text)
    assert(player.inputCheck == false)

  }

  // i'm skipping the navy seals copy pasta one

  test("testing against wikipedia entry") {
    val game = new Game
    val player = new Player("test")
    val json: String = "src/backend/tests/jsontests/test2.json"
    game.parseJson(json)

    // testing against the text
    //
    // It was planned that the Amagi class would be protected by a main belt 250 mm (9.8 in) thick, sloped at 12 degrees,
    // and a torpedo bulkhead 73 mm (2.9 in) thick. The main battery barbettes were designed to have between 230–280 mm (9.1–11.0 in) of armor plating,
    // and the conning tower would have had armor ranging in thickness from 75 mm (3.0 in) to a maximum of 360 mm (14 in).
    // Deck armor was to have been 95 mm (3.7 in) thick.

    // testing clearly wrong submissions
    player.input = "burger king foot lettuce tastes really good"
    player.checkTyping(game.text)
    assert(player.inputCheck == false)
    player.input = "bro have u seen despacito parody 2 by FlyingKitty"
    player.checkTyping(game.text)
    assert(player.inputCheck == false)
    player.input = "how is CSE116 right now? very hard thank you very much"
    player.checkTyping(game.text)
    assert(player.inputCheck == false)

    // testing against whole text and parts of text from beginning
    player.input = "It was planned that the Amagi class would be protected by a main belt 250 mm (9.8 in) thick, sloped at 12 degrees, and a torpedo bulkhead 73 mm (2.9 in) thick. The main battery barbettes were designed to have between 230–280 mm (9.1–11.0 in) of armor plating, and the conning tower would have had armor ranging in thickness from 75 mm (3.0 in) to a maximum of 360 mm (14 in). Deck armor was to have been 95 mm (3.7 in) thick."
    player.checkTyping(game.text)
    assert(player.inputCheck == true)
    player.input = "It was planned that the Amagi class would be protected by a main belt 250 mm (9.8 in) thick, sloped at 12 degrees"
    player.checkTyping(game.text)
    assert(player.inputCheck == true)
    player.input = "It was planned that "
    player.checkTyping(game.text)
    assert(player.inputCheck == true)

    // testing parts that don't start from the beginning
    player.input = "sloped at 12 degrees, and a torpedo bulkhead 73 mm (2.9 in) thick. The main battery barbettes were designed to have between 230–280 mm (9.1–11.0 in) of armor plating, and the conning tower would have had armor ranging in thickness from 75 mm (3.0 in) to a maximum of 360 mm (14 in). Deck armor was to have been 95 mm (3.7 in) thick."
    player.checkTyping(game.text)
    assert(player.inputCheck == false)
    player.input = "designed to have between 230–280 mm (9.1–11.0 in) of armor plating, and the conning tower would have had armor ranging in thickness from 75 mm (3.0 in) to a maximum of 360 mm (14 in). Deck armor was to have been 95 mm (3.7 in) thick."
    player.checkTyping(game.text)
    assert(player.inputCheck == false)
    player.input = " 230–280 mm (9.1–11.0 in) of armor plating, and the conning tower would have had armor ranging in thickness from 75 mm (3.0 in) to a maximum of 360 mm (14 in). Deck armor was to have been 95 mm (3.7 in) thick."
    player.checkTyping(game.text)
    assert(player.inputCheck == false)

    // testing if some characters replaced with something else, or capitalization
    player.input = "It was planned that the Amagi class would be protected by a main belt 250 mm (9.8 in) thick, sloped at 12 degrees, and a torpedo bulkhead 73 mm (2.9 in) thick. The main battery barbettes were designed to have between 230–280 mm (9.1–11.0 in) of armor plating, and the conning tower would have had armor ranging in thickness from 75 mm (3.0 in) to a maximum of 360 mm (14 in). Deck armor was to have been 95 mm (3.7 in) thick.".replace("m", "a")
    player.checkTyping(game.text)
    assert(player.inputCheck == false)
    player.input = "iT wAs pLaNnEd tHaT tHe aMaGi clASS"
    player.checkTyping(game.text)
    assert(player.inputCheck == false)
  }


}

