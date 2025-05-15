@echo off
cd bin
java test.ExprEvalTest ..\test\standard.xml  > ..\test\report.txt
cd ..
type test\report.txt
pause
del test\report.txt
@echo on
