package de.cect.blockid.identitymanager.exampleapp.ruleengine.logic.api;

/**
 * Generic class to define the subject of a rule.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@Deprecated
public class RuleSubject<V> {

	private String subject;

	private Class<V> valueType;

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the valueType
	 */
	public Class<V> getValue() {
		return valueType;
	}

	/**
	 * @param valueType
	 *            the value to set
	 */
	public void setValue(Class<V> valueType) {
		this.valueType = valueType;
	}

}
