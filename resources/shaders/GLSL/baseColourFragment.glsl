#version 400 core

in vec2 pass_TexCoords;
in vec3 pass_Colour;
in vec3 pass_Color;

uniform sampler2D texture_sampler;

out vec4 out_Colour;

void main(void)
{
	out_Colour = texture(texture_sampler, pass_TexCoords) * vec4(pass_Color, 1.0);
}