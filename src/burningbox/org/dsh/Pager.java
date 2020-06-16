//
// Pager.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh;

import burningbox.org.dsh.views.View;


/**
 * A Pager allows to browse files up and down (like less)
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/29 13:05:10 $
 * <br>$Id: Pager.java,v 1.6 2002/07/29 13:05:10 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Pager
{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.Pager");

    protected static int screenLines 
	= Integer.getInteger("dsh.term.lines", 24).intValue();

    //
    // FIELDS

    private CharSequence buffer = null;
    private int currentLine = 0;
    private int shouldExit = 0;
    private java.util.List indexes = null;

    private java.io.BufferedReader in = new java.io.BufferedReader
	(new java.io.InputStreamReader(System.in));

    //
    // CONSTRUCTORS

    public Pager (String fileName, boolean reverse)
	throws java.io.IOException
    {
	this.buffer = readFile(fileName, reverse);
	computeIndexes();
    }

    public Pager (View view)
    {
	this.buffer = view.process();
	computeIndexes();
    }

    //
    // METHODS

    public void use ()
    {
	while (true)
	{
	    display();

	    int c = -1;
	    try
	    {
		String s = this.in.readLine();

		try
		{
		    int lineCount = Integer.parseInt(s);

		    down(lineCount);
		    continue;
		}
		catch (NumberFormatException nfe)
		{
		    // ignore
		}

		if (s.trim().length() < 1)
		    c = ' ';
		else
		    c = s.toLowerCase().charAt(0);
	    }
	    catch (java.io.IOException ie)
	    {
		// ignore
	    }

	    switch (c)
	    {
		case 'u' :
		    up(screenLines-1);
		    break;

		case 'h' :
		    home();
		    break;

		case 'e' :
		    end();
		    break;

		case 'q' :
		case 'x' :
		    return;

		default :
		    down(screenLines-1);
	    }

	    if (shouldExit > 1) return;
	}
    }

    //
    // compute indexes

    private void computeIndexes ()
    {
	this.indexes = new java.util.ArrayList();

	int currentIndex = 0;
	boolean noMoreNewLines = false;

	while (true)
	{
	    if (currentIndex >= this.buffer.length()) break;

	    this.indexes.add(new Integer(currentIndex));

	    if (noMoreNewLines)
	    {
		currentIndex += 80;
		continue;
	    }

	    int nextNewLine = 
		Utils.newLineIndex(this.buffer, currentIndex);

	    if (nextNewLine < 0)
		noMoreNewLines = true;

	    if (noMoreNewLines || nextNewLine - currentIndex > 80)
	    {
		currentIndex += 80;
	    }
	    else
	    {
		currentIndex = nextNewLine+1;
	    }
	}
    }

    //
    // nav methods

    private void home ()
    {
	this.currentLine = 0;
	this.shouldExit = 0;
    }

    private void end ()
    {
	this.currentLine = this.indexes.size() - screenLines;
	if (this.currentLine < 0) this.currentLine = 0;
	this.shouldExit = 1;
    }

    private void up (int lineCount)
    {
	this.currentLine -= lineCount;
	if (this.currentLine < 0) this.currentLine = 0;
	this.shouldExit = 0;
    }

    private void down (int lineCount)
    {
	this.currentLine += lineCount;
	if (this.currentLine > this.indexes.size() - screenLines + 1)
	{
	    this.currentLine = this.indexes.size() - screenLines + 1;
	    this.shouldExit++;
	}
	else if (this.currentLine < 0)
	{
	    this.currentLine = 0;
	    this.shouldExit = 0;
	}
    }

    private int getIndex (int lineIndex)
    {
	//if (lineIndex < 0) return 0;
	if (lineIndex >= this.indexes.size())
	    return this.buffer.length();

	return ((Integer)this.indexes.get(lineIndex)).intValue();
    }

    //
    // display method

    private void display ()
    {
	for (int l = 0; l < screenLines-1; l++)
	{
	    if (this.currentLine + l > this.indexes.size()) 
	    {
		this.shouldExit++;
		break;
	    }

	    int currentIndex = getIndex(this.currentLine + l);
	    int nextIndex = getIndex(this.currentLine + l + 1);

	    /*
	    log.debug("currentIndex = "+currentIndex);
	    log.debug("nextIndex = "+nextIndex);
	    log.debug("this.buffer.length() = "+this.buffer.length());
	    log.debug("l = "+l);
	    log.debug("this.currentLine+l = "+(this.currentLine + l));
	    log.debug("this.indexes.size() = "+this.indexes.size());
	    */

	    System.out.print
		((String)this.buffer.subSequence(currentIndex, nextIndex));
	}
	System.out.println();
    }

    //
    // read file

    private StringBuffer readFile (String fileName, boolean reverse)
	throws java.io.IOException
    {
	StringBuffer result = new StringBuffer();

	java.io.BufferedReader br = null;
	try
	{
	    br = new java.io.BufferedReader(new java.io.FileReader(fileName));

	    while (true)
	    {
		String line = br.readLine();

		if (line == null) break;

		line += '\n';

		if (reverse)
		    result.insert(0, line);
		else
		    result.append(line);
	    }

	    return result;
	}
	finally
	{
	    br.close();
	}
    }

    //
    // MAIN METHOD

    public static void main (String[] args)
    {
	try
	{
	    Pager pager = new Pager(args[0], false);
	    pager.use();
	}
	catch (java.io.IOException ie)
	{
	    ie.printStackTrace();
	}
    }

}
