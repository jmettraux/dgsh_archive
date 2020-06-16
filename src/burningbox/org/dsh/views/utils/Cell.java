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
 * $Id: Cell.java,v 1.4 2002/07/29 06:51:10 jmettraux Exp $
 */

//
// Cell.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// There's an old proverb that says just about whatever you want it to.
// 

package burningbox.org.dsh.views.utils;

/**
 * A Cell for a MultiTable
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/29 06:51:10 $
 * <br>$Id: Cell.java,v 1.4 2002/07/29 06:51:10 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Cell
{

    //
    // CONSTANTS

    public final static int ALIGN_LEFT 		= 0;
    public final static int ALIGN_RIGHT 	= 1;
    public final static int ALIGN_CENTER 	= 2;

    //
    // FIELDS

    protected int width;
    protected int alignment;
    protected java.util.List cellLines;
    protected int innerLineToDisplay = 0;

    protected int rowspan = 0;

    //
    // CONSTRUCTORS

    private String adjustText (String text)
    {
	if (text.length() > this.width)
	    return text.substring(0, this.width);

	int delta = this.width - text.length();
	if (this.alignment == ALIGN_CENTER)
	    delta = delta/2;

	String pad = "";
	for (int i=0; i<delta; i++)
	    pad += ' ';

	if (this.alignment == ALIGN_RIGHT)
	{
	    text = pad + text;
	}
	else if (this.alignment == ALIGN_CENTER)
	{
	    text = pad + text + pad;
	    if (text.length() < this.width)
		text += ' ';
	}
	else // align left
	{
	    text += pad;
	}

	return text;
    }

    private java.util.List splitTextInLines (String text)
    {
	java.util.List result = new java.util.ArrayList();

	if (text == null) return result;

	while (true)
	{
	    int newLineIndex = text.indexOf('\n');
	    if (newLineIndex < 0)
	    {
		result.add(adjustText(text));
		break;
	    }
	    String line = text.substring(0, newLineIndex);
	    result.add(adjustText(line));
	    text = text.substring(newLineIndex+1);
	}

	return result;
    }

    public Cell (int width, int alignment, StringBuffer text)
    {
	this(width, alignment, text.toString());
    }

    public Cell (int width, int alignment, String text)
    {
	this.width = width;
	this.alignment = alignment;
	this.cellLines = splitTextInLines(text);
    }

    //
    // METHODS

    /**
     * This method is called by lines when they receive the cell
     */
    protected void increaseRowSpan ()
    {
	this.rowspan++;
    }

    public int getRowSpan ()
    {
	return this.rowspan;
    }

    public int getWidth ()
    {
	return this.width;
    }

    public int getHeight ()
    {
	return this.cellLines.size();
    }

    public int getLineCountStillToDisplay ()
    {
	return (this.cellLines.size() - this.innerLineToDisplay);
    }

    public String renderNextLineInSpan ()
    {
	return render(true);
    }

    public String renderNextLine ()
    {
	return render(false);
    }

    private String render (boolean inSpan)
    {
	String cellLine = null;

	if (this.innerLineToDisplay >= this.cellLines.size())
	    cellLine = burningbox.org.dsh.views.Utils.padString(this.width);
	else
	    cellLine = (String)this.cellLines.get(innerLineToDisplay);

	StringBuffer sb = new StringBuffer();

	if (inSpan)
	    sb.append("+ ");
	else
	    sb.append("| ");

	sb.append(cellLine);
	sb.append(' ');

	this.innerLineToDisplay++;

	return sb.toString();
    }

}
