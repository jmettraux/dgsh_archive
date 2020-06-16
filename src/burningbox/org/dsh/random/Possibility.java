//
// Possibility.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.random;

/**
 * A treasure or an encounter
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/06 12:42:40 $
 * <br>$Id: Possibility.java,v 1.1 2002/08/06 12:42:40 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Possibility
{

    //
    // FIELDS

    protected int weight = -1;
    protected Item item = null;

    //
    // CONSTRUCTORS

    public Possibility ()
    {
    }

    public Possibility (int weight, Item item)
    {
	this.weight = weight;
	this.item = item;
    }

    //
    // BEAN METHODS

    public int getWeight () { return this.weight; }
    public Item getItem () { return this.item; }

    public void setWeight (int i) { this.weight = i; }
    public void setItem (Item i) { this.item = i; }

    //
    // METHODS

}
