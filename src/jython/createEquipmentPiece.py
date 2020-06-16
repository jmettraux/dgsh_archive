# 
# Adds the character defined here to a party XML file
# 

import java.util as util

import burningbox.org.dsh as dsh
import burningbox.org.dsh.entities as entities
import burningbox.org.dsh.entities.equipment as equipment

#
# build the equipment piece

piece = equipment.EquipmentPiece()
piece.name = "bag"
piece.description = "bag"
piece.weight = 0.125
piece.value = "1sp"
piece.categoryDefinition = "misc"
#modifier = entities.MiscModifier()
#modifier.definition = "search"
#modifier.modifier = -1
#modifier.description = "cursed stuff, unknown to Troudukh"
#piece.modifiers.add(modifier)

#
# add and save

fileName = "%s.xml" % piece.name

bs = entities.BeanSet()

bs.add(piece)
bs.save(fileName)

print "Your piece of equipment '%s' has been added and saved to %s" % (piece.name, fileName)

