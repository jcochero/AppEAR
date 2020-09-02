<?php
session_save_path('/home/users/web/b1282/ipg.appearcomar/cgi-bin/tmp');
session_start();

?>
</head>
    <body>

	<section class="container">
    <div class="login">
        <form  action="login.php" autocomplete="on" method="post"> 
        <h2 class="Titulo">Ingrese su usuario y contraseña</h2> 
        <p>
            <label for="username" class="Texto" data-icon="u" > Usuario </label>
            <input id="username" name="username" type="text"/>
            <label for="password" class="youpasswd" data-icon="p"> <br>
            <span class="Texto">Contraseña</span></label>
            <input id="password" name="password" type="password" /> 
        </p>
        <p>
            <input type="checkbox" name="loginkeeping" id="loginkeeping" value="loginkeeping" /> 
            <span class="Texto">Mantenerme conectado</span></p>
        <p>
			<label for="loginkeeping"></label>
            <input name="login" type="submit" class="Titulo" value="ENTRAR" />
        </p>
        </form>
    </div>
	</section>	

    </body>
</html>