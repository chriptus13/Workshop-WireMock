FROM wiremock/wiremock:latest
ADD resources /home/wiremock

ARG WIREMOCK_FAKER_VERSION="0.1.1"

ADD https://repo1.maven.org/maven2/org/wiremock/extensions/wiremock-faker-extension-standalone/$WIREMOCK_FAKER_VERSION/wiremock-faker-extension-standalone-$WIREMOCK_FAKER_VERSION.jar /var/wiremock/extensions

ENTRYPOINT ["/docker-entrypoint.sh", "--global-response-templating", "--disable-gzip", "--verbose", "--extensions", "org.wiremock.RandomExtension"]