package galenscovell.sandbox.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.{Image, Table}
import com.badlogic.gdx.utils.Scaling
import galenscovell.sandbox.Main
import galenscovell.sandbox.global.{Constants, Resources}


class LoadScreen(root: Main) extends AbstractScreen(root) {
  private var loadingImage: Image = _


  override def create(): Unit = {
    super.create()

    val loadingMain: Table = new Table
    loadingMain.setFillParent(true)

    val loadingTable: Table = new Table
    loadingImage = new Image(new Texture(Gdx.files.internal("texture/loading.png")))
    loadingImage.setScaling(Scaling.fillY)
    loadingTable.add(loadingImage).width(Constants.UI_X / 2).height(Constants.UI_Y).expand.fill

    loadingMain.add(loadingTable).width(Constants.UI_X).height(Constants.UI_Y).expand.fill

    uiStage.addActor(loadingMain)
  }


  /**********************
    * Screen Operations *
    **********************/
  override def render(delta: Float): Unit = {
    super.render(delta)

    if (Resources.assetManager.update) {
      Resources.done()
      uiStage.getRoot.addAction(
        Actions.sequence(
          Actions.delay(0.25f),
          Actions.parallel(
            Actions.moveTo(Constants.UI_X * 1.4f, 0, 0.5f),
            Actions.fadeOut(0.5f)
          ),
          Actions.delay(0.25f),
          toGameScreen
        )
      )
    }
  }

  override def show(): Unit = {
    Resources.load()
    create()
    uiStage.getRoot.getColor.a = 0
    uiStage.getRoot.addAction(
      Actions.sequence(
        Actions.moveTo(-Constants.UI_X * 1.4f, 0),
        Actions.parallel(
          Actions.moveTo(0, 0, 0.5f),
          Actions.fadeIn(0.5f)
        )
      )
    )
  }


  /***************************
    * Custom Scene2D Actions *
    ***************************/
  private[screens] var toGameScreen: Action = (delta: Float) => {
    root.setGameScreen()
    true
  }
}
