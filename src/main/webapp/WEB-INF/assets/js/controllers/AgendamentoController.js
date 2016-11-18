"use strict";
app.controller("AgendamentoController", function($scope, $importService, send, $mdToast, $mdDialog){
    this.agendamentos = [];
    var AgndCtrl = this;

	this.carregarAgendamento = function(){
    	send.get("/agendamentos")
    		.success(function(agendamentos){
    			AgndCtrl.agendamentos = agendamentos;
    		});
    }

    this.excluir = function(event, idAgendamento, index){
    	var confirm = $mdDialog.confirm()
			.title("Tem certeza que deseja excluir esse agendamento?")
			.htmlContent("Essa ação não pode ser desfeita!")
			.ariaLabel("Excluir Agendamento")
			.ok("Excluir")
			.cancel("Cancelar");

		$mdDialog.show(confirm).then(function(){
			send.delete("/agendamentos", idAgendamento)
			.then(function(response){
				AgndCtrl.agendamentos.splice(index, 1);
			}, function(response){
				if(response.status == 403){
					$scope.toast403();
				}
			});
		}, function(){
			$mdDialog.hide();
		});
    }

});