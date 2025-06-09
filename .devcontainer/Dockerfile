# Use Java 21 LTS
ARG VARIANT=21
FROM mcr.microsoft.com/devcontainers/java:${VARIANT}

# Install Maven
ARG MAVEN_VERSION="3.9.4"
RUN su vscode -c "source /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""