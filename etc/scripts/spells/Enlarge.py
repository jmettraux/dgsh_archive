#
# Enlarge.py
#
# jmettraux@burningbox.org
#
# $Id: Enlarge.py,v 1.1 2002/08/05 07:07:35 jmettraux Exp $
#

#
# This trigger is linked to the 'Enlarge' spell
#

#import java.util as util
#import burningbox.org.dsh as dsh
#import burningbox.org.dsh.commands as commands
#import burningbox.org.dsh.entities as entities
import burningbox.org.dsh.entities.magic as magic

class EnlargeTrigger (commands.Trigger):

    def trigger (self):

	spellCaster = self.context.get(magic.Spell.SPELLCASTER);
	print "\n%s casts Enlarge" % spellCaster.name

	spellCasterClass = self.context.get(magic.Spell.SPELLCASTER_CLASS);
	spellCasterLevel = self.context.get(magic.Spell.SPELLCASTER_LEVEL);
	duration = spellCasterLevel * 10

	print "effect will last %i rounds" % duration

	growthPercentage = spellCasterLevel * 10

	print "target grows by %i percent" % growthPercentage

	if not dsh.CombatSession.initiativeGotRolled() :
	    print "No combat is going on or initiative hasn't been rolled."
	    print "No effect will be added."
	    return

	targetNames = askForTargetNames()

	#for key in self.context.keySet():
	#    print " %s = %s" % (key, self.context.get(key))

	effect = entities.Effect("%s's Enlarge (%s%i)" % (spellCaster.name, spellCasterClass.name, spellCasterLevel), "Target grows by %i percent" % growthPercentage, spellCaster.currentInitiative, spellCaster.computeAbilityScore(dsh.Definitions.DEXTERITY), duration, targetNames)

	bonusToStrength = growthPercentage / 20

	effect.addModifier(entities.MiscModifier(dsh.Definitions.STRENGTH, bonusToStrength))

	entities.Effect.addEffect(effect)

	print "effect '%s' added." % effect.name

