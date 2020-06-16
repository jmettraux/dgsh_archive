rem @echo off

rem set JAVA_HOME="c:\Program Files\Java\j2re1.4.0_01"
set TERM_LINE=25

rem echo %JAVA_HOME%
rem java -classpath lib\jython-2.1.jar;lib\log4j-1.2.5.jar;%JAVA_HOME%\lib\rt.jar;classes\;lib\dgsh.jar -Ddsh.term.lines=%TERM_LINES% burningbox.org.dsh.Shell
java -classpath lib\jython-2.1.jar;lib\log4j-1.2.5.jar;classes\;lib\dgsh.jar -Ddsh.term.lines=%TERM_LINES% -Dpython.path=lib/jyLib.jar burningbox.org.dsh.Shell
