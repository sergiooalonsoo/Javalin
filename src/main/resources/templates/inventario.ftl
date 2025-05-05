<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventario</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <h1>Inventario</h1>
    <p>Gestiona el inventario de tu personaje:</p>

    <!-- AQUI ES DONDE TIENES QUE HACER LO QUE SEA CON FREEMARKER, ES LO UNICO QUE TIENES QUE HACER AQUI -->

    <div id="agregar">
        <h3>Agregar nuevo objeto</h3>
        <form action="/agregar_item" method="post">
            <input type="text" name="nuevoObjeto" placeholder="Nombre del objeto" required>
            <button type="submit">Agregar</button>
        </form>
    </div>

    <script>
        function mostrarDetalles(nombre, cantidad) {
            document.getElementById('detalles').style.display = '';
            document.getElementById('nombreObjeto').textContent = nombre;
            document.getElementById('cantidadObjeto').textContent = 'Cantidad: ' + cantidad;
            document.getElementById('nombreObjetoForm').value = nombre;
        }

        function ocultarDetalles() {
            document.getElementById('detalles').style.display = 'none';
        }
    </script>
</body>
</html>
