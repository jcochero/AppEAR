
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>

<?php
require("../connect/connectmapa.php");

$con = mysql_connect($host,$user,$pw) or die(mysql_error());
mysql_select_db($db) or die(mysql_error());
mysql_query("SET CHARACTER SET utf8");
mysql_query("SET NAMES 'utf8'");

if (!$con)
  {

  die('Could not connect: ' . mysql_error());

  }

 if ($_POST[lat]==""){
	 die('NO HAY UBICACION');
	 }

  
mysql_select_db("cis_id", $con);
$sql="INSERT INTO markers (lat, lng, dateandtime, tiporio, valorind1, valorind2, valorind3, valorind4, valorind5, valorind6, valorind7, valorind8, valorind9, valorind10, valorind11, valorind12, valorind13, valorind14, foto1path, foto2path, foto3path, foto4path, username, deviceID, privado, verificado, bingo, notas) VALUES ('$_POST[lat]','$_POST[lng]','$_POST[dateandtime]','$_POST[tiporio]','$_POST[valorind1]','$_POST[valorind2]','$_POST[valorind3]','$_POST[valorind4]','$_POST[valorind5]','$_POST[valorind6]','$_POST[valorind7]','$_POST[valorind8]','$_POST[valorind9]','$_POST[valorind10]','$_POST[valorind11]','$_POST[valorind12]','$_POST[valorind13]','$_POST[valorind14]','$_POST[foto1path]','$_POST[foto2path]','$_POST[foto3path]','$_POST[foto4path]','$_POST[username]','webreport','$_POST[privado]', 'No Verificado', '$_POST[bingo]', '$_POST[notas]')";

if (!mysql_query($sql,$con))
  {
  die('Error: ' . mysql_error());
  }

mysql_close($con);
 ?>
 
<?php
foreach ($_FILES["f74"]["error"] as $key => $error) {
    if ($error == UPLOAD_ERR_OK) {
        $tmp_name = $_FILES["f74"]["tmp_name"][$key];
        // basename() may prevent filesystem traversal attacks;
        // further validation/sanitation of the filename may be appropriate
        $name = basename($_FILES["f74"]["name"][$key]);
        move_uploaded_file($tmp_name, "../collecteddata/$name");
    }
}
?>
</body>
</html>