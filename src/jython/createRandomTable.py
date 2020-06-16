# 
# Adds the character defined here to a party XML file
# 

import java.util as util

import burningbox.org.dsh as dsh
import burningbox.org.dsh.random as random
import burningbox.org.dsh.entities as entities

#
# build the table

table = random.TableSet()
table.name = "SRD_treasure_level_5"

t1 = random.Table()
t1.name = "coins"
t1.addPossibility(random.Possibility(9, random.StaticItem.NOTHING))
t1.addPossibility(random.Possibility(8, random.StaticItem("1d4x10000cp")))
t1.addPossibility(random.Possibility(18, random.StaticItem("1d6x1000sp")))
t1.addPossibility(random.Possibility(56, random.StaticItem("1d8x100gp")))
t1.addPossibility(random.Possibility(4, random.StaticItem("1d10x10pp")))

t2 = random.Table()
t2.name = "goods"
t2.addPossibility(random.Possibility(59, random.StaticItem.NOTHING))
t2.addPossibility(random.Possibility(34, random.TableItem("1d4", "SRD_gems")))
t2.addPossibility(random.Possibility(4, random.TableItem("1d4", "SRD_art")))

t3 = random.Table()
t3.name = "items"
t3.addPossibility(random.Possibility(56, random.StaticItem.NOTHING))
t3.addPossibility(random.Possibility(9, random.TableItem("1d4", "SRD_mundane")))
t3.addPossibility(random.Possibility(32, random.TableItem("1d3", "SRD_minor")))

table.addTable(t1)
table.addTable(t2)
table.addTable(t3)

#
# add and save

fileName = "%s.xml" % table.name.replace(' ', '_')

bs = entities.BeanSet()

bs.add(table)
bs.save(fileName)

print "Your table '%s' has been built and saved to %s" % (table.name, fileName)

