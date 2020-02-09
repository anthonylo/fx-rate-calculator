#
# Building the image
#
# Usage: docker image build -t fx-rate-calculator:1.0.0 .
#
# Description: This tells Docker to build a new image with the tag fx-rate-calculator:1.0.0 using
#              the Dockerfile which exists in the directory that this is ran in
#
#
# Running the image
#
# Usage: docker container run -p 7000:7000 fx-rate-calculator:1.0.0
#
# Description: This tells Docker to spawn a container using the fx-rate-calculator:1.0.0 image
#              using port 7000
#

# Specify that I want to use the openjdk8 image as the base
FROM openjdk:8-jdk-alpine

# Specify that I am the maintainer of this project
MAINTAINER loanthony09@gmail.com

# Copy the build jar from the maven target local folder to the deployments folder on the image
COPY target/fx-rate-calculator*.jar /deployments/lib/fx-rate-calculator.jar

# Use the deployments folder as the base for this image
WORKDIR /deployments

# Specify that this is the main class of the jar
ENV JAVA_MAIN_CLASS=com.anthonylo.fx.calculator.FxRateCalculatorApplication

# When the image is ran, start the image using java -jar /deployments/lib/fx-rate-calculator.jar
ENTRYPOINT ["java", "-jar", "/deployments/lib/fx-rate-calculator.jar"]

# Expose port 7000
EXPOSE 7000
