#
# MageArmor.py
#
# jmettraux@burningbox.org
#
# $Id: MageArmor.py,v 1.1 2002/11/11 12:26:10 jmettraux Exp $
#

#
# This trigger is linked to the 'mage armor' spell
#

#import java.util as util
#import burningbox.org.dsh as dsh
#import burningbox.org.dsh.commands as commands
#import burningbox.org.dsh.entities as entities
import burningbox.org.dsh.entities.magic as magic

class MageArmorTrigger (commands.Trigger):

    def trigger (self):

	spellCaster = self.context.get(magic.Spell.SPELLCASTER);
	print "\n%s casts Mage Armor" % spellCaster.name

	spellCasterClass = self.context.get(magic.Spell.SPELLCASTER_CLASS);
	spellCasterLevel = self.context.get(magic.Spell.SPELLCASTER_LEVEL);
	duration = spellCasterLevel * 600

	print "effect will last %i rounds" % duration

	if not dsh.CombatSession.initiativeGotRolled() :
	    print "No combat is going on or initiative hasn't been rolled."
	    print "No effect will be added."
	    return

	targetName = askForTargetName()

	#for key in self.context.keySet():
	#    print " %s = %s" % (key, self.context.get(key))

	effect = entities.Effect("%s's Mage Armor (%s%i)" % (spellCaster.name, spellCasterClass.name, spellCasterLevel), "+4 to AC", spellCaster.currentInitiative + 1, spellCaster.computeAbilityScore(dsh.Definitions.DEXTERITY), duration, targetName)

	effect.addModifier(entities.MiscModifier(dsh.Definitions.ARMOR_CLASS, 4))

	entities.Effect.addEffect(effect)

	print "effect '%s' added." % effect.name

