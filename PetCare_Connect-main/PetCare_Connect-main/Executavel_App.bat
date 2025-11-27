@echo off
echo Iniciando PetCare Connect...
java -cp bin com.petcare.view.LoginFrame
if %errorlevel% neq 0 (
    echo Erro ao executar! Certifique-se de que o projeto foi compilado primeiro.
    echo Execute: Compilador.bat
    pause
)

