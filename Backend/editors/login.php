<?php
session_save_path('/home/users/web/b1282/ipg.appearcomar/cgi-bin/tmp');
session_start();

if(isset($_POST['login'])){

require("connect_admin.php");
    $mysqli = new mysqli($host, $user, $pw, $db);
    $mysqli->query("SET CHARACTER SET utf8");
    $mysqli->query("SET NAMES 'utf8'");
    if ($mysqli->connect_error){
        die("Connection failed: " . $mysqli->connect_error);
    }


$uid = $mysqli->real_escape_string($_POST["username"]);
$pwd = $mysqli->real_escape_string($_POST["password"]);
$res =  $mysqli->query("SELECT * FROM admin_appear WHERE usr = '$uid' AND pss = '$pwd'");
	
if (!$res) {
    print json_encode("Error");
    echo "<br />" .  $mysqli->error;
    exit;
}else{
		if (mysqli_fetch_array($res) == 0) {
			echo "No Login";
			echo'<script>alert("USUARIO O CLAVE INCORRECTAS");</script>';
			$URL="index.php";
			echo '<META HTTP-EQUIV="refresh" content="0;URL=' . $URL . '">';
			echo "<script type='text/javascript'>document.location.href='{$URL}';</script>";
			exit;
		}
		else
		{
			echo "Login Successful";
			$_SESSION['userName'] = $uid;
			$URL="overview.php";
			echo '<META HTTP-EQUIV="refresh" content="0;URL=' . $URL . '">';
			echo "<script type='text/javascript'>document.location.href='{$URL}';</script>";
			exit;
		}
	}
}
?>