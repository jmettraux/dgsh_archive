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
 * $Id: Skill.java,v 1.2 2002/06/26 10:02:36 jmettraux Exp $
 */

//
// Skill.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You will outgrow your usefulness.
// 

package burningbox.org.dsh.entities;

/**
 * The skills in DnD
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/06/26 10:02:36 $
 * <br>$Id: Skill.java,v 1.2 2002/06/26 10:02:36 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Skill

    implements Named

{

    //
    // CONSTANTS

    public final static String ALCHEMY            	= "alchemy";
    public final static String ANIMAL_EMPATHY     	= "animal empathy";
    public final static String APPRAISE           	= "appraise";
    public final static String BALANCE            	= "balance";
    public final static String BLUFF              	= "bluff";
    public final static String CLIMB              	= "climb";
    public final static String CONCENTRATION      	= "concentration";
    public final static String DECIPHER_SCRIPT    	= "decipher script";
    public final static String DIPLOMACY          	= "diplomacy";
    public final static String DISABLE_DEVICE     	= "disable device";
    public final static String DISGUISE           	= "disguise";
    public final static String ESCAPE_ARTIST      	= "escape artist";
    public final static String FORGERY            	= "forgery";
    public final static String GATHER_INFORMATION 	= "gather information";
    public final static String HANDLE_ANIMAL      	= "handle animal";
    public final static String HEAL               	= "heal";
    public final static String HIDE               	= "hide";
    public final static String INNUENDO           	= "innuendo";
    public final static String INTIMIDATE         	= "intimidate";
    public final static String INTUIT_DIRECTION   	= "intuit direction";
    public final static String JUMP               	= "jump	";
    public final static String KNOWLEDGE_ARCANA   	= "knowledge arcana";
    public final static String KNOWLEDGE_RELIGION 	= "knowledge religion";
    public final static String KNOWLEDGE_NATURE   	= "knowledge nature";
    public final static String LISTEN             	= "listen";
    public final static String MOVE_SILENTLY      	= "move silently";
    public final static String OPEN_LOCK          	= "open lock";
    public final static String PERFORM            	= "perform";
    public final static String PICK_POCKET        	= "pick pocket";
    public final static String READ_LIPS          	= "read lips";
    public final static String RIDE               	= "ride";
    public final static String SCRY               	= "scry";
    public final static String SEARCH             	= "search";
    public final static String SENSE_MOTIVE       	= "sense motive";
    public final static String SPELLCRAFT         	= "spellcraft";
    public final static String SPOT               	= "spot";
    public final static String SWIM               	= "swim";
    public final static String TUMBLE             	= "tumble";
    public final static String USE_MAGIC_DEVICE   	= "use magic device";
    public final static String USE_ROPE           	= "use rope";
    public final static String WILDERNESS_LORE    	= "wilderness lore";

    public final static int CRAFT		 	= 10042;
    public final static int PROFESSION	 		= 10043;

    //
    // FIELDS

    protected String name = null;
    protected String baseAbilityDefinition = null;

    protected boolean natural = false;
    protected boolean armorRestrained = false;

    //
    // CONSTRUCTORS

    public Skill ()
    {
    }

    //
    // METHODS

    //
    // BEAN METHODS

    public String getName ()
    {
	return this.name;
    }
    public String getBaseAbilityDefinition ()
    {
	return this.baseAbilityDefinition;
    }
    public boolean isNatural () 
    { 
	return this.natural; 
    }
    public boolean isArmorRestrained () 
    { 
	return this.armorRestrained; 
    }

    public void setName (String name)
    {
	this.name = name;
    }
    public void setBaseAbilityDefinition (String baseAbilityDefinition)
    {
	this.baseAbilityDefinition = baseAbilityDefinition;
    }
    public void setNatural (boolean natural)
    {
	this.natural = natural;
    }
    public void setArmorRestrained (boolean armorRestrained)
    {
	this.armorRestrained = armorRestrained;
    }

    //
    // STATIC METHODS
    
    public static void main (String[] args)
    {
	try
	{
	    BeanSet bs = new BeanSet();

	    Skill s = new Skill();
	    s.setName(ALCHEMY);
	    s.setBaseAbilityDefinition
		(burningbox.org.dsh.Definitions.INTELLIGENCE);
	    s.setNatural
		(false);
	    s.setArmorRestrained
		(false);
	    bs.add(s);

	    bs.save("skills.xml");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }

}
