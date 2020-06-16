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
 * $Id: Being.java,v 1.27 2002/11/11 10:40:30 jmettraux Exp $
 */

//
// Being.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Q:	How many existentialists does it take to screw in a lightbulb?
// A:	Two.  One to screw it in and one to observe how the lightbulb
// 	itself symbolizes a single incandescent beacon of subjective
// 	reality in a netherworld of endless absurdity reaching out toward a
// 	maudlin cosmos of nothingness.
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.DshException;
import burningbox.org.dsh.commands.Trigger;
import burningbox.org.dsh.entities.equipment.Weapon;
import burningbox.org.dsh.entities.equipment.Equipment;


/**
 * A Being (not a Boeing !)
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/11/11 10:40:30 $
 * <br>$Id: Being.java,v 1.27 2002/11/11 10:40:30 jmettraux Exp $</font>
 *
 * @author jmettraux@burningbox.org
 */
public abstract class Being
    
    implements 
	Named,
	WithInitiative

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.Being");

    //
    // FIELDS
    
    protected String 		name = null;
    protected String		gender = Definitions.MALE;
    protected String		type = null;
    protected String		raceName = null;
    protected String		alignment = null;
    protected int		hitDice = -1;
    protected String		speeds = null;
    protected float		challengeRating = (float)0.0;
    protected Size		size = null;
    protected Abilities		abilities = null;
    protected Equipment		equipment = new Equipment();
    protected int 		currentInitiative = -100;
    protected String 		faceReach = "5 by 5 / 5";
    protected java.util.List	skills = new java.util.ArrayList(0);
    protected java.util.List	miscModifiers = new java.util.ArrayList(0);
    protected java.util.List	feats = new java.util.ArrayList(0);
    protected java.util.Map 	attributes = new java.util.HashMap(0);
    protected String		languages = null;

    protected transient java.util.List	effects = new java.util.ArrayList(1);

    protected java.util.Map specials = new java.util.HashMap(0);
	// Special instances are especially useful for monsters :
	// they represent either their special abilities either their
	// special attacks.
	// For characters, they will be used to store things like
	// the illusionism abilities gnomes have.

    protected transient int	opportunitiesLeft = 1;

    protected transient Race	race = null;

    //
    // CONSTRUCTORS
    
    public Being ()
    {
    }

    //
    // BEAN METHODS
    
    public String getName () { return this.name; }
    public String getGender () { return this.gender; }
    public String getType () { return this.type; }
    public String getRaceName () { return this.raceName; }
    public String getAlignment () { return this.alignment; }
    public int getHitDice () { return this.hitDice; }
    public String getSpeeds () { return this.speeds; }
    public float getChallengeRating () { return this.challengeRating; }
    public Size getSize () { return this.size; }
    public Abilities getAbilities () { return this.abilities; }
    public Equipment getEquipment () { return this.equipment; }
    public int getCurrentInitiative () { return this.currentInitiative; }
    public String getFaceReach () { return this.faceReach; }
    public java.util.List getSkills () { return this.skills; }
    public java.util.List getMiscModifiers () { return this.miscModifiers; }
    public java.util.List getFeats () { return this.feats; }
    public java.util.Map getSpecials () { return this.specials; }
    public java.util.Map getAttributes () { return this.attributes; }
    public String getLanguages () { return this.languages; }

    public void setName (String name) { this.name = name; }
    public void setGender (String s) { this.gender = s; }
    public void setType (String s) { this.type = s; }
    //public void setRaceName (String rn) { this.raceName = rn; }
    public void setAlignment (String alignment) { this.alignment = alignment; }
    public void setHitDice (int hd) { this.hitDice = hd; }
    public void setSpeeds (String speeds) { this.speeds = speeds; }
    public void setChallengeRating (float cr) { this.challengeRating = cr; }
    public void setSize (Size size) { this.size = size; }
    public void setAbilities (Abilities a) { this.abilities = a; }
    public void setEquipment (Equipment e) { this.equipment = e; }
    public void setCurrentInitiative (int ci) { this.currentInitiative = ci; }
    public void setFaceReach (String fr) { this.faceReach = fr; }
    public void setSkills (java.util.List l) { this.skills = l; }
    public void setMiscModifiers (java.util.List l) { this.miscModifiers = l; }
    public void setFeats (java.util.List l) { this.feats = l; }
    public void setSpecials (java.util.Map m) { this.specials = m; }
    public void setAttributes (java.util.Map m) { this.attributes = m; }
    public void setLanguages (String s) { this.languages = s; }

    public void setRaceName (String s)
    {
	this.raceName = s;

	if (s == null) this.race = null;

	this.race = DataSets.findRace(s);
    }

    //
    // METHODS

    public Race getRace () { return this.race; }

    //
    // ability methods

    public int computeAbilityScore (String abilityDefinition)
    {
	int score = this.abilities.getScore(abilityDefinition);

	score += sumMiscModifiers(abilityDefinition);

	return score;
    }

    /**
     * This method is used for keeping track of the initiative
     */
    public int computeDexterity ()
    {
	return computeAbilityModifier(Definitions.DEXTERITY);
    }

    public int computeAbilityModifier (String abilityDefinition)
    {
	return Abilities
	    .computeModifier(this.abilities.getScore(abilityDefinition));
    }

    public int computeAbilityDelta (String abilityDefinition)
    {
	int delta = this.abilities.getAbilityDelta(abilityDefinition);
	delta += sumMiscModifiers(abilityDefinition);

	return delta;
    }

    //
    // attribute methods

    public Object getAttribute (String attributeName)
    {
	return this.attributes.get(attributeName);
    }

    public void setAttribute (String attributeName, Object value)
    {
	this.attributes.put(attributeName, value);
    }

    public void removeAttribute (String attributeName)
    {
	this.attributes.remove(attributeName);
    }

    // 
    // skill methods

    public boolean hasSkill (String skillDefinition)
    {
	if (this.skills == null)
	    return false;

	skillDefinition = skillDefinition.toLowerCase();
	
	java.util.Iterator it = this.skills.iterator();
	while(it.hasNext())
	{
	    Named n = (Named)it.next();

	    if (n.getName().toLowerCase().equals(skillDefinition))
		return true;
	}

	return false;
    }

    public MonsterSkill getSkill (String skillDefinition)
    {
	if (this.skills == null)
	    return null;

	//
	// it's fast enough without requiring to a Map
	
	java.util.Iterator it = this.skills.iterator();
	while(it.hasNext())
	{
	    MonsterSkill s = (MonsterSkill)it.next();
	    if (s.getSkillName().equals(skillDefinition))
		return s;
	}

	return null;
    }

    //
    // modifiers methods

    public int sumMiscModifiers (String modifierDefinition)
    {
	int mod = 0;

	mod += MiscModifier.sumModifiers
	    (modifierDefinition, this.miscModifiers);
	mod += MiscModifier.sumModifiers
	    (modifierDefinition, this.feats);
	mod += MiscModifier.sumModifiers
	    (modifierDefinition, this.effects);

	mod += DataSets
	    .computeRacialModifier(this.race, modifierDefinition);

	return mod;
    }

    /**
     * It is only used in the skills view to display the effects of a feat
     * on a skill
     */
    public int sumFeatModifiers (String modifierDefinition)
    {
	return MiscModifier.sumModifiers(modifierDefinition, this.feats);
    }

    //
    // effect methods

    public void addEffect (Effect effect)
    {
	this.effects.add(effect);
    }

    public boolean isSubjectToAnyEffect ()
    {
	return (this.effects.size() > 0);
    }

    public java.util.Iterator effectIterator ()
    {
	return this.effects.iterator();
    }

    public boolean isDazed ()
    {
	java.util.Iterator it = effectIterator();

	while (it.hasNext())
	{
	    Effect fx = (Effect)it.next();
	    if (fx.isDazed()) return true;
	}

	return false;
    }

    public void purgeOldEffects ()
    {
	synchronized (this.effects)
	{
	    java.util.Iterator it = this.effects.iterator();
	    while (it.hasNext())
	    {
		Effect fx = (Effect)it.next();

		if (fx.getRoundsToGo() < 0) 
		{
		    log.debug("Purging old effect '"+fx.getName()+"'");

		    it.remove();
		}
	    }
	}
    }

    //
    // METHODS for special instances

    public Special findSpecial (String specialName)
    {
	Special result = (Special)this.specials.get(specialName);

	if (result == null && this.race != null)
	    result = (Special)this.race.getSpecials().get(specialName);

	return result;
    }

    public java.util.Iterator specialIterator ()
    {
	java.util.List result = new java.util.ArrayList(0);

	result.addAll(this.specials.values());

	if (this.race != null)
	    result.addAll(this.race.getSpecials().values());

	return result.iterator();
    }

    public void addSpecial (Special s)
    {
	this.specials.put(s.getName(), s);
    }

    /*
    public int sumEffectModifiers (String modifierDefinition)
    {
	return MiscModifier.sumModifiers(modifierDefinition, this.effects);
    }
    */

    public int computeArmorClassFlatFooted ()
    {
	int ac = computeArmorClass();

	int result = ac - computeAbilityModifier(Definitions.DEXTERITY);

	if (result > ac) return ac;

	return result;
    }

    //
    // feat methods

    private FeatInstance featLookup 
	(String featName, String beneficiaryDefinition)
    {
	if (this.feats == null) return null;

	java.util.Iterator it = this.feats.iterator();
	while (it.hasNext())
	{
	    FeatInstance fi = (FeatInstance)it.next();

	    if (featName != null && 
		fi.getFeatName().equals(featName)) 
	    {
		return fi;
	    }
	    if (beneficiaryDefinition != null &&
		beneficiaryDefinition.equals(fi.getBeneficiaryDefinition()))
	    {
		return fi;
	    }
	}
	return null;
    }

    public FeatInstance getWeaponProficiencyFeat 
	(String featName, Weapon w)
    {
	if (this.feats == null || w == null) return null;

	java.util.Iterator it = this.feats.iterator();
	while (it.hasNext())
	{
	    FeatInstance fi = (FeatInstance)it.next();
	    String beneficiaryDefinition = fi.getBeneficiaryDefinition();

	    if (fi.getFeatName().equals(featName) &&
		beneficiaryDefinition == null)
	    {
		return fi;
	    }
	    if (fi.getFeatName().equals(featName) &&
		beneficiaryDefinition != null &&
		(beneficiaryDefinition.equals(w.getCategoryDefinition()) ||
		 beneficiaryDefinition.equals(w.getName())))
	    {
		return fi;
	    }
	}
	return null;
    }

    public FeatInstance getFeat (String featName, String beneficiaryDefinition)
    {
	if (this.feats == null) return null;

	java.util.Iterator it = this.feats.iterator();
	while (it.hasNext())
	{
	    FeatInstance fi = (FeatInstance)it.next();

	    if (fi.getFeatName().equals(featName) &&
		fi.getBeneficiaryDefinition().equals(beneficiaryDefinition))
	    {
		return fi;
	    }
	}
	return null;
    }
    
    public FeatInstance getFeat (String featName)
    {
	return featLookup(featName, null);
    }

    public FeatInstance getFeatWithBeneficiary (String beneficiaryDefinition)
    {
	return featLookup(null, beneficiaryDefinition);
    }

    //
    // methods regarding attack of opportunities

    /**
     * This method will return true if the character has enough 
     * attacks of opportunity left.
     */
    public boolean performAttackOfOpportunity ()
    {
	if (this.opportunitiesLeft <= 0) return false;

	this.opportunitiesLeft--;

	return true;
    }

    public void flushAttackOfOpportunities ()
    {
	if (getFeat("combat reflexes") != null)
	{
	    this.opportunitiesLeft = 
		computeAbilityModifier(Definitions.DEXTERITY);
	}

	if (this.opportunitiesLeft < 1)
	    this.opportunitiesLeft = 1;
    }

    public void incOpportunitiesLeft (int count)
    {
	this.opportunitiesLeft += count;
    }

    public int getOpportunitiesLeft ()
    {
	return this.opportunitiesLeft;
    }

    //
    // ABSTRACT METHODS

    public abstract int getHitPoints ();
    public abstract int getCurrentHitPoints ();
    public abstract int getSubdualDamage ();
    public abstract void setHitPoints (int hp);
    public abstract void setCurrentHitPoints (int chp);
    public abstract void setSubdualDamage (int sd);

    //public abstract int computeAbilityModifier (String abilityDefinition);
    
    public abstract SavingThrowSet computeSavingThrowSet ();
    //public abstract int computeFortitudeModifier ();
    //public abstract int computeReflexModifier ();
    //public abstract int computeWillModifier ();

    public abstract int computeArmorClass ();
    public abstract int computeArmorClassVsTouchAttack ();

    public abstract int computeSkillModifier (String skillDefinition);

    public abstract int computeInitiativeModifier ();

    public abstract float computeChallengeRating ();

    public abstract Trigger fetchTriggerToApplyEachRound ()
	throws DshException;

    //
    // STATIC METHODS

    public static boolean isATemplate (Being b)
    {
	if (b instanceof burningbox.org.dsh.entities.Character) return false;

	if (b instanceof Monster) return false;

	return true;
    }

}
