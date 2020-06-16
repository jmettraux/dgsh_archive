#
# spellTriggeredTest.py
#
# jmettraux@burningbox.org
#

#
# this test trigger is currently linked to magic missile
# anyone casting this spell will see the result of this trigger
#

#import java.util as util
#import burningbox.org.dsh as dsh
#import burningbox.org.dsh.commands as commands
#import burningbox.org.dsh.entities as entities
import burningbox.org.dsh.entities.magic as magic

class SpellTriggeredTest (commands.Trigger):

    def trigger (self):

	print ">spellTriggeredTest.py<"
	print "  Hello, I'm a jython implemented trigger"
	print "  and you can find me at "
	print "etc/scripts/spellTriggeredTest.py\n"

	for key in self.context.keySet():
	    print " %s = %s" % (key, self.context.get(key))

	print ""
	spellCaster = self.context.get(magic.Spell.SPELLCASTER);
	print "Hello '%s'" % spellCaster.name

