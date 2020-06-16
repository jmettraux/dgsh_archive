//
// Table.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.random;

import burningbox.org.dsh.Utils;


/**
 * A table for random encounters or treasures
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/08 07:48:14 $
 * <br>$Id: Table.java,v 1.3 2002/08/08 07:48:14 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Table

    implements RandomTable

{

    static org.apache.log4j.Logger log =
	org.apache.log4j.Logger.getLogger("dgsh.random.Table");

    //
    // FIELDS

    protected String name = null;
    protected java.util.List possibilities = new java.util.ArrayList(0);

    //
    // CONSTRUCTORS

    /**
     * The classical no-parameter constructor of a bean.
     * This class is a bean, because it is supposed to be saved as XML.
     */
    public Table ()
    {
    }

    //
    // BEAN METHODS

    public String getName () { return this.name; }
    public java.util.List getPossibilities () { return this.possibilities; }

    public void setName (String s) { this.name = s; }
    public void setPossibilities (java.util.List l) { this.possibilities = l; }

    //
    // METHODS

    /**
     * A method for adding a possibility to the table.
     * This is used at table creation.
     */
    public void addPossibility (Possibility p) 
    {
	this.possibilities.add(p);
    }

    /**
     * Roll with this table...
     */
    public Item roll ()
    {
	int max = computeMax();
	int random = Utils.random(0, max);

	int index = 0;
	java.util.Iterator it = this.possibilities.iterator();
	while (it.hasNext())
	{
	    int pastIndex = index;
	    Possibility p = (Possibility)it.next();
	    index += p.getWeight();

	    if (random > pastIndex && random <= index) 
		return p.getItem().resolve();
	}
	return null;
    }

    private int computeMax ()
    {
	int max = 0;

	java.util.Iterator it = this.possibilities.iterator();
	while (it.hasNext())
	{
	    Possibility p = (Possibility)it.next();
	    max += p.getWeight();
	}

	return max;
    }

}
