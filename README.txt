     ___    ____  ____  __ __
    / _ \  / __/ / __/ / // /
   / // / / / / _\ \  / _  /
  /____/ /___/ /___/ /_//_/

$Id: README.txt,v 1.7 2002/08/27 06:53:37 jmettraux Exp $


README for dgsh
===============

What is needed to run dgsh :

- a java virtual machine (at least a 1.4.0) (see http://java.sun.com to dowload one)

then make sure that in dgsh.sh or dgsh.bat, the variable JAVA_HOME is set correctly.


Running dgsh
------------

On a Unix :

check that dgsh.sh is executable (chmod a+x dgsh.sh) then

./dgsh.sh


On a windows box :

dgsh.bat


Don't hesitate to modify your .sh or .bat file to suit your configuration.


Quitting dgsh
-------------

Type exit or quit at the dgsh prompt.


Adding a character to your party
--------------------------------

This is done outside of a game session by running the jython script for adding a character to the XML party file. At first edit, the addCharacter.py scripts with your new character statistics. Take some time to read this script, this is no big deal suiting it to your needs.

./jython.sh scripts/createCharacter.py

or 

./jython.bat scripts/createCharacter.py (Windows)

(On unix make sure that jython.sh is executable, chmod a+x jython.sh)

Don't hesitate to comment out entire lines in the createCharacter.py script (for example if your character isn't a spellcaster, comment out spellbooks and domains)

If you name the character 'Feanor', he will appear in the current directory under 'feanor.xml'. To include him in your party, copy it in your 'pty' folder, it will automatically get loaded when you run dgsh.

If your dgsh is already running, you can 'load pty/feanor.xml party' to include it in your party.


If you run into troubles...
---------------------------

Get on 

http://sf.net/projects/dgsh 

and get to the mailing lists. Drop a line or two in the mailing list dgsh-users to explain why you can't run dgsh.

If you run into a bug, don't hesitate to use the bug tracker you'll find at the URL above.



John
jmettraux@burningbox.org

