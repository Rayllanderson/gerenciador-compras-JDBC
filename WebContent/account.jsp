<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!------ Include the above in your HEAD tag ---------->

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
	
	
<!--   --><link href="resource/css/account.css" type="text/css" rel="stylesheet" />

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>                    


<style type="text/css">

.overlay {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100%;
  width: 100%;
  opacity: 0;
  transition: .3s ease;
  background-color: red;
}

#file:hover {
  background-color: yellow;
}


</style>

<title>My Account</title>
</head>
<body class="account" >

                   <header>

                         <nav class="navbar navbar-expand navbar-dark bg-primary">
                            <a class="navbar-brand" href="categorias.jsp"><img src="resource/img/back.png" width="25px" height="20px" /></a>

                            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample02" aria-controls="navbarsExample02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

                            <div class="collapse navbar-collapse" id="navbarsExample02">
                                <ul class="navbar-nav mr-auto">
                                    <li class="nav-item">
                                        <a class="nav-link" href="home.jsp">Home <span class="sr-only">(current)</span></a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="categorias">Categorias</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="produtos">Produtos</a>
                                    </li>
                                </ul>
                            </div>
                            
                            
                            <button type="button" class="btn btn-outline-light" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <!-- botao user -->
    <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-person-circle" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path d="M13.468 12.37C12.758 11.226 11.195 10 8 10s-4.757 1.225-5.468 2.37A6.987 6.987 0 0 0 8 15a6.987 6.987 0 0 0 5.468-2.63z"/>
  <path fill-rule="evenodd" d="M8 9a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
  <path fill-rule="evenodd" d="M8 1a7 7 0 1 0 0 14A7 7 0 0 0 8 1zM0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8z"/>
</svg>
  </button>

                            <div class="dropdown-menu dropdown-menu-right">
                                <a class="dropdown-item" href="my-account?action=view">Minha Conta</a>
                                <a class="dropdown-item" href="#">Another action</a>
                                <a class="dropdown-item" href="#">Something else here</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="logout">Logout</a>
                            </div>

</nav>
</header>

	<div class="container">
	  	<div class="alert alert-success" id="success-alert" style="margin-top: 1%">
   		 <button type="button" class="close" data-dismiss="alert">x</button>
   	<h4 id="titulo"></h4> <p id="alertMsg"></p>
  	</div>
  	
  	
		<div class="card card-signin my-5">
			<article class="card-body mx-auto">
				<h4 class="card-title mt-3 text-center">Sua Conta</h4>
				<div class="text-center">
				

					
				  
				</div>
				
				
					<form class="" action="my-account?action=editar"
		method="POST" id="formUser" enctype="multipart/form-data">			
		
			<div class="image-upload">
			  				<label for="file">

					<c:if test="${user.getFoto().isEmpty() || user.getFoto() == null}">
							
						<img id="target"class="target" src="resource/img/user.png" width="220" height="230" title="Nova foto de perfil"
						 alt="Imagem de perfil"> 
  				
						<input id="file" type="file" name="file" style="display: none" onchange="upload()" accept="image/*"/>
							
					</c:if>

					<c:if test="${!user.getFoto().isEmpty() && user.getFoto() != null}">

			  			 <img id="target" src="<c:out value="${user.getFoto()}"/>" width="260" height="260" style="border-radius: 50%;"
						 alt="Imagem de perfil" title="Nova foto de perfil"> 
  				
						<input id="file" type="file" name="file" style="display: none" onchange="upload()" accept="image/*"/>
	
					</c:if>
				</label>	
			</div>
				
			<input value="${user.id}" name="id" hidden="true" id="id">
				<span class="input-text"> Nome </span>
					<div class="form-group input-group">
						<input name="nome" class="form-control" placeholder="Nome"
							type="text" value="${user.name}" name="nome" id="nome">
					</div>
					<!-- form-group// -->
					<span class="input-text"> Username</span>
					<div class="form-group input-group">
						<input name="username" class="form-control" placeholder="Username"
							type="text" value="${user.username}" required="required" name="username" id="username">
					</div>
					
					<div style="display: flex; justify-content: space-between;">
						<div class="text-left">
						
							<input id="" class="btn btn-success" type="submit" 				
							value="Salvar" />
						</div>
					
					
					
						<div class="text-right">
							<a href="index.jsp">Mudar senha</a>
						</div>
					
					</div>
					</form>
			</article>
			
		</div>
		<!-- card.// -->
	</div>
	


<hr>
<div class="container">
	
		<div class="card card-signin my-5">
			<article class="card-body mx-auto">
			<p id="pName">Ol�, <strong> ${user.name}</strong>! </p>
			<p>Voc� possui ${tListas} listas no total </p>
			<p>Voc� comprou ${nProdutosComprados} produtos de um total de ${tProdutos} </p>
			<p>Voc� j� gastou ${tGasto} </p>
			<p>Voc� pretende gastar ${tEstipulado} </p>
			</article>
			</div>
			</div>
			
			
	
	<script type="text/javascript">


	$(".alert").hide();

	
	let error = "${error}"
	let success = "${success}"

	
	if (error) {
		console.log(error)
		alertBoostrap(error, 'alert alert-danger')
		error= '';
	}

	if (success) {
		console.log(success)
		alertBoostrap(success, 'alert alert-success')
		success = '';
	}

	function alertBoostrap(msg, classe){
	  $(".alert").show();
	  document.getElementById('alertMsg').innerHTML = msg;
	  document.getElementById("success-alert").className = classe;
	  $("#success-alert").fadeTo(2700, 500).slideUp(500, function(){
	    $("#success-alert").slideUp(500);
	});
	}

	function setColorAlert(response){
		if (response == 'Nenhuma altera��o foi detectada.'){
			alertBoostrap(response, 'alert alert-warning')
		}else{
			alertBoostrap(response, 'alert alert-success')
		}
	}
	
	</script>
	
	<script type="text/javascript">
	if ( window.history.replaceState ) {
		  window.history.replaceState( null, null, window.location.href );
		}
        	</script>
  
  <script type="text/javascript">

	function upload() {
	    console.log('ola1')
	    var target = document.querySelector("#target");
	    var file = document.querySelector("#file").files[0];
	    var reader = new FileReader();
	    reader.onloadend = function() {
	        target.src = reader.result;
		 console.log('ola3')
	    };
	    if (file) {
	        console.log('ola2')
	        target.style.width = '260px';
	        target.style.height = '260px';
	        target.style.borderRadius = '50%';
	        reader.readAsDataURL(file)
	    } else {
	        target.src = "resource/img/user.png"
	    }
	}
  
  
  </script>  



<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="resource/javascript/accountAjax.js"></script>   

</body>
</html>