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
	git update-index --chmod=+x gradlew
	gradle wrapper --gradle-version 8.4

clean:
	git update-index --chmod=+x gradlew
	./gradlew clean

build:
	git update-index --chmod=+x gradlew
	./gradlew clean build

run-dist:
	git update-index --chmod=+x gradlew
	./build/install/bin/-h

install:
	git update-index --chmod=+x gradlew
	./gradlew clean install

run-install-dist:
	git update-index --chmod=+x gradlew
	install run-dist

run:
	git update-index --chmod=+x gradlew
	./gradlew run

run-dist:
	git update-index --chmod=+x gradlew
	./build/install/bin/app

stop:
	git update-index --chmod=+x gradlew
	./gradlew stop

report:
	git update-index --chmod=+x gradlew
	./gradlew jacocoTestReport

generate-migrations:
	git update-index --chmod=+x gradlew
	./gradlew generateMigrations

.PHONY: build