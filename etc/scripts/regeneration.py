#
# regeneration.py
#
# jmettraux@burningbox.org
#

# 
# The code for all regenerating creatures
#

#import burningbox.org.dsh as dsh
import burningbox.org.dsh.commands as commands
#import burningbox.org.dsh.entities as entities
#import burningbox.org.dsh.entities.magic as magic
import re
import java.util as util
import java.lang as lang
import org.apache.log4j as log4j

class Regeneration (commands.Trigger):

    #
    # this method parses the Specials of the creature to find
    # something like 'regeneration 5/silver' and then
    # extracts the number and returns it
    #
    def computeRegenerationRate (self):

	creature = self.target
	
	for specialName in creature.specials.keySet():

	    if re.match("[Rr]egeneration", specialName):
		rates = re.findall("\d+", specialName)
		return int(rates[0])

	return 3


    def trigger (self):

	log = log4j.Logger.getLogger("dgsh.py.Regeneration")

	creature = self.target

	if creature.currentHitPoints < 1 : return
	if creature.currentHitPoints == creature.hitPoints : return

	creature.currentHitPoints += self.computeRegenerationRate()

	if creature.currentHitPoints > creature.hitPoints :
	    creature.currentHitPoints = creature.hitPoints
	
	#
	# check ceiling

	ceiling = creature.getAttribute(dsh.Definitions.HP_CEILING);

	if ceiling != None and creature.currentHitPoints > int(ceiling) :
	    creature.currentHitPoints = int(ceiling)

	print "\n   *** Creature '%s' regenerates to %i hp *** \n" % (creature.name, creature.currentHitPoints)

	log.info ("Creature '%s' regenerates to %i hp" % (creature.name, creature.currentHitPoints))
