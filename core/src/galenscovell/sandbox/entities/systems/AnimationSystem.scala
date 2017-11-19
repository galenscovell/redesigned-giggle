package galenscovell.sandbox.entities.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import galenscovell.sandbox.entities.components.{AnimationComponent, StateComponent, TextureComponent}


class AnimationSystem(family: Family) extends IteratingSystem(family) {
  private val animationMapper: ComponentMapper[AnimationComponent] = ComponentMapper.getFor(classOf[AnimationComponent])
  private val stateMapper: ComponentMapper[StateComponent] = ComponentMapper.getFor(classOf[StateComponent])
  private val textureMapper: ComponentMapper[TextureComponent] = ComponentMapper.getFor(classOf[TextureComponent])


  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val animationComponent: AnimationComponent = animationMapper.get(entity)
    val stateComponent: StateComponent = stateMapper.get(entity)
    val textureComponent: TextureComponent = textureMapper.get(entity)

    stateComponent.update(deltaTime)

    val animation: Animation[TextureRegion] = animationComponent.animationMap(stateComponent.getAnimationKey)
    val animationFrame: TextureRegion = animation.getKeyFrame(stateComponent.getStateTime)

    if (textureComponent.region != animationFrame) {
      textureComponent.region.setRegion(animationFrame)
    }
  }
}
