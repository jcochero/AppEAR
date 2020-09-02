<link href="style.css" rel="stylesheet" type="text/css" />

<?php
session_save_path('/home/users/web/b1282/ipg.appearcomar/cgi-bin/tmp');
session_start();
echo "<a href='logout.php'>Cerrar sesion </a>";
echo "<br></br>";

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

	 
require("../connect/connectmapa.php");

$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

$id = $mysqli->real_escape_string($_GET["id"]);
$tipoeval = $mysqli->real_escape_string($_GET["tipoeval"]);

$res = $mysqli->query("SELECT * FROM markers WHERE id= '$id'");
echo "<br>";
echo "</br>";
echo "<span class='Titulo'> VERIFICANDO REPORTE CON ID=" . $id . "</span>";
echo "<br>";
echo "</br>";

echo "<span class='Texto'>";
while($row = mysqli_fetch_array($res)) {
   echo "ID: " . $row["id"] ."<br>";
   echo "LATITUD: " . $row["lat"] ."<br>";
   echo "LONGITUD: " . $row["lng"] ."<br>";
   echo "FECHA: " . $row["dateandtime"] ."<br>";
   echo "USUARIO: " . $row["username"]. "<br>";
   echo "REPORTE: " . $row["tiporio"] ."<br>";
   echo "DEVICEID: " . $row["deviceID"] ."<br>";
   echo "</span>";
   
 //  echo "MAPA: ";
 //  echo "<img src='http://maps.google.com/maps/api/staticmap?center=" . $row["lat"] . "," . $row["lng"] . "&zoom=13&scale=false&size=600x300&maptype=roadmap&format=png&visual_refresh=true&markers=size:mid%7Ccolor:0xff0000%7Clabel:1%7C" . $row["lat"] . "," . $row["lng"] . "'/>";

	echo "MAPA: ";
   echo "<script src='https://maps.googleapis.com/maps/api/js?v=3.exp'></script><div style='overflow:hidden;height:440px;width:700px;'><div id='gmap_canvas' style='height:440px;width:700px;'></div><div></div><style>#gmap_canvas img{max-width:none!important;background:none!important}</style></div><script type='text/javascript'>function init_map(){var myOptions = {zoom:10,center:new google.maps.LatLng(". $row["lat"] . "," . $row["lng"] ."),mapTypeId: google.maps.MapTypeId.ROADMAP};map = new google.maps.Map(document.getElementById('gmap_canvas'), myOptions);marker = new google.maps.Marker({map: map,position: new google.maps.LatLng(". $row["lat"] . "," . $row["lng"] .")});google.maps.event.addListener(marker, 'click', function(){infowindow.open(map,marker);});infowindow.open(map,marker);}google.maps.event.addDomListener(window, 'load', init_map);</script>";
   
	//CARGA DE IMAGENES
	
	echo "<br>";
	echo "</br>";
   	if ($row["foto1path"] != 'null')
	{
		echo "<a href='http://appearcomar.ipage.com/AppEAR/collecteddata/unverified/" . $row["foto1path"] .".jpg' target='new'> <img src='http://appearcomar.ipage.com/AppEAR/collecteddata/unverified/" . $row["foto1path"] .".jpg' width=10% ></a>";

		
	} else {
		echo "NO HAY IMAGENES";
	}
		
   	if ($row["foto2path"] != 'null')
	{
		echo "<a href='http://appearcomar.ipage.com/AppEAR/collecteddata/unverified/" . $row["foto2path"] .".jpg' target='new'> <img src='http://appearcomar.ipage.com/AppEAR/collecteddata/unverified/" . $row["foto2path"] .".jpg' width=10% ></a>";
		
	}
   	if ($row["foto3path"] != 'null')
	{
		echo "<a href='http://appearcomar.ipage.com/AppEAR/collecteddata/unverified/" . $row["foto3path"] .".jpg' target='new'> <img src='http://appearcomar.ipage.com/AppEAR/collecteddata/unverified/" . $row["foto3path"] .".jpg' width=10% ></a>";
		
	}   
   	if ($row["foto4path"] != 'null')
	{
		echo "<a href='http://appearcomar.ipage.com/AppEAR/collecteddata/unverified/" . $row["foto4path"] .".jpg' target='new'> <img src='http://appearcomar.ipage.com/AppEAR/collecteddata/unverified/" . $row["foto4path"] .".jpg' width=10% ></a>";
		
	}   
    	if ($row["foto5path"] != 'null')
	{
		echo "<a href='http://appearcomar.ipage.com/AppEAR/collecteddata/unverified/" . $row["foto5path"] .".jpg' target='new'> <img src='http://appearcomar.ipage.com/AppEAR/collecteddata/unverified/" . $row["foto5path"] .".jpg' width=10% ></a>";
		
	}     
 	//BOTONES DE VERIFICACION
	echo "<span class='Texto'>";  
	echo "<br>";
	echo "</br>";

	if ($row["tiporio"] == 'Llanura' or $row["tiporio"] == 'llanura')
	{
		echo "Indice (usuario): " . $row["indice"] ."<br>";
		echo "Precision (usuario): " . $row["valprecision"] ."<br>";
		echo "<br>";
		echo "</br>";
		echo "La zona cercana al sitio del arroyo, para que se usa mayormente (usuario): " . round(($row["valorind1"] * 120)/100) ."<br>";
		echo "Hay ganado cerca del arroyo? (usuario): " . round(($row["valorind2"] * 120)/100) ."<br>";
		echo "Como es la vegetacion de ribera? (usuario): " . round(($row["valorind3"] * 120)/100) ."<br>";
		echo "Hay plantas acuáticas? (usuario): " . round(($row["valorind4"] * 120)/100) ."<br>";
		echo "Es transparente el agua? (usuario): " . round(($row["valorind5"] * 120)/100) ."<br>";
		echo "Tiene mal olor el agua? (usuario): " . round(($row["valorind6"] * 120)/100) ."<br>";
		echo "Hay basura en la ribera? (usuario): " . round(($row["valorind7"] * 120)/100) ."<br>";
		echo "Como es la corriente? (usuario): " . round(($row["valorind8"] * 120)/100) ."<br>";
		echo "Esta desbordado el rio? O en su cauce? (usuario): " . round(($row["valorind7"] * 120)/100) ."<br>";
		echo "Esta canalizado o entubado? (usuario): " . round(($row["valorind8"] * 120)/100) ."<br>";
		echo "El fondo del arroyo tiene: (usuario): " . round(($row["valorind9"] * 120)/100) ."<br>";
		echo "Como es la pendiente de las margenes? (usuario): " . round(($row["valorind10"] * 120)/100) ."<br>";
	}
	elseif ($row["tiporio"] == 'Montana' or $row["tiporio"] == 'montana')
	{
		echo "Indice (usuario): " . $row["indice"] ."<br>";
		echo "Precision (usuario): " . $row["valprecision"] ."<br>";
		echo "<br>";
		echo "</br>";
		echo "La zona cercana al sitio del arroyo, para que se usa mayormente (usuario): " . round(($row["valorind1"] * 130)/100) ."<br>";
		echo "Hay ganado cerca del arroyo? (usuario): " . round(($row["valorind2"] * 130)/100) ."<br>";
		echo "Como es la vegetacion de ribera? (usuario): " . round(($row["valorind3"] * 130)/100) ."<br>";
		echo "Hay plantas acuáticas? (usuario): " . round(($row["valorind4"] * 130)/100) ."<br>";
		echo "Es transparente el agua? (usuario): " . round(($row["valorind5"] * 130)/100) ."<br>";
		echo "Tiene mal olor el agua? (usuario): " . round(($row["valorind6"] * 130)/100) ."<br>";
		echo "Hay basura en la ribera? (usuario): " . round(($row["valorind7"] * 130)/100) ."<br>";
		echo "Como es la corriente? (usuario): " . round(($row["valorind8"] * 130)/100) ."<br>";
		echo "Esta desbordado el rio? O en su cauce? (usuario): " . round(($row["valorind7"] * 130)/100) ."<br>";
		echo "Esta canalizado o entubado? (usuario): " . round(($row["valorind8"] * 130)/100) ."<br>";
		echo "El fondo del arroyo tiene: (usuario): " . round(($row["valorind9"] * 130)/100) ."<br>";
		echo "Como es la pendiente de las margenes? (usuario): " . round(($row["valorind10"] * 130)/100) ."<br>";
		echo "Esta sombreado el arroyo? (usuario): " . round(($row["valorind13"] * 130)/100) ."<br>";		
	}
	elseif ($row["tiporio"] == 'Laguna' or $row["tiporio"] == 'laguna')
	{
		echo "Indice (usuario): " . $row["indice"] ."<br>";
		echo "Precision (usuario): " . $row["valprecision"] ."<br>";
		echo "<br>";
		echo "</br>";
		echo "La zona cercana al sitio del arroyo, para que se usa mayormente (usuario): " . round(($row["valorind1"] * 100)/100) ."<br>";
		echo "Hay ganado cerca del arroyo? (usuario): " . round(($row["valorind2"] * 100)/100) ."<br>";
		echo "Como es la vegetacion de ribera? (usuario): " . round(($row["valorind3"] * 100)/100) ."<br>";
		echo "Hay juncales en la costa? (usuario): " . round(($row["valorind4"] * 100)/100) ."<br>";
		echo "Es transparente el agua? (usuario): " . round(($row["valorind5"] * 100)/100) ."<br>";
		echo "Tiene mal olor el agua? (usuario): " . round(($row["valorind6"] * 100)/100) ."<br>";
		echo "Hay basura en la ribera? (usuario): " . round(($row["valorind7"] * 100)/100) ."<br>";
		echo "Hay murallas en la costa? (usuario): " . round(($row["valorind8"] * 100)/100) ."<br>";
		echo "Hay muelles o escolleras cerca? (usuario): " . round(($row["valorind9"] * 100)/100) ."<br>";
		echo "Hay edificaciones temporarias? (usuario): " . round(($row["valorind10"] * 100)/100) ."<br>";
	}
	elseif ($row["tiporio"] == 'Estuario' or $row["tiporio"] == 'estuario')
	{
		echo "Indice (usuario): " . $row["indice"] ."<br>";
		echo "Precision (usuario): " . $row["valprecision"] ."<br>";
		echo "<br>";
		echo "</br>";
		echo "La zona cercana al sitio del arroyo, para que se usa mayormente (usuario): " . round(($row["valorind1"] * 130)/100) ."<br>";
		echo "Hay ganado cerca del arroyo? (usuario): " . round(($row["valorind2"] * 130)/100) ."<br>";
		echo "Como es la vegetacion de ribera? (usuario): " . round(($row["valorind3"] * 130)/100) ."<br>";
		echo "Hay juncales en la costa? (usuario): " . round(($row["valorind4"] * 130)/100) ."<br>";
		echo "Es transparente el agua? (usuario): " . round(($row["valorind5"] * 130)/100) ."<br>";
		echo "Tiene mal olor el agua? (usuario): " . round(($row["valorind6"] * 130)/100) ."<br>";
		echo "Hay basura en la ribera? (usuario): " . round(($row["valorind7"] * 130)/100) ."<br>";
		echo "Hay pajonales en la costa? (usuario): " . round(($row["valorind8"] * 130)/100) ."<br>";
		echo "Hay árboles en la costa? (usuario): " . round(($row["valorind9"] * 130)/100) ."<br>";
		echo "Hay murallas en la costa? (usuario): " . round(($row["valorind10"] * 130)/100) ."<br>";
		echo "Hay muelles o escolleras cerca? (usuario): " . round(($row["valorind11"] * 130)/100) ."<br>";
		echo "Hay edificaciones temporarias? (usuario): " . round(($row["valorind12"] * 130)/100) ."<br>";
		echo "Hay escombros en la costa?  (usuario): " . round(($row["valorind13"] * 130)/100) ."<br>";
	}	
		echo "<form method='post' action='enviarvalidacion.php'>";
		echo "<input type='hidden' name='RepId' value='" . $id . "'>";
		echo "<input type='hidden' name='fechaenvio' value='" . $row["dateandtime"] . "'>";		
		echo "<input type='hidden' name='nombreusuario' value='" . $row["username"] . "'>";	
		echo "<input type='hidden' name='deviceID' value='" . $row["deviceID"] . "'>";		
		echo "<input type='submit' name='submit' value='Validar'>";
		echo "<input type='submit' name='submit' value='No Validar'>";
		echo "<input type='button' name='cancel' value='Cancelar' onClick='history.back()'>";
		echo "</form>";
		echo "</form>";
		echo "</span";  		
}
?>



