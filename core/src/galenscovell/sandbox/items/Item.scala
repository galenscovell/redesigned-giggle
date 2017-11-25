package galenscovell.sandbox.items


class Item(val buyPrice: Float, val sellPrice: Float, val description: String) {
  private val uuid: String = java.util.UUID.randomUUID().toString
}
