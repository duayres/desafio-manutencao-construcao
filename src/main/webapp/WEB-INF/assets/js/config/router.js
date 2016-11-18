"use strict";
app.config(["$routeProvider", function($routeProvider){
    $routeProvider
    .when("/agendamento", {
        templateUrl: './views/agendamentos/agendamento-list.html',
        controller: 'AgendamentoController'
    })
    .when("/agendamento/novo", {
        templateUrl: "./views/agendamentos/agendamento-form.html",
        controller: "AgendamentoFormController as AgndFormCtrl"            
    })
    .when("/agendamento/editar/:id", {
        templateUrl: "./views/agendamentos/agendamento-form.html",
        controller: "AgendamentoFormController as AgndFormCtrl"            
    })
    .when("/usuario", {
        templateUrl: './views/usuarios/usuario-list.html',
        controller: 'AgendamentoController'
    })
    .when("/salasdereuniao", {
    	templateUrl: './views/salasdereuniao/salasdereuniao-list.html',
        controller: 'SalasDeReuniaoController'	
    })
}]);