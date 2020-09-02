<link href="style.css" rel="stylesheet" type="text/css" />

<p><span class="Titulo">Bienvenido</span> 
  <?php 
	$_SESSION['userName']
?>
  
</p>
<?php
session_save_path('/home/users/web/b1282/ipg.appearcomar/cgi-bin/tmp');
session_start();
echo "<a href='logout.php'>Cerrar sesión </a>";

	 if(isset($_SESSION['userName']))
	 {
	 
		echo'<h1>EDITOR: ' . $_SESSION['userName'] . '</h1>';
	 } 
	 else
	 {
	 	echo 'No estas registrado <br>';
		echo'<a href="index.php">LOGIN</a>';
		exit;
	 }

	 /*Just for your server-side code*/
header('Content-Type: text/html; charset=ISO-8859-1');
if ($_POST['submit'] == "Cancelar")
{
    header("Location: overview.php");
	exit;
}
//SI NO HAY CAMBIOS DE VISTO, SIGUE CON LA GRABACION

$RepId = $_POST['RepId'];
$fechaenvio = $_POST['fechaenvio'];
$editor = $_SESSION['userName'];
$nombreusuario = $_POST['nombreusuario'];
$deviceID = $_POST['deviceID'];

if ($_POST['submit'] == "Validar")
{
		echo 'Se validará como correcto!.' . "<br>";
		echo "DEVICEID: " . $row["deviceID"] ."<br>";
		
		// Conecta a la db para actualizar el campo
		require("../connect/connectmapa.php");
    $mysqli = new mysqli($host, $user, $pw, $db);
    $mysqli->query("SET CHARACTER SET utf8");
    $mysqli->query("SET NAMES 'utf8'");
    if ($mysqli->connect_error){
        die("Connection failed: " . $mysqli->connect_error);
    }

		$res = $mysqli->query("UPDATE markers SET verificado='Verificado' WHERE id= '$RepId'");
		if (!$res) {
			print json_encode("Error");
			echo "<br />" . $mysqli->error;
			exit;
		}
		else {
			echo "." . "<br>";
			echo ".." . "<br>";
			echo "..." . "<br>";
			echo "...." . "<br>";
			echo "....." . "<br>";
			echo "ACTUALIZADA" . "<br>";
		}
		
		//Carga de puntos
			
		require("../connect/connect.php");
    $mysqli = new mysqli($host, $user, $pw, $db);
    $mysqli->query("SET CHARACTER SET utf8");
    $mysqli->query("SET NAMES 'utf8'");
    if ($mysqli->connect_error){
        die("Connection failed: " . $mysqli->connect_error);
    }

		$res = $mysqli->query("UPDATE tbl_member SET numevalsok=numevalsok+1 WHERE user_name= '$nombreusuario'");
		if (!$res) {
			print json_encode("Error");
			echo "<br />" . $mysqli->error;
			exit;
		}
		else {
			echo "." . "<br>";
			echo ".." . "<br>";
			echo "..." . "<br>";
			echo "...." . "<br>";
			echo "....." . "<br>";
			echo "PUNTOS CARGADOS" . "<br>" . "<br>" . "<br>";
		}
		
			// Formulario para enviar una devolución al usuario
			echo "Puedes enviar el siguiente texto al usuario (" . $nombreusuario. ") para hacerle saber que ha validado corectamente:" ."<br>";
			echo "<form method='post' action='enviarmensajeprivado.php'>";
			$msj = "El dato que has enviado en la fecha " . $fechaenvio . "  fue considerado válido por decisión de un revisor. Sigue así!";
			echo "<textarea name = 'msjprivado' cols=40 rows=6>" . $msj . "</textarea>" ."<br>" ."<br>" ; 
			echo "<input type='hidden' name='nombreusuario' value='" .$nombreusuario . "'/>";		
			echo "<input type='submit' name='submit' value='Enviar mensaje' class='botonverde'>";
			echo "</form>";
			
			echo "<a href='overview.php'> Volver al listado";
			exit;			
		
}

elseif ($_POST['submit'] == "No Validar")
{
		echo 'Se validará como incorrecto.' . "<br>";

		// Conecta a la db para actualizar el campo
		require("../connect/connectmapa.php");

    $mysqli = new mysqli($host, $user, $pw, $db);
    $mysqli->query("SET CHARACTER SET utf8");
    $mysqli->query("SET NAMES 'utf8'");
    if ($mysqli->connect_error){
        die("Connection failed: " . $mysqli->connect_error);
    }
		
		$res = $mysqli->query("UPDATE markers SET verificado='Incorrecto' WHERE id= '$RepId'");
		if (!$res) {
			print json_encode("Error");
			echo "<br />" . $mysqli->error;
			exit;
		}
		else {
			echo "." . "<br>";
			echo ".." . "<br>";
			echo "..." . "<br>";
			echo "...." . "<br>";
			echo "....." . "<br>";
			echo "ACTUALIZADA" . "<br>";
		}
		
			// Formulario para enviar una devolución al usuario
			echo "Puedes enviar el siguiente texto al usuario (" . $nombreusuario. ") para hacerle saber que no ha validado corectamente" ."<br>";
			echo "<form method='post' action='enviarmensajeprivado.php'>";
			$msj = "El dato que has enviado en la fecha " . $fechaenvio . " no fue considerado válido por decisión de un revisor. Sigue contribuyendo al proyecto!";
			echo "<textarea name = 'msjprivado' cols=40 rows=6>" . $msj . "</textarea>" ."<br>" ."<br>" ; 
			echo "<input type='hidden' name='nombreusuario' value='" .$nombreusuario . "'/>";
			echo "<input type='hidden' name='deviceID' value='" .$deviceID . "'/>";		
			echo "<input type='hidden' name='resvalidacion' value='corregido'/>";
			echo "<input type='hidden' name='mensajenoti' value='" .$msj . "'/>";			
			echo "<input type='submit' name='submit' value='Enviar mensaje' class='botonverde'>";
			echo "</form>";
			
			echo "<a href='overview.php'> Volver al listado";
			exit;			

}

echo "ID Muestra=" . $RepId . "<br>" . "<br>";
echo "<a href='overview.php'> Volver al listado";
?>



