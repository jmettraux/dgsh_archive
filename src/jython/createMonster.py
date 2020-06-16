# 
# creates a monster in a file named 'monster.xml'
# 

import java.util as util

import burningbox.org.dsh as dsh
import burningbox.org.dsh.entities as entities
import burningbox.org.dsh.entities.equipment as equipment

#
# build the monster template

m = entities.MonsterTemplate();

m.name = "ghoul"
# set the race only if a monster-race exists
#m.raceName = "troll" 
m.category = "medium-size undead"
m.size = entities.Size.MEDIUM
m.hitDice = 1
m.potentialHitPoints = "2d12"
m.initModifier = 2
m.speeds = "30ft (6sq)"
m.armorClass = 14
m.armorClassVsTouchAttack = 12

m.attackRoutine = "Bite +3 melee; 2 claws +0 melee"

m.attacks.add(entities.Attack("bite", [ 3 ], "1d6", dsh.Definitions.MELEE, "paralysis"))
m.attacks.add(entities.Attack("claws", [ 0 ], "1d3", dsh.Definitions.MELEE, "paralysis"))

m.faceReach = "5 by 5 / 5"

m.addSpecial(entities.Special("paralysis", entities.Special.TYPE_EX, "Those hit by a ghouls bite or claw attack must succeed at a Fortitude save (DC 14) or be paralyzed for 1d6+2 minutes. Elves are immune to this paralysis."))
m.addSpecial(entities.Special("create spawn", entities.Special.TYPE_SU, "In most cases, ghouls devour those they kill. From time to time, however, the bodies of their humanoid victims lie where they fell, to rise as ghouls themselves in 1d4 days. Casting protection from evil on a body before the end of that time averts the transformation. (The statistics above are for human ghouls and ghasts. Ghouls and ghasts may vary depending on their original race or kind.)"))
m.addSpecial(entities.Special("undead", "Immune to mind-influencing effects, poison, sleep, paralysis, stunning, and disease. Not subject to critical hits, subdual damage, ability damage, energy drain, or death from massive damage."))
m.addSpecial(entities.Special("+2 turn resistance"))
#m.addSpecial(entities.Special("darkvision 60ft"))
#m.addSpecial(entities.Special("cold and fire resistance 5"))
#m.addSpecial(entities.Special("SR 2"))

m.abilities = entities.Abilities(13, 15, 0, 13, 14, 16)
m.savingThrowSet = entities.SavingThrowSet(0, 2, 5)

m.skills.add(entities.MonsterSkill("climb", 6))
m.skills.add(entities.MonsterSkill("escape artist", 7))
m.skills.add(entities.MonsterSkill("hide", 7))
m.skills.add(entities.MonsterSkill("intuit direction", 3))
m.skills.add(entities.MonsterSkill("jump", 6))
m.skills.add(entities.MonsterSkill("listen", 7))
m.skills.add(entities.MonsterSkill("move silently", 7))
m.skills.add(entities.MonsterSkill("search", 6))
m.skills.add(entities.MonsterSkill("spot", 7))

m.feats.add(entities.FeatInstance("multiattack"))
m.feats.add(entities.FeatInstance("weapon finesse", "bite", 0))


m.alignment = dsh.Definitions.CHAOTIC_EVIL
m.challengeRating = 1
m.treasure = entities.Monster.TREASURE_NONE


#
# save the monster



filename = m.name.replace(' ', '_') + ".xml";

bs = entities.BeanSet()

bs.add(m)
bs.save(filename);

print "Your monster %s has been added and saved to '%s'" % (m.name, filename)

