app.controller("UsuarioFormController", function($scope, $mdDialog, data, send, $mdToast){
    this.usuario = data.usuario;
    this.tiposUsuarios = data.tiposUsuarios;
    this.usuarioLogado = data.usuarioLogado;
    var UserFormCtrl = this;

    this.submitForm = function(){
    	delete UserFormCtrl.usuario.errors;
    	delete UserFormCtrl.usuario.exception;
    	
    	
        DWRUsuarioService.save(UserFormCtrl.usuario, {
        	callback : function ( result ) {
        		alert(result);
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