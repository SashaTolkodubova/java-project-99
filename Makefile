installAndStart:
	chmod +x gradlew
	gradle installDist;
	./build/install/app/bin/app

setup:
	chmod +x gradlew
	npm install
	./gradlew wrapper --gradle-version 8.7
	./gradlew build

frontend:
	chmod +x gradlew
	make -C frontend start

backend:
	chmod +x gradlew
	./gradlew bootRun --args='--spring.profiles.active=dev'

clean:
	chmod +x gradlew
	./gradlew clean

build:
	chmod +x gradlew
	./gradlew clean build

dev:
	chmod +x gradlew
	heroku local

reload-classes:
	chmod +x gradlew
	./gradlew -t classes

start-prod:
	chmod +x gradlew
	./gradlew bootRun --args='--spring.profiles.active=prod'

install:
	chmod +x gradlew
	./gradlew installDist

# start-dist:
# 	./build/install/app/bin/app

lint:
	chmod +x gradlew
	./gradlew checkstyleMain checkstyleTest

test:
	chmod +x gradlew
	./gradlew test

report:
	chmod +x gradlew
	./gradlew jacocoTestReport

update-js-deps:
	chmod +x gradlew
	npx ncu -u

check-java-deps:
	chmod +x gradlew
	./gradlew dependencyUpdates -Drevision=release

# generate-migrations:
# 	gradle diffChangeLog

# db-migrate:
# 	./gradlew update


.PHONY: build frontend
