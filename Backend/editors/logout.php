<?php
session_save_path('/home/users/web/b1282/ipg.appearcomar/cgi-bin/tmp');
session_start();
	session_destroy();
	header("Location:index.php");
	exit; 
?>
