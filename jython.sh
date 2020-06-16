#!/bin/sh

#
# jython.sh
#
# Use this sh script to run python scripts you will find in dgsh/scripts. 
# If you downloaded the source of dgsh, the python scripts will be available in
# dgsh/src/jython/
#
# $Id: jython.sh,v 1.6 2002/11/25 07:41:17 jmettraux Exp $
#

# if necessary, modify value and uncomment :
#JAVA_HOME=/usr/local/java

java -classpath Lib/jylib.jar:lib/jython-2.1.jar:lib/log4j-1.2.5.jar:$JAVA_HOME/jre/lib/rt.jar:classes:lib/dgsh.jar/ -Dpython.path=lib/jyLib.jar org.python.util.jython $*
