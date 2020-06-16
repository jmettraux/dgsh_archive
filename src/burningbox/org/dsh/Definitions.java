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
 * $Id: Definitions.java,v 1.12 2002/09/04 19:15:56 jmettraux Exp $
 */

//
// Definitions.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// It's a very *__UN*lucky week in which to be took dead.
// 		-- Churchy La Femme
// 

package burningbox.org.dsh;

/**
 * Common vocabulary for the whole application
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/09/04 19:15:56 $
 * <br>$Id: Definitions.java,v 1.12 2002/09/04 19:15:56 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public interface Definitions
{

    public final static String DGSH_HOME		= "dgsh.home";

    //
    // ABILITIES

    public final static String STRENGTH			= "strength";
    public final static String CONSTITUTION		= "constitution";
    public final static String DEXTERITY		= "dexterity";
    public final static String INTELLIGENCE		= "intelligence";
    public final static String WISDOM			= "wisdom";
    public final static String CHARISMA			= "charisma";

    //
    // ALIGNMENTS

    public final static String NEUTRAL			= "neutral";
    public final static String GOOD			= "good";
    public final static String EVIL			= "evil";
    public final static String CHAOTIC			= "chaotic";
    public final static String LAWFUL			= "lawful";
    public final static String LAWFUL_GOOD		= "lawful good";
    public final static String LAWFUL_EVIL		= "lawful evil";
    public final static String CHAOTIC_GOOD		= "chaotic good";
    public final static String CHAOTIC_EVIL		= "chaotic evil";

    //
    // SAVING THROWS

    public final static String REFLEX 			= "reflex";
    public final static String WILL 			= "will";
    public final static String FORTITUDE 		= "fortitude";

    //
    // GENDER

    public final static String MALE			= "male";
    public final static String FEMALE			= "female";
    public final static String OTHER			= "other";

    //
    // ATTACKS

    public final static String ATTACK			= "attack";
	// for effects affecting any kind of attack
    public final static String MELEE			= "melee";
    public final static String RANGED			= "ranged";
    public final static String THROWN			= "thrown";
    public final static String DAMAGE			= "damage";

    //
    // WEAPONS

    //public final static String SIMPLE			= "simple";
    //public final static String MARTIAL		= "martial";
    //public final static String EXOTIC			= "exotic";

    //
    // ARMOR

    public final static String ARMOR_CLASS		= "armor class";
    public final static String ARMOR_PENALTY		= "armor penalty";
    public final static String ARMOR_SPELL_FAILURE	= "armor spell failure";
    public final static String ARMOR_MAX_DEX		= "armor max dex";

    public final static String LIGHT_ARMOR		= "light";
    public final static String MEDIUM_ARMOR		= "medium";
    public final static String HEAVY_ARMOR		= "heavy";

    //
    // REALLY MISC
    
    public final static String INITIATIVE		= "initiative";

    public final static String DOMAINS			= "domains";
	// for clerics
    public final static String SCHOOL			= "wizardry_school";
	// for specialist wizards

    //
    // LOAD QUALIFIERS

    public final static int LIGHT_LOAD			= 0;
    public final static int MEDIUM_LOAD			= 1;
    public final static int HEAVY_LOAD			= 2;
    public final static int UNBEARABLE_LOAD		= 3;

    //
    // FOR REGENERATING CREATURES

    public final static String HP_CEILING		= "hp.ceiling";

    //
    // ENV ATTRIBUTES
    
    public final static String DISPLAY_CHARACTER_SPECIALS
	= "display-character-specials";
    
    public final static String MASSIVE_DAMAGE_BASED_ON_SIZE
	= "massive_damage_based_on_size";

}
