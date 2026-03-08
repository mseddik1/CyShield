FROM maven:3.9.9-eclipse-temurin-21

RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    curl \
    ca-certificates \
    gnupg \
    libglib2.0-0 \
    libnss3 \
    libgbm1 \
    libxss1 \
    libasound2t64 \
    libatk-bridge2.0-0 \
    libgtk-3-0 \
    libu2f-udev \
    libvulkan1 \
    fonts-liberation \
    xdg-utils \
    && rm -rf /var/lib/apt/lists/*


WORKDIR /app

ENV CI=true

COPY pom.xml .
COPY src ./src

RUN mvn -DskipTests clean install

CMD ["mvn", "test", "-DsuiteXmlFile=src/test/suites/COM_Suite.xml"]