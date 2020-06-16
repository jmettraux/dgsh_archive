/*
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 * 
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 * 
 * 3. The name "dgsh" or "dsh" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of BurningBox.  For written permission,
 *    please contact john.mettraux@burningbox.org.
 * 
 * 4. Products derived from this Software may not be called "dgsh" or "dsh"
 *    nor may "dgsh" or "dsh" appear in their names without prior written
 *    permission of BurningBox.
 * 
 * 5. Due credit should be given to BurningBox
 *    (http://www.burningbox.com/).
 * 
 * THIS SOFTWARE IS PROVIDED BY BURNINGBOX AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * BURNINGBOX OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Copyright 2002 (C) BurningBox. All Rights Reserved.
 * 
 * $Id: DataSets.java,v 1.22 2002/08/29 11:04:10 jmettraux Exp $
 */

//
// DataSets.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You can do very well in speculation where land or anything to do with dirt
// is concerned.
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.Configuration;
import burningbox.org.dsh.random.RandomTable;
import burningbox.org.dsh.entities.magic.Spell;
//import burningbox.org.dsh.entities.magic.Spells;
import burningbox.org.dsh.entities.magic.SpellCachedSet;
import burningbox.org.dsh.entities.equipment.EquipmentPiece;


/**
 * This is a big singleton containing the game's databases for classes, races,
 * equipments...
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/29 11:04:10 $
 * <br>$Id: DataSets.java,v 1.22 2002/08/29 11:04:10 jmettraux Exp $</font>
 *
 * @author jmettraux@burningbox.org
 */
public class DataSets
{

    //static org.apache.log4j.Logger log =
    //	org.apache.log4j.Logger.getLogger("dgsh.DataSets");

    //
    // THE SINGLETON

    private static DataSets dataSets = new DataSets();

    //
    // some CONSTANTS
    
    private static int CACHED_RACES			= 14;
    private static int CACHED_CLASSES			= 14;
    private static int CACHED_MONSTER_TEMPLATES		=  7;
    private static int CACHED_SPELLS			= 21;
    private static int CACHED_SKILLS			= 49;
    private static int CACHED_FEATS			= 49;
    private static int CACHED_EQUIPMENT			= 21;
    private static int CACHED_TABLES			= 21;

    //
    // FIELDS

    private CachedSet skills = null;
    private BeanSet naturalSkills = new BeanSet();
    private CachedSet feats = null;
    private CachedSet equipments = null;

    private CachedSet randomTables = null;
    private CachedSet monsterTemplates = null;
    private SpellCachedSet spells = null;
    private CachedSet races = null;
    private CachedSet characterClasses = null;

    //
    // CONSTRUCTORS

    public DataSets ()
    {
    }

    //
    // METHODS

    public static CachedSet getEquipments ()
    {
	return dataSets.equipments;
    }

    public static CachedSet getMonsterTemplates ()
    {
	return dataSets.monsterTemplates;
    }

    public static SpellCachedSet getSpells ()
    {
	return dataSets.spells;
    }

    public static CachedSet getFeats ()
    {
	return dataSets.feats;
    }

    public static BeanSet getNaturalSkills ()
    {
	return dataSets.naturalSkills;
    }

    //
    // FINDERS
    
    public static CharacterClass findCharacterClass (String className)
    {
	return 
	    (CharacterClass)DataSets.dataSets.characterClasses.get(className);
    }

    public static boolean isACharacterClass (String potentialClassName)
    {
	return DataSets.dataSets.characterClasses.contains(potentialClassName);
    }

    public static CharacterClass findCharacterClassWithFullName 
	(String fullName)
    {
	java.util.Iterator it = DataSets.dataSets.characterClasses.iterator();
	while (it.hasNext())
	{
	    CharacterClass cc = (CharacterClass)it.next();

	    if (cc.getFullName().equals(fullName)) return cc;
	}
	return null;
    }

    public static Skill findSkill (String skillName)
    {
	if (skillName.toLowerCase().startsWith("knowledge"))
	{
	    return (Skill)DataSets.dataSets.skills.get("knowledge");
	}

	if (skillName.toLowerCase().startsWith("profession"))
	{
	    return (Skill)DataSets.dataSets.skills.get("profession");
	}

	if (skillName.toLowerCase().startsWith("craft") ||
	    (skillName.toLowerCase().endsWith("craft") &&
	     ! skillName.equals("spellcraft")))
	{
	    return (Skill)DataSets.dataSets.skills.get("profession");
	}

	return
	    (Skill)DataSets.dataSets.skills.get(skillName);
    }

    public static Feat findFeat (String featName)
    {
	return
	    (Feat)DataSets.dataSets.feats.get(featName);
    }

    public static Race findRace (String raceName)
    {
	return
	    (Race)DataSets.dataSets.races.get(raceName);
    }

    public static Spell findSpell (String spellName)
    {
	return (Spell)DataSets.dataSets.spells.get(spellName);
    }

    public static MonsterTemplate findMonsterTemplate (String templateName)
    {
	return (MonsterTemplate)DataSets.dataSets
	    .monsterTemplates.get(templateName);
    }

    public static int computeRacialModifier 
	(Race race,
	 String definition)
    {
	if (race == null) return 0;

	return MiscModifier
	    .sumModifiers(definition, race.getMiscModifiers());
    }

    public static EquipmentPiece findEquipment (String equipmentName)
    {
	EquipmentPiece original =
	    (EquipmentPiece)(((EquipmentPiece)dataSets.equipments
		  .get(equipmentName)));

	if (original == null) return null;

	return (EquipmentPiece)original.clone();
    }

    public static EquipmentPiece findEquipmentWithFullName 
	(String equipmentFullName)
    {
	equipmentFullName = equipmentFullName.toLowerCase();

	EquipmentPiece original = null;

	java.util.Iterator it = dataSets.equipments.iterator();
	while (it.hasNext())
	{
	    EquipmentPiece ep = (EquipmentPiece)it.next();
	    if (ep.getDescription().toLowerCase().equals (equipmentFullName))
	    {
		original = ep;
		break;
	    }
	}

	if (original == null) return null;

	return (EquipmentPiece)original.clone();
    }

    public static RandomTable findTable (String tableName)
    {
	return (RandomTable)DataSets.dataSets.randomTables.get(tableName);
    }

    /*
    public static void addTable (RandomTable t)
    {
	DataSets.dataSets.randomTables.add(t);
    }
    */

    //
    // STATIC METHODS

    /*
    public static void add (String dataFileName)
	throws java.io.IOException
    {
	java.io.BufferedInputStream in =
	    new java.io.BufferedInputStream
	        (new java.io.FileInputStream(dataFileName));
	java.beans.XMLDecoder decoder = 
	    new java.beans.XMLDecoder(in);

	//System.out.println("..... ");

	while(true)
	{
	    Named n = null;

	    try
	    {
		n = (Named)decoder.readObject();
	    }
	    catch (ArrayIndexOutOfBoundsException e)
	    {
		break;
	    }

	    //System.out.print("'"+n.getName()+"' ");

	    try
	    {
		if (n instanceof EquipmentPiece)
		{
		    DataSets.dataSets.equipments.add(n);
		}
		else if (n instanceof Skill)
		{
		    DataSets.dataSets.skills.add(n);

		    if (((Skill)n).isNatural())
			DataSets.dataSets.naturalSkills.add(n);
		}
		else if (n instanceof Feat)
		{
		    DataSets.dataSets.feats.add(n);
		}
		else if (n instanceof ScriptedCommand)
		{
		    // do nothing, the class already loaded itself into
		    // the python interpreter...
		}
		/*
		else if (n instanceof MonsterTemplate)
		{
		    DataSets.dataSets.monsterTemplates.add(n);
		}
		else if (n instanceof CharacterClass)
		{
		    DataSets.dataSets.characterClasses.add(n);
		}
		else if (n instanceof Race)
		{
		    DataSets.dataSets.races.add(n);
		}
		else if (n instanceof Spell)
		{
		    DataSets.dataSets.spells.addSpell((Spell)n);
		}
		else if (n instanceof RandomTable)
		{
		    DataSets.dataSets.randomTables.add(n);
		}
		*/
		/*
		else
		{
		    //cat.info 
		    Term.echo
			("Failed to add to DataSets object of class "+
			 n.getClass().getName());
		    Term.echo("\n");
		}
	    }
	    catch (ClassCastException cce)
	    {
		//cat.info 
		Term.echo
		    ("Object can't get added to DataSets "+
		     n.getClass().getName());
		Term.echo("\n");
	    }
	}
	//System.out.println();

	decoder.close();
	in.close();
    }
    */

    public static void init (Configuration conf, boolean verbose)
	throws java.io.IOException
    {
	dataSets = new DataSets();

	/*
	java.util.Iterator it = conf.getDataFileNames().iterator();
	while(it.hasNext())
	{
	    String fileName = (String)it.next();
	    if (verbose)
		System.out.println("...loading "+fileName);
	    add(fileName);
	}
	*/

	if (verbose)
	{
	    System.out.println
		("...setting races directory to "+
		 conf.getRaceDirectory());
	}
	dataSets.races = 
	    new CachedSet(conf.getRaceDirectory(), CACHED_RACES);

	if (verbose)
	{
	    System.out.println
		("...setting classes directory to "+
		 conf.getClassDirectory());
	}
	dataSets.characterClasses = 
	    new CachedSet(conf.getClassDirectory(), CACHED_CLASSES);

	if (verbose)
	{
	    System.out.println
		("...setting monster directory to "+conf.getMonsterDirectory());
	}
	dataSets.monsterTemplates = 
	    new CachedSet(conf.getMonsterDirectory(), CACHED_MONSTER_TEMPLATES);

	if (verbose)
	{
	    System.out.println
		("...setting spells directory to "+conf.getSpellDirectory());
	}
	dataSets.spells = SpellCachedSet
	    .buildSpellCachedSet(conf.getSpellDirectory(), CACHED_SPELLS);

	if (verbose)
	{
	    System.out.println
		("...setting skills directory to "+conf.getSkillDirectory());
	}
	dataSets.skills = 
	    new CachedSet(conf.getSkillDirectory(), CACHED_SKILLS);

	if (verbose)
	{
	    System.out.println
		("...setting feats directory to "+conf.getFeatDirectory());
	}
	dataSets.feats = 
	    new CachedSet(conf.getFeatDirectory(), CACHED_FEATS);

	if (verbose)
	{
	    System.out.println
		("...setting equipment directory to "+
		 conf.getEquipmentDirectory());
	}
	dataSets.equipments = 
	    new CachedSet(conf.getEquipmentDirectory(), CACHED_EQUIPMENT);

	if (verbose)
	{
	    System.out.println
		("...setting random tables directory to "+
		 conf.getRandomTableDirectory());
	}
	dataSets.randomTables = 
	    new CachedSet(conf.getRandomTableDirectory(), CACHED_TABLES);
    }

}
