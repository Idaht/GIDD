keystore:
	-rm src/main/resources/keystore/gruppe3.p12
	-rm src/main/resources/keystore/giddCertificate.crt
	-rm src/main/resources/keystore/private.key
	-rm src/main/resources/keystore/nopassword.key 
	-rm src/main/resources/keystore/server.pem 
	keytool -genkeypair -dname "cn=, ou=, o=, c=NO" -alias gruppe3 -storepass password -keypass password -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore src/main/resources/keystore/gruppe3.p12 -validity 3650
	keytool -export -keystore src/main/resources/keystore/gruppe3.p12 -alias gruppe3 -file src/main/resources/keystore/giddCertificate.crt -storepass password
	openssl pkcs12 -in src/main/resources/keystore/gruppe3.p12 -nodes -nocerts -out src/main/resources/keystore/private.key -password pass:password
	openssl rsa -in src/main/resources/keystore/private.key -out src/main/resources/keystore/nopassword.key
	cat src/main/resources/keystore/nopassword.key > src/main/resources/keystore/server.pem
	cat src/main/resources/keystore/giddCertificate.crt >> src/main/resources/keystore/server.pem