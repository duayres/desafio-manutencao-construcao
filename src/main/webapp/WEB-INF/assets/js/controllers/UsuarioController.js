"use strict";
app.controller("UsuarioController", function($scope, $importService, send, $mdDialog, $mdToast){
    $importService("UsuariosService");
    this.usuarios = [];

    if(sessionStorage.getItem("usuarioLogado") != null && sessionStorage.getItem("usuarioLogado") != ""){
        this.usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));
    }else{
        this.usuarioLogado = "";
    }

    var UserCtrl = this;
    
    this.carregarUsuarios = function(){
        send.get("/usuarios")
            .success(function(usuarios){
                UserCtrl.usuarios = usuarios;
        });
        // UsuariosService.listAll();
    }
    
    this.openModal = function(event, _usuario){
        $mdDialog.show({
            controller: "UsuarioFormController as UserFormCtrl",
            templateUrl: "./views/usuarios/usuario-form.html",
            parent: angular.element(document.body),
            targetEvent: event,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen,
            locals:{
                data: {usuarios: UserCtrl.usuarios, usuario: _usuario, tiposUsuarios: $scope.tiposUsuarios, usuarioLogado: UserCtrl.usuarioLogado}
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
        if(usuario.ativo == false){
            var confirm = $mdDialog.confirm()
                .title("Tem certeza que deseja desativar esse usuario?")
                .textContent("Ela não estará mais disponível e não poderá mais fazer login depois de desativado.")
                .ariaLabel("Desativar Usuario")
                .ok("Desativar")
                .cancel("Cancelar");

            $mdDialog.show(confirm)
            .then(function(){
                delete usuario.errors;
                send.post("/usuarios", usuario)
                .then(function(){
                    return;
                }, function(response){
                    if(response.status == 403){
                        usuario.ativo = !usuario.ativo;
                        $scope.toast403();
                    }
                });
            }, function(){
                $mdDialog.hide();
                usuario.ativo = !usuario.ativo;
            });
        }else{
            delete usuario.errors;
            send.post("/usuarios", usuario)
            .then(function(){
                return;
            },function(response){
                if(response.status == 403){
                    usuario.ativo = !usuario.ativo;
                    $scope.toast403();
                }
            });
        }
    }
    
    this.login = function(){
        send.post("login", $scope.usuario)
        .then(function(response){
            sessionStorage.setItem("logged", "true");
            var authString = btoa(response.data.email+":"+response.data.senha);
            
            sessionStorage.setItem("authToken",  "Basic "+authString);
            sessionStorage.setItem("usuarioLogado", JSON.stringify(response.data));
            location.href=base_url+"home";
        }, function(response){
            if(response.status == 400){
                $("#exception").html(response.data.exception);

                setTimeout(function(){
                    $("#exception").html("");                    
                }, 3000);
            }
        });
    }
    
    this.logout = function(){
        sessionStorage.setItem("logged", "");
        sessionStorage.setItem("usuarioLogado", "");
        location.href=base_url;
    }
});