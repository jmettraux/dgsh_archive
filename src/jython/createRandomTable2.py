# 
# Adds the character defined here to a party XML file
# 

import java.util as util

import burningbox.org.dsh as dsh
import burningbox.org.dsh.random as random
import burningbox.org.dsh.entities as entities

#
# build the table

table = random.Table()
table.name = "SRD_shields"

table.addPossibility(random.Possibility(9, random.StaticItem("buckler Shield")))
table.addPossibility(random.Possibility( 4, random.StaticItem("small wooden Shield")))
table.addPossibility(random.Possibility( 4, random.StaticItem("small steel Shield")))
table.addPossibility(random.Possibility( 9, random.StaticItem("large wooden Shield")))
table.addPossibility(random.Possibility(64, random.StaticItem("large steel Shield")))
table.addPossibility(random.Possibility( 4, random.StaticItem("tower Shield")))

#
# add and save

fileName = "%s.xml" % table.name.replace(' ', '_')

bs = entities.BeanSet()

bs.add(table)
bs.save(fileName)

print "Your table '%s' has been built and saved to %s" % (table.name, fileName)

