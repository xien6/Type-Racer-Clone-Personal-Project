package backend.tests

import org.scalatest._
import backend.Game

class TestParseJson extends FunSuite {

  // tests if the parseJson function actually works, not much to test really

  test("testing test0.json") {
    val game = new Game
    val json: String = "src/backend/tests/jsontests/test0.json"
    game.parseJson(json)
    // sentence that contains all words in alphabet
    assert(game.text == "The quick brown fox jumped over the lazy dog")

  }

  test("testing test1.json") {
    val game = new Game
    val json: String = "src/backend/tests/jsontests/test1.json"
    game.parseJson(json)
    // navy seals clean copy pasta
    assert(game.text == "What the jiminy crickets did you just flaming say about me, you little bozo? I’ll have you know I graduated top of my class in the Cub Scouts, and I’ve been involved in numerous secret camping trips in Wyoming, and I have over 300 confirmed knots. I am trained in first aid and I’m the top bandager in the entire US Boy Scouts (of America). You are nothing to me but just another friendly face. I will clean your wounds for you with precision the likes of which has never been seen before on this annual trip, mark my words. You think you can get away with saying those shenanigans to me over the Internet? Think again, finkle. As we speak I am contacting my secret network of MSN friends across the USA and your IP is being traced right now so you better prepare for the seminars, man. The storm that wipes out the pathetic little thing you call your bake sale. You’re frigging done, kid. I can be anywhere, anytime, and I can tie knots in over seven hundred ways, and that’s just with my bare hands. Not only am I extensively trained in road safety, but I have access to the entire manual of the United States Boy Scouts (of America) and I will use it to its full extent to train your miserable butt on the facts of the continents, you little schmuck. If only you could have known what unholy retribution your little “clever” comment was about to bring down upon you, maybe you would have held your silly tongue. But you couldn’t, you didn’t, and now you’re paying the price, you goshdarned sillyhead. I will throw leaves all over you and you will dance in them. You’re friggin done, kiddo.")

  }

  test("testing test2.json") {
    val game = new Game
    val json: String = "src/backend/tests/jsontests/test2.json"
    game.parseJson(json)
    // armor of Amagi-class battlecruisers, series of ships constructed near WWI. picked because it has numbers and stuff
    // testing case for time is a string converted to long because scala type conversion
    assert(game.text == "It was planned that the Amagi class would be protected by a main belt 250 mm (9.8 in) thick, sloped at 12 degrees, and a torpedo bulkhead 73 mm (2.9 in) thick. The main battery barbettes were designed to have between 230–280 mm (9.1–11.0 in) of armor plating, and the conning tower would have had armor ranging in thickness from 75 mm (3.0 in) to a maximum of 360 mm (14 in). Deck armor was to have been 95 mm (3.7 in) thick.")

  }

}
