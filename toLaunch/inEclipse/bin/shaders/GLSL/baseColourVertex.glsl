#version 400 core

in vec3 in_Position;
in vec2 in_TexCoords;
in vec3 in_Colour;

uniform mat4 projectionMatrix;
uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;
uniform vec3 color;

out vec2 pass_TexCoords;
out vec3 pass_Colour;
out vec3 pass_Color;

void main(void)
{
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(in_Position, 1.0);
	
	pass_TexCoords = in_TexCoords;
	pass_Colour = in_Colour;
	pass_Color = color;
}