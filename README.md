# Snake
A simple Snake program with save/load and top list options written in Java developed by Gergő Balkus

Run:
mvn clean compile package site site:deploy install exec:java -Dexec.mainClass=pkg.Main

Note:
OJDBC6 is needed, therefore you'll have to install the provided ojdbc6.jar to your local repository with maven.

mvn install:install-file -Dfile=/home/nute/workspace/Snake/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.2.0 -Dpackaging=jar -DgeneratePom=true


