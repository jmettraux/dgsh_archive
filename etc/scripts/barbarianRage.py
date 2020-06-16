#
# barbarianRage.py
#
# jmettraux@burningbox.org
#

#
# As referenced in etc/classes.xml
#

import java.util as util
import burningbox.org.dsh as dsh
import burningbox.org.dsh.commands as commands
import burningbox.org.dsh.entities as entities

#
# This command is used by the DM to make a barbarian 'rage'
# It can only get used in combat mode.
# It should save any DM a lot of paperwork.
#
class RageCommand (commands.AbstractCommand, commands.CombatCommand):

    def execute (self):

	if (self.noMoreTokens()): return 0

	barbarianName = self.tokenizer.nextToken()

	barbarian = dsh.GameSession.getInstance().findBeing(barbarianName)

	try:
	    # this 'tried' piece of code will raise exceptions if
	    # 1) barbarian is not an instance of 
	    #    burningbox.org.dsh.entities.Character.java and therefore
	    #    has no computeLevelForClass() method
	    # 2) barbarian is null (in phyton language is None)
	    # 3) it's not a barbarian (bbnLevel < 1)

	    bbnLevel = barbarian.computeLevelForClass("bbn")

	    if (bbnLevel < 1):
		raise StandardError
	except:
	    dsh.Term.echo("No barbarian named '"+barbarianName+"' was found.\n")
	    return 0

	#
	# tell how many times the barbarian rage can occur

	if (bbnLevel < 4): time = 1
	elif (bbnLevel < 8): time = 2
	elif (bbnLevel < 12): time = 3
	elif (bbnLevel < 16): time = 4
	elif (bbnLevel < 20): time = 5
	else: time = 6

	print ("%s can rage %i time(s) per day") % (barbarianName, time)
	# you can use print if you want to display just one line of text like
	# here. If you've got to print numerous lines and you want dgsh
	# to ask for '--more--', use dsh.Term.echo()

	#
	# prepare effect

	roundsToGo = 3 + entities.Abilities.computeModifier(barbarian.abilities.getScore(dsh.Definitions.CONSTITUTION) + 4)

	effect = entities.Effect("rage", "barbarian going ballistic.", barbarian.currentInitiative, barbarian.computeAbilityScore(dsh.Definitions.DEXTERITY), roundsToGo, barbarianName)

	if (bbnLevel >= 15):
	    print("Greater rage !")
	    #
	    # greater rage
	    #
	    effect.addModifier(entities.MiscModifier(dsh.Definitions.STRENGTH, 6))
	    effect.addModifier(entities.MiscModifier(dsh.Definitions.CONSTITUTION, 6))
	    effect.addModifier(entities.MiscModifier(dsh.Definitions.WILL, 3))

	else:
	    #
	    # normal rage
	    #
	    effect.addModifier(entities.MiscModifier(dsh.Definitions.STRENGTH, 4))
	    effect.addModifier(entities.MiscModifier(dsh.Definitions.CONSTITUTION, 4))
	    effect.addModifier(entities.MiscModifier(dsh.Definitions.WILL, 2))

	#
	# AC penalty is -2, even for greater rage
	effect.addModifier(entities.MiscModifier(dsh.Definitions.ARMOR_CLASS, -2))

	#
	# prepare post effect (the barbarian gets tired)

	trigger = BarbarianPostRage()
	context = util.HashMap();
	context.put("barbarian", barbarian)
	trigger.init(context)

	effect.setPostEffectTrigger(trigger)

	#
	# add effect 

	entities.Effect.addEffect(effect);

	#
	# increase hit points

	barbarian.currentHitPoints += (bbnLevel * 2)

	dsh.Term.echo(barbarianName+" is raging ! \n")
	return 1

    def getSyntax (self):
	return "rage {barbarianName}"

    def getHelp (self):
    	return "The designed barbarian starts to rage. At the end of the rage, the barbarian will get depressed for the remainder of the combat."


#
# the trigger at the end of the rage
#
# A trigger is a java class that you can extend to implement instantaneous
# events. (Effects lasts some time, and are only used in combats as currently
# implemented
#
class BarbarianPostRage (commands.Trigger):

    def trigger (self):

    	barbarian = self.context.get("barbarian")

	#
	# removes the additional hit points if any remaining

	if (barbarian.currentHitPoints > barbarian.hitPoints):
	    barbarian.currentHitPoints = barbarian.hitPoints

	#
	# put the barbarian into depression

	effect = entities.Effect("depression", "barbarian feels depressed", barbarian.currentInitiative, barbarian.computeAbilityScore(dsh.Definitions.DEXTERITY), 1000, barbarian.name)
	effect.addModifier(entities.MiscModifier(dsh.Definitions.STRENGTH, -2))
	effect.addModifier(entities.MiscModifier(dsh.Definitions.DEXTERITY, -2))

	#
	# add effect into combat session

	entities.Effect.addEffect(effect)
