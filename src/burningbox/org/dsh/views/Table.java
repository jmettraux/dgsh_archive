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
 * $Id: Table.java,v 1.6 2002/07/30 05:58:07 jmettraux Exp $
 */

//
// Table.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Rebellion lay in his way, and he found it.
// 		-- William Shakespeare, "Henry IV"
// 

package burningbox.org.dsh.views;

/**
 * A basic ascii table
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/30 05:58:07 $
 * <br>$Id: Table.java,v 1.6 2002/07/30 05:58:07 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Table
{

    org.apache.log4j.Logger log =
	org.apache.log4j.Logger.getLogger("dgsh.Table");

    //
    // FIELDS

    protected String[] sizes = null;
    protected String[] titles = null;

    protected java.util.List content = null;

    //
    // CONSTRUCTORS

    public Table (String[] sizes, String[] titles)
    {
	this.sizes = sizes;
	this.titles = titles;

	content = new java.util.ArrayList(20);
    }

    //
    // METHODS

    private static String[] hashCellIntoLines (int lineCount, String cell)
    {
	String[] result = new String[lineCount];

	for (int i=0; i<lineCount; i++)
	    result[i] = "";

	int lineIndex = 0;
	while (cell.length() > 0)
	{
	    //System.out.println("cell=>"+cell+"<");
	    int cutIndex = cell.indexOf('\n');
	    //System.out.println("cutIndex="+cutIndex);
	    if (cutIndex < 0)
	    {
		result[lineIndex] = cell;
		break;
	    }
	    else if (cell.endsWith("\n"))
	    {
		result[lineIndex] = cell.substring(0, cell.length()-1);
		break;
	    }
	    result[lineIndex] = cell.substring(0, cutIndex);
	    cell = cell.substring(cutIndex+1);
	    //System.out.println("(2) cell=>"+cell+"<");
	    lineIndex++;
	}

	return result;
    }
    
    private static int computeLineCount (String cell)
    {
	if (cell == null) return 0;

	int result = 1;
	for (int i=0; i<cell.length(); i++)
	{
	    char c = cell.charAt(i);
	    if (c == '\n') result++;
	}
	return result;
    }

    private static int computeLineCount (String[] cells)
    {
	if (cells == null || cells.length < 1) return 0;

	int lineCount = 1;
	for (int j=0; j<cells.length; j++)
	{
	    int result = computeLineCount(cells[j]);
	    if (result > lineCount)
		lineCount = result;
	}
	return lineCount;
    }

    private static String[][] turnToMultiLine (String[] cells)
    {
	int lineCount = computeLineCount(cells);
	//System.out.println("lineCount="+lineCount);

	if (lineCount == 1)
	    return new String[][] { cells };

	String[][] result = new String[lineCount][cells.length];
	//System.out.println("result[][] y="+result.length+" x="+result[0].length);
	
	for (int i=0; i<cells.length; i++)
	{
	    String[] multiLineCell = hashCellIntoLines(lineCount, cells[i]);
	    for (int j=0; j<lineCount; j++)
	    {
		result[j][i] = multiLineCell[j];
	    }
	}
	
	return result;
    }

    public void addLine (String[] line)
    {
	this.content.add(turnToMultiLine(line));
    }

    public String render ()
    {
	StringBuffer sb = new StringBuffer();

	sb.append(displayHeaders());

	java.util.Iterator it = this.content.iterator();
	while (it.hasNext())
	{
	    String[][] cells = (String[][])it.next();
	    sb.append(displayCells(cells));
	}

	sb.append(displayHorizontalBar());

	return sb.toString();
    }

    //
    // table methods

    private String displayHorizontalBar ()
    {
	StringBuffer sb = new StringBuffer();

	sb.append('+');
	for (int i=0; i<sizes.length; i++)
	{
	    int size = Integer.parseInt(sizes[i].substring(1));
	    sb.append(Utils.padMinus(size+2));
	    sb.append('+');
	}
	if (sb.length() < 80) sb.append('\n');

	return sb.toString();
    }

    private String displayCells (String[][] cells)
    {
	//System.out.println("cells[][] y="+cells.length+" x="+cells[0].length);
	StringBuffer sb = new StringBuffer();

	for (int j=0; j<cells.length; j++)
	{
	    //System.out.println("j="+j);
	    sb.append('|');
	    for (int i=0; i<this.sizes.length; i++)
	    {
		//System.out.println("i="+i);
		int size = Integer.parseInt(this.sizes[i].substring(1));
		boolean padOnLeft = (this.sizes[i].charAt(0) == 'r');
		sb.append(' ');
		sb.append(Utils.format(cells[j][i], size, padOnLeft));
		sb.append(' ');
		sb.append('|');

		if (i == sizes.length-1 && cells.length > 1)
		    sb.append('\n');
	    }

	    if (cells.length == 1 && sb.length() < 80) 
		sb.append('\n');
	}

	return sb.toString();
    }

    private String displayTitles (String[] cells)
    {
	StringBuffer sb = new StringBuffer();

	sb.append('|');
	for (int i=0; i<sizes.length; i++)
	{
	    int size = Integer.parseInt(sizes[i].substring(1));
	    boolean padOnLeft = (sizes[i].charAt(0) == 'r');
	    sb.append(' ');
	    sb.append(Utils.format(cells[i], size, padOnLeft));
	    sb.append(' ');
	    sb.append('|');
	}
	if (sb.length() < 80) sb.append('\n');

	return sb.toString();
    }

    private String displayHeaders ()
    {
	StringBuffer sb = new StringBuffer();

	sb.append(displayHorizontalBar());
	sb.append(displayTitles(this.titles));
	sb.append(displayHorizontalBar());

	return sb.toString();
    }

}
