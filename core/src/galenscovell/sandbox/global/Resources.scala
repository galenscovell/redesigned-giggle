package galenscovell.sandbox.global

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.freetype.{FreeTypeFontGenerator, FreeTypeFontGeneratorLoader, FreetypeFontLoader}
import com.badlogic.gdx.graphics.g2d.{Animation, BitmapFont, TextureAtlas, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import galenscovell.sandbox.entities.components.StateComponent
import galenscovell.sandbox.global.enums.Direction
import galenscovell.sandbox.states.State

import scala.collection.mutable


object Resources {
  val assetManager: AssetManager = new AssetManager
  var atlas: TextureAtlas = _

  var labelMediumStyle, labelSmallStyle: LabelStyle = _

  def load(): Unit = {
    assetManager.load("atlas/atlas.atlas", classOf[TextureAtlas])
    val resolver: FileHandleResolver = new InternalFileHandleResolver
    val fontGeneratorLoader: FreeTypeFontGeneratorLoader = new FreeTypeFontGeneratorLoader(resolver)
    assetManager.setLoader(classOf[FreeTypeFontGenerator], fontGeneratorLoader)
    val fontLoader: FreetypeFontLoader = new FreetypeFontLoader(resolver)
    assetManager.setLoader(classOf[BitmapFont], ".ttf", fontLoader)

    generateFont("ui/Verdana.ttf", 16, 0, Color.WHITE, Color.BLACK, "smallFont.ttf")
    generateFont("ui/Verdana.ttf", 24, 0, Color.WHITE, Color.BLACK, "mediumFont.ttf")
  }

  def done(): Unit = {
    atlas = assetManager.get("atlas/atlas.atlas", classOf[TextureAtlas])
    loadNinePatches()
    loadLabelStyles()
    loadButtonStyles()
    loadTextField()
    loadProgressBars()
  }

  def dispose(): Unit = {
    assetManager.dispose()
    atlas.dispose()
  }

  /*********************************
    *  Font/UI Resource Generation *
    ********************************/
  private def generateFont(fontName: String,
                           size: Int,
                           borderWidth: Int,
                           fontColor: Color,
                           borderColor: Color,
                           outName: String): Unit = {
    val params: FreetypeFontLoader.FreeTypeFontLoaderParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter
    params.fontFileName = fontName
    params.fontParameters.size = Math.ceil(size).toInt
    params.fontParameters.borderWidth = borderWidth
    params.fontParameters.borderColor = borderColor
    params.fontParameters.color = fontColor
    params.fontParameters.magFilter = TextureFilter.Linear
    params.fontParameters.minFilter = TextureFilter.Linear
    assetManager.load(outName, classOf[BitmapFont], params)
  }

  private def loadNinePatches(): Unit = {

  }

  private def loadLabelStyles(): Unit = {
    labelSmallStyle = new LabelStyle(assetManager.get("smallFont.ttf", classOf[BitmapFont]), Color.WHITE)
    labelMediumStyle = new LabelStyle(assetManager.get("mediumFont.ttf", classOf[BitmapFont]), Color.WHITE)
  }

  private def loadButtonStyles(): Unit = {

  }

  private def loadTextField(): Unit = {

  }

  private def loadProgressBars(): Unit = {

  }


  /*********************************
    *      Resource Generation     *
    ********************************/
  def generateAnimationAndAddToMap(map: mutable.Map[String, Animation[TextureRegion]],
                                   name: String,
                                   secondsPerFrame: Float,
                                   agentState: State[StateComponent],
                                   direction: Direction.Value,
                                   indices: List[Int],
                                   playMode: Animation.PlayMode): Unit = {
    val keyFrames: com.badlogic.gdx.utils.Array[TextureRegion] = new com.badlogic.gdx.utils.Array[TextureRegion]()

    for (i <- indices) {
      val textureName: String =
        s"${name.toLowerCase}-${agentState.toString.toLowerCase()}-${direction.toString.toLowerCase()}_${i.toString}"
      keyFrames.add(Resources.atlas.findRegion(textureName))
    }

    map += (s"$agentState-$direction" -> new Animation[TextureRegion](secondsPerFrame, keyFrames, playMode))
  }
}