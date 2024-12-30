package backend

// player class, blueprint for each player
class Player (val name: String) {

  var displayName: String = "Unknown player"
  var input: String = ""
  var inputCheck: Boolean = false
  var inputComplete: Boolean = false
  var WPM: Int = 0

  // checks to see if they're typing the correct words given a text
  // changes state of inputCheck depending on their input
  def checkTyping(text: String): Unit = {
    if (input.length <= text.length) {
      if (input == text) {
        inputComplete = true
        inputCheck = true
      }
      else if (input == text.substring(0,input.length)) {
        inputCheck = true
      }
      else {
        inputCheck = false
      }
    }
    else {
      inputComplete = false
      inputCheck = false
    }
  }

  // checks how WPM changes over time
  // changes state of WPM depending on their input
  def checkWPM(initialTime: Long, currentTime: Long): Unit = {
    val splits: Array[String] = input.split(" +")
    val tempWPM: Double = splits.length / (((currentTime - initialTime) * math.pow(10, -9)) / 60)
    if(splits(0) == ""){
      WPM = 0
    }else {
      WPM = (tempWPM + 0.5).toInt
    }
  }

}
