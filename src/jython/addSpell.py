#
# creates a spell and saves it in the file named 'mySpell.xml' (overrides).
#

import java.util as util

import burningbox.org.dsh.entities as entities
import burningbox.org.dsh.entities.magic as magic

#
# main characteristics

spell = magic.Spell()
spell.name = "Zone of Silence"
spell.description = "Creates a sphere from which no sound is heard"
spell.school = "Illusion"
spell.subSchool = "Glamer"
spell.type = None

#
# who can cast this

classLevel = entities.ClassLevel()
classLevel.levelReached = 4
classLevel.className = "brd"
spell.classes.add(classLevel)

#
# spell components

spell.components.add("V")
spell.components.add("S")
spell.components.add("F")

#
# the rest of the characteristics

spell.castingTime = "1 rnd"
spell.range = "5ft radius"
spell.effect = None
spell.duration = "1rnd/lvl"
spell.savingThrow = "Wil negates (harmless)"
spell.spellResistance = "Yes"
spell.longDescription = """By casting Zone of Silence, you can manipulate sound waves in your immediate vicinity so that you and those within the spell's area can converse normally, yet no one outside can hear your voices or any other noises from within. This effect is centered on you and moves with you. Anyone who enters the zone immediately becomes subject to its effects, but those who leave are no longer affected. 
Note, however, that a successful Read Lips attempt can still reveal what's said inside a Zone of Silence"""
spell.focus = "The caster's instrument"
spell.materialComponents = None

#
# save the spell

bs = entities.BeanSet()
bs.add(spell)
bs.save("mySpell.xml")

print ("Spell '%s' got created and saved to mySpell.xml" % spell.name)
