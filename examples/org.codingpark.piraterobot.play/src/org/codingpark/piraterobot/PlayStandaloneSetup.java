/*
* generated by Xtext
*/
package org.codingpark.piraterobot;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class PlayStandaloneSetup extends PlayStandaloneSetupGenerated{

	public static void doSetup() {
		new PlayStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}
