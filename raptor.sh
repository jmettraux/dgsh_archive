#!/bin/sh

#
# raptor.sh
#
# jmettraux@burningbox.org
#
# Raptor is a script for parsing Stat Blocks 
# (Standard ones and Jamis Buck's one are allowed)
#
# $Id: raptor.sh,v 1.2 2002/08/16 08:37:36 jmettraux Exp $
#

java -classpath lib/jython-2.1.jar:lib/log4j-1.2.5.jar:/usr/local/java/jre/lib/rt.jar:classes/:lib/dgsh.jar -Dpython.path=lib/jyLib.jar burningbox.org.dsh.export.StatBlockParser $*
