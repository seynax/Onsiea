#version 400 core

in vec2 pass_uvs;

uniform sampler2D texture_sampler;

out vec4 out_Colour;

void main(void)
{
	out_Colour = texture(texture_sampler, pass_uvs);
}