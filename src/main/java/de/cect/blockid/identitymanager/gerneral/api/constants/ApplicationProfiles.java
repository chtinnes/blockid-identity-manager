package de.cect.blockid.identitymanager.gerneral.api.constants;

/**
 * This class comprises constants for the profiles (spring profiles) that are
 * used by this application. Multiple spring profiles can be active at once.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public final class ApplicationProfiles {

	/**
	 * The default application profile.
	 */
	public static final String DEFAULT = "default";

	/**
	 * The profile for an identity giver, which allows to make identity assertions
	 * and forwards it to the identity storage.
	 */
	public static final String IDGIVER = "idgiver";
	/**
	 * The profile for an identity validator, which allows to decrypt identity
	 * assertions and validate them against given business rules.
	 */
	public static final String IDVALIDATOR = "idvalidator";
	/**
	 * The profile for an identity receiver, which allows to fetch identity
	 * assertions and decrypt them.
	 */
	public static final String IDRECEIVER = "idreceiver";
	/**
	 * The test profile, which allows for additional test specific functionality. In
	 * production, this profile should not be switched on.
	 */
	public static final String TEST = "test";

	/**
	 * The profile for the example app, this app servers only as a demo application
	 * and should not be part during productive deployment.
	 */
	public static final String EXAMPLEAPP = "exampleapp";

	/**
	 * Dummy constructor.
	 */
	private ApplicationProfiles() {
	}

}
