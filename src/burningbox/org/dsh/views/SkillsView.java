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
 * $Id: SkillsView.java,v 1.8 2002/08/16 08:37:36 jmettraux Exp $
 */

//
// SkillsView.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Courage is resistance to fear, mastery of fear--not absence of fear.  Except 
// a creature be part coward it is not a compliment to say it is brave; it is 
// merely a loose misapplication of the word.  Consider the flea!--incomparably 
// the bravest of all the creatures of God, if ignorance of fear were courage. 
// Whether you are asleep or awake he will attack you, caring nothing for the 
// fact that in bulk and strength you are to him as are the massed armies of the
// earth to a sucking child; he lives both day and night and all days and nights
// in the very lap of peril and the immediate presence of death, and yet is no 
// more afraid than is the man who walks the streets of a city that was 
// threatened by an earthquake ten centuries before.  When we speak of Clive, 
// Nelson, and Putnam as men who "didn't know what fear was," we ought always to
// add the flea--and put him at the head of the procession.
// 		-- Mark Twain, "Pudd'nhead Wilson's Calendar"
// 

package burningbox.org.dsh.views;

import burningbox.org.dsh.*;
import burningbox.org.dsh.entities.*;


/**
 * The view used by the skills command
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/16 08:37:36 $
 * <br>$Id: SkillsView.java,v 1.8 2002/08/16 08:37:36 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SkillsView

    implements View

{

    //static org.apache.log4j.Logger log = org.apache.log4j.Logger
    //	.getInstance(SkillsView.class.getName());

    //
    // FIELDS

    protected Being being = null;

    //
    // CONSTRUCTORS

    public SkillsView (Being being)
    {
	this.being = being;
    }

    //
    // METHODS

    public StringBuffer process ()
    {
	StringBuffer sb = new StringBuffer();

	//
	// headers
	
	String[] sizes = new String[] 
	    { "l16", "r6", "l3", "r6", "r6", "r6", "r6", "r6" };
	String[] titles = new String[]
	    { "Skill", "ranks", "ab", "ab mod", "misc", "race", "feat", "" };

	Table table = new Table(sizes, titles);

	java.util.Iterator it = 
	    this.being.getSkills().iterator();
	while (it.hasNext())
	{
	    MonsterSkill mSkill = 
		(MonsterSkill)it.next();
	    String skillName = 
		mSkill.getSkillName();
	    //System.out.println("skillName >"+skillName+"<");
	    Skill skill =
		DataSets.findSkill(skillName);
	    String abilityDefinition =
		skill.getBaseAbilityDefinition();
	    int abilityModifier =
		this.being.computeAbilityModifier(abilityDefinition);
	    int miscModifier =
		this.being.sumMiscModifiers(skillName);
	    int featModifier =
		this.being.sumFeatModifiers(skillName);
	    int racialModifier = DataSets
		.computeRacialModifier(this.being.getRace(), skillName);

	    miscModifier -= featModifier;

	    String sRanks = "";
	    if (mSkill instanceof CharacterSkill)
		sRanks = ""+((CharacterSkill)mSkill).getRanks();

	    int sum = this.being.computeSkillModifier(skillName);
	    String sSum = ""+sum;
	    if (sum > -1) sSum = "+"+sum;

	    String[] data = null;

	    data = new String[]
	    {
		skillName,
		sRanks,
		abilityDefinition,
		//""+this.being.getAbilities().getModifier(abilityDefinition),
		""+abilityModifier,
		""+miscModifier,
		""+racialModifier,
		""+featModifier,
		sSum
	    };

	    table.addLine(data);
	}

	sb.append(table.render());

	return sb;
    }

}
