package server

import play.api.libs.json.{JsValue, Json}
import scalaj.http.Http
import java.nio.file.{Files, Paths}

//http://www.icndb.com/api/
class API{
  //Basic retrieve info from API in the form of json
  //Then converts a string retrieved from json to a string for next function to use
  def fromJSON(): String = {
    var joke: String = ""
    val jsonURL: String = "http://api.icndb.com/jokes/random"
    val response: String = Http(jsonURL).asString.body
    val parsed: JsValue = Json.parse(response)
    joke = (parsed \ "value" \ "joke").as[String]
    joke
  }

  def tojson(): String = {
    //Loops the fromJSON function over a certain amount of times
    //Then creates a map from the string retrieved with a key
    var myMap: Map[String, String] = Map()
    var sum: Int = 0
    var key: String = "text"
    var joke: String = fromJSON()
    //This number is arbitrary mainly due to how many rounds we plan on implementing
    /*for(_ <- 1 to 10){
      sum += 1
      key = "Round" + sum.toString
      joke = fromJSON()
      myMap = myMap + (key -> joke)
    }*/
    myMap = myMap + (key -> joke)
    val mapJS: JsValue = Json.toJson(myMap)
    Json.stringify(Json.toJson(mapJS))
  }

  def getLines(): Unit = {
    //This function like the clicker game takes the json string created and saves it as a file
    //This is for when the GUI can access it when starting the game
    val filename: String = "jokes.json"
    Files.write(Paths.get(filename), tojson().getBytes())
  }
  //To load the file use: game.fromJSON(Source.fromFile(jokes.json).mkString)

  //Basic test to make sure that everything is retrieving and saving properly
  /*def main(args: Array[String]): Unit = {
    getLines()
  }
  */
}