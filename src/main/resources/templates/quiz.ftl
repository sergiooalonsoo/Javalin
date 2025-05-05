<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Interactivo</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <h1>Quiz Interactivo</h1>

    <!-- AQUI ES DONDE TIENES QUE HACER LO QUE SEA CON FREEMARKER, ES LO UNICO QUE TIENES QUE HACER AQUI -->
    <table>
        <tr>
            <td colspan="2">
                <#if preguntaTexto??>
                    <p>${preguntaTexto}</p>
                </#if>
            </td>
        </tr>
        <#if opciones??>
            <#list opciones as opcion>
                <tr>
                    <td>
                        <form action="/responder" method="post">
                            <input type="hidden" name="respuesta" value="${opcion}">
                            <button type="submit" class="celda"><span>A</span></button>
                        </form>
                    </td>
                    <td style="text-align: left; width: 250px;">
                        <div style="display: inline-block;">
                            <p>${opcion}</p>
                        </div>
                    </td>
                </tr>
            </#list>
        </#if>
    </table>
    <#if puntuacion??>
        <p>¡Felicidades! Has completado el quiz. Tu puntuación es: ${puntuacion}</p>
    </#if>
    <form action="/otro_quiz" method="post">
        <button type="submit">Jugar otra vez</button>
    </form>
</body>
</html>