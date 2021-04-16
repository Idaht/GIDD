keystore:
	-rm src/main/resources/keystore/gruppe3.p12
	-rm src/main/resources/keystore/giddCertificate.crt
	keytool -genkeypair -dname "cn=, ou=, o=, c=NO" -alias gruppe3 -storepass password -keypass password -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore src/main/resources/keystore/gruppe3.p12 -validity 3650
	keytool -export -keystore src/main/resources/keystore/gruppe3.p12 -alias gruppe3 -file src/main/resources/keystore/giddCertificate.crt -storepass password
	keytool -importcert -file src/main/resources/keystore/giddCertificate.crt -storepass changeit -alias gruppe3 -cacerts