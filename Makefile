#installAndStart:
#	gradle installDist;
#	./build/install/app/bin/app
#
#setup:
#	npm install
#	./gradlew wrapper --gradle-version 8.7
#	./gradlew build
#
#frontend:
#	make -C frontend start
#
#backend:
#	./gradlew bootRun --args='--spring.profiles.active=dev'
#
#clean:
#	./gradlew clean
#
#build:
#	chmod +x gradlew
#	./gradlew clean build
#
#dev:
#	heroku local
#
#reload-classes:
#	./gradlew -t classes
#
#start-prod:
#	./gradlew bootRun --args='--spring.profiles.active=prod'
#
#install:
#	./gradlew installDist
#
## start-dist:
## 	./build/install/app/bin/app
#
#lint:
#	./gradlew checkstyleMain checkstyleTest
#
#test:
#	chmod +x gradlew
#	./gradlew test
#
#report:
#	chmod +x gradlew
#	./gradlew jacocoTestReport
#
#update-js-deps:
#	npx ncu -u
#
#check-java-deps:
#	./gradlew dependencyUpdates -Drevision=release
#
## generate-migrations:
## 	gradle diffChangeLog
#
## db-migrate:
## 	./gradlew update
#
#
#.PHONY: build frontend
setup:
	gradle wrapper --gradle-version 8.4

clean:
	./gradlew clean

build:
	./gradlew clean build

run-dist:
	./build/install/bin/-h

install:
	./gradlew clean install

run-install-dist: install run-dist

run:
	./gradlew run

run-dist:
	./build/install/bin/app

stop:
	./gradlew stop

report:
	./gradlew jacocoTestReport

generate-migrations:
	./gradlew generateMigrations

.PHONY: build