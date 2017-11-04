extends KinematicBody2D

const WALK_SPEED = 100

func _ready():
	set_fixed_process(true)
	set_process_input(true)

func _fixed_process(delta):
	var motion = Vector2()
	
	if Input.is_action_pressed("pad_up"):
		motion += Vector2(0, -1)
	if Input.is_action_pressed("pad_down"):
		motion += Vector2(0, 1)
	if Input.is_action_pressed("pad_left"):
		motion += Vector2(-1, 0)
	if Input.is_action_pressed("pad_right"):
		motion += Vector2(1, 0)
	
	motion = motion.normalized() * WALK_SPEED * delta
	move(motion)