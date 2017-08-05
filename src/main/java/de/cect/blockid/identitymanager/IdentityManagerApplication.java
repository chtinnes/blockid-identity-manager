package de.cect.blockid.identitymanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.util.Base64;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import com.google.common.io.Files;

import de.cect.blockid.identitymanager.gerneral.common.CryptoUtils;

/**
 * Main class for the identity manager application. This application has two
 * operations modes. If started without arguments, it is run in production mode.
 * If started with one parameters, then it initializes the application (i.e.
 * generates RSA keys) and stores them in an application.properties file.
 * 
 * The parameter is the path to overriding application.properties file.
 * 
 * @author ctinnes
 * @since blockid-0.0.1
 *
 */
@SpringBootApplication
public class IdentityManagerApplication {
	private static final Logger LOG = LoggerFactory.getLogger(IdentityManagerApplication.class);

	public static void main(String[] args) {
		switch (args.length) {
		case 0:
			LOG.info("Starting identity manager application in production mode.");
			SpringApplication.run(IdentityManagerApplication.class, args);
			break;
		case 1:
			LOG.info("Starting identity manager application in initialization mode.");
			File file = new File(args[0]);
			if (file.exists()) {
				LOG.info("Initialization has already taken place.");
				break;
			} else {
				LOG.info("Initializing identity manager. Generating RSA keys.");
				initialize(file);
			}
			break;

		default:
			LOG.error(
					"Invalid number of agruments. Either no arumgents (production) or one argument for the file path to a overrding application properties file (initialization) is expected.");
		}

	}

	/**
	 * Initializes the app, i.e. generates RSA keys for the identity.
	 * 
	 * @param outputFile
	 *            the file where the generated properties should be stored.
	 * @throws IOException
	 */
	private static void initialize(File outputFile) {
		ClassPathResource resource = new ClassPathResource("overriding.template");
		try (InputStream inputStream = resource.getInputStream();
				Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
			String templateContent = s.hasNext() ? s.next() : "";
			KeyPair rsaKeyPair = new CryptoUtils().generateRsaKeyPair();
			String privateKeyBase64 = Base64.getEncoder().encodeToString(rsaKeyPair.getPrivate().getEncoded());
			String publicKeyBase64 = Base64.getEncoder().encodeToString(rsaKeyPair.getPublic().getEncoded());
			String content = templateContent.replaceAll("pubkey_template", publicKeyBase64);
			content = content.replaceAll("privkey_template", privateKeyBase64);
			outputFile.createNewFile();
			Files.write(content.getBytes(), outputFile);
		} catch (IOException e) {
			LOG.error("Could not initialize application due to file problems:", e);
		}

	}

}
