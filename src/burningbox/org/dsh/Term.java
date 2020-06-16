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
 * $Id: Term.java,v 1.9 2002/07/29 09:57:46 jmettraux Exp $
 */

//
// Term.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// It is so very hard to be an 
// on-your-own-take-care-of-yourself-because-there-is-no-one-else-to-do-it-for-
// you
// grown-up.
// 

package burningbox.org.dsh;

import burningbox.org.dsh.views.View;


/**
 * An abstraction for the input/output. Something really static
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/29 09:57:46 $
 * <br>$Id: Term.java,v 1.9 2002/07/29 09:57:46 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public abstract class Term
{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.Term");

    //
    // FIELDS

    protected static java.io.BufferedReader in =
	new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

    protected static int linesDisplayed = 0;
    protected static int screenLines 
	= Integer.getInteger("dsh.term.lines", 25).intValue();

    //
    // CONSTRUCTORS

    public Term ()
    {
    }

    //
    // METHODS
    
    //
    // STATIC METHODS

    public static void setScreenLines (int lineCount)
    {
	screenLines = lineCount;
    }

    public static String readLine ()
	throws java.io.IOException
    {
	return in.readLine();
    }

    /**
     * Echoes a simple new line
     */
    public static void echon ()
    {
	System.out.println();
	linesDisplayed++;
    }

    public static void echo (View v)
    {
	echo(0, false, v.process());
    }

    private static void echo 
	(int beginIndex, boolean noMoreNewLines, CharSequence message)
    {
	if (linesDisplayed >= screenLines)
	{
	    System.out.print("  --more--");
	    try
	    {
		readLine();
	    }
	    catch (java.io.IOException ie)
	    {
	    }
	    linesDisplayed = 0;
	}

	int length = message.length() - beginIndex;

	if (length < 1) return;

	int newLineIndex = -1;

	if (noMoreNewLines)
	    newLineIndex = -1;
	else
	    newLineIndex = Utils.newLineIndex(message, beginIndex);

	int distance = -1;
	if (newLineIndex > -1)
	    distance = newLineIndex - beginIndex;
	else
	    noMoreNewLines = true;

	/*
	System.out.println("beginIndex = "+beginIndex);
	System.out.println("noMoreNewLines = "+noMoreNewLines);
	System.out.println("length = "+length);
	System.out.println("newLineIndex = "+newLineIndex);
	System.out.println("distance = "+distance);
	*/

	//
	// display !

	if (distance > -1 && distance <= 80)
	    // 
	    // there is a new line 
	{
	    System.out.print(message.subSequence(beginIndex, newLineIndex+1));
	    linesDisplayed++;
	    echo(newLineIndex+1, noMoreNewLines, message);
	}
	else
	    //
	    // no new lines for the moment
	{
	    if (length <= 80)
	    {
		System.out.print
		    (message.subSequence(beginIndex, message.length()));
	    }
	    else
	    {
		System.out.print
		    (message.subSequence(beginIndex, beginIndex+80));
		linesDisplayed++;
		echo(beginIndex+80, noMoreNewLines, message);
	    }
	}
    }

    public static void echo (CharSequence message)
    {
	echo (0, false, message);
    }

    public static String ask (String valueName)
	throws java.io.IOException
    {
	echo("    "+valueName+": ");
	return readLine();
    }

    public static void reset ()
    {
	linesDisplayed = 0;
    }

    public static void close ()
	throws java.io.IOException
    {
	in.close();
    }

}
