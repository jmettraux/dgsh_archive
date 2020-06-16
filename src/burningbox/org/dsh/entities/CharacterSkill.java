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
 * $Id: CharacterSkill.java,v 1.7 2002/08/16 08:37:36 jmettraux Exp $
 */

//
// CharacterSkill.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Try to relax and enjoy the crisis.
// 		-- Ashleigh Brilliant
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.entities.equipment.Equipment;


/**
 * Skills for characters or monsters with classes
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/16 08:37:36 $
 * <br>$Id: CharacterSkill.java,v 1.7 2002/08/16 08:37:36 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class CharacterSkill

    extends MonsterSkill

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger(CharacterSkill.class.getName());

    //
    // FIELDS

    protected int ranks = 0;

    //
    // CONSTRUCTORS

    public CharacterSkill ()
    {
	super();
    }

    public CharacterSkill
	(String skillName,
	 //int modifier, // racial modifier ?
	 int ranks)
    {
	super(skillName, 0); // modifier is set to 0
	this.ranks = ranks;
    }

    //
    // BEAN METHODS
    
    public int getRanks () { return this.ranks; }

    public void setRanks (int r) { this.ranks = r; }

    //
    // METHODS

    public int computeModifier 
	(burningbox.org.dsh.entities.Character c/*,
	 java.util.List miscModifiers,
	 Equipment equipment*/)
    {

	//
	// fetch info
	
	Skill skill = DataSets
	    .findSkill(this.skillName);

	if (skill == null) return 0; // unknown skill

	//int abilityModifier = 
	//    abilities.getModifier(skill.getBaseAbilityDefinition());
	int abilityModifier = 
	    c.computeAbilityModifier(skill.getBaseAbilityDefinition());

	//int miscModifierSum = MiscModifier.sumModifiers
	//    (skill.getName(),
	//     miscModifiers);
	int equipmentModifierSum = 
	    c.getEquipment().sumMiscModifiers(skill.getName());

	//
	// compute
	
	int result = 
	    abilityModifier + 
	    //miscModifierSum + 
	    equipmentModifierSum +
	    this.modifier + 
	    this.ranks;

	if (skill.isArmorRestrained())
	{
	    result += c.getEquipment().sumArmorPenalty();
	}

	return result;
    }

}
