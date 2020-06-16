#
# MonsterEditor.py
#

#
# dgsh is no GUI stuff, but I made this as an amusement
#

import sys
import java.awt as awt
import java.lang as lang
import java.util as util
import javax.swing as swing
import burningbox.org.dsh.entities as entities

def exit (event) :
    lang.System.exit(0)

class AttackPanel (swing.JPanel) :

    attack = None

    name = None
    attackModifier = None
    damage = None
    critical = None
    attackType = None
    description = None

    def __init__ (self, attack) :
	swing.JPanel.__init__(self, size=(480, 20))
	self.layout = None
	self.attack = attack

	components = [ "name", "attackModifier", "damage", "critical", "attackType", "description" ]

	colIndex = 0
	for componentName in components :
	    component = getattr(self, componentName)
	    component = swing.JTextField(str(getattr(self.attack, componentName)))
	    component.size = (79, 19)
	    component.setLocation(0+colIndex, 0)
	    colIndex += 80

	    self.add(component)

class MonsterEditor (swing.JFrame) :

    template = None
    mainPanel = None
    attacksPanel = None
    attacksView = None
    attackRowIndex = 0
    attackScrollPane = None

    def __init__ (self, template) :
	swing.JFrame.__init__(self, "Monster Editor - %s" % template.name)
	self.size = (760, 300)
	self.template = template
	self.contentPane.layout = None
	self.windowClosing = exit

	self.mainPanel = self.buildMainPanel()
	self.mainPanel.setLocation(0, 0)
	self.contentPane.add(self.mainPanel)

	self.attacksPanel = self.buildAttacksPanel()
	self.attacksPanel.setLocation(self.mainPanel.width+3, 0)
	self.contentPane.add(self.attacksPanel)

	self.visible = 1

    def buildMainPanel (self) :
	fields = [ "name", "raceName", "category", "size", "hitDice", "potentialHitPoints", "initModifier", "speeds", "armorClass", "armorClassVsTouchAttack", "faceReach", "challengeRating", "alignment" ]

	panel = swing.JPanel(size = (250, len(fields) * 20))
	panel.layout = None

	rowIndex = 0
	for field in fields :
	    label = swing.JLabel(field, size=(100, 19))
	    label.setLocation(0, 0+rowIndex)
	    panel.add(label)

	    field = swing.JTextField(str(getattr(template, field)), size=(150, 19))
	    field.setLocation(101, 0+rowIndex)
	    panel.add(field)
	    rowIndex += 20

	return panel

    def addAttack (self, event) :
	item = AttackPanel(entities.Attack())
	item.setLocation(0, 0+self.attackRowIndex)
	self.attacksView.add(item)
	self.attackRowIndex += 20

    def removeAttack (self, event) :
	pass

    def buildAttacksPanel (self) :
        panel = swing.JPanel()
	panel.layout = None
	panel.size = (500, 100)

	label = swing.JLabel("Attacks", size=(200, 19))
	label.setLocation(0, 0)
	panel.add(label)

	self.attacksView = swing.JPanel()
	self.attacksView.size = (480, 60)
	self.attacksView.setLocation(0, 21)
	self.attacksView.layout = None

	self.attackRowIndex = 0
	for attack in self.template.attacks :
	    item = AttackPanel(attack)
	    item.setLocation(0, 0+self.attackRowIndex)
	    self.attacksView.add(item)
	    self.attackRowIndex += 20
	self.attackScrollPane = swing.JScrollPane(self.attacksView, swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, size=(500, 60))
	self.attackScrollPane.setLocation(0, 20)
	panel.add(self.attackScrollPane)
	
	addButton = swing.JButton("add attack", size=(100, 19))
	addButton.setLocation(0, 20+self.attacksView.height)
	addButton.actionPerformed = self.addAttack
	panel.add(addButton)
	self.attackRowIndex += 20

	return panel


templateName = sys.argv[1]

files = util.ArrayList()
files.add("etc/monsters.xml")

entities.DataSets.init(files)

template = entities.DataSets.findMonsterTemplate(templateName)

if template == None: template = entities.MonsterTemplate()

editor = MonsterEditor(template)
