<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buscaminas</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <h1>Buscaminas</h1>
    <p>Haz clic en una celda para descubrir si hay una bomba</p>

    <table>
        <#assign indiceFila = 0>
        <#list tablero as fila>
            <tr>
                <#assign indiceColumna = 0>
                <#list fila as celda>
                    <td>
                        <button class="celda" data-fila="${indiceFila}" data-columna="${indiceColumna}" style="">
                            <span class="sin-tocar">?</span>
                            <span class="oculto">${celda}</span>
                        </button>
                    </td>
                <#assign indiceColumna = indiceColumna + 1>
                </#list>
            </tr>
            <#assign indiceFila = indiceFila + 1>
        </#list>
    </table>

    <form action="/otro_buscaminas" method="post">
        <button type="submit">Jugar otra vez</button>
    </form>

    <script>
        var celdas = document.querySelectorAll('.celda');

        celdas.forEach(celda => {
            celda.addEventListener('click', async function (e) {

                var fila = celda.getAttribute('data-fila');
                var columna = celda.getAttribute('data-columna');

                let comprobarPosicion = await fetch('/comprobar_posicion', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'fila=' + encodeURIComponent(fila) + '&columna=' + encodeURIComponent(columna)
                });

                const data = await comprobarPosicion.json();

                celda.querySelector('.sin-tocar').remove();
                celda.querySelector('.oculto').classList.add('revelado');
                var valor = celda.querySelector('.oculto').textContent;
                if (data.result == -1) {
                    celda.style.backgroundColor = '#ff4d4d';
                    const botones = document.querySelectorAll('button.celda');
                    botones.forEach(boton => {
                        boton.disabled = true;
                    });
                } else {
                    celda.style.backgroundColor = '#80ff80';
                }
            });

            celda.addEventListener('contextmenu', (e) => {
                e.preventDefault();
                var currentBackground = celda.style.backgroundColor;
                if (currentBackground === 'rgb(255, 77, 77)' || currentBackground === 'rgb(128, 255, 128)') {
                    return;
                }
                if (celda.style.backgroundColor === 'orange') {
                    celda.style.backgroundColor = '';
                } else {
                    celda.style.backgroundColor = 'orange';
                }
            });
        });
    </script>
</body>
</html>