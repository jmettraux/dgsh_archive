#
# trollRegeneration.py
#
# jmettraux@burningbox.org
#

#
# as the name implies
#

#import java.util as util
#import burningbox.org.dsh as dsh
#import burningbox.org.dsh.commands as commands
#import burningbox.org.dsh.entities as entities
#import burningbox.org.dsh.entities.magic as magic

class TrollRegeneration (commands.Trigger):

    def trigger (self):

	troll = self.target

	if troll.currentHitPoints < 1 : return
	if troll.currentHitPoints == troll.hitPoints : return

	troll.currentHitPoints += 5;

	if troll.currentHitPoints > troll.hitPoints :
	    troll.currentHitPoints = troll.hitPoints

	print "\n   *** Troll '%s' regenerates to %i hp *** \n" % (troll.name, troll.currentHitPoints)
