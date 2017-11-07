package galenscovell.sandbox.ecs.component

import com.badlogic.ashley.core.{Component, Entity}
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d._
import galenscovell.sandbox.util.Constants


class BodyComponent(entity: Entity,
                    world: World,
                    posX: Float,
                    posY: Float,
                    size: Float,
                    movable: Boolean,
                    soft: Boolean) extends Component{
  val body: Body = createBody
  val fixture: Fixture = createFixture
  body.setUserData(entity)
  fixture.setUserData(this)


  private def createBody: Body = {
    val bodyDef: BodyDef = new BodyDef

    if (!movable) {
      if (soft) {
        bodyDef.`type` = BodyType.KinematicBody
      } else {
        bodyDef.`type` = BodyType.StaticBody
      }
    } else {
      bodyDef.`type` = BodyType.DynamicBody
    }

    bodyDef.fixedRotation = true
    bodyDef.angularDamping = 1f
    bodyDef.linearDamping = 0.1f
    bodyDef.position.set(posX, posY)

    world.createBody(bodyDef)
  }

  private def createFixture: Fixture = {
    val shape: Shape = new CircleShape
    shape.setRadius(size / 3)

    // val shape: PolygonShape = new PolygonShape
    // shape.setAsBox(size / 3, size / 3)

    val fixtureDef: FixtureDef = new FixtureDef
    fixtureDef.shape = shape
    fixtureDef.density = 1f
    fixtureDef.friction = 0.1f
    fixtureDef.filter.categoryBits = Constants.ENTITY_CATEGORY
    fixtureDef.filter.maskBits = Constants.ENTITY_MASK
    val fixture: Fixture = body.createFixture(fixtureDef)

    shape.dispose()
    fixture
  }

  def updateCollision(newCategory: Short, newMask: Short): Unit = {
    val filter: Filter = fixture.getFilterData
    filter.categoryBits = newCategory
    filter.maskBits = newMask
    fixture.setFilterData(filter)
  }

  def inMotion: Boolean = {
    !body.getLinearVelocity.isZero(0.005f)
  }
}
