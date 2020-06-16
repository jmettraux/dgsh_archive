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
 * $Id: Utils.java,v 1.13 2002/09/12 06:30:36 jmettraux Exp $
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
// Stay away from flying saucers today.
// 

package burningbox.org.dsh;

/**
 * Some useful static methods
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/09/12 06:30:36 $
 * <br>$Id: Utils.java,v 1.13 2002/09/12 06:30:36 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public abstract class Utils
{

    static org.apache.log4j.Logger log =
	org.apache.log4j.Logger.getLogger("dgsh.Utils");


    private static java.util.Random generator = 
	new java.util.Random (System.currentTimeMillis());

    public static void resetRandomGenerator ()
    {
	generator =
	    new java.util.Random (System.currentTimeMillis());
    }

    public static int random (int minScore, int maxScore)
    {
	int delta = maxScore - minScore;

	if (delta < 0)
	{
	    delta = -delta;
	    minScore = maxScore;
	    maxScore = minScore + delta;
	}

	delta += 1;

	int random = generator.nextInt(delta);

	return minScore + random;
    }

    public static int throwDice (int dice)
    {
	return random (1, dice);
    }

    /**
     * like "6d4+2 + 1d8+1 + 3d10+3"
     */
    public static int throwSetOfDices (String setOfDices)
    {
	int result = 0;

	String[] dices = setOfDices.split(" + ");
	for (int i=0; i<dices.length; i++)
	{
	    result += throwDices(dices[i]);
	}

	return result;
    }

    /**
     * like "5d2+10"
     */
    public static int throwDices (String dices)
    {
	if (dices == null) return 0;

	int result = 0;

	dices = dices.trim().toLowerCase();

	int dIndex = dices.indexOf('d');

	int times = 1;
	if (dIndex > 0)
	{
	    String sTimes = dices.substring(0, dIndex);
	    times = Integer.parseInt(sTimes);
	}

	dices = dices.substring(dIndex+1);

	int plusIndex = dices.indexOf('+');
	int minusIndex = dices.indexOf('-');

	int bonusIndex = plusIndex;
	if (minusIndex > plusIndex)
	    bonusIndex = minusIndex;

	//System.out.println("dices=\""+dices+"\"");
	//System.out.println("bonusIndex="+bonusIndex);

	String bonus = "+0";
	if (bonusIndex > -1)
	{
	    bonus = dices.substring(bonusIndex);
	    dices = dices.substring(0, bonusIndex);
	}
	int dice = Integer.parseInt(dices);
	//System.out.println("dice=\""+dice+"\"");

	for (int i=0; i<times; i++)
	{
	    result += throwDice(dice);
	}

	//System.out.println("bonus=\""+bonus+"\"");
	if (bonus.charAt(0) == '+')
	    bonus = bonus.substring(1);
	result += Integer.parseInt(bonus);

	return result;
    }

    public static String rollValue (String value)
    {
	value = value.toLowerCase();

	//System.out.println("input = >"+value+"<");

	if (value.indexOf("d") < 0) return value;

	//
	// initial computing
	
	int xIndex = value.indexOf("x");
	int unitIndex = value.indexOf("p") - 1;

	//
	// find dices
	
	int i = xIndex;
	if (xIndex < 0) i = unitIndex;

	String sDices = value.substring(0, i);

	//System.out.println("sDices = >"+sDices+"<");
	
	//
	// find multiplier (if any)
	
	i++;
	
	String sMultiplier = "1";

	if ( i < unitIndex)
	    sMultiplier = value.substring(i, unitIndex).trim();

	//System.out.println("sMultiplier = >"+sMultiplier+"<");

	int multiplier = 1;
	try
	{
	    multiplier = Integer.parseInt(sMultiplier);
	}
	catch (NumberFormatException nfe)
	{
	    // ignore
	}
	
	//
	// find monetary unit (the rest)
	
	String monetaryUnit = value.substring(unitIndex);

	//System.out.println("monetaryUnit = >"+monetaryUnit+"<");
	
	// 
	// roll and reply
	
	int roll = throwDices(sDices);
	roll = roll * multiplier;

	return 
	    ""+roll+" "+monetaryUnit;
    }

    public static int throwD20 ()
    {
	return throwDice(20);
    }

    /*
    public static void main (String[] args)
    {
	String dice = null;

	System.out.println("");
	dice = "2d10+1";
	System.out.println(dice+" "+throwDices(dice));
	dice = "d20";
	System.out.println(dice+" "+throwDices(dice));
	dice = "d20";
	System.out.println(dice+" "+throwDices(dice));
	dice = "d20";
	System.out.println(dice+" "+throwDices(dice));
	dice = "d20";
	System.out.println(dice+" "+throwDices(dice));
	dice = "d20";
	System.out.println(dice+" "+throwDices(dice));
	dice = "d20";
	System.out.println(dice+" "+throwDices(dice));
	dice = "d20";
	System.out.println(dice+" "+throwDices(dice));
	dice = "d20+1";
	System.out.println(dice+" "+throwDices(dice));
	dice = "d20-1";
	System.out.println(dice+" "+throwDices(dice));
    }
    */

    //
    // SOME ARRAYS METHODS

    public static int[] addArrays (int[] original, int[] toAdd)
    {
	int[] result = new int[toAdd.length];

	for (int i=0; i<toAdd.length; i++)
	{
	    int sum = toAdd[i];
	    if (i < original.length)
		sum += original[i];
	    result[i] = sum;
	}

	return result;
    }

    public static int[] negateArray (int[] array)
    {
	int[] result = new int[array.length];
	for (int i=0; i<array.length; i++)
	{
	    result[i] = -array[i];
	}
	return result;
    }

    public static int[] addToWholeArray (int[] array, int number)
    {
	for (int i=0; i<array.length; i++)
	{
	    array[i] += number;
	}
	return array;
    }

    /**
     * returns array1 - array2
     */
    public static int[] computeDeltaArray (int[] array1, int[] array2)
    {
	// initial checking
	
	if (array1 == null || array2 == null) return new int[] {};

	/*
	if (array1 == null && array2 == null) return new int[] {};

	if (array1 == null) return array2;
	if (array2 == null) return array1;
	*/

	// now, do the job

	int l = array1.length;
	if (array2.length > l) l = array2.length;

	int[] result = new int[l];

	for (int i=0; i<l; i++)
	{
	    int value1 = 0;
	    int value2 = 0;

	    if (i < array1.length) value1 = array1[i];
	    if (i < array2.length) value2 = array2[i];

	    result[i] = value1 - value2;
	}

	return result;
    }

    /**
     * returns true if array is an array of zero
     */
    public static boolean isZeroed (int[] array)
    {
	for (int i=0; i<array.length; i++)
	{
	    if (array[i] != 0) return false;
	}
	return true;
    }

    //
    // SOME CHAR SEQUENCE METHODS
    
    public static int lastNewLineIndex (CharSequence seq)
    {
	return previousNewLineIndex (seq, seq.length()-1);
    }

    public static int previousNewLineIndex (CharSequence seq, int currentIndex)
    {
	if (seq.length() < 1) return -1;

	for (int i=currentIndex; i>=0; i--)
	{
	    if (seq.charAt(i) == '\n') return i;
	}
	return -1;
    }

    public static int newLineIndex (CharSequence seq)
    {
	return newLineIndex(seq, 0);
    }
    
    public static int newLineIndex (CharSequence seq, int startIndex)
    {
	for (int i=startIndex; i<seq.length(); i++)
	{
	    if (seq.charAt(i) == '\n') return i;
	}
	return -1;
    }

    //
    // XML INPUT and OUTPUT

    public static Object xmlLoad (String fileName) 
    {
	Object result = null;
	try
	{
	    java.io.BufferedInputStream in = 
		new java.io.BufferedInputStream
		    (new java.io.FileInputStream(fileName));
	    java.beans.XMLDecoder decoder =
		new java.beans.XMLDecoder(in);

	    result = decoder.readObject();

	    decoder.close();
	    in.close();
	}
	catch (Exception e)
	{
	    log.info("Failed to load object from file "+fileName, e);
	}

	return result;
    }

    public static void xmlSave (Object o, String fileName)
    {
	try
	{
	    java.io.BufferedOutputStream out =
		new java.io.BufferedOutputStream
		    (new java.io.FileOutputStream(fileName));
	    java.beans.XMLEncoder encoder =
		new java.beans.XMLEncoder(out);

	    encoder.writeObject(o);

	    encoder.close();
	    out.close();
	}
	catch (Exception e)
	{
	    log.info("Failed to save object in file "+fileName, e);
	}
    }

}
