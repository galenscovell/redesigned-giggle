package galenscovell.sandbox.entities.systems

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d._
import galenscovell.sandbox.entities.ZComparator
import galenscovell.sandbox.entities.components.{BodyComponent, SizeComponent, TextureComponent}
import galenscovell.sandbox.ui.components.EntityStage


class RenderSystem(family: Family,
                   spriteBatch: SpriteBatch,
                   entityStage: EntityStage) extends SortedIteratingSystem(family, new ZComparator) {
  private val bodyMapper: ComponentMapper[BodyComponent] = ComponentMapper.getFor(classOf[BodyComponent])
  private val sizeMapper: ComponentMapper[SizeComponent] = ComponentMapper.getFor(classOf[SizeComponent])
  private val textureMapper: ComponentMapper[TextureComponent] = ComponentMapper.getFor(classOf[TextureComponent])


  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val bodyComponent: BodyComponent = bodyMapper.get(entity)
    val currentX: Float = bodyComponent.body.getPosition.x
    val currentY: Float = bodyComponent.body.getPosition.y

    val sizeComponent: SizeComponent = sizeMapper.get(entity)
    val texture: TextureRegion = textureMapper.get(entity).region

    // Cull entities outside of camera
    if (entityStage.inCamera(currentX, currentY)) {
      // Render sprite with physics body more towards the bottom for overlapping
      spriteBatch.draw(
        texture,
        currentX - (sizeComponent.width / 2),
        currentY - (sizeComponent.height / 3),
        sizeComponent.width,
        sizeComponent.height
      )
    }
  }

  override def update(deltaTime: Float): Unit = {
    forceSort()
    super.update(deltaTime)
  }
}
