
#
# print.py
#
# A script for outputting a character or a monster in dgsh's table format
#
# $Id: print.py,v 1.2 2002/08/26 16:25:00 jmettraux Exp $
#

import sys

import burningbox.org.dsh as dsh
import burningbox.org.dsh.views as views
import burningbox.org.dsh.entities as entities

configuration = dsh.Configuration.load("etc/dgsh-config.xml")
configuration.apply(0)

if len(sys.argv) < 2 :
    print "\n    ** print **\n\nUsage :\n"
    print "print {creatureName} [fileName]\n"
    print "    Prints a character or a monster to the standard output or"
    print "    to the given fileName.\n"
    sys.exit(0)

creatureName = sys.argv[1]

fileName = None
if len(sys.argv) > 2 :
    fileName = sys.argv[2]

creature = dsh.GameSession.getInstance().findBeing(creatureName)

if creature == None :
    creature = entities.DataSets.findMonsterTemplate(creatureName)

if creature == None :
    print "No character named '%s' could be found." % creatureName
    sys.exit(1)

detailView = views.DetailView(creature)

if fileName != None :
    detailView.setFileOutput(1)
    f = open(fileName, "w")
    f.write(str(detailView.process()))
    f.write("\n")
    f.close()
else :
    print detailView.process()
