keystore:
	-rm gruppe3.p12 && src/main/resources/keystore/gruppe3.p12
	keytool -genkeypair -alias gruppe3 -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore gruppe3.p12 -validity 3650
	mv gruppe3.p12 src/main/resources/keystore