package equilinoxmodkit.loader;

import equilinoxmodkit.util.EmkLogger;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;


/* Main class of the Equilinox Mod Loader. */
class EmlLauncher {
	
	
	public static void main( String[] args ) {
		EmkLogger.log( "Starting the Equilinox Mod Loader" );
		LaunchHelper.handleLaunchArguments( args );
		LaunchHelper.prepareLaunch();
		
		ModLoader.loadMods();
		ModLoader.sortMods();
		ModLoader.preInitializeMods();

		EmlLauncher.launchEquilinox();
	}
	
	
	static void stop( int exitCode ) {
		EmkLogger.log( "Stopping the Equilinox Mod Loader" );
		
		if(LaunchHelper.getLogFile() != null) EmkLogger.save( LaunchHelper.getLogFile() );
		System.exit( exitCode );
	}
	
	
	private static void launchEquilinox() {
		URLClassLoader loader = LaunchHelper.getLoader();

		EmkLogger.log( "Launching Equilinox" );
		/*
		try {
			Process process = new ProcessBuilder(
					new String[] {
							LaunchHelper.getJavaFile().getPath(),
							"-jar",
							LaunchHelper.getEquilinoxJar().getPath(),
							"-classpath",
							System.getProperty( "java.class.path" ),
							"-EmlDebug", // temporary
							"-Xms2G","-Xmx4G"
					}
			).inheritIO().start();
			int exitCode = process.waitFor();
			EmlLauncher.stop( exitCode );
		} catch( IOException | InterruptedException e ) {
			e.printStackTrace();
		}*/
		try {
			Class mainClass = loader.loadClass("main.MainApp");
			Method method = mainClass.getDeclaredMethod("main", String[].class);

			method.invoke(mainClass, new Object[] {new String[] {}});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		EmkLogger.log( "Stopped Equilinox" );
	}
}
