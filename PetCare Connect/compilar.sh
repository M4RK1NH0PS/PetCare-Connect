#!/bin/bash
echo "Compilando PetCare Connect..."
mkdir -p bin
javac -d bin -sourcepath src src/main/java/com/petcare/model/*.java src/main/java/com/petcare/data/*.java src/main/java/com/petcare/view/*.java
if [ $? -eq 0 ]; then
    echo "Compilação concluída com sucesso!"
    echo "Execute: java -cp bin com.petcare.view.LoginFrame"
else
    echo "Erro na compilação!"
fi

