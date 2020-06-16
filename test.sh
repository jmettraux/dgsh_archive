#!/bin/sh

#CLASS=burningbox.org.dsh.Shell
#CLASS=burningbox.org.dsh.entities.Feat
#CLASS=burningbox.org.dsh.entities.CharacterClass
#CLASS=burningbox.org.dsh.entities.Monster
#CLASS=burningbox.org.dsh.entities.MonsterTemplate
#CLASS=burningbox.org.dsh.entities.equipment.Armor
#CLASS=burningbox.org.dsh.entities.equipment.Weapon
#CLASS=burningbox.org.dsh.Configuration
#CLASS=burningbox.org.dsh.Pager
#CLASS=burningbox.org.dsh.export.StatBlockParser
#CLASS=burningbox.org.dsh.entities.ClassLevel
CLASS=burningbox.org.dsh.entities.Utils

java -classpath lib/log4j-1.2.5.jar:/usr/local/java/jre/lib/rt.jar:classes/ $CLASS $*
#serialver -classpath lib/log4j-1.2.5.jar:/usr/local/java/jre/lib/rt.jar:classes/ $CLASS
