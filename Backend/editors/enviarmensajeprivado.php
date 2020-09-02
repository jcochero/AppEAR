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

$msjprivado = $_POST['msjprivado'];
$nombreusuario = $_POST['nombreusuario'];
$deviceID = $_POST['deviceID'];
$resvalidacion = $_POST['resvalidacion'];
$markerID = $_POST['RepId'];

echo "Enviando mensaje al usuario: " . $nombreusuario . "<br>";

		require("../connect/connect.php");

$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}
		$msjprivado2 = '#' . $msjprivado;
		
		//sistema nuevo de guardado en DB
		$res2 = $mysqli->query("INSERT INTO mensajes_appear(user, deviceID, mensaje, enviado, leido, markerID) VALUES ('$nombreusuario', '$deviceID', '$msjprivado', '1', '0', '$markerID')");
		
		if (!$res2) {
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
			echo "Guardado - Mensaje en cola para ser enviado al dispositivo" . "<br>" . "<br>" . "<br>";
		}		

		// COMIENZA ENVIO DE NOTIFICACION
        
		$t='AppEAR tiene un mensaje!';
		$noti = "Un experto revisó tu envío";
		
		// if ($resvalidacion = "invalido"){
			// $noti = "Dato inválido";
		// } elseif ($resvalidacion = "valido"){
			// $noti = "Dato correcto!";
		// }elseif ($resvalidacion = "corregido"){
			// $noti = "Dato corregido!";
		// }elseif ($resvalidacion = "sospechoso"){
			// $noti = "Dato sospechoso!";
		// }
		
		echo "Enviando notificación al Dispositivo:" . $deviceID ."<br>" . "<br>" . "<br>";
		
        $j=json_decode(notify($deviceID, $t, $noti));
       
        $succ=0;
        $fail=0;
       
        $succ=$j->{'success'};
        $fail=$j->{'failure'};
       
        print "Success: " . $succ . "<br>";
        print "Fail   : " . $fail . "<br>";
       


			echo "<a href='overview.php'> Volver al listado";
		exit;	

function notify ($deviceID, $t, $noti)
    {
    // API access key from Google API's Console
        if (!defined('API_ACCESS_KEY')) define( 'API_ACCESS_KEY', 'AAAAHGCbv2o:APA91bHVJnhsmC4Pu1UEKn-xzRBfQYnwV-YYWxoz3BGYV1NN-d6mc97c5kgIFsg3wQ2lFqQKz1_e0CSt9TyaLhTPj-d0OmMR2U29gS-8yBvS5SQSBFu7dWKmgtnKxpiBK9KhQ9wlDfTE' );
        $tokenarray = array($deviceID);
        // prep the bundle
        $msg = array
        (
            'title'     => $t,
            'body'     => $noti,
            'MyKey1'       => 'MyData1',
            'MyKey2'       => 'MyData2',
           
        );
        $fields = array
        (
            'registration_ids'     => $tokenarray,
            'data'            => $msg,
        );
        
        $headers = array
        (
            'Authorization: key=' . API_ACCESS_KEY,
            'Content-Type: application/json',
        );
        
        $ch = curl_init();
        curl_setopt( $ch,CURLOPT_URL, 'fcm.googleapis.com/fcm/send' );
        curl_setopt( $ch,CURLOPT_POST, true );
        curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
        curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
        curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
        curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
        $result = curl_exec($ch );
        curl_close( $ch );
        return $result;
    }
	// TERMINA ENVIO DE NOTIFICACION
	//TERMINAR

