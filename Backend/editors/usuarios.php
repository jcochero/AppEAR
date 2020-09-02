<link href="style.css" rel="stylesheet" type="text/css" />
<?php
session_save_path('/home/users/web/b1282/ipg.appearcomar/cgi-bin/tmp');
session_start();
	
if(isset($_SESSION['userName']))
	 {
		echo '<span class="Titulo"> BIENVENIDO ' . $_SESSION['userName'] . '</span>';
	 } 
	 else
	 {
	 	echo 'No estas registrado <br>';
		echo'<a href="index.php">LOGIN</a>';
		exit;
	 }

echo "<br></br><a href='logout.php'>Cerrar sesión </a>";
echo "<br></br><center><a href='overview.php'>Volver </a></center>";

require("../connect/connect.php");


//Una tabla con los usuarios
$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

//carga los resultados de la search bar si los hay
//si no carga todos
echo "<br>";
echo "</br>";
echo "<br>";
echo "</br>";

if(isset($_GET['submit'])){
	$query=$_GET['query'];
	echo $query;
	$res = $mysqli->query("SELECT user_id, user_name, email, location, tipousuario, puntostotales, evalsTotales FROM members_mosquito WHERE user_name='$query'");
} else {
	$res = $mysqli->query("SELECT user_id, user_name, email, location, tipousuario, puntostotales, evalsTotales FROM members_mosquito ORDER BY user_id DESC");
}


//ESTILO TABLAS

echo "<style type='text/css'>";
echo ".tg  {border-collapse:collapse;border-spacing:0;border-color:#999;margin:0px auto;}";
echo ".tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 11px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-color:#999;color:#444;background-color:#F7FDFA;border-top-width:1px;border-bottom-width:1px;}";
echo ".tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 11px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-color:#999;color:#fff;background-color:#26ADE4;border-top-width:1px;border-bottom-width:1px;}";
echo ".tg .tg-yw4l{font-size:12px;vertical-align:top}";
echo ".tg .tg-6k2t{background-color:#D2E4FC;vertical-align:top}";
echo "</style>";

//BUSQUEDa

echo   "<center><form action='' method='GET'>";
echo   "     <input type='text' name='query' />";
echo   "     <input type='submit' name='submit' value='Buscar usuario' />";
echo   " </form></center>";


echo "<span class='Texto'>";
echo "<br>";
echo "</br>";
echo "<p style='color:red; text-align: center'>TOTAL DE USUARIOS:". mysqli_fetch_array($res) . "</p>";
echo "<table class='tg'>";
echo "<tr>";
echo "<th class='tg-yw4l'> Id </th>";
echo "<th class='tg-yw4l'> Usuario </th>";
echo "<th class='tg-yw4l'> Email </th>";
echo "<th class='tg-yw4l'> Localización </th>";
echo "<th class='tg-yw4l'> Tipo de usuario </th>";
echo "<th class='tg-yw4l'> Puntos totales </th>";
echo "<th class='tg-yw4l'> Evaluaciones totales </th>";
echo "</tr>";
	
	while($row = mysqli_fetch_array($res)) {
		   echo "<tr>";
		   echo "<td>" . $row["user_id"] . "</td>";
		   echo "<td>" . $row["user_name"] . "</td>";
		   echo "<td>" . $row["email"] . "</td>";
		   echo "<td>" . $row["location"] . "</td>";   
		   echo "<td>" . $row["tipousuario"] . "</td>"; 
		   echo "<td>" . $row["puntostotales"] . "</td>";
		   echo "<td>" . $row["evalsTotales"] . "</td>";
		   echo "</tr>";
	}
echo "</table>";

echo "</span>";	
?>