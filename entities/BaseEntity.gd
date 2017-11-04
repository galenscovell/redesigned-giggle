extends KinematicBody2D

export(float) var default_speed

var can_move = true
var moving = false
var zero_vector = Vector2()
var next_velocity = Vector2()
var facing_direction = Vector2()

onready var current_speed


##############
#    Init    #
##############
func _ready():
    _setup()
    
func _setup():
    current_speed = default_speed
    set_fixed_process(true)


##############
#   Update   #
##############
func _fixed_process(delta):
    if can_move:
        # Entity is allowed to move
        if _is_moving():
            # If moving, set direction moved in
            _set_facing_direction(next_velocity)
            moving = true
        else:
            # If idle, use last direction moved in as facing direction
            moving = false
    else:
        # Entity is not allowed to move
        set_next_velocity(zero_vector)

    # Set new body velocity directly based on nextVelocity
    move(next_velocity.normalized() * current_speed)
    

##############
#    Get     #
##############
func _is_moving():
    return zero_vector.distance_to(next_velocity) > 0.1


##############
#    Set     #
##############
func _enable_motion():
    can_move = true

func _disable_motion():
    can_move = false

func _set_speed(s):
    current_speed = s

func _restore_default_speed():
    current_speed = default_speed

func _set_next_velocity(velocity):
    next_velocity = velocity

func _set_facing_direction(velocity):
    facing_direction = velocity.normalized()