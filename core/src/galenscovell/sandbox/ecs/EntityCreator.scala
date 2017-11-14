package galenscovell.sandbox.ecs

import com.badlogic.ashley.core.{Engine, Entity}
import com.badlogic.gdx.physics.box2d.World
import galenscovell.sandbox.ecs.component._
import galenscovell.sandbox.enums.Crop
import galenscovell.sandbox.singletons.Constants
import galenscovell.sandbox.stateMachines.{CropAgent, PlayerAgent}
import galenscovell.sandbox.util.DataParser


class EntityCreator(engine: Engine,
                    world: World) {
  private val dataParser: DataParser = new DataParser


  def makePlayer(posX: Float, posY: Float): Entity = {
    val entity: Entity = new Entity
    val bodyComponent: BodyComponent = new BodyComponent(
      entity, world, posX, posY, Constants.MEDIUM_ENTITY_SIZE, movable=true, soft=true
    )

    entity.add(bodyComponent)
    entity.add(new MovementComponent)
    entity.add(new PlayerComponent)
    entity.add(new RenderableComponent)
    entity.add(new SizeComponent(Constants.SMALL_ENTITY_SIZE, Constants.MEDIUM_ENTITY_SIZE))
    entity.add(new SpriteComponent("player"))
    entity.add(new StateComponent(PlayerAgent.DEFAULT))
    entity.add(new SteeringComponent(bodyComponent.body, Constants.MEDIUM_ENTITY_SIZE, 10, 10))

    engine.addEntity(entity)
    entity
  }

  def makeGatherable(posX: Float, posY: Float): Unit = {
    var entity: Entity = new Entity
    val bodyComponent: BodyComponent = new BodyComponent(
      entity, world, posX, posY, Constants.TILE_SIZE, movable=false, soft=true
    )

    entity.add(bodyComponent)
    entity.add(new RenderableComponent)
    entity.add(new SizeComponent(Constants.TILE_SIZE, Constants.TILE_SIZE))
    entity.add(new SpriteComponent("gatherable"))

    entity = dataParser.parseGatherable("gatherable", entity)

    engine.addEntity(entity)
  }

  def makeCrop(crop: Crop.Value, width: Float, height: Float, bodySize: Float, posX: Float, posY: Float): Unit = {
    var entity: Entity = new Entity
    val bodyComponent: BodyComponent = new BodyComponent(
      entity, world, posX, posY, bodySize, movable=false, soft=true
    )

    entity.add(bodyComponent)
    entity.add(new RenderableComponent)
    entity.add(new StateComponent(CropAgent.DEFAULT))
    entity.add(new SizeComponent(width, height))

    entity = dataParser.parseCrop(crop, entity)

    engine.addEntity(entity)
  }

  def makeCharacter(): Unit = {

  }

  def makeAnimal(): Unit = {

  }
}
