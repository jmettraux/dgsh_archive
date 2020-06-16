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
 * $Id: Configuration.java,v 1.17 2002/08/19 13:54:26 jmettraux Exp $
 */

//
// Configuration.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Q:	How many marketing people does it take to change a lightbulb?
// A:	I'll have to get back to you on that.
// 

package burningbox.org.dsh;

import burningbox.org.dsh.entities.DataSets;


/**
 * Configuration store
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/19 13:54:26 $
 * <br>$Id: Configuration.java,v 1.17 2002/08/19 13:54:26 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Configuration
{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.Configuration");

    //
    // FIELDS
    
    //protected java.util.List dataFileNames = null;
    protected java.util.Map aliases = null;
    //protected java.util.List scriptFileNames = null;
    protected String scriptFileDirectory = "etc/scripts/";
    protected String logFileName = null;
    protected String logLevel = "INFO";
    protected String scenarioDirectory = "scn/";
    protected String partyDirectory = "pty/";
    protected String randomTableDirectory = "etc/tables/";
    protected java.util.Map envAttributes = null;
    protected String monsterDirectory = "etc/monsters/";
    protected String spellDirectory = "etc/spells/";
    protected String raceDirectory = "etc/races/";
    protected String classDirectory = "etc/classes/";
    protected String skillDirectory = "etc/skills/";
    protected String featDirectory = "etc/feats/";
    protected String equipmentDirectory = "etc/equipment/";

    //
    // CONSTRUCTORS

    public Configuration ()
    {
    }

    //
    // BEAN METHODS

    /*
    public java.util.List getDataFileNames () { return this.dataFileNames; }
    public void setDataFileNames (java.util.List dfn) { this.dataFileNames = dfn; }
    */

    public java.util.Map getAliases () { return this.aliases; }
    //public java.util.List getScriptFileNames () { return this.scriptFileNames; }
    public String getLogFileName () { return this.logFileName; }
    public String getLogLevel () { return this.logLevel; }
    public String getScenarioDirectory () { return this.scenarioDirectory; }
    public String getPartyDirectory () { return this.partyDirectory; }
    public String getRandomTableDirectory () { return this.randomTableDirectory; }
    public java.util.Map getEnvAttributes () { return this.envAttributes; }
    public String getMonsterDirectory () { return this.monsterDirectory; }
    public String getSpellDirectory () { return this.spellDirectory; }
    public String getRaceDirectory () { return this.raceDirectory; }
    public String getClassDirectory () { return this.classDirectory; }
    public String getSkillDirectory () { return this.skillDirectory; }
    public String getFeatDirectory () { return this.featDirectory; }
    public String getEquipmentDirectory () { return this.equipmentDirectory; }
    public String getScriptFileDirectory () { return this.scriptFileDirectory; }


    public void setAliases (java.util.Map m) { this.aliases = m; }
    //public void setScriptFileNames (java.util.List sfn) { this.scriptFileNames = sfn; }
    public void setLogFileName (String s) { this.logFileName = s; }
    public void setLogLevel (String s) { this.logLevel = s.toUpperCase(); }
    public void setScenarioDirectory (String s) { this.scenarioDirectory = checkPath(s); }
    public void setPartyDirectory (String s) { this.partyDirectory = checkPath(s); }
    public void setRandomTableDirectory (String s) { this.randomTableDirectory = checkPath(s); }
    public void setEnvAttributes (java.util.Map m) { this.envAttributes = m; }
    public void setMonsterDirectory (String s) { this.monsterDirectory = s; }
    public void setSpellDirectory (String s) { this.spellDirectory = s; }
    public void setRaceDirectory (String s) { this.raceDirectory = s; }
    public void setClassDirectory (String s) { this.classDirectory = s; }
    public void setSkillDirectory (String s) { this.skillDirectory = s; }
    public void setFeatDirectory (String s) { this.featDirectory = s; }
    public void setEquipmentDirectory (String s) { this.equipmentDirectory = s; }
    public void setScriptFileDirectory (String s) { this.scriptFileDirectory = s; }

    //
    // METHODS

    private static String checkPath (String path)
    {
	if ( ! path.endsWith(java.io.File.separator))
	    path += java.io.File.separator;

	return path;
    }

    private void display (boolean verbose, String message)
    {
	if (verbose) System.out.println(message);
    }

    public void apply (boolean verbose)
	throws java.io.IOException
    {
	display(verbose, "..Setting up aliases"); 
	Aliases.init(this.aliases);

	display (verbose, "..Setting scripts directory to "+this.scriptFileDirectory);
	PythonInterpreter.getInstance()
	    .loadAndInterpretDirectory(this.scriptFileDirectory);

	display(verbose, "..Setting up "+this.logLevel.toLowerCase()+" logging to "+this.logFileName);
	LoggingConfigurator.configure
	    ("dgsh", this.logFileName, this.logLevel);
	GameSession.getInstance().setLogFileName(this.logFileName);

	display(verbose, "..DataSets init");
	DataSets.init(this, verbose);

	display(verbose, "..Setting scenario directory to "+this.scenarioDirectory);
	GameSession.getInstance().setScenarioDirectory(this.scenarioDirectory);

	display(verbose, "..Setting party directory to "+this.partyDirectory);
	loadFilesInDirectory(this.partyDirectory, verbose);

	display(verbose, "..Setting env attributes");
	GameSession.getInstance().setEnvAttributes(this.envAttributes);
    }

    public void save (String destinationFileName)
	throws java.io.IOException
    {
	java.io.BufferedOutputStream out =
	    new java.io.BufferedOutputStream
	        (new java.io.FileOutputStream(destinationFileName));
	java.beans.XMLEncoder encoder = 
	    new java.beans.XMLEncoder(out);

	encoder.writeObject(this);

	encoder.close();
	out.close();
    }

    public static void loadFilesInDirectory (String directory, boolean verbose)
	throws java.io.IOException
    {
	java.io.File dir = new java.io.File(directory);

	if (dir == null)
	{
	    throw new java.io.IOException
		("Directory '"+directory+"' doesn't exist");
	}

	java.io.File[] files = dir.listFiles();

	for (int i=0; i<files.length; i++)
	{
	    java.io.File f = files[i];

	    if (f.getName().endsWith(".xml"))
	    {
		if (verbose)
		{
		    System.out.println
			("...Loading "+directory+f.getName());
		}

		try
		{
		    GameSession.getInstance().getParty().load(f.getPath());
		}
		catch (java.io.IOException ie)
		{
		    ie.printStackTrace();
		}
	    }
	}
    }

    //
    // STATIC METHODS

    public static Configuration load (String xmlFileName)
	throws java.io.IOException, DshException
    {
	java.io.BufferedInputStream in =
	    new java.io.BufferedInputStream
	        (new java.io.FileInputStream(xmlFileName));
	java.beans.XMLDecoder decoder = 
	    new java.beans.XMLDecoder(in);

	try
	{
	    return (Configuration)decoder.readObject();
	}
	catch (ClassCastException cce)
	{
	    throw new DshException
		("Not a valid configuration file "+xmlFileName, cce);
	}
	finally
	{
	    decoder.close();
	    in.close();
	}
    }

    //
    // MAIN METHOD

    /*
    public static void main (String[] args)
    {
	try
	{
	    Configuration config = new Configuration();

	    java.util.Map aliases = new java.util.HashMap();
	    aliases.put("p", "party");
	    aliases.put("ll", "browse");
	    config.setAliases(aliases);

	    java.util.List dataFileNames = new java.util.ArrayList(3);
	    dataFileNames.add("races.xml");
	    //config.setDataFileNames(dataFileNames);
	    config.save("dsh-config2.xml");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    */

}
