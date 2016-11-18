"use strict";
app.controller("AppController", function($scope, $timeout, $mdSidenav, send, $http, $mdToast){
    if(sessionStorage.getItem("logged") != "true"){
        location.href=base_url;
    }    
    
    //usado para abrir a sidebar em dispositivos menores
    $scope.toggleLeft = buildDelayedToggler('left');

    function debounce(func, wait, context) {
        var timer;
        return function debounced() {
            var context = $scope, args = Array.prototype.slice.call(arguments);
            $timeout.cancel(timer);
            timer = $timeout(function() {
                timer = undefined;
                func.apply(context, args);
            }, wait || 10);
        };
    }
    function buildDelayedToggler(navID) {
        return debounce(function() {
            // Component lookup should always be available since we are not using `ng-if`
            $mdSidenav(navID)
                .toggle()
                .then(function () {
                console.log("Abriu barra esquerda");
            });
        }, 200);
    }
    //fim sidebar
    
    //Adiciona a string do basic auth nos headers para todos os envios ajax
    $http.defaults.headers.common['Authorization'] = sessionStorage.getItem("authToken"); 
    
    //recupera do servidor os tipos de usuarios e coloca na scope
    $scope.tiposUsuarios = [];
    
    send.get("/tipoUsuario")
        .success(function(data){
            $scope.tiposUsuarios = data;  
        }
    );
    //fim tipos de usuarios

    $scope.toast403 = function(){
        $mdToast.show(
            $mdToast.simple()
                .textContent('Você não tem permissão para executar essa ação')
                .position("bottom right")
                .hideDelay(3000)
            );
    }
   
    
});