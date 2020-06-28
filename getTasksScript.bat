call runcrud
if %ERRORLEVEL% == 0 echo WAR file build and server deployment was successful & goto startbrowser
echo runcrud.bat execution ended in an error & goto fail


:startbrowser
call start chrome http://localhost:8080/crud/v1/task/getTasks
if %ERRORLEVEL% == 0 echo Browser has been started & goto end
echo Could not start browser & goto fail

:fail
echo There were errors, which stopped the script.

:end
echo Script execution has been successful.