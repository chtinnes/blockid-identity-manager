package de.cect.blockid.identitymanager.gerneral.api.constants;

/**
 * Collection of all application wide constant.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public final class ApplicationConstants {
	/**
	 * App version.
	 */
	public static final String APP_VERSION = "0.1.0";

	/**
	 * Root path for the REST endpoints.
	 */
	public static final String REST_ROOT_PATH = "/blockid/v1";

	/**
	 * Path for the identity giver rest endpoints.
	 */
	public static final String IDENTITYGIVER_PATH = "/identitygiver/";

	/**
	 * Path for the identity validator rest endpoints.
	 */
	public static final String IDENTITYVALIDATOR_PATH = "/identityvalidator/";

	/**
	 * Path for the example app rest endpoints.
	 */
	public static final String EXAMPLEAPP_PATH = "/exampleapp/";

	/**
	 * Path for the identity receiver rest endpoints.
	 */
	public static final String IDENTITYRECEIVER_PATH = "/identityreceiver/";

	/**
	 * Private dummy constructor to hide the public one.
	 */
	private ApplicationConstants() {

	}

}
