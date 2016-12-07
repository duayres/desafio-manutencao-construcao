app.controller("UsuarioFormController", function($scope, $mdDialog, data, send, $mdToast){
    this.usuario = data.usuario;
    this.tiposUsuarios = data.tiposUsuarios;
    this.usuarioLogado = data.usuarioLogado;
    var UserFormCtrl = this;

    this.submitForm = function(){
    	delete UserFormCtrl.usuario.errors;
    	delete UserFormCtrl.usuario.exception;
    	
    	
    	if (UserFormCtrl.usuario.senha!=UserFormCtrl.usuario.confSenha && !UserFormCtrl.usuario.idUsuario){
    		$("#exception").show().html("As senhas digitadas não conferem!");
    		setTimeout(function(){$("#exception").fadeOut()},5000);
    		return false;
    	}
    	
        DWRUsuarioService.save(UserFormCtrl.usuario, {
        	async: false,
        	callback : function ( result ) {
        		if(UserFormCtrl.usuario.idUsuario){
        			$mdDialog.hide();
        			if(UserFormCtrl.usuario.idUsuario == UserFormCtrl.usuarioLogado.idUsuario
        			   && UserFormCtrl.usuario.tipoUsuario != UserFormCtrl.usuarioLogado.tipoUsuario){
                        return location.href=base_url+"logout";
                    }else{
                        $mdDialog.hide();
                    }
        		} else {
        			$mdDialog.hide(result);
        		}
        	},
        	errorHandler : function (msg, exception){
        		erros=exception.localizedMessage.split("'");
        		var msgErro = "";
        		if (erros.length<3) msgErro=exception.localizedMessage;
        		for(i=1;i<erros.length;i=i+4){
        			msgErro+=erros[i]+"<br />";
        		}
        		$("#exception").show().html(msgErro);
        		setTimeout(function(){$("#exception").fadeOut()},5000*(erros.length/4));
        		return false;
        	}
        });
        
        /*
        if(UserFormCtrl.usuario.tipo){
            send.post("/usuarios", UserFormCtrl.usuario)
            .then(function(response){
                if(UserFormCtrl.usuario.idUsuario){
                    if(UserFormCtrl.usuario.idUsuario == UserFormCtrl.usuarioLogado.idUsuario){
                        return location.href=base_url;
                    }else{
                        $mdDialog.hide();
                    }
                }else{
                    $mdDialog.hide(response.data);
                }
            }, function(response){
                if(response.status == 403){
                    $mdToast.show(
                      $mdToast.simple()
                        .textContent('Você não tem permissão para executar essa ação')
                        .position("bottom right")
                        .hideDelay(3000)
                    );
                }

                if(response.status == 400){
                    $mdToast.show(
                      $mdToast.simple()
                        .textContent(response.data.exception)
                        .position("bottom right")
                        .hideDelay(3000)
                    );
                } 

                if(response.data.errors != null){
                    var error = "";
                    for(i = 0; i < response.data.errors.length; i++){
                        error += response.data.errors[i].defaultMessage + "<br>";
                    }

                    $("#exception").html(error);
                } 
            });
        }*/
    }

    this.closeModal = function(){
        $mdDialog.hide();
    }
}); 