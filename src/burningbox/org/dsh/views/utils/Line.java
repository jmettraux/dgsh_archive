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
 * $Id: Line.java,v 1.5 2002/08/01 07:48:17 jmettraux Exp $
 */

//
// Line.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Lewis's Law of Travel:
// 	The first piece of luggage out of the chute doesn't belong to anyone,
// 	ever.
// 

package burningbox.org.dsh.views.utils;

import burningbox.org.dsh.views.Utils;


/**
 * A line for a multi table
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/01 07:48:17 $
 * <br>$Id: Line.java,v 1.5 2002/08/01 07:48:17 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Line
{

    //
    // FIELDS

    protected MultiTable table;

    protected java.util.List cells;
    protected java.util.Map cellMap;
    protected Cell lastCellVisited;
    protected int innerLineToDisplay;

    //
    // CONSTRUCTORS

    public Line ()
    {
	this.cells = new java.util.ArrayList();
	this.cellMap = null;
	this.innerLineToDisplay = 0;
    }

    //
    // METHODS

    /**
     * This method is called by MultiTable each a Line instance
     * got added to it
     */
    protected void setTable (MultiTable table)
    {
	this.table = table;
    }

    /*
     * the cellMap is a map column - cell.
     */
    private void buildCellMap ()
    {
	if (this.cellMap != null)
	    return;

	this.cellMap = new java.util.HashMap();

	int index = 0;
	
	java.util.Iterator it = this.cells.iterator();
	while (it.hasNext())
	{
	    Cell c = (Cell)it.next();
	    this.cellMap.put(new Integer(index), c);
	    index += (3 + c.getWidth());
	}
    }

    public Line addCell (Cell cell)
    {
	this.cells.add(cell);
	cell.increaseRowSpan();
	return this;
    }

    public boolean shouldDisplayCross (int column)
    {
	buildCellMap();
	return (this.cellMap.get(new Integer(column)) != null);
    }

    public Cell getCellAt (int column)
    {
	buildCellMap();
	Cell c = (Cell)this.cellMap.get(new Integer(column));

	if (c != null)
	{
	    this.lastCellVisited = c;
	    return c;
	}

	return null;
    }

    /*
    private int computeLineHeight ()
    {
	int height = 1;
	java.util.Iterator it = this.cells.iterator();
	while (it.hasNext())
	{
	    Cell c = (Cell)it.next();

	    if (c.getHeight() > height)
		height = c.getHeight();
	}
	return height;
    }
    */

    private boolean shouldDisplayAnotherLine ()
    {
	java.util.Iterator it = this.cells.iterator();
	while (it.hasNext())
	{
	    Cell c = (Cell)it.next();

	    /*
	    System.out.println
	        ("rowSpan            = "+c.getRowSpan());
	    System.out.println
	        ("linesToDisplay     = "+c.getLineCountStillToDisplay());
	    System.out.println
		("innerLineToDisplay = "+this.innerLineToDisplay);
	    */

	    if (c.getRowSpan() <= 1 && 
		c.getLineCountStillToDisplay() > 0)
	    {
		return true;
	    }
	}
	return false;
    }

    public String render ()
    {
	StringBuffer sb = new StringBuffer();

	//int height = computeLineHeight();

	//
	// render real line per real line
	//
	while (true)
	{
	    int currentWidth = 0;
	    java.util.Iterator it = this.cells.iterator();
	    while (it.hasNext())
	    {
		Cell c = (Cell)it.next();
		sb.append(c.renderNextLine());
		currentWidth += c.getWidth()+3;
	    }

	    int delta = this.table.getWidth() - currentWidth;
	    if (delta > 0)
		sb.append(Utils.padString(delta-1));

	    sb.append('|');
	    if (sb.length() < 80 || this.table.isFileOutput()) sb.append('\n');

	    //
	    // exit conditions

	    this.innerLineToDisplay++;

	    if ( ! shouldDisplayAnotherLine()) break;
	}

	return sb.toString();
    }

}
