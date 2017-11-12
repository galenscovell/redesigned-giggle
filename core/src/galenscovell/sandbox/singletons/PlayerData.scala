package galenscovell.sandbox.singletons

import com.badlogic.gdx.{Gdx, Preferences}
import galenscovell.sandbox.items.Item

import scala.collection.mutable.ArrayBuffer


/**
  * PlayerData persists game data across instances of game (for continuing).
  * Ingame data usage is also maintained here.
  *
  * Windows: %UserProfile%/.prefs/My Preferences
  * Linux and OSX: ~/.prefs/My Preferences
  */
object PlayerData {
  private var prefs: Preferences = _
  private val inventoryItems: ArrayBuffer[Item] = new ArrayBuffer[Item]()


  /**
    * Instantiate preferences file and add default SFX/Music settings.
    */
  def init(): Unit = {
    prefs = Gdx.app.getPreferences("redesignedgiggle_player_data")
    prefs.flush()
  }

  def clear(): Unit = {
    prefs.clear()
  }

  def get: Preferences = prefs
}
