
#
# utils.py
#
# Some libraries for spell scripts
#
# $Id: utils.py,v 1.2 2002/08/09 06:08:08 jmettraux Exp $
#

import java.util as util


def askForTargetName () :

    lang.System.out.print("    targetName> ")
    targetName = dsh.Term.readLine()

    targetName = targetName.strip().lower()

    if len(targetName) < 1 :
	return targetName

    target = dsh.GameSession.getInstance().findBeing(targetName)

    if target == None :
	print "  There is no character or monster named '%s'" % targetName
	return None

    return targetName


def askForTargetNames () :

    targetNames = util.ArrayList(10)

    while (1) :

	targetName = askForTargetName()

	if targetName == None :
	    continue

	if len(targetName) < 1 :
	    break

	targetNames.add(targetName)

    return targetNames

