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
 * $Id: Utils.java,v 1.12 2002/08/27 06:53:37 jmettraux Exp $
 */

//
// Utils.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Q:	How does a hacker fix a function which
// 	doesn't work for all of the elements in its domain?
// A:	He changes the domain.
// 

package burningbox.org.dsh.views;

import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.entities.Special;
import burningbox.org.dsh.entities.ClassLevel;
import burningbox.org.dsh.entities.CharacterClass;
import burningbox.org.dsh.entities.magic.SpellSlots;


/**
 * Output utilities
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/27 06:53:37 $
 * <br>$Id: Utils.java,v 1.12 2002/08/27 06:53:37 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public abstract class Utils
{

    static org.apache.log4j.Logger log =
	org.apache.log4j.Logger.getLogger("dgsh.views.Utils");

    //
    // METHODS

    public static String padString (int desiredLength)
    {
	StringBuffer sb = new StringBuffer();

	for (int i=0; i<desiredLength; i++)
	{
	    sb.append(' ');
	}

	return sb.toString();
    }

    public static String padMinus (int desiredLength)
    {
	StringBuffer sb = new StringBuffer();

	for (int i=0; i<desiredLength; i++)
	{
	    sb.append('-');
	}

	return sb.toString();
    }

    public static String format 
	(String input, int desiredLength, boolean padOnLeft)
    {
	if (input == null)
	    return padString(desiredLength);

	if (input.length() == desiredLength)
	    return input;

	if (input.length() > desiredLength)
	    return input.substring(0, desiredLength);

	String pad = padString(desiredLength - input.length());

	if (padOnLeft)
	    return pad + input;

	return input + pad;
    }

    public static String format
	(String input, int desiredLength)
    {
	return format(input, desiredLength, false);
    }

    public static String hpFormat
	(int hitPoints)
    {
	return format(""+hitPoints, 3, true);
    }

    public static String displayClasses 
	(burningbox.org.dsh.entities.Character c)
    {
	if (c == null) return "";

	StringBuffer sb = new StringBuffer();

	java.util.Iterator it = c.getClasses().iterator();
	while (it.hasNext())
	{
	    ClassLevel cl = (ClassLevel)it.next();
	    sb.append(cl.getClassName());
	    sb.append(cl.getLevelReached());

	    if (it.hasNext())
		sb.append(", \n");
	}

	return sb.toString();
    }

    public static String formatModifier (int mod, int cols)
    {
	StringBuffer sb = new StringBuffer();

	if (mod >= 0) 
	    sb.append('+');
	sb.append(mod);

	String result = sb.toString();

	int pad = cols - result.length();

	if (pad <= 0)
	    return result;

	return (padString(pad) + result);
    }

    public static String formatModifiers (int[] mods, int colsPerMod)
    {
	StringBuffer sb = new StringBuffer();

	for (int i=0; i<mods.length; i++)
	{
	    sb.append(formatModifier(mods[i], colsPerMod));

	    if (i < mods.length-1)
		sb.append('/');
	}

	return sb.toString();
    }

    private static void mlFormat (StringBuffer result, String text, int cols)
    {
	if (text.length() < cols)
	{
	    result.append(text);
	    return;
	}

	result.append(text.substring(0, cols));
	result.append('\n');
	mlFormat(result, text.substring(cols), cols);
    }

    public static String multiLineFormat (String text, int cols)
    {
	//text = text.replace('\n', ' ');
	StringBuffer result = new StringBuffer();

	mlFormat(result, text, cols);

	return result.toString();
    }

    /**
     * Displays spell slots.
     * If className is not null, this method will only display slots for this
     * class.
     */
    public static String displaySpellSlots 
	(SpellSlots slots, 
	 CharacterClass cClass)
    {
	StringBuffer sb = new StringBuffer();

	sb.append("           lvl ");
	for (int i=0; i<=9; i++)
	{
	    sb.append(Utils.format(""+i, 2, true));

	    if (i < 9) sb.append("   ");
	}
	sb.append('\n');

	java.util.Iterator it = slots.classIterator();
	while (it.hasNext())
	{
	    String slotClassName = (String)it.next();
	    //slotClassName = slotClassName.trim();

	    //log.debug("slotClassName is >"+slotClassName+"<");

	    if (cClass != null)
	    {
		if ( ! (slotClassName.equals(Definitions.DOMAINS+"_"+cClass.getName()) || slotClassName.equals(cClass.getName())))
		{
		    continue;
		}
	    }

	    int[] sl = slots.getSlots(slotClassName);

	    sb.append("           ");

	    if (slotClassName.startsWith(Definitions.DOMAINS))
	    {
		sb.append("Dom ");
	    }
	    else
	    {
		sb.append(Utils.format(slotClassName, 4));
	    }

	    for (int i=0; i<sl.length; i++)
	    {
		if (sl[i] > 0)
		    sb.append(Utils.format(""+sl[i], 2, true));
		else
		    sb.append("--");

		if (i < sl.length-1)
		    sb.append(" / ");
	    }

	    if (it.hasNext())
		sb.append('\n');
	}

	return sb.toString();
    }

    /*
    public static String displaySpellSlots (int[] freeSlots, String className)
    {
	className = className.trim();

	SpellSlots slots = new SpellSlots();
	java.util.Map slotMap = new java.util.HashMap();
	slotMap.put(className, freeSlots);
	slots.setSlotsPerClass(slotMap);

	return displaySpellSlots(slots, className);
    }
    */

    public static String displaySpecials (java.util.Iterator specialIterator)
    {
	StringBuffer sb = new StringBuffer();

	while (specialIterator.hasNext())
	{
	    Special special = (Special)specialIterator.next();

	    String name = special.getName();
	    switch (special.getType())
	    {
		case Special.TYPE_SU :
		    name += " (Su)"; break;
		case Special.TYPE_EX :
		    name += " (Ex)"; break;
		case Special.TYPE_SP :
		    name += " (Sp)"; break;
		//default :
		//    name += "(?): ";
	    }

	    sb.append("     - ");
	    sb.append(name);

	    if (special.getDescription() != null &&
		special.getDescription().trim().length() > 0)
	    {
		sb.append(" : \n");
		sb.append(multiLineFormat(special.getDescription(), 76));
	    }

	    if (special.getTriggerName() != null)
	    {
		sb.append("\n  trigger : ");
		sb.append(special.getTriggerName());
	    }

	    if (specialIterator.hasNext())
		sb.append("\n");
	}

	return sb.toString();
    }

}
