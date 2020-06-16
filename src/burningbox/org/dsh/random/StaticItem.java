//
// StaticItem.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.random;

import burningbox.org.dsh.Utils;


/**
 * This item is used for describing things like 'gp' or 'gems'...
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/08 07:48:14 $
 * <br>$Id: StaticItem.java,v 1.3 2002/08/08 07:48:14 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class StaticItem

    extends AbstractItem

{

    static org.apache.log4j.Logger log =
	org.apache.log4j.Logger.getLogger("dgsh.random.StaticItem");


    public final static StaticItem NOTHING 
	= new StaticItem(0, null, "0 gp", "0 gp", "Nothing");

    //
    // FIELDS

    protected String value = null;
    protected String average = null;
    protected String description = null;

    //
    // CONSTRUCTORS

    public StaticItem ()
    {
	super();
    }

    /**
     * for coins
     */
    public StaticItem (String value)
    {
	super();

	this.value = value;
    }

    public StaticItem 
	(int count, 
	 String countRoll, 
	 String value, 
	 String average,
	 String description)
    {
	super();

	this.count = count;
	this.countRoll = countRoll;
	this.value = value;
	this.average = average;
	this.description = description;
    }

    /**
     * For gems : count defaults to 1
     */
    public StaticItem 
	(String value, 
	 String average,
	 String description)
    {
	super();

	this.count = 1;
	this.value = value;
	this.average = average;
	this.description = description;
    }

    /**
     * for mundane items
     */
    public StaticItem 
	(String description,
	 int count, 
	 String countRoll, 
	 String value)
    {
	super();

	this.count = count;
	this.countRoll = countRoll;
	this.value = value;
	this.average = null;
	this.description = description;
    }

    //
    // BEAN METHODS

    public String getValue () { return this.value; }
    public String getAverage () { return this.average; }
    public String getDescription () { return this.description; }

    public void setValue (String s) { this.value = s; }
    public void setAverage (String s) { this.average = s; }
    public void setDescription (String s) { this.description = s; }

    //
    // METHODS

    public Item resolve () 
    {
	if (this.countRoll != null)
	{
	    //log.debug("rolling count >"+this.countRoll+"<");

	    this.count = Utils.throwDices(this.countRoll);
	}
	return this;
    }

}
