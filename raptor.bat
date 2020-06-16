rem @echo off

rem set JAVA_HOME="c:\Program Files\Java\j2re1.4.0_01"

rem java -classpath lib\jython-2.1.jar;lib\log4j-1.2.5.jar;%JAVA_HOME%\lib\rt.jar;classes\;lib\dgsh.jar burningbox.org.dsh.export.StatBlockParser -f %*
java -cp lib\jython-2.1.jar;lib\log4j-1.2.5.jar;classes\;lib\dgsh.jar -Dpython.path=lib/jyLib.jar burningbox.org.dsh.export.StatBlockParser -f %*
