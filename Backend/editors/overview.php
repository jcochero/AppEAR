<link href="style.css" rel="stylesheet" type="text/css" />
<link href="css/style2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/styleTabs.css">
<meta charset="utf-8">


<?php
//ESTILO DE TABLAS

echo "<style type='text/css'>
.tg  {border-collapse:collapse;border-spacing:0;border-color:#999;margin:0px auto;}
.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 11px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-color:#999;color:#444;background-color:#F7FDFA;border-top-width:1px;border-bottom-width:1px;}
.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 11px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-color:#999;color:#fff;background-color:#26ADE4;border-top-width:1px;border-bottom-width:1px;}
.tg .tg-yw4l{font-size:12px;vertical-align:top}
.tg .tg-6k2t{background-color:#D2E4FC;vertical-align:top}
</style>";

// LOGIN CHECK
session_save_path('/home/users/web/b1282/ipg.appearcomar/cgi-bin/tmp');
session_start();
if(isset($_SESSION['userName']))
	 {
		echo '';
	 } 
	 else
	 {
	 	echo '<center> No estas registrado <br>';
	 	echo 'username:' . $_SESSION['userName'];
		echo'<a href="index.php">LOGIN</a></center>';
		exit;
	 }


// LOGIN OUT
echo "<br></br><center><a href='logout.php' class='botonrojo'>Cerrar sesión </a></center>";
echo "<br>";
echo "</br>";
echo "<br>";
echo "</br>";
// LINK A USUARIOS
echo "<center><a href='usuarios.php' class='botonblanco'>Lista de usuarios</a></center>";
echo "<br>";
echo "</br>";
//CONEXION GENERAL
require("../connect20/connectmapa.php");


//TABS

echo "<script src='https://code.jquery.com/jquery-1.12.4.js'></script>";
echo "<script src='https://code.jquery.com/ui/1.12.1/jquery-ui.js'></script>";
echo " <script>
  $( function() {
    $( '#tabs' ).tabs();
  } );
  </script>";
echo "<center><div id='tabs' style='width: 80%'>";
echo " <ul>
    <li><a href='#tabs-1'>No verificados</a></li>
    <li><a href='#tabs-2'>Corregidos</a></li>
    <li><a href='#tabs-3'>Verificados</a></li>
    <li><a href='#tabs-4'>Sospechosos</a></li>		
    <li><a href='#tabs-5'>Incorrectos</a></li>	
  </ul>";


//Una tabla con los no verficados
echo " <div id='tabs-1'>";
$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

$res = $mysqli->query("SELECT * FROM markers WHERE verificado= 'No Verificado'");

//REPORTES NO VERIFICADOS
echo "<span class='Texto'>";
echo "<br>";
echo "</br>";
//echo "<p style='color:red; text-align: center'>TOTAL DE REPORTES NO VERIFICADOS:". mysqli_num_rows($res) . "</p>";
echo "<table class='tg'>";
echo "<tr>";
echo "<th class='tg-yw4l'> Id </th>";
echo "<th class='tg-yw4l'> Latitud </th>";
echo "<th class='tg-yw4l'> Longitud </th>";
echo "<th class='tg-yw4l'> Fecha </th>";
echo "<th class='tg-yw4l'> Tipo de dato </th>";
echo "<th class='tg-yw4l'> Usuario </th>";
echo "<th class='tg-yw4l'> Verificar </th>";
echo "<th class='tg-yw4l'> Visto </th>";
echo "</tr>";

while($row = mysqli_fetch_array($res)) {
    echo "<tr>";
    echo "<td>" . $row["id"] . "</td>";
    echo "<td>" . $row["lat"] . "</td>";
    echo "<td>" . $row["lng"] . "</td>";
    echo "<td>" . $row["dateandtime"] . "</td>";
    echo "<td>" . $row["tipoevaluacion"] . "</td>";
    echo "<td>" . $row["username"] . "</td>";
    echo "<td><a href='verificar.php?id=" . $row["id"] . '&tipoeval=' . $row["tipoevaluacion"] . "'>Verificar</a></td>";
    if ($row["vistoeditor"] == "1"){
        echo "<td><input type='checkbox' disabled='disabled' name='vistoeditor' value='vistoeditor' checked> Visto <br></td>";
    } else {
        echo "<td><input type='checkbox' disabled='disabled' name='vistoeditor' value='vistoeditor'> Visto <br></td>";
    }
    echo "</tr>";
}
echo "</table>";
echo "</div>";

//REPORTES CORREGIDOS

//  UNA TABLA CON LOS CORREGIDOS
echo " <div id='tabs-2'>";
//$res = $mysqli->query("SELECT * FROM markers WHERE verificado= 'Corregido'");
echo "<br>";
echo "</br>";
echo "<p style='color:orange; text-align: center'>TOTAL DE REPORTES CORREGIDOS:". mysqli_num_rows($res) . "</p>";
echo "<table class='tg'>";
echo "<tr>";
echo "<th class='tg-yw4l'> Id </th>";
echo "<th class='tg-yw4l'> Latitud </th>";
echo "<th class='tg-yw4l'> Longitud </th>";
echo "<th class='tg-yw4l'> Fecha </th>";
echo "<th class='tg-yw4l'> Tipo de dato </th>";
echo "<th class='tg-yw4l'> Usuario </th>";
echo "<th class='tg-yw4l'> Estado </th>";
echo "<th class='tg-yw4l'> Verificado por </th>";
echo "</tr>";

while($row = mysqli_fetch_array($res)) {
    echo "<tr>";
    echo "<td>" . $row["id"] . "</td>";
    echo "<td>" . $row["lat"] . "</td>";
    echo "<td>" . $row["lng"] . "</td>";
    echo "<td>" . $row["dateandtime"] . "</td>";
    echo "<td>" . $row["tipoevaluacion"] . "</td>";
    echo "<td>" . $row["username"] . "</td>";
    echo "<td>" . $row["verificado"] . "</td>";
    echo "<td>" . $row["verificadopor"] . "</td>";
    echo "<td><a href='verificar.php?id=" . $row["id"] . '&tipoeval=' . $row["tipoevaluacion"] . "'>Editar</a></td>";
    echo "</tr>";
}
echo "</table>";
echo "</div>";

// UNA TABLA CON LOS YA VALIDADOS
echo " <div id='tabs-3'>";
//$res = $mysqli->query("SELECT * FROM markers WHERE verificado= 'Verificado'");
echo "<br>";
echo "</br>";
echo "<p style='color:green; text-align: center'>TOTAL DE REPORTES VERIFICADOS:". mysqli_num_rows($res) . "</p>";
echo "<table class='tg'>";
echo "<tr>";
echo "<th class='tg-yw4l'> Id </th>";
echo "<th class='tg-yw4l'> Latitud </th>";
echo "<th class='tg-yw4l'> Longitud </th>";
echo "<th class='tg-yw4l'> Fecha </th>";
echo "<th class='tg-yw4l'> Tipo de dato </th>";
echo "<th class='tg-yw4l'> Usuario </th>";
echo "<th class='tg-yw4l'> Estado </th>";
echo "<th class='tg-yw4l'> Verificado por </th>";
echo "</tr>";

while($row = mysqli_fetch_array($res)) {
    echo "<tr>";
    echo "<td>" . $row["id"] . "</td>";
    echo "<td>" . $row["lat"] . "</td>";
    echo "<td>" . $row["lng"] . "</td>";
    echo "<td>" . $row["dateandtime"] . "</td>";
    echo "<td>" . $row["tipoevaluacion"] . "</td>";
    echo "<td>" . $row["username"] . "</td>";
    echo "<td>" . $row["verificado"] . "</td>";
    echo "<td>" . $row["verificadopor"] . "</td>";
    echo "<td><a href='verificar.php?id=" . $row["id"] . '&tipoeval=' . $row["tipoevaluacion"] . "'>Editar</a></td>";
    echo "</tr>";
}
echo "</table>"	;
echo "</div>";

// UNA TABLA CON LOS SOSPECHOSOS
echo " <div id='tabs-4'>";
//$res = $mysqli->query("SELECT * FROM markers WHERE verificado= 'Sospechoso'");
echo "<p style='color:green; text-align: center'>TOTAL DE REPORTES SOSPECHOSOS:". mysqli_num_rows($res) . "</p>";
echo "<table class='tg'>";
echo "<tr>";
echo "<th> Id </th>";
echo "<th> Lat </th>";
echo "<th> Long </th>";
echo "<th> Fecha </th>";
echo "<th> TipoEvaluacion </th>";
echo "<th> Usuario </th>";
echo "<th> Estado </th>";
echo "<th> Verificado por </th>";
echo "</tr>";

while($row = mysqli_fetch_array($res)) {
    echo "<tr>";
    echo "<td>" . $row["id"] . "</td>";
    echo "<td>" . $row["lat"] . "</td>";
    echo "<td>" . $row["lng"] . "</td>";
    echo "<td>" . $row["dateandtime"] . "</td>";
    echo "<td>" . $row["tipoevaluacion"] . "</td>";
    echo "<td>" . $row["username"] . "</td>";
    echo "<td> Reporte sospechoso </td>";
    echo "<td>" . $row["verificadopor"] . "</td>";
    echo "<td><a href='verificar.php?id=" . $row["id"] . '&tipoeval=' . $row["tipoevaluacion"] . "'>Editar</a></td>";
    echo "</tr>";
}
echo "</table>"	;
echo "	</div>";


// UNA TABLA CON LOS INCORRECTOS
echo " <div id='tabs-5'>";
//$res = $mysqli->query("SELECT * FROM markers WHERE verificado= 'Incorrecto'");
echo "<p style='color:green; text-align: center'>TOTAL DE REPORTES NO VÁLIDOS:". mysqli_num_rows($res) . "</p>";
echo "<table class='tg'>";
echo "<tr>";
echo "<th> Id </th>";
echo "<th> Lat </th>";
echo "<th> Long </th>";
echo "<th> Fecha </th>";
echo "<th> TipoEvaluacion </th>";
echo "<th> Usuario </th>";
echo "<th> Estado </th>";
echo "<th> Verificado por </th>";
echo "</tr>";

while($row = mysqli_fetch_array($res)) {
    echo "<tr>";
    echo "<td>" . $row["id"] . "</td>";
    echo "<td>" . $row["lat"] . "</td>";
    echo "<td>" . $row["lng"] . "</td>";
    echo "<td>" . $row["dateandtime"] . "</td>";
    echo "<td>" . $row["tipoevaluacion"] . "</td>";
    echo "<td>" . $row["username"] . "</td>";
    echo "<td> No valido </td>";
    echo "<td>" . $row["verificadopor"] . "</td>";
    echo "<td><a href='verificar.php?id=" . $row["id"] . '&tipoeval=' . $row["tipoevaluacion"] . "'>Editar</a></td>";
    echo "</tr>";
}
echo "</table>"	;
echo "	</div>";
echo "</div></center>";
echo "</span>";
?>