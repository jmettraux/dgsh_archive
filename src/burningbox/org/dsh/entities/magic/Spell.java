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
 * $Id: Spell.java,v 1.11 2002/08/28 08:56:56 jmettraux Exp $
 */

//
// Spell.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.entities.magic;

import java.util.StringTokenizer;

import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.DshException;
import burningbox.org.dsh.CombatSession;
import burningbox.org.dsh.PythonInterpreter;
import burningbox.org.dsh.commands.Trigger;
import burningbox.org.dsh.entities.Named;
import burningbox.org.dsh.entities.Effect;
import burningbox.org.dsh.entities.DataSets;
import burningbox.org.dsh.entities.ClassLevel;
import burningbox.org.dsh.entities.CharacterClass;


/**
 * As its name implies.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/28 08:56:56 $
 * <br>$Id: Spell.java,v 1.11 2002/08/28 08:56:56 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Spell

    implements Named

{

    static org.apache.log4j.Logger log =
	org.apache.log4j.Logger.getLogger("dgsh.entities.Spell");


    public final static String SPELL_NAME = "spell.name";
    public final static String SPELLCASTER = "spell.spellcaster";
    public final static String SPELLCASTER_LEVEL = "spell.spellcaster.level";
    public final static String SPELLCASTER_CLASS = "spell.spellcaster.class";

    //
    // FIELDS

    protected String name = null;
    protected String description = null;
    protected String school = null;
    protected String subSchool = null;
    protected String type = null;
    protected java.util.List classes = new java.util.ArrayList(2);
    protected java.util.List components = new java.util.ArrayList(3);
    protected String castingTime = null;
    protected String range = null;
    protected String effect = null;
    protected String duration = null;
    protected String savingThrow = null;
    protected String spellResistance = null;
    protected String focus = null;
    protected String materialComponents = null;
    protected String longDescription = null;
    protected String triggerName = null;

    protected transient int castingTimeInRound = -1;

    //protected transient java.util.Map domains = new java.util.HashMap(2);

    //
    // CONSTRUCTORS

    public Spell ()
    {
    }

    //
    // BEAN METHODS

    public String getName () { return name; }
    public String getDescription () { return description; }
    public String getSchool () { return school; }
    public String getSubSchool () { return subSchool; }
    public String getType () { return type; }
    public java.util.List getClasses () { return classes; }
    public java.util.List getComponents () { return components; }
    public String getCastingTime () { return castingTime; }
    public String getRange () { return range; }
    public String getEffect () { return effect; }
    public String getDuration () { return duration; }
    public String getSavingThrow () { return savingThrow; }
    public String getSpellResistance () { return spellResistance; }
    public String getFocus () { return focus; }
    public String getMaterialComponents () { return materialComponents; }
    public String getLongDescription () { return longDescription; }
    public String getTriggerName () { return triggerName; }

    public void setName (String s) { this.name = s; }
    public void setDescription (String s) { this.description = s; }
    public void setSchool (String s) { this.school = s; }
    public void setSubSchool (String s) { this.subSchool = s; }
    public void setType (String s) { this.type = s; }
    public void setClasses (java.util.List l) 
    { 
	this.classes = l; 

	/*
	java.util.Iterator it = this.classes.iterator();
	while (it.hasNext())
	{
	    ClassLevel cl = (ClassLevel)it.next();

	    //log.debug("Class is >"+cl.getClassName()+"<");

	    if ( ! DataSets.isACharacterClass(cl.getClassName()))
	    {
		this.domains.put
		    (cl.getClassName(), new Integer(cl.getLevelReached()));
	    }
	}
	*/
    }
    public void setComponents (java.util.List l) { this.components = l; }
    public void setCastingTime (String s) { this.castingTime = s; }
    public void setRange (String s) { this.range = s; }
    public void setEffect (String s) { this.effect = s; }
    public void setDuration (String s) { this.duration = s; }
    public void setSavingThrow (String s) { this.savingThrow = s; }
    public void setSpellResistance (String s) { this.spellResistance = s; }
    public void setFocus (String s) { this.focus = s; }
    public void setMaterialComponents (String s) { this.materialComponents = s; }
    public void setLongDescription (String s) { this.longDescription = s; }
    public void setTriggerName (String s) { this.triggerName = s; }

    //
    // METHODS

    /*
    public boolean isDomainSpell ()
    {
	return (this.domains.size() > 0);
    }
    */

    /*
    public java.util.Map getDomains ()
    {
	return this.domains;
    }
    */

    public boolean isFromDomain (String domainName)
    {
	/*
	log.debug("isFromDomain(\""+domainName+"\")");
	java.util.Iterator it = this.domains.keySet().iterator();
	StringBuffer sb = new StringBuffer();
	while (it.hasNext())
	{
	    sb.append('\'');
	    sb.append((String)it.next());
	    sb.append('\'');
	    if (it.hasNext()) sb.append(", ");
	}
	log.debug("domains: "+sb.toString());
	*/

	//return this.domains.keySet().contains(domainName);
	
	return (getLevel(domainName) > -1);
    }

    public int getDomainLevel (String domainName)
    {
	return getLevel(domainName);
    }

    public int getLevel (String className)
    {
	//System.out.println("Spell.getLevel().className >"+className+"<");

	if (this.classes == null || className == null) return -1;

	boolean usesDomainSpells = false;

	if (DataSets.isACharacterClass(className))
	{
	    //System.out.println("Yes, '"+className+"' is a character class");

	    CharacterClass cClass = DataSets.findCharacterClass(className);

	    if (cClass != null) 
		usesDomainSpells = cClass.isUsingDomainSpells();
	}

	//System.out.println("Uses spell domains -> "+usesSpellDomains);

	java.util.Iterator it = this.classes.iterator();
	while (it.hasNext())
	{
	    ClassLevel cl = (ClassLevel)it.next();

	    if (className.equals(cl.getClassName()))
	    {
		return cl.getLevelReached();
	    }

	    if (usesDomainSpells && 
		( ! DataSets.isACharacterClass(cl.getClassName())))
	    {
		return cl.getLevelReached();
	    }
	}

	return -1;
    }

    public int computeCastingTimeInRound ()
    {
	if (this.castingTimeInRound > -1)
	    return this.castingTimeInRound;

	if (this.castingTime == null ||
	    this.castingTime.equals("1 action"))
	{
	    this.castingTimeInRound = 0;
	    return 0;
	}

	StringTokenizer tok = new StringTokenizer(this.castingTime);

	int time = 0;
	try
	{
	    time = Integer.parseInt(tok.nextToken());
	}
	catch (NumberFormatException nfe)
	{
	    this.castingTimeInRound = 0;
	    return 0;
	}

	int mod = 0;
	String unit = tok.nextToken();

	if (unit.equals("full")) // "full round"
	    mod = 1;

	if (unit.startsWith("minute"))
	    mod = 10;

	if (unit.startsWith("hour"))
	    mod = 60;

	if (unit.startsWith("day"))
	    mod = 1440;

	this.castingTimeInRound = time * mod;
	return this.castingTimeInRound;
    }

    private Trigger prepareTrigger
	(burningbox.org.dsh.entities.Character spellCaster,
	 CharacterClass spellCasterClass)
    throws 
	DshException
    {
	Trigger trigger = (Trigger)PythonInterpreter.getInstance()
	    .instantiate(this.triggerName, Trigger.class);

	//
	// prepare spell context
	
	Integer spellCasterLevel = new Integer
	    (spellCaster.computeLevelForClass(spellCasterClass.getName()));

	java.util.Map context = new java.util.HashMap();
	context.put(SPELL_NAME, this.name);
	context.put(SPELLCASTER, spellCaster);
	context.put(SPELLCASTER_LEVEL, spellCasterLevel);
	context.put(SPELLCASTER_CLASS, spellCasterClass);
	trigger.init(context);

	return trigger;
    }

    public void cast 
	(burningbox.org.dsh.entities.Character spellCaster,
	 CharacterClass spellCasterClass,
	 boolean domainSpell)
    throws 
	DshException
    {
	//
	// consume spell slot
	
	// throws an exception if out of slots
	
	spellCaster.getSpellSlots()
	    .consume(this, spellCasterClass.getName(), domainSpell);

	//
	// unprepare spell
	
	if (spellCasterClass.mustPrepareSpells())
	{
	    spellCaster.getPreparedSpells()
		.removeSpell(spellCasterClass, this.getName(), domainSpell);
	}

	//
	// cast the spell

	// if spellcasting is not '1 action' and combat is on,
	// attach effect...
	
	int castingTime = computeCastingTimeInRound();

	//System.out.println("casting time in round : "+castingTime);

	if (castingTime > 0 && 
	    CombatSession.isCombatGoingOn())
	{
	    //System.out.println("casting with an effect");

	    Effect fx = new Effect
		(spellCaster.getName()+"'s "+this.name,
		 "casting time",
		 spellCaster.getCurrentInitiative(),
		 spellCaster.computeAbilityScore(Definitions.DEXTERITY)-1,
		 castingTime,
		 new java.util.ArrayList());

	    if (this.triggerName != null)
	    {
		//System.out.println("trigger is added to an effect");

		fx.setPostEffectTrigger
		    (prepareTrigger(spellCaster, spellCasterClass));
	    }

	    Effect.addEffect(fx);
	}
	else if (this.triggerName != null)
	{
	    //System.out.println("triggering immediately");

	    prepareTrigger(spellCaster, spellCasterClass).trigger();
	}
    }

}
