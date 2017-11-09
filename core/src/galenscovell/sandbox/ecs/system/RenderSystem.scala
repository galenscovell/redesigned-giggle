package galenscovell.sandbox.ecs.system

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d._

import galenscovell.sandbox.ecs.ZComparator
import galenscovell.sandbox.ecs.component.{BodyComponent, SizeComponent, SpriteComponent}
import galenscovell.sandbox.ui.component.EntityStage


class RenderSystem(family: Family,
                   spriteBatch: SpriteBatch,
                   entityStage: EntityStage) extends SortedIteratingSystem(family, new ZComparator) {
  private val bodyMapper: ComponentMapper[BodyComponent] = ComponentMapper.getFor(classOf[BodyComponent])
  private val sizeMapper: ComponentMapper[SizeComponent] = ComponentMapper.getFor(classOf[SizeComponent])
  private val spriteMapper: ComponentMapper[SpriteComponent] = ComponentMapper.getFor(classOf[SpriteComponent])


  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val bodyComponent: BodyComponent = bodyMapper.get(entity)
    val currentX: Float = bodyComponent.body.getPosition.x
    val currentY: Float = bodyComponent.body.getPosition.y

    val sizeComponent: SizeComponent = sizeMapper.get(entity)
    val width: Float = sizeComponent.width
    val height: Float = sizeComponent.height

    val sprite: Sprite = spriteMapper.get(entity).sprite

    // Cull entities outside of camera
    if (entityStage.inCamera(currentX, currentY)) {
      // Render sprite with physics body more towards the bottom for overlapping
      spriteBatch.draw(sprite, currentX - (width / 2), currentY - (height / 2.5f), width, height)
    }
  }

  override def update(deltaTime: Float): Unit = {
    forceSort()
    super.update(deltaTime)
  }
}
