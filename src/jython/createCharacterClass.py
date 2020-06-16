# 
# Creates a character class
# 

import java.util as util

import burningbox.org.dsh as dsh
import burningbox.org.dsh.entities as entities

#
# build the character class

cc = entities.CharacterClass()

cc.name = "adp"
cc.fullName = "adept"

cc.baseAttackModifiers = [
[ 0	],
[ 1	],
[ 1	],
[ 2	],
[ 2	],
[ 3	],
[ 3	],
[ 4	],
[ 4	],
[ 5	],
[ 5	],
[ 6, 1	],
[ 6, 1	],
[ 7, 2	],
[ 7, 2	],
[ 8, 3	],
[ 8, 3	],
[ 9, 4	],
[ 9, 4	],
[ 10, 5 ]
]

cc.savingThrowPerLevels = [
    entities.SavingThrowSet(0, 0, 2),
    entities.SavingThrowSet(0, 0, 3),
    entities.SavingThrowSet(1, 1, 3),
    entities.SavingThrowSet(1, 1, 4),
    entities.SavingThrowSet(1, 1, 4),
    entities.SavingThrowSet(2, 2, 5),
    entities.SavingThrowSet(2, 2, 5),
    entities.SavingThrowSet(2, 2, 6),
    entities.SavingThrowSet(3, 3, 6),
    entities.SavingThrowSet(3, 3, 7),
    entities.SavingThrowSet(3, 3, 7),
    entities.SavingThrowSet(4, 4, 8),
    entities.SavingThrowSet(4, 4, 8),
    entities.SavingThrowSet(4, 4, 9),
    entities.SavingThrowSet(5, 5, 9),
    entities.SavingThrowSet(5, 5, 10),
    entities.SavingThrowSet(5, 5, 10),
    entities.SavingThrowSet(6, 6, 11),
    entities.SavingThrowSet(6, 6, 11),
    entities.SavingThrowSet(6, 6, 12)
]

cc.spellsPerDay = [
[ 3, 1, -100, -100, -100, -100 ],
[ 3, 1, -100, -100, -100, -100 ],
[ 3, 2, -100, -100, -100, -100 ],
[ 3, 2, 0, -100, -100, -100 ],
[ 3, 2, 1, -100, -100, -100 ],
[ 3, 2, 1, -100, -100, -100 ],
[ 3, 3, 2, -100, -100, -100 ],
[ 3, 3, 2, 0, -100, -100 ],
[ 3, 3, 2, 1, -100, -100 ],
[ 3, 3, 2, 1, -100, -100 ],
[ 3, 3, 3, 2, -100, -100 ],
[ 3, 3, 3, 2, 0, -100 ],
[ 3, 3, 3, 2, 1, -100 ],
[ 3, 3, 3, 2, 1, -100 ],
[ 3, 3, 3, 3, 2, -100 ],
[ 3, 3, 3, 3, 2, 0 ],
[ 3, 3, 3, 3, 2, 1 ],
[ 3, 3, 3, 3, 2, 1 ],
[ 3, 3, 3, 3, 3, 2 ],
[ 3, 3, 3, 3, 3, 2 ]
]

#
# add and save

fileName = "%s.xml" % cc.name

bs = entities.BeanSet()

bs.add(cc)
bs.save(fileName)

print "Your class %s has been added and saved to %s" % (cc.name, fileName)

