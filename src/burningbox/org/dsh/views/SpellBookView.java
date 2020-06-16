//
// SpellBookView.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.views;

import burningbox.org.dsh.entities.DataSets;
import burningbox.org.dsh.entities.CharacterClass;
import burningbox.org.dsh.entities.magic.Spells;


/**
 * Lets you view the spell known / copied in the spell book for a character
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/29 13:05:10 $
 * <br>$Id: SpellBookView.java,v 1.3 2002/07/29 13:05:10 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SpellBookView

    implements View

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.SpellBook");

    //
    // FIELDS

    private Spells spells = null;

    //
    // CONSTRUCTORS

    public SpellBookView (Spells s)
    {
	this.spells = s;
    }

    //
    // METHODS
    
    public StringBuffer process ()
    {

	StringBuffer sb = new StringBuffer();

	java.util.Iterator cit = this.spells.classIterator();

	while (cit.hasNext())
	{
	    String className = (String)cit.next();

	    //
	    // is it a class that must know spells ?
	    
	    CharacterClass cClass = DataSets.findCharacterClass(className);

	    if ( ! cClass.getSpellListUser())
		continue;

	    //
	    // display

	    sb.append(className.toUpperCase());
	    sb.append(" : \n\n");

	    SpellsView sv = new SpellsView(this.spells);
	    sv.setClassToDisplay(className);

	    sb.append(sv.process());
	    
	    if (cit.hasNext())
		sb.append("\n\n");
	}

	return sb;
    }

}
