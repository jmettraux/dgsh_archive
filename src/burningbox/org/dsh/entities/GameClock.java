//
// GameClock.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.entities;

//import burningbox.org.dsh.DshException;


/**
 * To keep time of game time (not real time)
 * Time is time elapsed since beginning of session / scenario / campaign
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/12 08:22:45 $
 * <br>$Id: GameClock.java,v 1.3 2002/08/12 08:22:45 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class GameClock
{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.GameClock");

    //
    // FIELDS

    protected long elapsedMillis = 0;

    //
    // CONSTRUCTORS

    public GameClock ()
    {
    }

    //
    // BEAN METHODS

    public long getElapsedMillis () { return this.elapsedMillis; }

    public void setElapsedMillis (long l) { this.elapsedMillis = l; }

    //
    // METHODS

    private long parseTimeString (String time)
    {
	int index = -1;
	long result = 0;

	String number = "";

	while (true)
	{
	    index++;

	    if (index >= time.length())
		break;

	    char c = time.charAt(index);

	    if (java.lang.Character.isDigit(c))
	    {
		number += c;
		continue;
	    }

	    int value = 0;
	    try
	    {
		value = Integer.parseInt(number);
		number = "";
	    }
	    catch (NumberFormatException nfe)
	    {
		// ignore
	    }

	    if (c == 'd') // day
	    {
		result += (value * 24 * 60 * 60 * 1000);
	    }
	    else if (c == 'h') // hour
	    {
		result += (value * 60 * 60 * 1000);
	    }
	    else if (c == 'm') // minute
	    {
		result += (value * 60 * 1000);
	    }
	    else if (c == 's') // second
	    {
		result += (value * 1000);
	    }
	    else if (c == 'r') // round
	    {
		result += (value * 6 * 1000);
	    }
	}

	return result;
    }

    private void add (long timeElapsed)
    {
	this.elapsedMillis += timeElapsed;
    }

    public void set (long time)
    {
	this.elapsedMillis = time;
    }

    public void set (String time)
    {
	this.elapsedMillis = parseTimeString(time);
    }

    public void add (String time)
    {
	add(parseTimeString(time));
    }

    public void addRounds (int elapsedRounds)
    {
	add(elapsedRounds * 6 * 1000);
    }

    public void sub (String time)
    {
	add( - parseTimeString(time));
    }

    public String display (boolean small)
    {
	return display(this.elapsedMillis, small);
    }

    //
    // display commands

    public static String display (long time, boolean small)
    {
	long days = time / (24 * 3600 * 1000);
	long rhours = time / (3600 * 1000);
	long rminutes = time / (60 * 1000);
	long rrounds = time / (6 * 1000);

	long hours = rhours - (days * 24);
	long minutes = rminutes - (rhours * 60);
	long rounds = rrounds - (rminutes * 10);

	//
	// now display ...
	
	StringBuffer sb = new StringBuffer();

	sb.append(days);
	if (small)
	    sb.append('d');
	else
	    sb.append(" days ");
	sb.append(hours);
	if (small)
	    sb.append('h');
	else
	    sb.append(" hours ");
	sb.append(minutes);
	if (small)
	    sb.append('m');
	else
	    sb.append(" minutes ");
	sb.append(rounds);
	if (small)
	    sb.append('r');
	else
	    sb.append(" rounds ");

	return sb.toString();
    }

}
