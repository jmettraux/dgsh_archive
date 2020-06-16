#
# Bless.py
#
# jmettraux@burningbox.org
#
# $Id: Bless.py,v 1.4 2002/11/25 07:35:59 jmettraux Exp $
#

#
# This trigger is linked to the 'bless' spell
#

import java.util as util
import burningbox.org.dsh as dsh
import burningbox.org.dsh.commands as commands
import burningbox.org.dsh.entities as entities
import burningbox.org.dsh.entities.magic as magic

class BlessTrigger (commands.Trigger):

    def trigger (self):

	spellCaster = self.context.get(magic.Spell.SPELLCASTER);
	print "\n%s casts Bless" % spellCaster.name

	spellCasterClass = self.context.get(magic.Spell.SPELLCASTER_CLASS);
	spellCasterLevel = self.context.get(magic.Spell.SPELLCASTER_LEVEL);
	duration = spellCasterLevel * 10

	print "effect will last %i rounds" % duration

	if not dsh.CombatSession.initiativeGotRolled() :
	    print "No combat is going on or initiative hasn't been rolled."
	    print "No effect will be added."
	    return

	targetNames = askForTargetNames()

	#for key in self.context.keySet():
	#    print " %s = %s" % (key, self.context.get(key))

	effect = entities.Effect("%s's Bless (%s%i)" % (spellCaster.name, spellCasterClass.name, spellCasterLevel), "+1 to attack, +1 to saves against fear", spellCaster.currentInitiative + 1, spellCaster.computeAbilityScore(dsh.Definitions.DEXTERITY), duration, targetNames)

	effect.addModifier(entities.MiscModifier(dsh.Definitions.ATTACK, 1))

	entities.Effect.addEffect(effect)

	print "effect '%s' added." % effect.name

