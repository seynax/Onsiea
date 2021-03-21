package fr.seynax.onsiea.vulkan;

import fr.seynax.onsiea.utils.FileUtils;

public class Pipeline
{
	// Variables

	// Constructor

	public Pipeline(final String vertCodeFilePath, final String fragCodeFilePathIn)
	{

	}

	// Methods

	public void createGraphicsPipeline(final String vertCodeFilePath, final String fragCodeFilePathIn)
	{
		final var	vertCode	= FileUtils.loadResource(vertCodeFilePath);
		final var	fragCode	= FileUtils.loadResource(fragCodeFilePathIn);

		System.out.println(vertCode.length() + " | " + fragCode.length());
	}

	// Getter | Setter
}