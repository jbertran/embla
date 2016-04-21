#version 150 core

in vec4 in_position;
in vec4 in_color;

out vec4 pass_color;

void main(void) {
	gl_Position = in_position;
	pass_color = in_color;
} 