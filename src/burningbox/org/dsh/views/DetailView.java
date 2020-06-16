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
 * $Id: DetailView.java,v 1.31 2002/11/11 10:40:30 jmettraux Exp $
 */

//
// DetailView.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You own a dog, but you can only feed a cat.
//

package burningbox.org.dsh.views;

import burningbox.org.dsh.*;
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.entities.equipment.Weapon;
import burningbox.org.dsh.entities.equipment.MeleeWeapon;
import burningbox.org.dsh.entities.equipment.RangedWeapon;
import burningbox.org.dsh.entities.equipment.EquipmentPiece;
import burningbox.org.dsh.entities.equipment.MeleeOrRangedWeapon;
import burningbox.org.dsh.views.utils.*;


/**
 * This view is used by the next command when the turn passed to a being
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/11/11 10:40:30 $
 * <br>$Id: DetailView.java,v 1.31 2002/11/11 10:40:30 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class DetailView

    implements View

{

    //static org.apache.log4j.Logger log = org.apache.log4j.Logger
    //	.getInstance(DetailView.class.getName());

    //
    // FIELDS

    protected Being being = null;
    protected burningbox.org.dsh.entities.Character character = null;
    protected Monster monster = null;
    protected MonsterTemplate template = null;
    protected boolean fileOutput = false;

    //
    // CONSTRUCTORS

    public DetailView (Being being)
    {
	this.being = being;

	if (this.being instanceof burningbox.org.dsh.entities.Character)
	{
	    this.character = (burningbox.org.dsh.entities.Character)this.being;
	    return;
	}

	if (this.being instanceof Monster)
	{
	    this.monster = (Monster)this.being;
	    return;
	}

	this.template = (MonsterTemplate)this.being;
    }

    //
    // METHODS

    public void setFileOutput (boolean fileOutput)
    {
	this.fileOutput = fileOutput;
    }

    private String displayPlayerName ()
    {
	if (this.character != null)
	    return this.character.getPlayer();
	return "DM";
    }

    private String displayHitPoints ()
    {
	if (this.template != null)
	    return "hp   "+this.template.getPotentialHitPoints();

	int current = this.being.getCurrentHitPoints();
	int max = this.being.getHitPoints();
	int sub = this.being.getSubdualDamage();

	StringBuffer sb = new StringBuffer();

	sb.append("hp ");
	sb.append(Utils.hpFormat(current));
	sb.append('/');
	sb.append(Utils.hpFormat(max));
	if (sub > 0)
	{
	    sb.append(" (");
	    sb.append(sub);
	    sb.append(" S)");
	}

	if (current > 0)
	{
	    if (sub == current)
	    {
		sb.append("  fainting...");
	    }
	    else if (sub > current)
	    {
		sb.append("  Knocked Out");
	    }
	    else
	    {
		sb.append("  OK");
	    }
	}
	else
	{
	    if (this.character != null)
	    {
		if (current == 0)
		{
		    sb.append("  poor state...");
		}
		else if (current <= -10)
		{
		    sb.append("  DEAD.");
		}
		else
		{
		    sb.append("  Dying...");
		}
	    }
	    else
	    {
		sb.append("  DEAD.");
	    }
	}
	

	return sb.toString();
    }

    private String displayArmorClass ()
    {
	StringBuffer sb = new StringBuffer();
	
	sb.append("AC   ");
	sb.append(this.being.computeArmorClass());
	sb.append("\nflt  ");
	sb.append(this.being.computeArmorClassFlatFooted());
	sb.append("\ntch  ");
	sb.append(this.being.computeArmorClassVsTouchAttack());

	if (this.character != null)
	{
	    sb.append("\npen ");
	    sb.append(Utils.formatModifier
		(this.being.getEquipment().sumArmorPenalty(), 3));
	    sb.append("\nspl ");
	    sb.append(this.being.getEquipment().sumArmorSpellFail());
	    sb.append('%');
	    sb.append("\nmdx ");
	    sb.append(Utils.formatModifier
		(this.being.getEquipment().computeArmorMaxDexModifier(), 3));
	}

	return sb.toString();
    }

    private String displayBaseAttackModifiers ()
    {
	if (this.character == null) return "";

	return 
	    "base   "+
	    Utils.formatModifiers
		(this.character.computeBaseAttackModifier(), 3);
    }

    private String displayMeleeAttackModifiers ()
    {
	if (this.character == null) return "";

	return 
	    "melee  "+
	    Utils.formatModifiers
		(this.character.computeMeleeAttackModifier(), 3);
    }

    private String displayRangedAttackModifiers ()
    {
	if (this.character == null) return "";

	return 
	    "ranged "+
	    Utils.formatModifiers
		(this.character.computeRangedAttackModifier(), 3);
    }

    private String displayAllAttackModifiers ()
    {
	StringBuffer sb = new StringBuffer();

	sb.append(displayBaseAttackModifiers());
	sb.append('\n');
	sb.append(displayMeleeAttackModifiers());
	sb.append('\n');
	sb.append(displayRangedAttackModifiers());

	return sb.toString();
    }

    private void displayAbility (StringBuffer sb, String abilityDefinition)
    {
	int score = this.being.getAbilities().getScore(abilityDefinition);
	int delta = this.being.computeAbilityDelta(abilityDefinition);
	int mod   = this.being.computeAbilityModifier(abilityDefinition);

	sb.append(Utils.format(""+score, 2, true));
	if (delta == 0)
	{
	    sb.append("  ");
	}
	else
	{
	    sb.append(Utils.formatModifier(delta, 2));
	}
	sb.append(' ');
	sb.append(Utils.formatModifier(mod, 3));
    }

    private String displayAbilities ()
    {
	Abilities abilities = this.being.getAbilities();

	StringBuffer sb = new StringBuffer();

	sb.append("str  ");
	displayAbility(sb, Definitions.STRENGTH);
	sb.append('\n');

	sb.append("dex  ");
	displayAbility(sb, Definitions.DEXTERITY);
	sb.append('\n');

	sb.append("con  ");
	displayAbility(sb, Definitions.CONSTITUTION);
	sb.append('\n');

	sb.append("int  ");
	displayAbility(sb, Definitions.INTELLIGENCE);
	sb.append('\n');

	sb.append("wis  ");
	displayAbility(sb, Definitions.WISDOM);
	sb.append('\n');

	sb.append("cha  ");
	displayAbility(sb, Definitions.CHARISMA);

	return sb.toString();
    }

    public String displaySavingThrows ()
    {
	SavingThrowSet set = this.being.computeSavingThrowSet();

	StringBuffer sb = new StringBuffer();

	sb.append("fort ");
	sb.append(Utils.formatModifier(set.getFortitudeModifier(), 3));
	sb.append('\n');
	sb.append("refl ");
	sb.append(Utils.formatModifier(set.getReflexModifier(), 3));
	sb.append('\n');
	sb.append("will ");
	sb.append(Utils.formatModifier(set.getWillModifier(), 3));

	return sb.toString();
    }

    private String displaySkills ()
    {
	if (this.being.getSkills() == null)
	    return "";

	StringBuffer sb = new StringBuffer();

	java.util.Iterator it = this.being.getSkills().iterator();
	while (it.hasNext())
	{
	    MonsterSkill ms = (MonsterSkill)it.next();
	    int mod = this.being.computeSkillModifier(ms.getSkillName());
	    sb.append(Utils.format(ms.getSkillName(), 20));
	    sb.append(' ');
	    sb.append(Utils.formatModifier(mod, 3));

	    if (it.hasNext())
		sb.append('\n');
	}

	return sb.toString();
    }

    private String displayFeats ()
    {
	if (this.being.getFeats() == null)
	    return "";

	StringBuffer sb = new StringBuffer();

	java.util.Iterator it = this.being.getFeats().iterator();
	while (it.hasNext())
	{
	    FeatInstance fi = (FeatInstance)it.next();
	    sb.append(fi.getFeatName());

	    if (fi.getBeneficiaryDefinition() != null)
	    {
		sb.append(" (");
		sb.append(fi.getBeneficiaryDefinition());
		sb.append(' ');
		sb.append(Utils.formatModifier(fi.getModifier(), 3));
		sb.append(')');
	    }

	    if (it.hasNext())
		sb.append('\n');
	}

	return sb.toString();
    }

    private String displayInitiative ()
    {
	int mod = this.being.computeInitiativeModifier();
	int ci = this.being.getCurrentInitiative();

	StringBuffer sb = new StringBuffer();
	sb.append("init mod ");
	sb.append(Utils.formatModifier(mod, 3));

	if (this.template == null && CombatSession.isCombatGoingOn())
	{
	    sb.append("  ini ");
	    sb.append(ci);
	}

	return sb.toString();
    }

    // 
    // display attacks
    
    private String displayAttack (Attack att)
    {
	StringBuffer sb = new StringBuffer();

	sb.append(Utils.format(att.getName(), 15));
	sb.append(Utils.formatModifiers(att.computeAttackModifier(), 3));
	sb.append("   ");
	sb.append(Utils.format(att.computeDamage(), 9));
	sb.append(Utils.format(att.getCritical(), 9));
	sb.append(Utils.format(att.getAttackType(), 7));
	sb.append(Utils.format(att.getDescription(), 25));

	return sb.toString();
    }

    private String displayMonsterAttacks ()
    {
	StringBuffer sb = new StringBuffer();

	java.util.Iterator it = null;
	if (this.monster != null)
	{
	    if (this.monster.getAttacks() == null) return "";
	    it = this.monster.getAttacks().iterator();
	}
	else
	{
	    if (this.template.getAttacks() == null) return "";
	    it = this.template.getAttacks().iterator();
	}
	while (it.hasNext())
	{
	    Attack att = (Attack)it.next();

	    /* TRICK */
	    if (this.monster != null)
	    	att.setOwner(this.monster);
	    /* not nice ! */

	    sb.append(displayAttack(att));

	    if (it.hasNext())
		sb.append('\n');
	}

	return sb.toString();
    }

    private String displayCharacterAttacks ()
    {
	java.util.List weapons = 
	    this.character.getEquipment().findHeldWeapons();

	StringBuffer sb = new StringBuffer();

	java.util.Iterator it = weapons.iterator();
	Weapon w = null;
	while (it.hasNext() || w != null)
	{
	    if (w == null) w = (Weapon)it.next();

	    int[] attackModifiers = this.character.computeAttackModifier(w);

	    Attack att = new Attack();
	    att.setName(w.getName());
	    att.setAttackModifier(attackModifiers);
	    att.setDamage(w.getDamages()+" "+Utils.formatModifier
		(this.character.computeDamageModifier(w), 3));
	    att.setCritical(w.getCritical());
	    // TODO : code for 'improved critical...'

	    //
	    // attack type
	    if (w instanceof MeleeOrRangedWeapon)
	    {
		if ( ! ((MeleeOrRangedWeapon)w).viewAsMeleeWeapon())
		{
		    //att.setAttackType(Definitions.RANGED);
		    att.setAttackType(""+((MeleeOrRangedWeapon)w)
			.getRangeIncrement()+"ft");
		}
	    }
	    else if (w instanceof RangedWeapon)
	    {
		//att.setAttackType(Definitions.RANGED);
		att.setAttackType(""+((RangedWeapon)w)
		    .getRangeIncrement()+"ft");
	    }
	    
	    att.setDescription(w.getDescription());

	    //
	    // next iteration ?
	    if (w instanceof MeleeOrRangedWeapon &&
		((MeleeOrRangedWeapon)w).viewAsMeleeWeapon())
	    {
		((MeleeOrRangedWeapon)w)
		    .setView(MeleeOrRangedWeapon.VIEW_AS_RANGED_WEAPON);
	    }
	    else if (w instanceof MeleeOrRangedWeapon &&
		( ! ((MeleeOrRangedWeapon)w).viewAsMeleeWeapon()))
	    {
		((MeleeOrRangedWeapon)w)
		    .setView(MeleeOrRangedWeapon.VIEW_AS_MELEE_WEAPON);
		w = null;
	    }
	    else
	    {
		w = null;
	    }

	    //
	    // display
	    sb.append(displayAttack(att));

	    if (it.hasNext() || w != null)
		sb.append('\n');
	}

	return sb.toString();
    }

    private String displayAttacks ()
    {
	if (this.character != null)
	    return displayCharacterAttacks();
	else
	    return displayMonsterAttacks();
    }

    private String displayWornEquipment ()
    {
	StringBuffer sb = new StringBuffer();

	java.util.Iterator it  =
	    this.being.getEquipment().getWornEquipment().iterator();
	while (it.hasNext())
	{
	    EquipmentPiece ep = (EquipmentPiece)it.next();

	    //sb.append(Utils.format(ep.getName(), 10));
	    //sb.append(' ');
	    sb.append(Utils.format(ep.getDescription(), 18));

	    if (it.hasNext())
		sb.append('\n');
	}

	return sb.toString();
    }

    private String displayEffects ()
    {
	StringBuffer sb = new StringBuffer();

	java.util.Iterator it = this.being.effectIterator();
	while (it.hasNext())
	{
	    Effect fx = (Effect)it.next();
	    sb.append("fx: ");
	    sb.append(Utils.format(fx.getName(), 15));
	    sb.append(Utils.format(""+fx.getRoundsToGo(), 4, true));
	    if (fx.getRoundsToGo() > 1)
		sb.append(" rnds  ");
	    else
		sb.append(" rnd   ");
	    sb.append(Utils.format(fx.getDescription(), 59));
	    
	    java.util.Iterator mit = fx.modifierIterator();
	    while (mit != null && mit.hasNext())
	    {
		sb.append("\n    ");
		MiscModifier mm = (MiscModifier)mit.next();
		sb.append(Utils.format(mm.getDefinition(), 20));
		sb.append(Utils.formatModifier(mm.getModifier(), 3));
	    }

	    if (it.hasNext()) sb.append('\n');
	}

	return sb.toString();
    }

    /*
     * PROCESS()
     */
    public StringBuffer process ()
    {
	StringBuffer sb = new StringBuffer();

	//sb.append('\n');

	Line l = null;

	MultiTable table = new MultiTable(80);
	table.setFileOutput(this.fileOutput);

	//
	// 1st line
	l = new Line();
	l.addCell(new Cell(15, Cell.ALIGN_LEFT, this.being.getName().toUpperCase()));
	l.addCell(new Cell(15, Cell.ALIGN_LEFT, displayPlayerName()));
	l.addCell(new Cell(15, Cell.ALIGN_LEFT, this.being.getAlignment()));
	l.addCell(new Cell(10, Cell.ALIGN_LEFT, this.being.getRaceName()));
	l.addCell(new Cell(5, Cell.ALIGN_LEFT, Utils.displayClasses(this.character)));
	table.addLine(l);

	//
	// monster line (1b)
	if (this.being instanceof MonsterTemplate)
	{
	    l = new Line();
	    l.addCell(new Cell(45, Cell.ALIGN_LEFT, ((MonsterTemplate)this.being).getCategory()));
	    l.addCell(new Cell(28, Cell.ALIGN_LEFT, "tres: "+((MonsterTemplate)this.being).getTreasure()));
	    table.addLine(l);
	}

	//
	// 2nd line
	l = new Line();
	l.addCell(new Cell(34, Cell.ALIGN_LEFT, displayHitPoints()));
	l.addCell(new Cell(21, Cell.ALIGN_LEFT, displayInitiative()));
	l.addCell(new Cell(15, Cell.ALIGN_LEFT, this.being.getGender()));
	table.addLine(l);

	//
	// line 2b
	l = new Line();
	if (CombatSession.initiativeGotRolled())
	{
	    l.addCell(new Cell(8, Cell.ALIGN_LEFT, "AoOl: "+this.being.getOpportunitiesLeft()));
	}
	l.addCell(new Cell(50, Cell.ALIGN_LEFT, this.being.getSpeeds()));
	table.addLine(l);

	//
	// 3rd line
	l = new Line();
	l.addCell(new Cell(14, Cell.ALIGN_LEFT, displayAbilities()));
	l.addCell(new Cell(8, Cell.ALIGN_LEFT, displaySavingThrows()));
	l.addCell(new Cell(7, Cell.ALIGN_LEFT, displayArmorClass()));
	l.addCell(new Cell(22, Cell.ALIGN_LEFT, displayAllAttackModifiers()));
	l.addCell(new Cell(13, Cell.ALIGN_LEFT, displayWornEquipment()));
	table.addLine(l);

	//
	// 4th line
	l = new Line();
	l.addCell(new Cell(73, Cell.ALIGN_LEFT, displayAttacks()));
	table.addLine(l);

	if (this.being instanceof MonsterTemplate)
	{
	    //
	    // 4.5th line
	    l = new Line();
	    l.addCell(new Cell(73, Cell.ALIGN_LEFT, ((MonsterTemplate)this.being).getAttackRoutine()));
	    table.addLine(l);
	}

	//
	// 5th line
	l = new Line();
	l.addCell(new Cell(24, Cell.ALIGN_LEFT, displaySkills()));
	l.addCell(new Cell(40, Cell.ALIGN_LEFT, displayFeats()));
	table.addLine(l);

	if (this.character != null &&
	    this.character.isASpellCaster())
	{
	    l = new Line();
	    l.addCell(new Cell(76, Cell.ALIGN_LEFT, Utils.displaySpellSlots(this.character.getSpellSlots(), null)));
	    table.addLine(l);
	}

	//
	// the 'specials' line
	
	boolean displayCharacterSpecials = true;

	String sDisplaySpecials = GameSession.getInstance()
	    .getEnvAttributeValue(Definitions.DISPLAY_CHARACTER_SPECIALS);

	if (sDisplaySpecials  != null) 
	{
	    displayCharacterSpecials = 
		sDisplaySpecials.toLowerCase().equals("true");
	}

	if (this.being instanceof MonsterTemplate ||
	    (this.being instanceof burningbox.org.dsh.entities.Character &&
	     displayCharacterSpecials))
	{
	    java.util.Iterator spit = this.being.specialIterator();
	    if (spit != null && spit.hasNext())
	    {
		l = new Line();
		l.addCell(new Cell(76, Cell.ALIGN_LEFT, Utils.displaySpecials(spit)));
		table.addLine(l);
	    }
	}

	//
	// the effect line
	
	if (this.being.isSubjectToAnyEffect())
	{ 
	    l = new Line();
	    l.addCell(new Cell(76, Cell.ALIGN_LEFT, displayEffects()));
	    table.addLine(l);
	}

	sb.append(table.render());

	//sb.append('\n');

	return sb;
    }

}
