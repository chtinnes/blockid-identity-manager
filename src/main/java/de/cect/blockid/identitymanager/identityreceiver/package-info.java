package de.cect.blockid.identitymanager.identityreceiver;

/**
 * 
 * Component for receiving identity assertions. The application should define
 * its own public key and use it to fetch its own assertions from an identity
 * storage. Since should also known the matching private key, it is able do
 * decrypt the identity assertion.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */