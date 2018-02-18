FROM picoded/ubuntu-openjdk-8-jdk
ADD tree.json /bot/tree.json
ADD target/botstate.jar /bot/botstate.jar
ENTRYPOINT [ "java", "-jar", "/bot/botstate.jar", "/bot/tree.json" ]
