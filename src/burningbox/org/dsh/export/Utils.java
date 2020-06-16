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
 * $Id: Utils.java,v 1.6 2002/08/31 06:41:25 jmettraux Exp $
 */

//
// Utils.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.export;

/**
 * Utility methods for parsing StringBuffers
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/31 06:41:25 $
 * <br>$Id: Utils.java,v 1.6 2002/08/31 06:41:25 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public abstract class Utils
{

    //
    // METHODS
    
    public static int parseInt (String s, int def)
    {
	if (s.charAt(0) == '+') s = s.substring(1);

	try
	{
	    return Integer.parseInt(s);
	}
	catch (NumberFormatException nfe)
	{
	    return def;
	}
    }

    public static int parsePreviousInt (String s, int index, int def)
    {
	String sub = s.substring(0, index);

	int i = sub.lastIndexOf(" ");

	if (i > -1) sub = sub.substring(i).trim();

	return parseInt(sub, def);
    }

    public static int[] parsePreviousIntArray (String s, int index, int[] def)
    {
	//System.out.println("parsePreviousIntArray().s is >"+s+"<");
	//System.out.println("index is "+index);

	String sub = s.substring(0, index);

	//System.out.println("parsePreviousIntArray().sub is >"+sub+"<");

	int i = sub.lastIndexOf(" ");

	if (i > -1) sub = sub.substring(i).trim();

	//System.out.println("parsePreviousIntArray().sub is >"+sub+"<");

	return parseIntArray(sub, def);
    }

    public static int[] parseIntArray (String s, int[] def)
    {
	//System.out.println("parseIntArray >"+s+"<");

	String[] splits = s.split("/");

	int[] result = new int[splits.length];

	for (int i=0; i<splits.length; i++)
	{
	    String smod = splits[i];
	    if (smod.charAt(0) == '+') smod = smod.substring(1);
	    try
	    {
		result[i] = Integer.parseInt(smod);
	    }
	    catch (NumberFormatException nfe)
	    {
		return def;
	    }
	}

	return result;
    }

    public static int[] parseFirstIntArray (String s, int[] def)
    {
	String sNumber = "";

	for (int i=0; i<s.length(); i++)
	{
	    char c = s.charAt(i);

	    if (c == '+') 
		continue;
	    else if (c == '-' || c == '/' || Character.isDigit(c)) 
		sNumber += c;
	    else 
		break;
	}

	try
	{
	    return parseIntArray(sNumber, def);
	}
	catch (NumberFormatException nfe)
	{
	    return def;
	}
    }

    public static int parseFirstInt (String s, int def)
    {
	String sNumber = "";

	for (int i=0; i<s.length(); i++)
	{
	    char c = s.charAt(i);

	    if (c == '+') 
		continue;
	    else if (c == '-' || Character.isDigit(c)) 
		sNumber += c;
	    else 
		break;
	}

	try
	{
	    return Integer.parseInt(sNumber);
	}
	catch (NumberFormatException nfe)
	{
	    return def;
	}
    }

    public static int parseLastInt (String s, int def)
    {
	String sNumber = "";

	for (int i=s.length()-1; i>=0; i--)
	{
	    char c = s.charAt(i);

	    if (c == '+') 
		break;
	    else if (c == '-' || Character.isDigit(c)) 
		sNumber = c + sNumber;
	    else 
		break;
	}

	try
	{
	    return Integer.parseInt(sNumber);
	}
	catch (NumberFormatException nfe)
	{
	    return def;
	}
    }

    public static boolean containsDigit (String text)
    {
	for (int i=0; i<text.length(); i++)
	{
	    char c = text.charAt(i);
	    if (Character.isDigit(c)) return true;
	}
	return false;
    }

    public static int indexOfFirstDigit (String text)
    {
	for (int i=0; i<text.length(); i++)
	{
	    char c = text.charAt(i);

	    if (Character.isDigit(c)) return i;
	}
	return -1;
    }

    /**
     * This method is intended to read things like "male dwarf, Ftr2/Rgr5"
     * and return the index of 'F'
     */
    public static int indexOfFirstCharacterClass (String text)
    {
	//System.out.println("looking for classes in >"+text+"<");

	int i = indexOfFirstDigit(text);

	String subText = text.substring(0, i);

	//
	// this should work because fully written PrC like 'dwarven defender1' 
	// are usually preceded by common classes (not commoners...)
	
	int result = subText.lastIndexOf(" ");

	if (result < 0) return 0;

	return result;
    }

    public static String removeModifier (String dice)
    {
	String[] items = null;

	if (dice.indexOf("+") > dice.indexOf("-"))
	    items = dice.split("\\+");
	else
	    items = dice.split("-");

	return items[0];
    }

}
