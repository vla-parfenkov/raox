/*
* generated by Xtext
*/
package ru.bmstu.rk9.rdo;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class RDOStandaloneSetup extends RDOStandaloneSetupGenerated{

	public static void doSetup() {
		new RDOStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}
