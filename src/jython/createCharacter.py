# 
# Adds the character (spellCaster) defined here to a party XML file
# 

import java.util as util

import burningbox.org.dsh as dsh
import burningbox.org.dsh.entities as entities
import burningbox.org.dsh.entities.equipment as equipment

configuration = dsh.Configuration.load("etc/dgsh-config.xml")
configuration.apply(0)

#
# build the character

c = entities.Character()
c.name="lethale"
c.player="Anne"
c.abilities = entities.Abilities(11, 16, 10, 14, 12, 16)
    # STR, DEX, CON, INT, WIS, CHA
c.raceName="elf"
c.alignment=dsh.Definitions.CHAOTIC_EVIL
c.hitDice=1
c.hitPoints=7
c.currentHitPoints=7
c.experiencePoints=0
c.miscModifiers = None
c.speeds = "10m, 6sq"
c.size = entities.Size.MEDIUM

#
# character's classes

c.classes.add(entities.ClassLevel("sor", 1))
#c.classes.add(entities.ClassLevel("clr", 1))
#c.classes.add(entities.ClassLevel("sor", 1))

#
# character's skills

    # you will find skills and feats in etc/skills.xml and 
    # etc/feats.xml respectively

    # the int numbers here are ranks

c.skills.add(entities.CharacterSkill("alchemy", 3))
c.skills.add(entities.CharacterSkill("concentration", 3))
c.skills.add(entities.CharacterSkill("craft (bowsmith)", 2))
c.skills.add(entities.CharacterSkill("intimidate", 1))
c.skills.add(entities.CharacterSkill("knowledge (arcana)", 1))
c.skills.add(entities.CharacterSkill("profession (herbalist)", 1))
c.skills.add(entities.CharacterSkill("scry", 2))
c.skills.add(entities.CharacterSkill("spellcraft", 2))
#c.skills.add(entities.CharacterSkill("craft (weaponsmith)", 4))
#c.skills.add(entities.CharacterSkill("craft (armorsmith)", 4))
#c.skills.add(entities.CharacterSkill("concentration", 2))
#c.skills.add(entities.CharacterSkill("diplomacy", 2))
#c.skills.add(entities.CharacterSkill("handle animal", 4))
#c.skills.add(entities.CharacterSkill("heal", 4))
#c.skills.add(entities.CharacterSkill("jump", 2))
#c.skills.add(entities.CharacterSkill("knowledge (arcana)", 2))
#c.skills.add(entities.CharacterSkill("knowledge (religion)", 3))
#c.skills.add(entities.CharacterSkill("profession (farmer)", 5))
#c.skills.add(entities.CharacterSkill("ride", 2))
#c.skills.add(entities.CharacterSkill("spellcraft", 2))
#c.skills.add(entities.CharacterSkill("swim", 2))
#c.skills.add(entities.CharacterSkill("use rope", 2))

#
# character's feats

c.feats.add(entities.FeatInstance("simple weapon proficiency", None, 0))
#c.feats.add(entities.FeatInstance("martial weapon proficiency", None, 0))
#c.feats.add(entities.FeatInstance("armor proficiency (light)", None, 0))
#c.feats.add(entities.FeatInstance("armor proficiency (medium)", None, 0))
#c.feats.add(entities.FeatInstance("armor proficiency (heavy)", None, 0))
#c.feats.add(entities.FeatInstance("power attack", None, 0))
c.feats.add(entities.FeatInstance("toughness", None, 0))

#c.feats.add(entities.FeatInstance("combat reflexes", None, 0))
#c.feats.add(entities.FeatInstance("weapon focus", "lsword", 1))

#
# add equipment

#c.equipment.wornEquipment.add(entities.DataSets.findEquipment("scmarm"))
#c.equipment.wornEquipment.add(entities.DataSets.findEquipment("sstshield"))

c.equipment.heldEquipment.add(entities.DataSets.findEquipment("lbow"))
c.equipment.heldEquipment.add(entities.DataSets.findEquipment("dgr"))

#
# wizard spellbook
#
# (or sorcerer's known spells)

spellbook = entities.magic.SpellList()
spellbook.addSpell("sor", "detect magic");
spellbook.addSpell("sor", "read magic");
spellbook.addSpell("sor", "detect poison");
spellbook.addSpell("sor", "ray of frost");

spellbook.addSpell("sor", "charm person");
spellbook.addSpell("sor", "magic missile");
c.spellList = spellbook

#
# clerical domains

#domains = util.ArrayList(2)
#domains.add("Good")
#domains.add("War")
#
#c.setAttribute("domains_clr", domains)

#
# add and save

fileName = "%s.xml" % c.name
fileName = fileName.lower()

bs = entities.BeanSet()

bs.add(c)
bs.save(fileName)

print "Your character %s has been created and saved to %s" % (c.name, fileName)

