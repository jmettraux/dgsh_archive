#
# Daze.py
#
# jmettraux@burningbox.org
#
# $Id: Daze.py,v 1.1 2002/08/09 08:20:14 jmettraux Exp $
#

#
# This trigger is linked to the 'Daze' spell
#

#import java.util as util
#import burningbox.org.dsh as dsh
#import burningbox.org.dsh.commands as commands
#import burningbox.org.dsh.entities as entities
import burningbox.org.dsh.entities.magic as magic

class DazeTrigger (commands.Trigger):

    def trigger (self):

	spellCaster = self.context.get(magic.Spell.SPELLCASTER);
	print "\n%s casts Daze" % spellCaster.name

	spellCasterClass = self.context.get(magic.Spell.SPELLCASTER_CLASS);
	spellCasterLevel = self.context.get(magic.Spell.SPELLCASTER_LEVEL);
	duration = 1

	if not dsh.CombatSession.initiativeGotRolled() :
	    print "No combat is going on or initiative hasn't been rolled."
	    print "No effect will be added."
	    return

	while 1 :
	    targetName = askForTargetName()

	    if targetName == None :
		continue

	    if len(targetName) < 1 :
		return

	    break

	#for key in self.context.keySet():
	#    print " %s = %s" % (key, self.context.get(key))

	effect = entities.Effect("%s dazed" % targetName, "%s will lose its next action" % targetName, spellCaster.currentInitiative + 1, spellCaster.computeAbilityScore(dsh.Definitions.DEXTERITY), duration, targetName)

	entities.Effect.addEffect(effect)

	print "effect '%s' added." % effect.name

