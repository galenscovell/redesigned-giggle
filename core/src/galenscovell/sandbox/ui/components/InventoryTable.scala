package galenscovell.sandbox.ui.components

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.sandbox.global.Constants


class InventoryTable(interfaceStage: Stage) extends Table {
  private val tableWidth: Float = Constants.EXACT_X * 0.5f
  private val tableHeight: Float = Constants.EXACT_Y * 0.25f
  private val gridRows: Int = 3
  private val gridColumns: Int = 10
  private val cellPadding: Int = 4
  private val cellSize: Int = 48
  private val cells: Array[Table] = new Array[Table](gridRows * gridColumns)

  create()


  private def create(): Unit = {
    this.setFillParent(true)

    val mainTable: Table = new Table

    val itemTable: Table = createItemTable

    mainTable.add(itemTable).expand.width(tableWidth).height(tableHeight).bottom().padBottom(48)

    this.add(mainTable).expand.fill
  }

  private def createItemTable: Table = {
    val table: Table = new Table
    table.setDebug(true, true)

    for (row <- 1 to gridRows) {
      for (col <- 1 to gridColumns) {
        val cell: Table = new Table
        table.add(cell).width(cellSize).height(cellSize).pad(cellPadding / 2)
      }

      table.row()
    }

    table
  }
}
