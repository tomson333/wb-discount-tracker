FROM ubuntu:22.04
RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y openjdk-17-jdk curl unzip wget git ca-certificates
WORKDIR /workspace
COPY . /workspace
RUN chmod +x ./gradlew || true
# Install sdkman/Android cmdline tools skipped for brevity; recommended to build with local Android Studio.
RUN ./gradlew :app:assembleDebug --no-daemon || true
