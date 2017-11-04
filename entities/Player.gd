extends "BaseEntity.gd"

var input_enabled = true


##############
#    Init    #
##############
func _setup():
	._setup()
	set_process_input(true)


##############
#   Update   #
##############
func _fixed_process(delta):
	._fixed_process(delta)

	if input_enabled:
		_default_update()


##############
#   States   #
##############
func _default_update():
	._restore_default_speed()
	._set_next_velocity(_get_axis_input())


#############
#   Input   #
#############
func enable_input(setting):
	input_enabled = setting

func _get_axis_input():
	var axis_vector = Vector2()
	
	if Input.is_action_pressed("pad_up"):
		axis_vector += Vector2(0, -1)
	if Input.is_action_pressed("pad_down"):
		axis_vector += Vector2(0, 1)
	if Input.is_action_pressed("pad_left"):
		axis_vector += Vector2(-1, 0)
	if Input.is_action_pressed("pad_right"):
		axis_vector += Vector2(1, 0)

	return axis_vector
