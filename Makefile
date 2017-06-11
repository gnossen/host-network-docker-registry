DOCKER_REGISTRY ?= gnossen
IMAGE_NAME := host-network-docker-registry-proxy
MAJOR_VERSION := 0
MINOR_VERSION := 0
BUILD_NUMBER ?= dev
IMAGE_VERSION := ${MAJOR_VERSION}.${MINOR_VERSION}.${BUILD_NUMBER}
IMAGE_TAG := ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_VERSION}

.PHONY: build
build: Dockerfile
	docker build -t ${IMAGE_TAG} .

.PHONY: deploy
deploy:
	docker push ${IMAGE_TAG}
