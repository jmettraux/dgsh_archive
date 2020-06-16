#
# myTestCommand.py
#
# jmettraux@burningbox.org
#

#
# in dgsh, type 'load scripts/myTestCommand.py'
#

import burningbox.org.dsh as dsh
import burningbox.org.dsh.commands as commands

#
# This command will be called by typing 'test' in dgsh.
# It extends AbstractCommand. If it were undoable, it would extend 
# UndoableAbstractCommand.
# It implements RegularCommand and CombatCommand, because it's a command
# you can use during normal mode and during combat mode
#
class TestCommand (commands.AbstractCommand, commands.RegularCommand, commands.CombatCommand):

    #
    # where all the work is done
    #
    def execute (self):
	if (dsh.CombatSession.isCombatGoingOn()):
	    dsh.Term.echo("A combat is raging !\n")
	else:
	    dsh.Term.echo("All is quiet.\n")

    #
    # A small text describing the syntax of your command.
    # Notice the use of the argument 'self'. It's a reference to the command
    # object (instance) itself and it is required.
    #
    def getSyntax (self):
	return "Test"

    #
    # the help text
    #
    def getHelp (self):
    	return "Just a test"
