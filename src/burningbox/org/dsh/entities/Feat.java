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
 * $Id: Feat.java,v 1.4 2002/08/26 16:25:00 jmettraux Exp $
 */

//
// Feat.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Noise proves nothing.  Often a hen who has merely laid an egg cackles
// as if she laid an asteroid.
// 		-- Mark Twain
// 

package burningbox.org.dsh.entities;

/**
 * A Feat...
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/26 16:25:00 $
 * <br>$Id: Feat.java,v 1.4 2002/08/26 16:25:00 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Feat

    implements 
	Named,
	MiscModified

{

    public final static String SIMPLE_WEAPON_PROFICIENCY
	= "simple weapon proficiency";

    public final static String MARTIAL_WEAPON_PROFICIENCY
	= "martial weapon proficiency";

    public final static String EXOTIC_WEAPON_PROFICIENCY
	= "exotic weapon proficiency";

    public final static String WEAPON_FOCUS
	= "weapon focus";

    //
    // FIELDS

    protected String name = null;
    protected String type = null;
    protected String source = "PHB";
    protected String prerequisite = null;
    protected String description = null;
    protected java.util.List requiredFeats = null;
    protected java.util.List miscModifiers = null;

    //
    // CONSTRUCTORS

    public Feat ()
    {
    }

    //
    // BEAN METHODS

    public String getName () { return this.name; }
    public String getType () { return this.type; }
    public String getSource () { return this.source; }
    public String getPrerequisite () { return this.prerequisite; }
    public String getDescription () { return this.description; }
    public java.util.List getRequiredFeats () { return this.requiredFeats; }
    public java.util.List getMiscModifiers () { return this.miscModifiers; }

    public void setName (String s) { this.name = s; }
    public void setType (String s) { this.type = s; }
    public void setSource (String s) { this.source = s; }
    public void setPrerequisite (String s) { this.prerequisite = s; }
    public void setDescription (String s) { this.description = s; }
    public void setRequiredFeats (java.util.List l) { this.requiredFeats = l; }
    public void setMiscModifiers (java.util.List l) { this.miscModifiers = l; }

    //
    // METHODS

    public int sumMiscModifiers (String definition)
    {
	return MiscModifier.sumModifiers(definition, this.miscModifiers);
    }

    //
    // STATIC METHODS

    public static boolean hasFeat (String featName, java.util.List featList)
    {
	java.util.Iterator it = featList.iterator();
	while (it.hasNext())
	{
	    Feat f = (Feat)it.next();

	    if (f.getName().toLowerCase().equals(featName.toLowerCase())) 
		return true;
	}
	return false;
    }

    public static int sumMiscModifiers 
	(String modifierDefinition, java.util.List featNameList)
    {
	int result = 0;
	java.util.Iterator it = featNameList.iterator();
	while (it.hasNext())
	{
	    String featName = (String)it.next();
	    Feat f = DataSets.findFeat(featName);

	    result += f.sumMiscModifiers(modifierDefinition);
	}
	return result;
    }

    /*
    public static void main (String[] args)
    {
	try
	{
	    BeanSet bs = new BeanSet();
	    Feat f = null;

	    f = new Feat();
	    f.setName("Alertness");
	    f.setPrerequisite("");
	    f.setDescription("Benefit: The character gets a +2 bonus on all Listen checks and Spot checks.\nSpecial: The master of a familiar gains the Alertness feat whenever the familiar is within arm's reach.");
	    java.util.List mm = new java.util.ArrayList(2);
	    mm.add(new MiscModifier(Skill.LISTEN, 2, "alertness : listen +2"));
	    mm.add(new MiscModifier(Skill.SPOT, 2, "alertness : spot +2"));
	    f.setMiscModifiers(mm);

	    bs.add(f);
	    bs.save("feats.xml");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    */

}
