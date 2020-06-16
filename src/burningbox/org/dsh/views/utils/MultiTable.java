/*

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
 * $Id: MultiTable.java,v 1.7 2002/08/01 07:48:17 jmettraux Exp $
 */

//
// MultiTable.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// nada de especial
// 

package burningbox.org.dsh.views.utils;

/**
 * A more sophisticated table
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/01 07:48:17 $
 * <br>$Id: MultiTable.java,v 1.7 2002/08/01 07:48:17 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class MultiTable
{

    //
    // FIELDS

    protected int width;
    protected java.util.List lines;

    protected boolean fileOutput = false;

    //
    // CONSTRUCTORS

    public MultiTable ()
    {
	this(80);
    }

    public MultiTable (int width)
    {
	this.width = width;
	this.lines = new java.util.ArrayList();
    }

    //
    // METHODS

    public void setFileOutput (boolean fileOutput)
    {
	this.fileOutput = fileOutput;
    }

    public boolean isFileOutput ()
    {
	return this.fileOutput;
    }

    public int getWidth ()
    {
	return this.width;
    }

    public void addLine (Line line)
    {
	line.setTable(this);
	this.lines.add(line);
    }

    public String render ()
    {
	if (this.lines == null || this.lines.size() < 1)
	{
	    return "";
	}

	boolean rowSpanLast = false;
	    // this is set to true if the last thing displayed
	    // is a cell spanning 2 or more rows

	StringBuffer sb = new StringBuffer();

	Line previousLine = new BlankLine();

	java.util.Iterator it = this.lines.iterator();
	while (it.hasNext())
	{
	    Line nextLine = (Line)it.next();

	    //
	    // display '+--------+'
	    
	    for (int i=0; i<this.width; i++)
	    {

		// display colspanning cells
		Cell upperCell = previousLine.getCellAt(i);
		Cell lowerCell = nextLine.getCellAt(i);
		if (upperCell == lowerCell &&
		    upperCell != null)
		{
		    if (i == 0)
			sb.append(upperCell.renderNextLine());
		    else
			sb.append(upperCell.renderNextLineInSpan());
		    i += (upperCell.getWidth() + 2);
		    rowSpanLast = true;
		    continue;
		}

		if (i == 0 || 
		    i >= this.width - 1)
		{
		    if (rowSpanLast)
			sb.append('|');
		    else
			sb.append('+');
		    rowSpanLast = false;
		    continue;
		}

		rowSpanLast = false;
		if (previousLine.shouldDisplayCross(i) ||
		    nextLine.shouldDisplayCross(i))
		{
		    sb.append('+');
		}
		else
		{
		    sb.append('-');
		}
	    }
	    if (sb.length() < 80 || this.fileOutput) sb.append('\n');

	    sb.append(nextLine.render());
	    //sb.append('\n');

	    previousLine = nextLine;
	}

	//
	// display last '+--------+'
	
	for (int i=0; i<this.width-1; i++)
	{
	    if (previousLine.shouldDisplayCross(i))
		sb.append('+');
	    else
		sb.append('-');
	}
	sb.append('+');

	return sb.toString();
    }

    //
    // a small but useful inner class
    
    class BlankLine extends Line
    {
	public BlankLine ()
	{
	}
	public Cell getCellAt (int column)
	{
	    return null;
	}
	public boolean shouldDisplayCross (int column)
	{
	    return false;
	}
    }

    //
    // the main method

    public static void main (String[] args)
    {
	MultiTable table = new MultiTable(80);
	Line l = new Line();
	Cell c = null;

	c = new Cell(34, Cell.ALIGN_CENTER, "RIEN du TOUT\net\nrien de rien");
	l.addCell(c);
	l.addCell(new Cell(39, Cell.ALIGN_LEFT, "Hello les Mickeys !"));
	
	table.addLine(l);
	l = new Line();
	l.addCell(c);
	l.addCell(new Cell(39, Cell.ALIGN_RIGHT, "rien à dire\ndu tout"));
	table.addLine(l);
	
	System.out.println(table.render());
    }

}
