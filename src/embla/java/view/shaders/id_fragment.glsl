#version 150 core

in vec4 pass_color;

out vec4 out_color;

void main(void) {
	out_color = pass_color;
}