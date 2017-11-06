extends Node

var current_screen
var fps

var screens = {
	"load": preload("res://ui/screens/Load.tscn"),
	"main_menu": preload("res://ui/screens/MainMenu.tscn"),
	"game": preload("res://levels/SandboxLevel.tscn")
}

func _ready():
	set_process_input(true)
	set_process(true)
	current_screen = find_node("Screen")
	fps = find_node("FPS")
	_load_screen("game")

func _input(event):
	return
	
func _process(delta):
	fps.set_text("FPS: %d" % Performance.get_monitor(Performance.TIME_FPS))
	
func _load_screen(name):
	if name in screens:
		var old_screen = null
		if current_screen.get_child_count() > 0:
			old_screen = current_screen.get_child(0)
		if old_screen != null:
			current_screen.remove_child(old_screen)

		var new_screen = screens[name].instance()
		new_screen.connect("next_screen", self, "_load_screen")
		current_screen.add_child(new_screen)
	else:
		print("[ERROR] Cannot load screen: ", name)
