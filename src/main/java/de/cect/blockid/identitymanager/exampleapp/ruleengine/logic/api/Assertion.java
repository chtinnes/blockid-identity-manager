package de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api;

/**
 * A assertion about a subject.
 * 
 * @param <V>
 *            the value type.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
public class Assertion<V> {
	private String subject;

	private V value;

	/**
	 * Getter for the subject of the assertion.
	 * 
	 * @return the subject.
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * Getter for the value of the assertion.
	 * 
	 * @return the value of the assertion
	 */
	public V getValue() {
		return this.value;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(V value) {
		this.value = value;
	}

}
