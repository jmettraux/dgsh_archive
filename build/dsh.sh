#!/bin/sh

java \
    -classpath ./log4j-1.2rc1.jar:./dgsh.jar:/usr/local/j2sdk1.4.0/jre/lib/rt.jar \
    -Ddsh.term.lines=55 burningbox.org.dsh.Shell
