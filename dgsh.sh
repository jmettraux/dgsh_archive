#!/bin/sh

#
# dgsh.sh
#
# This sh script should start the Dungeon Shell
#
# $Id: dgsh.sh,v 1.8 2002/07/24 07:38:25 jmettraux Exp $
#

# if necessary, modify value and uncomment :
#JAVA_HOME=/usr/local/java

# set here how many lines your console has
TERM_LINES=55
#TERM_LINES=25

java -classpath lib/jython-2.1.jar:lib/log4j-1.2.5.jar:/usr/local/java/jre/lib/rt.jar:classes/:lib/dgsh.jar -Ddsh.term.lines=$TERM_LINES -Dpython.path=lib/jyLib.jar burningbox.org.dsh.Shell
