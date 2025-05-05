<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wordle</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <h1>Wordle</h1>
    <p>Adivina la palabra de ${longitudPalabra} letras:</p>

    <form id="wordleForm">
        <input type="text" id="guess" maxlength="${longitudPalabra}" placeholder="Tu palabra" autocomplete="off" required>
        <button type="submit">Enviar</button>
    </form>

     <table>
        <#list 1..intentosMaximos as fila>
            <tr>
                <#list 1..longitudPalabra as columna>
                    <td>
                        <button class="celda">
                            <span class="sin-tocar" id="celda-${fila}-${columna}">?</span>
                        </button>
                    </td>
                </#list>
            </tr>
        </#list>

     </table>
    <form action="/otra_palabra" method="post">
        <button type="submit">Jugar otra vez</button>
    </form>

    <script>
        const inputField = document.getElementById('guess');
        const form = document.getElementById('wordleForm');
        const wordleTable = document.getElementById('wordleTable');
        let currentRow = 1;

        inputField.addEventListener('input', function () {
            const guess = inputField.value.toUpperCase();

            for (let i = 0; i < guess.length; i++) {
                const celda = document.getElementById('celda-'+currentRow+'-'+(i + 1));
                if (celda) {
                    celda.textContent = guess[i];
                }
            }

            for (let i = guess.length; i < ${longitudPalabra}; i++) {
                const celda = document.getElementById('celda-'+currentRow+'-'+(i + 1));
                if (celda) {
                    celda.textContent = '?';
                }
            }
        });

        form.addEventListener('submit', async function (e) {
            e.preventDefault();
            const guess = inputField.value.toLowerCase();

            const response = await fetch('/comprobar_palabra', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'guess=' + encodeURIComponent(guess)
            });

            const data = await response.json();
            if (data.result) {
                let todoVerde = true;
                for (let i = 0; i < guess.length; i++) {
                    var celda = document.getElementById('celda-'+currentRow+'-'+(i + 1));
                    if (data.result[i] === "green") {
                        celda.parentElement.style.backgroundColor = '#80ff80';
                    } else if (data.result[i] === "yellow") {
                        todoVerde = false;
                        celda.parentElement.style.backgroundColor = 'orange';
                    } else {
                        todoVerde = false;
                        celda.parentElement.style.backgroundColor = '#ff4d4d';
                    }
                }
                if(todoVerde) {
                    const botones = document.querySelectorAll('button.celda');
                    botones.forEach(boton => {
                        if(boton.style.backgroundColor == ''){
                            boton.disabled = true;
                        }
                    });
                    form.querySelector('button').disabled = true;
                    alert("¡Enhorabuena!");
                    return;
                }
                currentRow++;
                inputField.value = "";
            } else {
                alert(data.message);
            }

            if (currentRow > ${intentosMaximos}) {
                alert("¡Has alcanzado el máximo de intentos!");
                form.querySelector('button').disabled = true;
            }
        });
    </script>

</body>
</html>
