package galenscovell.sandbox

import com.badlogic.gdx.{Game, Gdx}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import galenscovell.sandbox.global.Resources
import galenscovell.sandbox.ui.screens.{GameScreen, LoadScreen}


class Main extends Game {
  var uiSpriteBatch: SpriteBatch = _
  var loadScreen: LoadScreen = _
  var gameScreen: GameScreen = _


  def create(): Unit =  {
    uiSpriteBatch = new SpriteBatch
    loadScreen = new LoadScreen(this)
    setScreen(loadScreen)
  }

  override def dispose(): Unit =  {
    loadScreen.dispose()
    gameScreen.dispose()
    Resources.dispose()
  }

  def setGameScreen(): Unit = {
    gameScreen = new GameScreen(this)
    setScreen(gameScreen)
  }

  def loadGame(): Unit = {
    // TODO: Not yet implemented
  }

  def quitGame(): Unit = {
    Gdx.app.exit()
  }
}
