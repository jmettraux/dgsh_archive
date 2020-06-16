#!/bin/sh

#
# Testing raptor with all possible stat block files
#
# $Id: test-raptor.sh,v 1.3 2002/09/04 19:15:56 jmettraux Exp $
#

find import_samples/ -name 'standard*.sbo' -exec ./raptor.sh {} \;

