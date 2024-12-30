package frontend

import scalafx.scene.control._
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Text, TextAlignment}
import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import javafx.application.Platform
import play.api.libs.json.{JsValue, Json}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.StringProperty
import scalafx.scene.Scene
import scalafx.scene.control.TextField

class HandleMessagesFromPython() extends Emitter.Listener {
  override def call(objects: Object*): Unit = {
    // Use runLater when interacting with the GUI
    Platform.runLater(() => {
      val jsonGameState = objects.apply(0).toString
      val gameState: JsValue = Json.parse(jsonGameState)
      val text = (gameState \ "text").as[String]
      GUI.textDisplay.text = text
      val leaderboard = (gameState \ "leaderBoard").as[String]
      GUI.leaderBoardDisplay.text = leaderboard
      GUI.sendUserText()
    })
  }
}

object GUI extends JFXApp {

  var socket: Socket = IO.socket("http://localhost:8080/")
  socket.on("gameState", new HandleMessagesFromPython)
  socket.connect()


  def sendUserText(): Unit ={
    val text: StringProperty = this.inputDisplay.text
    socket.emit("sendPlayerText",text.value)
  }

  // globals
  val windowWidth: Double = 1920
  val windowHeight: Double = 1080

  val nameText: Text = new Text {
    text = "TypeFaster"
    textAlignment = TextAlignment.Center
    style = "-fx-font-size: 48pt"
    fill = new LinearGradient(
      endX = 0,
      stops = Stops(SkyBlue, Turquoise))
    layoutX = 1920/2 - 150
    layoutY = 100
  }

  // input for the person to type in
  val inputDisplay: TextField = new TextField {
    promptText = "Input your text here"
    style = "-fx-font: 32 ariel;"
    layoutX = 100
    layoutY = 600
    prefHeight = 100
    prefWidth = 1000
  }

  val user: TextField = new TextField {
    promptText = "Enter Name here"
    style = "-fx-font: 32 ariel;"
    layoutX = 100
    layoutY = 600
    prefHeight = 100
    prefWidth = 1000
  }

  // shows up the text that the user will write
  val textDisplay: Text = new Text {
    style = "-fx-font: 32 ariel;"
    layoutX = 100
    layoutY = 200
    wrappingWidth = 1200
  }

  // displays person's wpm
  /*val WPMDisplay: TextArea = new TextArea {
    editable = false
    promptText = "WPM"
    style = "-fx-font: 32 ariel;"
    layoutX = 1150
    layoutY = 600
    prefHeight = 100
    prefWidth = 150
  }*/

  val leaderBoardDisplay: TextArea = new TextArea {
    editable = false
    promptText = "Leaderboard"
    style = "-fx-font: 32 ariel;"
    layoutX = 1350
    layoutY = 200
    prefHeight = 650
    prefWidth = 400
  }

  // prompts for username
  val usernameDialog: TextInputDialog = new TextInputDialog() {
    initOwner(stage)
    title = "Prompt for Username"
    headerText = "Please enter your username."
    contentText = "Username:"
  }

  val result = usernameDialog.showAndWait()

  result match {
    case Some(name) => socket.emit("createUser", name)
    case None => println("No Name Entered")
  }

  // set GUI picture
  this.stage = new PrimaryStage {
    title = "TypeFaster"
    scene = new Scene(windowWidth, windowHeight) {
      usernameDialog
      content = List(
        nameText,
        textDisplay,
        user,
        inputDisplay,
        leaderBoardDisplay,
      )
    }

  }
}
