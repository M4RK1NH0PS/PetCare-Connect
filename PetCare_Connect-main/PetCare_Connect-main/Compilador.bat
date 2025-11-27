@echo off
echo Compilando PetCare Connect...
if not exist bin mkdir bin
javac -d bin -sourcepath src src/main/java/com/petcare/model/*.java src/main/java/com/petcare/data/*.java src/main/java/com/petcare/view/*.java
if %errorlevel% == 0 (
    echo Compilacao concluida com sucesso!
    echo Execute: java -cp bin com.petcare.view.LoginFrame
) else (
    echo Erro na compilacao!
    pause
)

