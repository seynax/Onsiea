#version 400 core

in vec2 in_Position;

uniform mat4 projectionMatrix;
uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;

out vec2 pass_uvs;

void main(void)
{
	gl_Position = transformationMatrix * vec4(in_Position, 0.0, 1.0);

	pass_uvs = vec2((in_Position.x + 0.5), (0.5 - in_Position.y)); 
}