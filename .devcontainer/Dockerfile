# Start with a base image that has Java and other tools
FROM debian:bookworm-slim

# Install Android SDK and other dependencies
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get install -y openjdk-17-jdk curl unzip \
    && curl -o android-sdk.zip https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip \
    && mkdir -p /usr/local/lib/android/sdk \
    && unzip android-sdk.zip -d /usr/local/lib/android/sdk/cmdline-tools \
    && rm android-sdk.zip \
    && mv /usr/local/lib/android/sdk/cmdline-tools/cmdline-tools /usr/local/lib/android/sdk/cmdline-tools/latest

# Set up PATH for the new SDK tools
ENV PATH="/usr/local/lib/android/sdk/cmdline-tools/latest/bin:/usr/local/lib/android/sdk/platform-tools:/usr/lib/jvm/openjdk-17-jdk/bin:${PATH}"