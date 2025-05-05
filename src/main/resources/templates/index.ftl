<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Juegos Interactivos</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <h1>${titulo}</h1>

    <p>Selecciona uno de los juegos para comenzar:</p>

    <table>
        <#list enlaces as nombre, enlace>
            <tr>
                <td>
                    <a href="/${enlace}">
                      <button class="celda">${nombre?substring(0,1)}</button>
                    </a>
                </td>
                <td>
                    <span class="revelado">${nombre}</span>
                </td>
            </tr>
        </#list>
    </table>
</body>
</html>