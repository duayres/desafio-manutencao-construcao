"use strict";
app.controller("UsuarioController", function($scope, $importService, send, $mdDialog, $mdToast){
    $importService("DWRUsuarioService");
    this.usuarios = [];

    if(sessionStorage.getItem("usuario") != null && sessionStorage.getItem("usuario") != ""){
        this.usuarioLogado = JSON.parse(sessionStorage.getItem("usuario"));
    }else{
        this.usuarioLogado = "";
    }

    var UserCtrl = this;
    
    this.carregarUsuarios = function(){
        DWRUsuarioService.listAll({
        	async: false,
        	callback: function(usuarios){
        		UserCtrl.usuarios=usuarios;
        		}
        });
        DWRUsuarioService.listTiposUsuario({
        	async: false,
        	callback: function(tipos){UserCtrl.tipos=tipos}
        });
		//$scope.$apply();
    }
    
    this.openModal = function(event, _usuario){
    	var auth=true;
    	$.ajax({
    		url:"usuario/isSuperUser",
    		async: false,
    		complete: function (xhr,msg){
    			if (xhr.status==403){
                    auth=false;
                    $scope.toast403();
                    return;
    			}
    		}
    	})

        if (!auth) return;
    	
    	if (_usuario == null){
    		var _usuario = {status: true};
    	}
        $mdDialog.show({
            controller: "UsuarioFormController as UserFormCtrl",
            templateUrl: "./views/usuarios/usuario-form.html",
            parent: angular.element(document.body),
            targetEvent: event,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen,
            locals:{
                data: {usuarios: UserCtrl.usuarios, usuario: _usuario, tiposUsuarios: UserCtrl.tipos, usuarioLogado: UserCtrl.usuario}
            }
        }).then(function(usuario){
            if(usuario){
                UserCtrl.usuarios.push(usuario);
                $mdToast.show(
                  $mdToast.simple()
                    .textContent('Usuario cadastrado com sucesso')
                    .position("bottom right")
                    .hideDelay(3000)
                );
            }
        });
    }

    this.toggleAtivoUsuario = function(usuario){
        if(usuario.status == false){
            var confirm = $mdDialog.confirm()
                .title("Tem certeza que deseja desativar esse usuario?")
                .textContent("Se você desativar este usuário ele não terá mais acesso ao sistema")
                .ariaLabel("Desativar Usuario")
                .ok("Confirmar")
                .cancel("Cancelar");

            $mdDialog.show(confirm)
            .then(function(){
            	var idUsuario=usuario.idUsuario;
                send.post("/api/usuario/alter-status", {id: idUsuario, status:usuario.status})
                .then(function(){
                    return;
                }, function(response){
                    if(response.status == 403){
                        usuario.status = !usuario.status;
                        $scope.toast403();
                    }
                });
            }, function(){
                $mdDialog.hide();
                usuario.status = !usuario.status;
            });
        } else {
        	var idUsuario=usuario.idUsuario;
            send.post("/api/usuario/alter-status", {id: idUsuario, status:usuario.status})
            .then(function(){
                //
            }, function(response){
                if(response.status == 403){
                    usuario.status = !usuario.status;
                    $scope.toast403();
                }
            });
        }
    }
    
    this.login = function(){
    	
    	/*$.post("login",$scope.usuario)
    	.done(function (data) {
    		//alert("ok xD"+JSON.stringify(data));
    		if (typeof data === "object"){
    			sessionStorage.setItem("usuario", JSON.stringify(data));
    			//location.href=base_url+"home";
    		} else {
    			if (data.indexOf('') !== -1){location.href=base_url+"home";return;};
    			$("#exception").html("Usuário e/ou senha incorretos");  
    			console.log("não é um obj");
    		}
    	})
    	.fail(function (data) {
    		console.log("erro de requisicao");
    	});*/
    	
        send.post("login", $scope.usuario)
        .then(function(response){
        	if (typeof response.data === "object"){
    			sessionStorage.setItem("usuario", JSON.stringify(response.data));
    			location.href=base_url+"home";
    			return false;
    		} else {
    			if (response.data.indexOf('sidebar') !== -1){//spring-sec vampiro de response
    				send.get("currentUser")
	    		        .then(function(response){
	    		        	sessionStorage.setItem("usuario", JSON.stringify(response.data));
	    	    			location.href=base_url+"home";
	    		        });
    				return;
    			}
    			$("#exception").html("Usuário e/ou senha incorretos");  
    			console.log("não é um obj");
    			return false;
        	}
        });
    }
    
    this.logout = function(){
        sessionStorage.setItem("logged", "");
        sessionStorage.setItem("usuario", "");
        location.href=base_url+"logout";
    }
});