@echo off
cd "P:\Java\Eclipse\Engines\GameDev\GameDev 5.1\resources\shaders\GLSLtoSPIRV"
for %%i in (*.vert *.frag) do "C:\VulkanSDK\1.2.170.0\Bin\glslangValidator.exe" -V "%%~i" -o "P:\Java\Eclipse\Engines\GameDev\GameDev 5.1\resources\shaders\SPIRV\%%~i.spv"
cd "P:\Java\Eclipse\Engines\GameDev\GameDev 5.1\"