# 
# Adds the character (spellCaster) defined here to a party XML file
# 

import java.util as util

import burningbox.org.dsh as dsh
import burningbox.org.dsh.entities as entities
import burningbox.org.dsh.entities.equipment as equipment

#
# build the character

c = entities.Character()
c.name="GlockMuch"
c.player="Nemo"
c.abilities = entities.Abilities(12, 13, 15, 17, 18, 8)
    # STR, DEX, CON, INT, WIS, CHA
c.raceName="gnome"
c.alignment=dsh.Definitions.NEUTRAL
c.hitDice=2
c.hitPoints=12
c.currentHitPoints=12
c.experiencePoints = 1320
c.miscModifiers = None
c.speeds = "25ft"
c.size = entities.Size.SMALL

#
# character's classes

c.classes.add(entities.ClassLevel("clr", 1))
c.classes.add(entities.ClassLevel("wiz", 1))
#c.classes.add(entities.ClassLevel("sor", 1))

#
# character's skills

    # you will find skills and feats in etc/skills.xml and 
    # etc/feats.xml respectively

c.skills.add(entities.CharacterSkill("search", 0, 2));
c.skills.add(entities.CharacterSkill("knowledge (arcana)", 0, 4));
c.skills.add(entities.CharacterSkill("knowledge (religion)", 0, 4));

#
# character's feats

#c.feats.add(entities.FeatInstance("alertness", None, 0))
c.feats.add(entities.FeatInstance("simple weapon profiency", None, 0))
c.feats.add(entities.FeatInstance("armor profiency (light)", None, 0))
c.feats.add(entities.FeatInstance("armor profiency (medium)", None, 0))

#
# equipment

# at first load the equipment list

entities.DataSets.add("etc/weapons.xml")
entities.DataSets.add("etc/shields.xml")
entities.DataSets.add("etc/armors.xml")
    # have a look at these files to see what 'larm' or 'mstar' means

# add equipment

c.equipment.wornEquipment.add(entities.DataSets.findEquipment("larm"))
c.equipment.wornEquipment.add(entities.DataSets.findEquipment("lwdshield"))
c.equipment.heldEquipment.add(entities.DataSets.findEquipment("mstar"))

#
# wizard spellbook
#
# (or sorcerer's known spells)

spellbook = entities.magic.SpellList()
spellbook.addSpell("wiz", "Magic Missile");
spellbook.addSpell("wiz", "Detect Magic");
spellbook.addSpell("wiz", "Disrupt Undead");
spellbook.addSpell("wiz", "Alarm");
#spellbook.addSpell("sor", "Magic Missile");
c.spellList = spellbook

#
# add and save

fileName = "%s.xml" % c.name

bs = entities.BeanSet()

bs.add(c)
bs.save(fileName)

print "Your character %s has been added and saved to %s" % (c.name, fileName)

