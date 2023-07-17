echo "Gradle build...."
./gradlew clean build
echo "Gradle build fin."

echo "Docker build...."
docker build -f .docker/Dockerfile-api -t hseungho/egomogo-api:$1 .
echo "Docker build fin."

echo "Docker push...."
docker push hseungho/egomogo-api:$1
echo "Docker push fin."
