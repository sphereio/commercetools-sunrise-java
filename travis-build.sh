#! /bin/bash

set -e

export REPO="sphereio/sunrise"
export TAG=`if [ "$TRAVIS_BRANCH" == "master" ]; then echo "latest"; else echo ${TRAVIS_BRANCH/\//-} ; fi`
echo "Building Docker image using tag '${REPO}:${COMMIT}'."
BUILD_VCS_NUMBER=$COMMIT ./sbt clean docker:publishLocal

echo "Adding additional tag '${REPO}:${TAG}' to already built Docker image '${REPO}:${COMMIT}'."
docker tag -f $REPO:$COMMIT $REPO:$TAG
echo "Adding additional tag '${REPO}:travis-${TRAVIS_BUILD_NUMBER}' to already built Docker image '${REPO}:${COMMIT}'."
docker tag -f $REPO:$COMMIT $REPO:travis-$TRAVIS_BUILD_NUMBER
if [ "$TRAVIS_TAG" ]; then
  echo "Adding additional tag '${REPO}:production' to already built Docker image '${REPO}:${COMMIT}'."
  docker tag -f $REPO:$COMMIT $REPO:production;
fi
echo "Pushing Docker images to repository '${REPO}' (all local tags are pushed)."
docker push $REPO
