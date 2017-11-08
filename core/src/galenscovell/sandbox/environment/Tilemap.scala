package galenscovell.sandbox.environment

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.maps.{MapLayer, MapProperties}
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.utils
import galenscovell.sandbox.processing.BaseSteerable
import galenscovell.sandbox.processing.pathfinding.AStarGraph
import galenscovell.sandbox.singletons.Constants

import scala.collection.mutable.ArrayBuffer


class Tilemap(world: World, mapName: String) {
  private val tileMap: TiledMap = new TmxMapLoader().load(s"maps/tilemaps/$mapName.tmx")
  private val tiledMapRenderer: OrthogonalTiledMapRenderer =
    new OrthogonalTiledMapRenderer(tileMap, Constants.TILE_SIZE / Constants.PIXEL_PER_METER)

  private val propSteerables: ArrayBuffer[Steerable[Vector2]] = new ArrayBuffer[Steerable[Vector2]]()
  private val baseLayers: scala.Array[Int] = scala.Array(0, 1)
  private val overlapLayers: scala.Array[Int] = scala.Array(2)

  private var aStarGraph: AStarGraph = _

  create()


  /********************
    *       Get       *
    ********************/
  def getAStarGraph: AStarGraph = aStarGraph
  def getPropSteerables: Array[Steerable[Vector2]] = propSteerables.toArray


  /********************
    *      Update     *
    ********************/
  def updateShader(shaderProgram: ShaderProgram): Unit = {
    tiledMapRenderer.getBatch.setShader(shaderProgram)
  }

  def updateCamera(camera: OrthographicCamera): Unit = {
    tiledMapRenderer.setView(camera)
  }


  /********************
    *      Render     *
    ********************/
  def renderBaseLayer(): Unit = {
    tiledMapRenderer.render(baseLayers)
  }

  def renderOverlapLayer(): Unit = {
    tiledMapRenderer.render(overlapLayers)
  }


  /********************
    *    Creation     *
    ********************/
  private def create(): Unit = {
    val prop: MapProperties = tileMap.getProperties
    val mapWidth: Int = prop.get("width", classOf[Int])
    val mapHeight: Int = prop.get("height", classOf[Int])
    val tileWidth: Int = prop.get("tilewidth", classOf[Int])
    val tileHeight: Int = prop.get("tileheight", classOf[Int])

    println(mapWidth, mapHeight, tileWidth, tileHeight)

    // Find boundary collision objects
    val boundaryObjectLayer: MapLayer = tileMap.getLayers.get("BoundaryObjects")
    val boundaryObjects: utils.Array[RectangleMapObject] = boundaryObjectLayer.getObjects.getByType(classOf[RectangleMapObject])

    for (rmo: RectangleMapObject <- boundaryObjects.toArray) {
      val rect: Rectangle = rmo.getRectangle
      createCollisionBody(
        rect.x / Constants.PIXEL_PER_METER, rect.y / Constants.PIXEL_PER_METER,
        rect.width / Constants.PIXEL_PER_METER, rect.height / Constants.PIXEL_PER_METER
      )
    }

    // Find prop collision objects
    val propObjectLayer: MapLayer = tileMap.getLayers.get("PropObjects")
    val propObjects: utils.Array[RectangleMapObject] = propObjectLayer.getObjects.getByType(classOf[RectangleMapObject])

    for (rmo: RectangleMapObject <- propObjects.toArray) {
      val rect: Rectangle = rmo.getRectangle
      val rmoBody: Body = createCollisionBody(
        rect.x / Constants.PIXEL_PER_METER, rect.y / Constants.PIXEL_PER_METER,
        rect.width / Constants.PIXEL_PER_METER, rect.height / Constants.PIXEL_PER_METER
      )

      propSteerables += new BaseSteerable(rmoBody, rect.width / Constants.PIXEL_PER_METER)
    }

    // Assemble AStarGraph for pathfinding
    aStarGraph = new AStarGraph(world, tileMap, mapWidth, mapHeight)
  }


  private def createCollisionBody(rx: Float, ry: Float, width: Float, height: Float): Body = {
    val bodyDef: BodyDef = new BodyDef
    bodyDef.`type` = BodyType.StaticBody
    bodyDef.fixedRotation = true
    bodyDef.angularDamping = 1f
    bodyDef.position.set(rx + width / 2, ry + height / 2)

    val body: Body = world.createBody(bodyDef)

    val shape: PolygonShape = new PolygonShape()
    shape.setAsBox(width / 2, height / 2)

    val fixtureDef: FixtureDef = new FixtureDef
    fixtureDef.shape = shape
    fixtureDef.density = 1f
    fixtureDef.friction = 0.1f
    fixtureDef.filter.categoryBits = Constants.WALL_CATEGORY
    fixtureDef.filter.maskBits = Constants.WALL_MASK

    val fixture: Fixture = body.createFixture(fixtureDef)
    shape.dispose()

    body
  }
}
