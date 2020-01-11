FROM openjdk:8-jdk-alpine AS build
LABEL maintainer="Natanael Copa <ncopa@alpinelinux.org>"
WORKDIR /workspace/app
RUN addgroup -S gowtham && adduser -S gowtham -G gowtham
USER gowtham
RUN chown -R gowtham:gowtham /workspace/app


COPY gradlew .
COPY gradle gradle
COPY build.gradle .
RUN ./gradlew dependencies


COPY src src
RUN ./gradlew build unpack -x test
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)


FROM openjdk:8-jre-alpine
LABEL maintainer="Natanael Copa <ncopa@alpinelinux.org>"
RUN addgroup -S gowtham && adduser -S gowtham -G gowtham
USER gowtham
RUN chown -R gowtham:gowtham /app
VOLUME ["/tmp"]
HEALTHCHECK --interval=5s --timeout=2s --retries=12 CMD curl --silent --fail localhost:8080/userProfileController/status || exit 1
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.paddyseedexpert.userprofile.UserProfileApplication"]


EXPOSE 8080
