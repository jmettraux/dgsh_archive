//
// SpellList.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.entities.magic;

import burningbox.org.dsh.entities.Named;
import burningbox.org.dsh.entities.BeanSet;
import burningbox.org.dsh.entities.DataSets;
import burningbox.org.dsh.entities.ClassLevel;


/**
 * For wizards, a spell list is called a spellbook. For sorcerers, it's a bunch
 * of souvenirs.
 * I planned to use a map of sets but as, set didn't serialize correctly, I
 * had to use a list. So now, two times the same spell name may be found...
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/29 13:05:10 $
 * <br>$Id: SpellList.java,v 1.6 2002/07/29 13:05:10 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SpellList
{

    //
    // FIELDS

    protected java.util.Map spellsPerClass = new java.util.HashMap();

    //
    // CONSTRUCTORS

    public SpellList ()
    {
    }

    //
    // BEAN METHODS

    public java.util.Map getSpellsPerClass () { return this.spellsPerClass; }

    public void setSpellsPerClass (java.util.Map m) { this.spellsPerClass = m; }

    //
    // METHODS

    public void addSpell (String className, String spellName)
    {
	java.util.List list = 
	    (java.util.List)this.spellsPerClass.get(className);

	if (list == null)
	{
	    list = new java.util.ArrayList();
	    this.spellsPerClass.put(className, list);
	}
	list.add(spellName);
    }

    public void removeSpell (String className, String spellName)
    {
	java.util.List list = 
	    (java.util.List)this.spellsPerClass.get(className);

	if (list != null)
	{
	    list.remove(spellName);
	}
    }

    public java.util.Iterator classIterator (String className)
    {
	java.util.List list = 
	    (java.util.List)this.spellsPerClass.get(className);

	if (list == null) return null;

	return list.iterator();
    }

}
