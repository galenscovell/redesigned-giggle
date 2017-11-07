package galenscovell.sandbox.ecs

import com.badlogic.ashley.core.{Engine, Entity}
import com.badlogic.gdx.physics.box2d.World
import galenscovell.sandbox.ecs.component._
import galenscovell.sandbox.ecs.component.collectibles.GatherableComponent
import galenscovell.sandbox.util.{Constants, DataParser}


class EntityCreator(engine: Engine,
                    world: World) {
  private val dataParser: DataParser = new DataParser


  def makePlayer(posX: Float, posY: Float): Entity = {
    val e: Entity = new Entity
    val bodyComponent: BodyComponent = new BodyComponent(
      e, world, posX, posY, Constants.MEDIUM_ENTITY_SIZE, movable=true, soft=true
    )

    e.add(bodyComponent)
    e.add(new MovementComponent)
    e.add(new PlayerComponent)
    e.add(new RenderableComponent)
    e.add(new SizeComponent(Constants.SMALL_ENTITY_SIZE, Constants.MEDIUM_ENTITY_SIZE))
    e.add(new SpriteComponent("player"))

    engine.addEntity(e)
    e
  }

  def makeGatherable(posX: Float, posY: Float): Unit = {
    var entity: Entity = new Entity
    val bodyComponent: BodyComponent = new BodyComponent(
      entity, world, posX, posY, Constants.TILE_SIZE, movable=false, soft=true
    )

    entity.add(bodyComponent)
    entity.add(new GatherableComponent)
    entity.add(new RenderableComponent)
    entity.add(new SizeComponent(Constants.TILE_SIZE, Constants.TILE_SIZE))
    entity.add(new SpriteComponent("gatherable"))

    entity = dataParser.parseGatherable("gatherable", entity)

    engine.addEntity(entity)
  }

  def makeCrop(size: Float, posX: Float, posY: Float): Unit = {
    val e: Entity = new Entity
    val bodyComponent: BodyComponent = new BodyComponent(
      e, world, posX, posY, Constants.SMALL_ENTITY_SIZE, movable=false, soft=true
    )
  }

  def makeCharacter(): Unit = {

  }

  def makeAnimal(): Unit = {

  }
}
