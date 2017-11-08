package galenscovell.sandbox.singletons

object Constants {

  // Box2D masks
  // fixture filter category = "This is what I am"
  val WALL_CATEGORY: Short = 0x0001
  val ENTITY_CATEGORY: Short = 0x0002
  val NO_CATEGORY: Short = 0x0008

  // fixture filter mask = "This is what I collide with"
  val WALL_MASK: Short = ENTITY_CATEGORY
  val ENTITY_MASK: Short = (WALL_CATEGORY | ENTITY_CATEGORY).toShort
  val NO_MASK: Short = -2

  // Box2D dimensions conversion factor
  val PIXEL_PER_METER: Float = 16

  // Screen dimension units
  // Game runs at 270p (16:9, 480x270)
  val EXACT_X: Int = 1280
  val EXACT_Y: Int = 720
  val SCREEN_X: Float = 480 / PIXEL_PER_METER
  val SCREEN_Y: Float = 270 / PIXEL_PER_METER
  val UI_X: Float = 1280
  val UI_Y: Float = 720

  // Sprite sizes
  val SMALL_ENTITY_SIZE: Float = 24 / PIXEL_PER_METER
  val MEDIUM_ENTITY_SIZE: Float = 32 / PIXEL_PER_METER
  val LARGE_ENTITY_SIZE: Float = 64 / PIXEL_PER_METER
  val HUGE_ENTITY_SIZE: Float = 80 / PIXEL_PER_METER
  val TILE_SIZE: Float = 16 / PIXEL_PER_METER

  // Character speeds
  val WALK_SPEED: Float = 4.5f
  val RUN_SPEED: Float = 7f
}


