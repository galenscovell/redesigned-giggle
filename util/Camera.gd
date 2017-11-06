extends Camera2D

onready var player_body

func _ready():
	player_body = get_tree().get_root().get_node("Main/Screen/Game/PlayerBody")

func attach_to_body(b):
	self.get_parent().remove_child(self)
	b.add_child(self)
	set_owner(b)

func attach_to_player():
	self.get_parent().remove_child(self)
	player_body.add_child(self)
	set_owner(player_body)