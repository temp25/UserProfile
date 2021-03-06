#!/bin/bash

export JAVA_TOOL_OPTIONS="-Xmx256m -Xms64m -Xss228k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8"
chmod +x gradlew
./gradlew bootJar
java -Dserver.port=$PORT $JAVA_TOOL_OPTIONS -jar build/libs/userprofile-1.0.0-SNAPSHOT.jar