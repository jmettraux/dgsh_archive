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
 * $Id: Character.java,v 1.36 2002/11/11 10:40:30 jmettraux Exp $
 */

//
// Character.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You're growing out of some of your problems, but there are others that
// you're growing into.
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.Utils;
import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.DshException;
import burningbox.org.dsh.commands.Trigger;
import burningbox.org.dsh.entities.magic.Spells;
import burningbox.org.dsh.entities.magic.SpellList;
import burningbox.org.dsh.entities.magic.SpellSlots;
import burningbox.org.dsh.entities.magic.PreparedSpells;
import burningbox.org.dsh.entities.equipment.Weapon;
import burningbox.org.dsh.entities.equipment.MeleeWeapon;
import burningbox.org.dsh.entities.equipment.RangedWeapon;
import burningbox.org.dsh.entities.equipment.MeleeOrRangedWeapon;


/**
 * A PC or a NPC.
 * (in fact someone who has got a Class, i.e. not a basic monster)
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/11/11 10:40:30 $
 * <br>$Id</font>
 *
 * @author jmettraux@burningbox.org
 */
public class Character

    extends Being

{

    org.apache.log4j.Logger log = 
	org.apache.log4j.Logger.getLogger("dgsh.Character");

    //
    // FIELDS
    
    protected String		player = null;
    protected int		hitPoints = -10;
    protected int		currentHitPoints = -10;
    protected java.util.List 	classes = null;
    protected int 		experiencePoints = -1;
    protected int 		subdualDamage = 0;

    protected int[]		meleeModifier = null;
    protected int[]		rangedModifier = null;
	// these two are used for custom (on the fly races)

    protected SpellSlots 	spellSlots = null;
    protected SpellList		spellList = null;
    protected PreparedSpells	preparedSpells = new PreparedSpells(this);

    // the full spell book/list doesn't get serialized
    protected transient Spells  knownSpells = null;

    //
    // CONSTRUCTORS
    
    public Character ()
    {
	super();

	this.classes = new java.util.ArrayList(1);
    }

    //
    // BEAN METHODS
    
    public String getPlayer () { return this.player; }
    public int getHitPoints () { return this.hitPoints; }
    public int getCurrentHitPoints () { return this.currentHitPoints; }
    public java.util.List getClasses () { return this.classes; }
    public int getExperiencePoints () { return this.experiencePoints; }
    public int getSubdualDamage () { return this.subdualDamage; }
    public int[] getMeleeModifier () { return this.meleeModifier; }
    public int[] getRangedModifier () { return this.rangedModifier; }
    public SpellSlots getSpellSlots () 
    { 
	if (this.spellSlots == null)
	    SpellSlots.resetSlots(this);
	return this.spellSlots; 
    }
    public SpellList getSpellList () { return this.spellList; }
    public PreparedSpells getPreparedSpells () { return this.preparedSpells; }

    public void setPlayer (String p) { this.player = p; }
    public void setHitPoints (int hp) { this.hitPoints = hp; }
    public void setCurrentHitPoints (int chp) { this.currentHitPoints = chp; }
    public void setClasses (java.util.List classes) { this.classes = classes; }
    public void setExperiencePoints (int xp) { this.experiencePoints = xp; }
    public void setSubdualDamage (int sd) { this.subdualDamage = sd; }
    public void setMeleeModifier (int[] is) { this.meleeModifier = is; }
    public void setRangedModifier (int[] is) { this.rangedModifier = is; }
    public void setSpellSlots (SpellSlots ss) { this.spellSlots = ss; }
    public void setSpellList (SpellList sl) { this.spellList = sl; }
    public void setPreparedSpells (PreparedSpells ps) 
    { 
	this.preparedSpells = ps; 
	this.preparedSpells.setSpellCaster(this);
    }

    //
    // METHODS

    /**
     * Hm, well, for the moment, I leave it like this : this accessor method
     * returns the spellSlots instance without trying to reset it like the
     * getX one.
     * Not very elegant.
     */
    public SpellSlots fetchSpellSlots ()
    {
	return this.spellSlots;
    }

    //
    // METHODS FROM Being
    
    //
    // ability methods

    public int computeAbilityModifier (String abilityDefinition)
    {
	if (abilityDefinition.equals(Definitions.DEXTERITY))
	    return computeDexterityModifier();

	int modifier = 0;

	modifier += Abilities
	    .computeModifier(computeAbilityScore(abilityDefinition));

	modifier += this.equipment.sumMiscModifiers(abilityDefinition);

	// Brazil just won against Turkey

	return modifier;
    }

    private int computeDexterityModifier ()
    {
	int modifier = Abilities
	    .computeModifier(computeAbilityScore(Definitions.DEXTERITY));

	int armorMaxDex = this.equipment.getWornEquipment()
	    .getArmorMaxDexModifier();
	if (armorMaxDex < modifier)
	    modifier = armorMaxDex;

	int loadMaxDex = this.equipment.getMaxDexModifier
	    (this.abilities.getScore(Definitions.STRENGTH), this.size);
	if (loadMaxDex < modifier)
	    modifier = loadMaxDex;

	return modifier;
    }
    
    //
    // saving throw methods
    
    private int sumSavingThrowModifiers (String savingThrowIdentifier)
    {
	int result = 0;

	//
	// get class[es] modifiers

	java.util.Iterator it = this.classes.iterator();
	while(it.hasNext())
	{
	    ClassLevel cl = 
		(ClassLevel)it.next();
	    CharacterClass cc = 
		DataSets.findCharacterClass(cl.getClassName());

	    if (cc == null) continue; // don't know about 'dwarven defenders'...

	    int level = 
		cl.getLevelReached();
	    SavingThrowSet sts = 
		cc.fetchSavingThrowSet(level);

	    result += sts.fetchModifier(savingThrowIdentifier);
	}

	//
	// get ability modifier
	
	if (savingThrowIdentifier.equals(Definitions.FORTITUDE))
	{
	    result += computeAbilityModifier(Definitions.CONSTITUTION);
	}
	else if (savingThrowIdentifier.equals(Definitions.REFLEX))
	{
	    result += computeAbilityModifier(Definitions.DEXTERITY);
	}
	else if (savingThrowIdentifier.equals(Definitions.WILL))
	{
	    result += computeAbilityModifier(Definitions.WISDOM);
	}

	//
	// apply misc modifiers
	
	result += sumMiscModifiers (savingThrowIdentifier);

	return result;
    }

    private int computeFortitudeModifier ()
    {
	return sumSavingThrowModifiers(Definitions.FORTITUDE);
    }
    private int computeReflexModifier ()
    {
	return sumSavingThrowModifiers(Definitions.REFLEX);
    }
    private int computeWillModifier ()
    {
	return sumSavingThrowModifiers(Definitions.WILL);
    }

    public SavingThrowSet computeSavingThrowSet ()
    {
	SavingThrowSet result = new SavingThrowSet();

	result.setFortitudeModifier(computeFortitudeModifier());
	result.setReflexModifier(computeReflexModifier());
	result.setWillModifier(computeWillModifier());

	return result;
    }

    //
    // skill methods

    public int computeSkillModifier (String skillDefinition)
    {
	CharacterSkill cSkill = 
	    (CharacterSkill)getSkill(skillDefinition);

	if (cSkill == null)
	    return -15;

	int result = cSkill.computeModifier(this);

	result += 
	    sumMiscModifiers(skillDefinition);

	return result;
    }

    //
    // initiative methods

    public int computeInitiativeModifier ()
    {
	int result = 0;

	//
	// apply dex modifier

	result += computeAbilityModifier(Definitions.DEXTERITY);
	
	//
	// apply misc modifiers
	
	result += sumMiscModifiers (Definitions.INITIATIVE);

	return result;
    }

    //
    // armor class methods

    public int computeArmorClass ()
    {
	int ac = 10;

	ac += computeAbilityModifier(Definitions.DEXTERITY);
	ac += this.equipment.sumArmorClass();
	ac += this.equipment.sumMiscModifiers(Definitions.ARMOR_CLASS);
	ac += this.size.computeSizeModifier();
	ac += sumMiscModifiers(Definitions.ARMOR_CLASS);

	return ac;
    }

    public int computeArmorClassVsTouchAttack ()
    {
	/*
	int ac = computeArmorClass();

	ac -= computeAbilityModifier(Definitions.DEXTERITY);

	return ac;
	*/

	int acvta = 10 + computeAbilityModifier(Definitions.DEXTERITY);
	acvta += this.size.computeSizeModifier();

	return acvta;
    }

    //
    // attack modifiers methods

    /**
     * returns the base attack modifier for any character
     */
    public int[] computeBaseAttackModifier ()
    {
	int[] sum = new int[] { 0 };

	java.util.Iterator it = classes.iterator();
	while (it.hasNext())
	{
	    ClassLevel cLevel = (ClassLevel)it.next();

	    CharacterClass cClass = DataSets
		.findCharacterClass(cLevel.getClassName());

	    if (cClass == null) continue;

	    int[][] baseAttacks = cClass.getBaseAttackModifiers();
	    int[] cBaseAttack = baseAttacks[cLevel.getLevelReached()-1];

	    sum = Utils.addArrays(sum, cBaseAttack);
	}

	//
	// now rectify the attack modifier !!!
	//
	// Many thanks to CRGreathouse for explaining me how the rules
	// apply here !
	
	sum = burningbox.org.dsh.entities.Utils.rectifyAttackModifier(sum);

	/*
	System.out.println
	    ("rectified BAB : "+
	     burningbox.org.dsh.views.Utils.formatModifiers(sum, 2));
	*/

	//
	// add modifiers...

	int mod = sumMiscModifiers(Definitions.ATTACK);
	if (mod != 0)
	    Utils.addToWholeArray(sum, mod);

	return sum;
    }

    public int[] computeMeleeAttackModifier ()
    {
	//System.out.println("--MELEE--");

	int[] result = computeBaseAttackModifier();
	
	result = Utils.addToWholeArray
	    (result, computeAbilityModifier(Definitions.STRENGTH));
	result = Utils.addToWholeArray
	    (result, this.size.computeSizeModifier());

	int mod = sumMiscModifiers(Definitions.MELEE);
	if (mod != 0)
	    Utils.addToWholeArray(result, mod);

	if (this.meleeModifier != null)
	    result = Utils.addArrays(result, this.meleeModifier);

	return result;
    }

    public int[] computeRangedAttackModifier ()
    {
	//System.out.println("--RANGED--");

	int[] result = computeBaseAttackModifier();
	
	result = Utils.addToWholeArray
	    (result, computeAbilityModifier(Definitions.DEXTERITY));
	result = Utils.addToWholeArray
	    (result, this.size.computeSizeModifier());

	int mod = sumMiscModifiers(Definitions.RANGED);
	if (mod != 0)
	    Utils.addToWholeArray(result, mod);

	if (this.rangedModifier != null)
	    result = Utils.addArrays(result, this.rangedModifier);

	return result;
    }

    public int[] computeAttackModifier (Weapon w)
    {
	int[] attackModifier;

	if (w instanceof MeleeOrRangedWeapon)
	{
	    if (((MeleeOrRangedWeapon)w).viewAsMeleeWeapon())
	    {
		attackModifier = computeMeleeAttackModifier();
	    }
	    else
	    {
		attackModifier = computeRangedAttackModifier();
		int mod = sumMiscModifiers(Definitions.THROWN);
		if (mod != 0)
		    Utils.addToWholeArray(attackModifier, mod);
	    }
	}
	else if (w instanceof MeleeWeapon)
	{
	    attackModifier = computeMeleeAttackModifier();

	    attackModifier = Utils.addToWholeArray
		(attackModifier, w.sumMiscModifiers(Definitions.MELEE));
	}
	else
	{
	    attackModifier = computeRangedAttackModifier();

	    attackModifier = Utils.addToWholeArray
		(attackModifier, w.sumMiscModifiers(Definitions.RANGED));
	}

	//
	// check feats associated with the weapon
	
	// weapon proficiencies
	
	FeatInstance simpleFeat = getWeaponProficiencyFeat
	    (Feat.SIMPLE_WEAPON_PROFICIENCY, null);

	if (simpleFeat == null)
	{
	    simpleFeat = getWeaponProficiencyFeat
		(Feat.SIMPLE_WEAPON_PROFICIENCY, w);
	}
	
	FeatInstance martialFeat = getWeaponProficiencyFeat
	    (Feat.MARTIAL_WEAPON_PROFICIENCY, w);

	FeatInstance exoticFeat = getWeaponProficiencyFeat
	    (Feat.EXOTIC_WEAPON_PROFICIENCY, w);
	
	if (w.getProficiencyType().equals(Feat.SIMPLE_WEAPON_PROFICIENCY) &&
	    simpleFeat == null)
	{
	    attackModifier = Utils.addToWholeArray(attackModifier, -4);
	}
	else 
	if (w.getProficiencyType().equals(Feat.MARTIAL_WEAPON_PROFICIENCY) &&
	    martialFeat == null)
	{
	    attackModifier = Utils.addToWholeArray(attackModifier, -4);
	}
	else
	if (w.getProficiencyType().equals(Feat.EXOTIC_WEAPON_PROFICIENCY) &&
	    exoticFeat == null)
	{
	    attackModifier = Utils.addToWholeArray(attackModifier, -4);
	}

	// weapon focus
	
	FeatInstance focus = 
	    getFeat(Feat.WEAPON_FOCUS, w.getCategoryDefinition());

	if (focus == null)
	{
	    focus = getFeat(Feat.WEAPON_FOCUS, w.getName());
	}

	if (focus != null)
	{
	    attackModifier = 
		Utils.addToWholeArray(attackModifier, focus.getModifier());
	}

	//
	// magical weapon
	
	attackModifier = Utils.addToWholeArray
	    (attackModifier, w.sumMiscModifiers(Definitions.ATTACK));

	return attackModifier;
    }

    public int computeDamageModifier (Weapon w)
    {
	int mod = 0;

	mod += sumMiscModifiers(Definitions.DAMAGE);

	if (w instanceof MeleeOrRangedWeapon)
	{
	    if (((MeleeOrRangedWeapon)w).viewAsMeleeWeapon())
		mod += computeAbilityModifier(Definitions.STRENGTH);
	}
	else if (w instanceof MeleeWeapon)
	{
	    mod += computeAbilityModifier(Definitions.STRENGTH);
	}

	// magical weapon
	mod += w.sumMiscModifiers(Definitions.DAMAGE);

	return mod;
    }

    public Trigger fetchTriggerToApplyEachRound ()
	throws DshException
    {
	Trigger t = this.race.fetchTriggerToApplyEachRound();

	if (t == null) return null;

	t.setTarget(this);

	return t;
    }

    //
    // Class and levels related methods

    public int computeLevelForClass (String className)
    {
	java.util.Iterator it = this.classes.iterator();
	while (it.hasNext())
	{
	    ClassLevel cl = (ClassLevel)it.next();

	    if (cl.getClassName().equals(className))
		return cl.getLevelReached();
	}

	return 0;
    }

    public int computeLevel ()
    {
	int result = 0;

	java.util.Iterator it = this.classes.iterator();
	while (it.hasNext())
	{
	    ClassLevel cl = (ClassLevel)it.next();
	    result += cl.getLevelReached();
	}

	return result;
    }

    public float computeChallengeRating ()
    {
	int level = this.computeLevel();

	if (this.race == null) return level;

	return 
	    (float)(level + race.getLevelEquivalentModifier());
    }

    public boolean isASpellCaster ()
    {
	if (this.spellSlots == null)
	    SpellSlots.resetSlots(this);
	return this.spellSlots.isASpellCaster();
    }

    //
    // transient accessors

    public Spells getKnownSpells ()
    {
	if (this.knownSpells == null)
	{
	    //log.debug("Preparing known spells for "+this.name);

	    this.knownSpells = new Spells(this);
	}
	return this.knownSpells;
    }

    public void addClassLevel (String className, int levelCount)
	throws DshException
    {
	if (levelCount == 0) return;

	java.util.Iterator it = this.classes.iterator();
	while (it.hasNext())
	{
	    ClassLevel cl = (ClassLevel)it.next();
	    if (cl.getClassName().equals(className))
	    {
		int currentLevel = cl.getLevelReached();
		currentLevel += levelCount;
		cl.setLevelReached(currentLevel);

		if (currentLevel < 1) 
		    it.remove();

		return;
	    }
	}

	//
	// class not found so add to classes
	
	if (levelCount < 0)
	{
	    throw new DshException
		("Cannot add negative level for a class "+
		 "in which character had no previous levels.");
	}
	
	ClassLevel cl = new ClassLevel(className, levelCount);
	this.classes.add(cl);
    }

    //
    // methods for stat block generated characters

    /**
     * Adds a skill to the character.
     * The targetModifier is the total score that the character should have for
     * this skill, thus this method will compute the ranks for the skills.
     */
    public void addSkill (String skillName, int targetModifier)
    {
	CharacterSkill cSkill = new CharacterSkill(skillName, 0);

	int mod = cSkill.computeModifier(this);
	mod += sumMiscModifiers(skillName);

	int delta = targetModifier - mod;

	cSkill.setRanks(delta);

	this.skills.add(cSkill);
    }

}
