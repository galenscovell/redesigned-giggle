package galenscovell.sandbox.singletons

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.freetype.{FreeTypeFontGenerator, FreeTypeFontGeneratorLoader, FreetypeFontLoader}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, TextureAtlas}
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle


object Resources {
  val assetManager: AssetManager = new AssetManager
  var atlas: TextureAtlas = _

  var labelMediumStyle: LabelStyle = _

  def load(): Unit = {
    assetManager.load("atlas/atlas.atlas", classOf[TextureAtlas])
    val resolver: FileHandleResolver = new InternalFileHandleResolver
    val fontGeneratorLoader: FreeTypeFontGeneratorLoader = new FreeTypeFontGeneratorLoader(resolver)
    assetManager.setLoader(classOf[FreeTypeFontGenerator], fontGeneratorLoader)
    val fontLoader: FreetypeFontLoader = new FreetypeFontLoader(resolver)
    assetManager.setLoader(classOf[BitmapFont], ".ttf", fontLoader)

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
    * Font and Resource Generation *
    ********************************/
  private def generateFont(fontName: String, size: Int, borderWidth: Int, fontColor: Color, borderColor: Color, outName: String): Unit = {
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
    labelMediumStyle = new LabelStyle(assetManager.get("mediumFont.ttf", classOf[BitmapFont]), Color.WHITE)
  }

  private def loadButtonStyles(): Unit = {

  }

  private def loadTextField(): Unit = {

  }

  private def loadProgressBars(): Unit = {

  }
}