package galenscovell.sandbox.items

import com.badlogic.gdx.scenes.scene2d.ui.Table


class Item(val description: String, val buyPrice: Int, val sellPrice: Int) {
  private val uuid: String = java.util.UUID.randomUUID().toString
  private val table: Table = new Table

  create()


  private def create(): Unit = {

  }

  def getTable: Table = table
}
