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
 * $Id: StatBlockParser.java,v 1.19 2002/11/03 17:13:49 jmettraux Exp $
 */

//
// StatBlockParser.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

// 
// AC off-line, battery status high: 70%
//

package burningbox.org.dsh.export;

import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.DshException;
import burningbox.org.dsh.Configuration;
import burningbox.org.dsh.entities.Size;
import burningbox.org.dsh.entities.Being;
import burningbox.org.dsh.entities.Attack;
import burningbox.org.dsh.entities.Special;
import burningbox.org.dsh.entities.BeanSet;
import burningbox.org.dsh.entities.Monster;
import burningbox.org.dsh.entities.DataSets;
import burningbox.org.dsh.entities.Abilities;
import burningbox.org.dsh.entities.Character;
import burningbox.org.dsh.entities.ClassLevel;
import burningbox.org.dsh.entities.MiscModifier;
import burningbox.org.dsh.entities.FeatInstance;
import burningbox.org.dsh.entities.SavingThrowSet;
import burningbox.org.dsh.entities.MonsterTemplate;
import burningbox.org.dsh.entities.magic.SpellList;
import burningbox.org.dsh.entities.magic.PreparedSpells;
import burningbox.org.dsh.entities.equipment.Weapon;
import burningbox.org.dsh.entities.equipment.Equipment;
import burningbox.org.dsh.entities.equipment.MeleeWeapon;
import burningbox.org.dsh.entities.equipment.RangedWeapon;
import burningbox.org.dsh.entities.equipment.EquipmentPiece;


/**
 * This class is used to parse standard (http://www.d20statblock.org) 
 * statistics blocks
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/11/03 17:13:49 $
 * <br>$Id: StatBlockParser.java,v 1.19 2002/11/03 17:13:49 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class StatBlockParser
{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.StatBlockParser");


    private final static int TYPE_MONSTER 		= 0;
    private final static int TYPE_MONSTER_TEMPLATE 	= 1;
    private final static int TYPE_CHARACTER 		= 2;

    //
    // FIELDS

    protected int creatureType = -1; 
	// for the beginning, it's undefined

    protected String name = null;
    protected String gender = null;
    protected String race = null;
    protected String type = null;
    protected Size size = Size.MEDIUM;
    protected float challengeRating = (float)1.0;
    protected java.util.List classes = null;
    protected String potentialHp = null;
    protected int hp = 0;
    protected int init = 0;
    protected String speed = null;
    protected int ac = 10;
    protected int acVsTouch = 10;
    protected int naturalAcMod = 0;
    protected String faceReach = "5 by 5 / 5";
    protected java.util.List attacks = null; 		// for monsters
    protected int[] meleeAttack = null;
    protected int[] rangedAttack = null;
    protected java.util.List specials = null;
    protected int fortitude = 0;
    protected int reflex = 0;
    protected int will = 0;
    protected String alignment = null;
    protected Abilities abilities = null;
    protected java.util.HashMap skills = null;
    protected String languages = null;
    protected java.util.List feats = null;
    protected SpellList spellList = null;
    protected java.util.List preparedSpells = null;
    protected Equipment equipment = new Equipment();

    //
    // CONSTRUCTORS

    public StatBlockParser ()
    {
    }

    //
    // METHODS

    /**
     * Takes a list of stat block lines and turn them into a character or
     * a monster
     */
    public Being parseStatBlocks (java.util.List statBlockLines)
    {
	java.util.Iterator it = statBlockLines.iterator();
	while (it.hasNext())
	{
	    parseLine((String)it.next());
	}

	//System.out.println(this.toString());

	switch (this.creatureType)
	{
	    case TYPE_CHARACTER : return buildCharacter();
	    case TYPE_MONSTER : return buildMonster();
	    case TYPE_MONSTER_TEMPLATE : return buildMonsterTemplate();
	}

	System.out.println
	    ("Failed to determine type of creature. "+
	     "Assuming type is CHARACTER.");
	return buildCharacter();
    }

    // 
    // parse methods

    private void parseLine (String line)
    {
	line = line.toLowerCase();

	if (line.indexOf(" cr ") > -1)
	{
	    parseHeaderLine(line);
	}
	else if (line.indexOf(" spells known") > -1)
	{
	    parseSpellLine(line);
	}
	else if (line.indexOf("spells prepared") > -1)
	{
	    parsePreparedSpellsLine(line);
	}
	else if (line.indexOf("domain spells") > -1)
	{
	    parseDomainSpellsLine(line);
	}
	else if (line.startsWith("skills and feats:"))
	{
	    parseSkillLine(line);
	}
	else if (line.startsWith("languages spoken: "))
	{
	    parseLanguageLine(line);
	}
    }

    // header line

    private void parseHeaderLine (String line)
    {
	String[] items = line.split("; ");

	/*
	if (items[0].indexOf("cr") < 0)
	{
	    parseNameItem(items[0]);
	}
	else
	{
	*/
	String[] itemss = items[0].split(": ");
	parseStandardNameItem(itemss[0]);
	parseCr(itemss[1]);
	/*
	}
	*/

	for (int i=1; i<items.length; i++)
	{
	    String item = items[i];

	    //System.out.println("item is >"+item+"<");

	    if (item.startsWith("hd "))
		parseHdItem(item);
	    else if (item.startsWith("hp "))
		parseHpItem(item);
	    else if (item.startsWith("cr "))
		parseCr(item);
	    else if (item.startsWith("init "))
		parseInitItem(item);
	    else if (item.startsWith("speed ") || item.startsWith("spd "))
		parseSpeedItem(item);
	    else if (item.startsWith("size "))
		parseSizeItem(item);
	    else if (item.startsWith("ac "))
		parseAcItem(item);
	    else if (item.startsWith("melee ") || item.startsWith("ranged "))
		parseAttack(item);
	    else if (item.startsWith("face"))
		parseFaceAndReachItem(item);
	    else if (item.startsWith("sa "))
		parseSaqrItem(item);
	    else if (item.startsWith("sq "))
		parseSaqrItem(item);
	    else if (item.startsWith("sr "))
		parseSaqrItem(item);
	    else if (item.startsWith("al "))
		parseAlItem(item);
	    else if (item.startsWith("sv "))
		parseSaveItem(item);
	    else if (item.startsWith("str "))
		parseAbilitiesItem(item);
	}
    }

    private void parseHdItem (String item)
    {
	this.potentialHp = item.substring(3);
	System.out.println("...HD >"+this.potentialHp+"<");
    }

    private void parseHpItem (String item)
    {
	this.hp = Utils.parseInt(item.substring(3), this.hp);
	System.out.println("...hp "+this.hp);
    }

    private void parseInitItem (String item)
    {
	item = item.substring(5);
	int i = item.indexOf(" ");

	if (i > -1) item = item.substring(0, i);

	if (item.charAt(0) == '+') item = item.substring(1);

	this.init = Utils.parseInt(item, this.init);

	System.out.println
	    ("...Init "+burningbox.org.dsh.views.Utils
		.formatModifier(this.init, 2));
    }

    private void parseSpeedItem (String item)
    {
	this.speed = item.substring(item.indexOf(" ")+1);
	System.out.println("...Spd >"+this.speed+"<");
    }

    private void parseAcItem (String item)
    {
	item = item.substring(3);
	
	int i = item.indexOf(" ");

	if (i < 0)
	{
	    this.ac = Utils.parseInt(item, this.ac);
	}
	else
	{
	    this.ac = Utils.parseInt(item.substring(0, i), this.ac);
	}

	i = item.indexOf("ouch ");
	if (i > -1)
	{
	    i--;

	    this.acVsTouch = 
		Utils.parseFirstInt(item.substring(i+6), this.ac);
	}
	else
	{
	    i = item.indexOf("atural");

	    if (i > -1)
	    {
		i -= 2;

		this.naturalAcMod = Utils.parsePreviousInt(item, i, 0);

		this.acVsTouch = this.ac - this.naturalAcMod;
	    }
	}

	System.out.println
	    ("...AC "+this.ac+" (touch "+this.acVsTouch+", natural "+
	     this.naturalAcMod+")");
    }

    private void parseMonsterAttack (boolean meleeAttack, String item)
    {
	System.out.println("parseMonsterAttack() >"+item+"<");

	if (this.attacks == null) this.attacks = new java.util.ArrayList(3);

	//
	// determine ranged and melee attack modifiers

	int i = item.indexOf(" (");

	int[] attack = 
	    Utils.parsePreviousIntArray(item, i, new int[] { -4 });

	//boolean meleeAttack = item.startsWith("melee");

	//
	// determine weapon used (and add to equipment)

	String weaponFullName = item.substring(0, i);
	weaponFullName = weaponFullName.substring
	    (0, //weaponFullName.indexOf(" ")+1, 
	     weaponFullName.lastIndexOf(" "));

	System.out.println("...weapon >"+weaponFullName+"<");

	int magicModifier = 0;

	if (weaponFullName.charAt(0) == '+' ||
	    weaponFullName.charAt(0) == '-')
	{
	    String sMagicModifier = 
		weaponFullName.substring(0, weaponFullName.indexOf(" "));
	    weaponFullName = 
		weaponFullName.substring(weaponFullName.indexOf(" ")+1);

	    magicModifier = Utils.parseInt(sMagicModifier, 0);
	}

	System.out.println
	    ("...weapon "+burningbox.org.dsh.views.Utils.formatModifier(magicModifier, 2)+" >"+weaponFullName+"<");

	/*
	//
	// substract magic modifier to attack 
	
	if (meleeAttack)
	{
	    this.meleeAttack = burningbox.org.dsh.Utils
		.addToWholeArray(this.meleeAttack, -magicModifier);
	}
	else
	{
	    this.rangedAttack = burningbox.org.dsh.Utils
		.addToWholeArray(this.rangedAttack, -magicModifier);
	}
	*/

	//
	// parse damage
	
	String damageItem = item.substring(i+2, item.length()-1);

	//System.out.println("damage >"+damageItem+"<");

	String[] damageItems = damageItem.split("/crit ");

	String damageDice = damageItems[0];
	String damageCrit = null;
	if (damageItems.length > 1)
	    damageCrit = damageItems[1];

	//System.out.println("damage >"+damageDice+"< /crit >"+damageCrit+"<");

	//
	// generate eq piece and set magic modifier (if any)

	Weapon weapon = (Weapon)DataSets
	    .findEquipmentWithFullName(weaponFullName);

	if (weapon == null)
	{
	    if (meleeAttack)
		weapon = new MeleeWeapon();
	    else
		weapon = new RangedWeapon();

	    weapon.setName(weaponFullName);
	    weapon.setDescription(weaponFullName);
	    weapon.setDamages(Utils.removeModifier(damageDice));
	    if (damageCrit != null) weapon.setCritical(damageCrit);
	    
	    // don't set any proficiencyType.
	}
	else
	{
	    System.out.println("...found weapon : "+weapon.getName());
	}

	if (magicModifier != 0)
	{
	    weapon.getModifiers().add
		(new MiscModifier(Definitions.ATTACK, magicModifier));
	    weapon.getModifiers().add
		(new MiscModifier(Definitions.DAMAGE, magicModifier));
	    weapon.setDescription
		(burningbox.org.dsh.views.Utils.formatModifier(magicModifier, 2)+" "+weapon.getDescription());
	}

	//
	// Add it to equipment
	
	this.equipment.getHeldEquipment().add(weapon);
	    // a PC might want to steal it from the body ;-)

	//
	// create attack and add it to attacks
	
	String attackType = "melee";
	if ( ! meleeAttack) attackType = "ranged";
	if (weapon instanceof RangedWeapon)
	{
	    int rangeInc = ((RangedWeapon)weapon).getRangeIncrement();
	    if (rangeInc > 0) attackType = ""+rangeInc+" ft";
	}
	
	Attack a = new Attack
	    (weaponFullName,
	     attack,
	     damageDice,
	     damageCrit,
	     attackType,
	     weaponFullName);

	this.attacks.add(a);
    }

    private void parseCharacterAttack (boolean meleeAttack, String item)
    {
	System.out.println("parseCharacterAttack() >"+item+"<");

	//
	// determine ranged and melee attack modifiers

	int i = item.indexOf(" (");

	int[] attack = 
	    Utils.parsePreviousIntArray(item, i, new int[] { -4 });

	if (meleeAttack)
	{
	    if (this.meleeAttack == null)
		this.meleeAttack = attack;
	}
	else
	{
	    if (this.rangedAttack == null)
		this.rangedAttack = attack;
	}

	//
	// determine weapon used (and add to equipment)

	String weaponFullName = item.substring(0, i);
	weaponFullName = weaponFullName.substring
	    (0, //weaponFullName.indexOf(" ")+1, 
	     weaponFullName.lastIndexOf(" "));

	//System.out.println("...weapon >"+weaponFullName+"<");

	int magicModifier = 0;

	if (weaponFullName.charAt(0) == '+' ||
	    weaponFullName.charAt(0) == '-')
	{
	    String sMagicModifier = 
		weaponFullName.substring(0, weaponFullName.indexOf(" "));
	    weaponFullName = 
		weaponFullName.substring(weaponFullName.indexOf(" ")+1);

	    magicModifier = Utils.parseInt(sMagicModifier, 0);
	}

	System.out.println
	    ("...weapon "+burningbox.org.dsh.views.Utils.formatModifier(magicModifier, 2)+" >"+weaponFullName+"<");

	//
	// substract magic modifier to attack 
	
	if (meleeAttack)
	{
	    this.meleeAttack = burningbox.org.dsh.Utils
		.addToWholeArray(this.meleeAttack, -magicModifier);
	}
	else
	{
	    this.rangedAttack = burningbox.org.dsh.Utils
		.addToWholeArray(this.rangedAttack, -magicModifier);
	}

	//
	// parse damage
	
	String damageItem = item.substring(i+2, item.length()-1);

	//System.out.println("damage >"+damageItem+"<");

	String[] damageItems = damageItem.split("/crit ");

	String damageDice = damageItems[0];
	String damageCrit = null;
	if (damageItems.length > 1)
	    damageCrit = damageItems[1];
	damageDice = Utils.removeModifier(damageDice);

	//System.out.println("damage >"+damageDice+"< /crit >"+damageCrit+"<");

	//
	// generate eq piece and set magic modifier (if any)

	Weapon weapon = (Weapon)DataSets
	    .findEquipmentWithFullName(weaponFullName);

	if (weapon == null)
	{
	    if (meleeAttack)
		weapon = new MeleeWeapon();
	    else
		weapon = new RangedWeapon();

	    weapon.setName(weaponFullName);
	    weapon.setDescription(weaponFullName);
	    weapon.setDamages(damageDice);
	    if (damageCrit != null) weapon.setCritical(damageCrit);
	    
	    // don't set any proficiencyType.
	}
	else
	{
	    System.out.println("...found weapon : "+weapon.getName());
	}

	if (magicModifier != 0)
	{
	    weapon.getModifiers().add
		(new MiscModifier(Definitions.ATTACK, magicModifier));
	    weapon.getModifiers().add
		(new MiscModifier(Definitions.DAMAGE, magicModifier));
	    weapon.setDescription
		(burningbox.org.dsh.views.Utils.formatModifier(magicModifier, 2)+" "+weapon.getDescription());
	}

	//
	// Add it to equipment
	
	this.equipment.getHeldEquipment().add(weapon);
    }

    private void parseAttack (String item)
    {
	System.out.println("Atk item >"+item+"<");

	if (this.creatureType < 0)
	{
	    if (this.classes == null)
		//
		// probably a monster template
		this.creatureType = TYPE_MONSTER_TEMPLATE;
	    else
		//
		// a character (or a static character, ie a monster)
		this.creatureType = TYPE_CHARACTER;
	}

	//
	// determine attack type
	
	boolean meleeAttack = item.startsWith("melee");

	item = item.substring(item.indexOf(" ")+1);

	//
	// split with ", " ----> problem with the range
	
	String[] items = item.split("\\), ");

	for (int i=0; i<items.length-1; i++)
	{
	    items[i] += ")";
	}

	//
	// parse the attack
	
	if (this.creatureType == TYPE_CHARACTER)
	{
	    for (int i=0; i<items.length; i++)
		parseCharacterAttack(meleeAttack, items[i]);
	}
	else
	{
	    for (int i=0; i<items.length; i++)
		parseMonsterAttack(meleeAttack, items[i]);
	}
    }

    private void parseFaceAndReachItem (String item)
    {
	int i = item.indexOf("each");

	if (i < 0) return;

	this.faceReach = item.substring(i+5).trim();
    }

    private void parseSaqrItem (String item)
    {
	this.specials = new java.util.ArrayList();

	item = item.substring(item.indexOf(" ")+1);

	String[] items = item.split(", ");

	for (int i=0; i<items.length; i++)
	{
	    String s = items[i];
	    this.specials.add(new Special(s));
	}
    }

    private void parseSaveItem (String saves)
    {
	java.util.StringTokenizer st = 
	    new java.util.StringTokenizer(saves.toLowerCase());

	st.nextToken(); // skip SV

	st.nextToken(); // skip Fort
	String item = st.nextToken();
	//System.out.println("sv >"+item+"<");
	this.fortitude = Utils.parseFirstInt(item, 0);

	st.nextToken(); // skip Ref
	item = st.nextToken();
	//System.out.println("sv >"+item+"<");
	this.reflex = Utils.parseFirstInt(item, 0);

	st.nextToken(); // skip Will
	item = st.nextToken();
	//System.out.println("sv >"+item+"<");
	this.will = Utils.parseFirstInt(item, 0);
    }

    private void parseAlItem (String item)
    {
	item = item.substring(item.indexOf(" ")+1); // skip AL

	item = item.trim().toLowerCase();

	if (item.equals("le")) this.alignment = Definitions.LAWFUL_EVIL;
	else if (item.equals("ln")) this.alignment = Definitions.LAWFUL;
	else if (item.equals("lg")) this.alignment = Definitions.LAWFUL_GOOD;
	else if (item.equals("ne")) this.alignment = Definitions.EVIL;
	else if (item.equals("n")) this.alignment = Definitions.NEUTRAL;
	else if (item.equals("g")) this.alignment = Definitions.GOOD;
	else if (item.equals("ce")) this.alignment = Definitions.CHAOTIC_EVIL;
	else if (item.equals("cn")) this.alignment = Definitions.CHAOTIC;
	else if (item.equals("cg")) this.alignment = Definitions.CHAOTIC_GOOD;
    }

    private void parseAbilitiesItem (String item)
    {
	java.util.StringTokenizer st = new java.util.StringTokenizer(item);

	st.nextToken();
	int str = Utils.parseFirstInt(st.nextToken(), 10);

	st.nextToken();
	int dex = Utils.parseFirstInt(st.nextToken(), 10);

	st.nextToken();
	int con = Utils.parseFirstInt(st.nextToken(), 10);

	st.nextToken();
	int itl = Utils.parseFirstInt(st.nextToken(), 10);

	st.nextToken();
	int wis = Utils.parseFirstInt(st.nextToken(), 10);

	st.nextToken();
	int cha = Utils.parseFirstInt(st.nextToken(), 10);

	this.abilities = new Abilities(str, dex, con, itl, wis, cha);
    }

    /*
    private void parseNameItem (String item)
    {
	int i = item.indexOf(":");

	if (i < 0)
	{
	    this.name = item;
	    //System.out.println("Name is >"+this.name+"<");
	    return;
	}

	this.name = item.substring(0, i);

	//System.out.println("Name is >"+this.name+"<");

	item = item.substring(i+2);

	//System.out.println("rest is >"+item+"<");

	if (Utils.containsDigit(item)) // probably a class level indication
	{
	    parseGenderRaceClass(item);
	    return;
	}

	i = item.indexOf(" ");

	String size = item.substring(0, i);
	item = item.substring(i);

	parseSize(size);
	this.type = item;
    }
    */

    private void parseGenderRaceClass (String item)
    {
	int i = item.lastIndexOf(" ");

	String genderRace = item.substring(0, i);
	String classes = item.substring(i+1);

	i = genderRace.indexOf(" ");
	this.gender = genderRace.substring(0, i);
	this.race = genderRace.substring(i+1);

	parseStandardClasses(classes); 
    }

    private void parseStandardNameItem (String item)
    {
	System.out.println("parsing >"+item+"<");

	int i = item.indexOf(", ");

	if (i < 0)
	{
	    this.name = item;

	    System.out.println("...name is >"+this.name+"<");

	    return;
	}

	this.name = item.substring(0, i);

	System.out.println("...name is >"+this.name+"<");

	item = item.substring(i+2);

	//System.out.println("rest is >"+item+"<");

	i = item.indexOf(" ");

	this.gender = item.substring(0, i);

	System.out.println("...gender is >"+this.gender+"<");

	item = item.substring(i+1);

	i = Utils.indexOfFirstCharacterClass(item);

	this.race = item.substring(0, i);

	item = item.substring(i+1);

	System.out.println("...race is >"+this.race+"<");

	parseStandardClasses(item);
    }

    private void parseStandardClasses (String item)
    {
	this.classes = new java.util.ArrayList();

	String[] sClasses = item.split("/");

	for (int i=0; i<sClasses.length; i++)
	{
	    this.classes.add(parseClassAndLevel(sClasses[i]));
	}
    }

    private ClassLevel parseClassAndLevel (String item)
    {
	String className = "";
	String sLevel = "";

	boolean reachedNumbers = false;
	for (int i=0; i<item.length(); i++)
	{
	    char c = item.charAt(i);

	    if ( ! reachedNumbers && java.lang.Character.isDigit(c))
		reachedNumbers = true;

	    if (reachedNumbers)
		sLevel += c;
	    else
		className += c;
	}
	className = className.toLowerCase();

	System.out.println("...class "+className+"/"+sLevel);

	int level = Utils.parseInt(sLevel, 1);

	return new ClassLevel(className, level);
    }

    private void parseCr (String item)
    {
	int i = item.indexOf("/");

	if (i < 0)
	{
	    try
	    {
		this.challengeRating = (float)Integer.parseInt(item);
	    }
	    catch (NumberFormatException nfe)
	    {
		// ignore
	    }
	    return;
	}

	String a = item.substring(0, i);
	String b = item.substring(i+1);

	try
	{
	    float fa = (float)Integer.parseInt(a);
	    float fb = (float)Integer.parseInt(b);

	    this.challengeRating = fa / fb;

	    System.out.println
		("...CR is "+this.challengeRating+" ("+fa+"/"+fb+")");
	}
	catch (NumberFormatException nfe)
	{
	    // ignore
	}
    }

    /*
    private void parseSize (String item)
    {
	String s = item.trim().toLowerCase();

	if (s.matches(".*fine.*")) this.size = Size.FINE;
	else if (s.matches(".*diminutive.*")) this.size = Size.DIMINUTIVE;
	else if (s.matches(".*tiny.*")) this.size = Size.TINY;
	else if (s.matches(".*small.*")) this.size = Size.SMALL;
	else if (s.matches(".*medium.*")) this.size = Size.MEDIUM;
	else if (s.matches(".*large.*")) this.size = Size.LARGE;
	else if (s.matches(".*huge.*")) this.size = Size.HUGE;
	else if (s.matches(".*gargantuan.*")) this.size = Size.GARGANTUAN;
	else if (s.matches(".*colossal.*")) this.size = Size.COLOSSAL;
    }
    */

    private void parseSizeItem (String item)
    {
	item = item.substring(item.indexOf(" ")+1);
	char c = item.charAt(0);

	switch(c)
	{
	    case 'f' : this.size = Size.FINE; break;
	    case 'd' : this.size = Size.DIMINUTIVE; break;
	    case 't' : this.size = Size.TINY; break;
	    case 's' : this.size = Size.SMALL; break;
	    case 'm' : this.size = Size.MEDIUM; break;
	    case 'l' : this.size = Size.LARGE; break;
	    case 'h' : this.size = Size.HUGE; break;
	    case 'g' : this.size = Size.GARGANTUAN; break;
	    case 'c' : this.size = Size.COLOSSAL; break;
	    default : this.size = Size.MEDIUM;
	}
    }

    // skill line

    private void parseSkillLine (String line)
    {
	line = line.substring(line.indexOf(":")+1);

	String[] ss = line.split("; ");

	parseSkills(ss[0].trim());

	if (ss.length > 1) parseFeats(ss[1].trim());
    }

    private void parseSkills (String item)
    {
	this.skills = new java.util.HashMap();

	String[] ss = item.split(", ");

	for (int i=0; i<ss.length; i++)
	{
	    parseSkill(ss[i].trim());
	}
    }

    private void parseSkill (String item)
    {
	//System.out.println("parseSkill >"+item+"<");

	int i = item.indexOf(" +");
	int j = item.indexOf(" -");

	if (j > i) i = j;

	if (i < 0) i = item.indexOf(" ");

	String description = item.substring(0, i);

	Integer mod = new Integer(Utils.parseFirstInt(item.substring(i+1), 0));

	this.skills.put(description, mod);

	System.out.println("...'"+description+"' "+mod);
    }

    private void parseFeats (String item)
    {
	this.feats = new java.util.ArrayList();

	String[] ss = item.split(", ");

	for (int i=0; i<ss.length; i++)
	{
	    String sFeat = ss[i];

	    if (sFeat.indexOf("(") > -1 &&
		sFeat.indexOf(")") < 0)
	    {
		sFeat = sFeat + ", " + ss[i+1];
		i++;
	    }

	    parseFeat(sFeat.toLowerCase());
	}
    }

    private void parseFeat (String item)
    {
	System.out.println("parsing feat >"+item+"<");

	item = item.replace('[', ' ');
	item = item.replace(']', ' ');
	item = item.replace('.', ' ');
	item = item.trim();

	if (item.indexOf("(") < 0)
	{
	    this.feats.add(new FeatInstance(item));
	    return;
	}

	int i = item.indexOf(" (");
	String featName = item.substring(0, i);
	int j = item.indexOf(")");
	String beneficiary = item.substring(i+2, j);

	this.feats.add(new FeatInstance(featName, beneficiary));
    }

    // known spells line

    private void parseSpellLine (String line)
    {
	this.spellList = new SpellList();
	
	String className = line.substring(0, line.indexOf(" spells "));
	className = className.substring(0, 3).toLowerCase();

	//System.out.println("ClassName >"+className+"<");
	
	line = line.substring(line.indexOf(":")+1);

	String[] items = line.split("\\.");

	for (int i=0; i<items.length; i++)
	{
	    parseSpellLevel(items[i], className);
	}
    }

    private void parseSpellLevel (String item, String className)
    {
	int level = Utils.parseFirstInt(item, 0);

	item = item.substring(item.indexOf("-- ")+3);

	String[] items = item.split(", ");

	for (int i=0; i<items.length; i++)
	{
	    String spellName = items[i];
	    spellName = spellName.replace('/', ' ');

	    this.spellList.addSpell(className, spellName);
	}
    }

    // prepared spells line

    /*
     * In the cases of {known|prepared} spell lines with no indication
     * of the class, it is certainly a monoclassed character.
     * This methods fetches the first class (certainly the only class) 
     * of the character
     */
    private String fetchClass ()
    {
	if (this.classes == null || this.classes.size() < 1) 
	{
	    System.out.println("this.classes = "+this.classes);
	    return null;
	}

	ClassLevel cl = (ClassLevel)this.classes.get(0);

	return cl.getClassName();
    }

    private void parsePreparedSpellsLine (String line)
    {
	this.preparedSpells = new java.util.ArrayList();

	String className = null;
	int i = line.indexOf("spells prepared ");
	if (i > 0) 
	{
	    className = 
		line.substring(0, i-1);
	    className = 
		DataSets.findCharacterClassWithFullName(className).getName();
	}

	//System.out.println("className >"+className+"<");

	if (className == null)
	    className = fetchClass();

	//System.out.println("className >"+className+"<");
	
	i = line.indexOf("): ") + 3;
	line = line.substring(i);

	String[] levels = line.split("; ");

	for (int li=0; li<levels.length; li++)
	{
	    parsePreparedSpellLevel(className, levels[li]);
	}
    }

    private void parsePreparedSpellLevel (String className, String item)
    {
	//
	// remove spell level
	
	int i = item.indexOf("-");
	if (item.charAt(i+1) == '-') 
	    i += 2;
	else
	    i += 1;
	item = item.substring(i);

	String[] spells = item.split(", ");

	for (int si=0; si < spells.length; si++)
	{
	    parsePreparedSpell (className, spells[si]);
	}
    }

    private void parsePreparedSpell (String className, String item)
    {
	item = item.replace('*', ' ');
	item = item.replace('.', ' ');
	item = item.replace('/', ' ');
	item = item.trim();

	//System.out.println("parsePreparedSpell >"+item+"<");

	String spellName = null;
	int count = 1;

	if (item.indexOf("(") < 0)
	{
	    spellName = item;
	}
	else
	{
	    int i = item.indexOf("(");
	    spellName = item.substring(0, i).trim();
	    String sCount = item.substring(i+1, item.indexOf(")"));
	    count = Utils.parseInt(sCount, 1);
	}

	for (int i=0; i<count; i++)
	{
	    //System.out.println("adding >"+spellName+"<");
	    this.preparedSpells.add(new String[] { className, spellName });
	}
    }

    // domain spells

    private void parseDomainSpellsLine (String line)
    {
	System.out.println("parseDomainSpellsLine >"+line+"<");

	//
	// parse className
	
	String className = line.substring(0, line.indexOf(" domain spells"));

	//
	// identify domain !
    }

    // language line

    private void parseLanguageLine (String line)
    {
	this.languages = line.substring(line.indexOf(":")+2);

	if (this.languages.endsWith(".")) 
	{
	    this.languages = 
		this.languages.substring(0, this.languages.length()-1);
	}
    }

    //
    // build methods

    private burningbox.org.dsh.entities.Character buildCharacter ()
    {
	burningbox.org.dsh.entities.Character c = 
	    new burningbox.org.dsh.entities.Character();

	c.setName(this.name);
	c.setGender(this.gender);
	c.setRaceName(this.race);
	c.setType(this.type);
	c.setSize(this.size);
	c.setChallengeRating(this.challengeRating);
	c.setClasses(this.classes);
	c.setHitPoints(this.hp);
	c.setCurrentHitPoints(this.hp);
	// don't set init, it's computed automagically
	c.setSpeeds(this.speed);
	// don't set ac, it's computed automagically
	c.setFaceReach(this.faceReach);
	// don't set BAB, it's computed automagically
	// don't set SavingThrows, it's computed automagically
	c.setAbilities(this.abilities);
	c.setLanguages(this.languages);
	c.setFeats(this.feats);
	c.setSpellList(this.spellList);

	c.setEquipment(this.equipment);

	java.util.Iterator it = this.skills.entrySet().iterator();
	while (it.hasNext())
	{
	    java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
	    String skillDefinition = (String)entry.getKey();
	    int targetMod = ((Integer)entry.getValue()).intValue();

	    c.addSkill(skillDefinition, targetMod);
	}

	//
	// now, check AC, init, save mods and attack mods...
	
	int delta = 0;
	
	if (this.naturalAcMod != 0)
	{
	    c.getMiscModifiers().add
		(new MiscModifier(Definitions.ARMOR_CLASS, this.naturalAcMod));
	}

	System.out.println("..Deltas");

	delta = this.init - c.computeInitiativeModifier();
	if (delta != 0)
	{
	    System.out.println("...init delta : "+delta);
	    c.getMiscModifiers().add
		(new MiscModifier(Definitions.INITIATIVE, delta));
	}

	delta = 
	    this.fortitude - c.computeSavingThrowSet().getFortitudeModifier();
	if (delta != 0)
	{
	    System.out.println("...fortitude : "+this.fortitude);
	    System.out.println("...fortitude delta : "+delta);
	    c.getMiscModifiers().add
		(new MiscModifier(Definitions.FORTITUDE, delta));
	}

	delta = this.reflex - c.computeSavingThrowSet().getReflexModifier();
	if (delta != 0)
	{
	    System.out.println("...reflex : "+this.reflex);
	    System.out.println("...reflex delta : "+delta);
	    c.getMiscModifiers().add
		(new MiscModifier(Definitions.REFLEX, delta));
	}

	delta = this.will - c.computeSavingThrowSet().getWillModifier();
	if (delta != 0)
	{
	    System.out.println("...will : "+this.will);
	    System.out.println("...will delta : "+delta);
	    c.getMiscModifiers().add
		(new MiscModifier(Definitions.WILL, delta));
	}
	
	//
	// attack mods
	
	int[] aDelta = burningbox.org.dsh.Utils.computeDeltaArray
	    (this.meleeAttack, c.computeMeleeAttackModifier());
	if ( ! burningbox.org.dsh.Utils.isZeroed(aDelta)) 
	{
	    System.out.println
		("...melee delta : "+
		 burningbox.org.dsh.views.Utils.formatModifiers(aDelta, 2));
	    c.setMeleeModifier(aDelta);
	}
	
	System.out.println
	    ("...ranged computed : "+burningbox.org.dsh.views.Utils
		 .formatModifiers(c.computeRangedAttackModifier(), 2));
	/*
	System.out.println
	    ("...ranged : "+burningbox.org.dsh.views.Utils
		 .formatModifiers(this.rangedAttack, 2));
	*/
	aDelta = burningbox.org.dsh.Utils.computeDeltaArray
	    (this.rangedAttack, c.computeRangedAttackModifier());
	if ( ! burningbox.org.dsh.Utils.isZeroed(aDelta)) 
	{
	    System.out.println
		("...ranged delta : "+
		 burningbox.org.dsh.views.Utils.formatModifiers(aDelta, 2));
	    c.setRangedModifier(aDelta);
	}

	//
	// what about the spellBook ?
	
	// :-(

	//
	// prepared spells
	
	if (this.preparedSpells != null)
	{
	    it = this.preparedSpells.iterator();
	    while (it.hasNext())
	    {
		String[] spell = (String[])it.next();
		try
		{
		    c.getPreparedSpells().addPreparedSpell(spell[0], spell[1]);

		    System.out.println
			("...prepared '"+spell[1]+"' ("+spell[0]+")");
		}
		catch (DshException de)
		{
		    System.out.println
			("Failed to prepare spell '"+spell[1]+
			 "' ("+spell[0]+") :\n"+de.getMessage());
		}
	    }
	}
	
	return c;
    }

    private MonsterTemplate buildMonsterTemplate ()
    {
	burningbox.org.dsh.entities.MonsterTemplate m = 
	    new burningbox.org.dsh.entities.MonsterTemplate();

	m.setName(this.name);
	m.setGender(this.gender);
	m.setRaceName(this.race);
	m.setType(this.type);
	m.setSize(this.size);
	m.setChallengeRating(this.challengeRating);
	//m.setClasses(this.classes);
	//m.setHitPoints(this.hp);
	//m.setCurrentHitPoints(this.hp);
	m.setPotentialHitPoints(this.potentialHp);
	m.setSpeeds(this.speed);
	// don't set ac, it's computed automagically
	m.setFaceReach(this.faceReach);
	// don't set BAB, it's computed automagically
	m.setAbilities(this.abilities);
	m.setLanguages(this.languages);
	m.setFeats(this.feats);
	//m.setSpellList(this.spellList);
	m.setAttacks(this.attacks);
	m.setSavingThrowSet
	    (new SavingThrowSet(this.fortitude, this.reflex, this.will));
	m.setInitModifier(this.init);
	m.setArmorClass(this.ac);
	//m.setArmorClassVsTouchAttack(this....

	m.setEquipment(this.equipment);

	/*
	java.util.Iterator it = this.skills.entrySet().iterator();
	while (it.hasNext())
	{
	    java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
	    String skillDefinition = (String)entry.getKey();
	    int targetMod = ((Integer)entry.getValue()).intValue();

	    c.addSkill(skillDefinition, targetMod);
	}
	*/

	return m;
    }

    private Monster buildMonster ()
    {
	return null;
    }

    //
    // STATIC METHODS

    /**
     * Turns a file into a character or a monster
     */
    public static Being parseFile (String fileName)
    {

	java.util.List statBlockLines = new java.util.ArrayList();

	try
	{
	    java.io.BufferedReader br = 
		new java.io.BufferedReader(new java.io.FileReader(fileName));

	    while (true)
	    {
		String line = br.readLine();

		if (line == null) break;

		statBlockLines.add(line);
	    }
	}
	catch (java.io.IOException ie)
	{
	    System.out.println("failed to parse stat blocks '"+fileName+"'");
	    ie.printStackTrace();
	    return null;
	}

	return (new StatBlockParser()).parseStatBlocks(statBlockLines);
    }

    private static void printUsage ()
    {
	System.out.println("\nUSAGE :\n");
	System.out.println("./raptor.sh {statBlockFileToImport}");
	System.out.println("    or");
	System.out.println("raptor {statBlockFileToImport}\n");
    }

    /**
     * parses standard input, so you can 'cat monster.sbo &gt; ./test.sh'
     */
    public static void main (String[] args)
    {

	if (args.length == 0 ||
	    (args.length > 0 && 
	     (args[0].equals("-h") || args[0].equals("--help"))))
	{
	    printUsage();
	    System.exit(0);
	}

	//
	// load configuration
	
	try
	{
	    System.out.println("..loading reference data from etc/");
	    Configuration conf = Configuration.load("etc/dgsh-config.xml");
	    conf.apply(false);
	}
	catch (Exception e)
	{
	    System.out.println
		("Failed to load configuration files, cannot continue.");
	    e.printStackTrace();

	    System.exit(1);
	}

	//
	// parse command line then do the job

	Being b = (new StatBlockParser()).parseFile(args[0]);

	// do something with b ...
	
	if (b == null)
	{
	    System.out.println("Failed to parse stat blocks.");
	    printUsage();
	    System.exit(1);
	}

	BeanSet bs = new BeanSet();
	bs.add(b);
	String fileName = b.getName().toLowerCase().replace(' ', '_');
	fileName += ".xml";
	try
	{
	    bs.save(fileName);
	}
	catch (java.io.IOException ie)
	{
	    System.out.println("Failed to save creature");
	    ie.printStackTrace();
	    System.exit(1);
	}

	System.out.println("Creature saved to '"+fileName+"'");
	System.exit(0);
    }

}
