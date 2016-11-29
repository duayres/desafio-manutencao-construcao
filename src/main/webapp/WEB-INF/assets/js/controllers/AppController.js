"use strict";
app.controller("AppController", function($scope, $timeout, $mdSidenav, send, $http, $mdToast){
    if(sessionStorage.getItem("logged") != "true"){
        //location.href=base_url; 
    	/*@todo aqui está o login*/
    }    
    
    //toggler da barra lateral (sidenav) para mobile (extraido do site do angularmat)
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
                .toggle();
        }, 200);
    }
    
    //recupera do servidor os tipos de usuarios e coloca na scope
    /*$scope.tiposUsuarios = [];
    
    send.get("/tipoUsuario")
        .success(function(data){
            $scope.tiposUsuarios = data;  
        }
    );*/
    //fim tipos de usuarios

    $scope.toast403 = function(){
        $mdToast.show(
            $mdToast.simple()
                .textContent('Somente administradores podem realizar esta ação')
                .position("bottom right")
                .hideDelay(5000)
            );
    }
   
    
});