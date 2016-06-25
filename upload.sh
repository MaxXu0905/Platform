if [ $# -ne 1 ]
then
	echo "Usage:" $0 "VERSION"
	exit 1
fi

VERSION=$1
PROJECT=Platform
mvn deploy:deploy-file -DgroupId=com.ailk.sets -DartifactId=I${PROJECT} -Dversion=${VERSION}-SNAPSHOT -Dpackaging=jar -Dfile=/Users/xugq/Workspaces/MyEclipse\ 10/${PROJECT}/target/${PROJECT}-${VERSION}-SNAPSHOT-interface.jar -Durl=http://10.1.249.33:8081/nexus/content/repositories/snapshots/