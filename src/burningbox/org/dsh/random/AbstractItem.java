//
// AbstractItem.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.random;

/**
 * The base of a 'leaf' (not list) item.
 * It is abstract because it should not get instantianted.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/13 09:43:46 $
 * <br>$Id: AbstractItem.java,v 1.2 2002/08/13 09:43:46 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public abstract class AbstractItem

    implements Item

{

    //
    // FIELDS

    protected int count = 0;
    protected String countRoll = null;

    protected String staticText = null;

    //
    // CONSTRUCTORS

    public AbstractItem ()
    {
    }

    //
    // BEAN METHODS

    public int getCount () { return this.count; }
    public String getCountRoll () { return this.countRoll; }
    public String getStaticText () { return this.staticText; }

    public void setCount (int i) { this.count = i; }
    public void setCountRoll (String s) { this.countRoll = s; }
    public void setStaticText (String s) { this.staticText = s; }

}
