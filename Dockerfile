FROM gradle:8.7.0-jdk21

WORKDIR /demo

COPY / .

RUN gradle installDist

CMD ./build/install/demo/bin/demo