package server

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import backend.GameActor
import play.api.libs.json.{JsValue, Json}
import Tcp._
import context.system
import actorSystem.dispatcher
import scala.concurrent.duration._


class TCPSocketServer(gameActor: ActorRef) extends Actor {

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 8000))

  var webServers: Set[ActorRef] = Set()
  var buffer: String = ""
  val delimiter: String = "~"

  override def receive: Receive = {
    case b: Bound => println("Listening on port: " + b.localAddress.getPort)

    case c: Connected =>
      println("Client Connected: " + c.remoteAddress)
      this.webServers = this.webServers + sender()
      sender() ! Register(self)

    case PeerClosed =>
      println("Client Disconnected: " + sender())
      this.webServers = this.webServers - sender()

    case r: Received =>
      buffer += r.data.utf8String
      while (buffer.contains(delimiter)) {
        val curr = buffer.substring(0, buffer.indexOf(delimiter))
        buffer = buffer.substring(buffer.indexOf(delimiter) + 1)
        handleMessageFromWebServer(curr)
      }

    case SendGameState =>
      gameActor ! SendGameState

    case gs: GameState =>
      this.webServers.foreach((client: ActorRef) => client ! Write(ByteString(gs.gameState + delimiter)))

    case UpdateGameState =>
      gameActor ! UpdateGameState
  }


  def handleMessageFromWebServer(messageString:String):Unit = {
    val message: JsValue = Json.parse(messageString)
    val username = (message \ "username").as[String]
    val messageType = (message \ "action").as[String]

    messageType match {
      case "connected" => gameActor ! AddPlayer(username)
      case "disconnected" => gameActor ! RemovePlayer(username)
      case "nameChange" =>
        val display = (message \ "newName").as[String]
        gameActor ! DisplayName(username, display)
      case "playerText" =>
        val text = (message \ "text").as[String]
        gameActor ! PlayerText(username, text)
    }
  }
}


object TCPSocketServer {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem()

    val gameActor = actorSystem.actorOf(Props(classOf[GameActor]))
    val server = actorSystem.actorOf(Props(classOf[TCPSocketServer], gameActor))

    actorSystem.scheduler.schedule(16.milliseconds, 32.milliseconds, gameActor, UpdateGame)
    actorSystem.scheduler.schedule(32.milliseconds, 32.milliseconds, server, SendGameState)
    actorSystem.scheduler.schedule(48.milliseconds, 32.milliseconds, gameActor, UpdateGameState)

  }

}
