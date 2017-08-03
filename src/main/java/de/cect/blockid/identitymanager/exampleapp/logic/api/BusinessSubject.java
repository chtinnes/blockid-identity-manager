package de.cect.blockid.identitymanager.exampleapp.logic.api;

/**
 * Enum for business properties used in an application.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public enum BusinessSubject {

	AGE("AGE", Integer.class), SIZE("SIZE", Integer.class), GENDER("GENDER",
			Character.class), CREDITWORTHINESS("CREDITWORTHINESS", Character.class);

	private String description;
	private Class<?> type;

	/**
	 * Default constructor.
	 */
	private BusinessSubject(String description, Class<?> type) {
		this.description = description;
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public Class<?> getType() {
		return type;
	}

	public static BusinessSubject fromDescription(String description) {
		for (BusinessSubject subject : BusinessSubject.values()) {
			if (subject.getDescription().equals(description.toUpperCase())) {
				return subject;
			}
		}
		return null;
	}
}
