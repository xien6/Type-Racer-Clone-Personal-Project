package backend.gameStates

trait GameState {

  // methods that will be changed based off state

  def update(): Unit

  def checkIfChangeState(): Unit

  def changeState(): Unit


}
