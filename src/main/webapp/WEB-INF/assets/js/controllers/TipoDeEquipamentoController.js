"use strict";
app.controller("TipoDeEquipamentoController", function($scope, $importService, send, $mdDialog, $mdToast){
	var TipoDeEquipamentoCtrl = this;

    $importService("DWRTipoDeEquipamentoService");
	this.carregarTiposDeEquipamento = function(){
        DWRTipoDeEquipamentoService.listAll({
        	callback: function(equipamentos){TipoDeEquipamentoCtrl.tiposDeEquipamento=equipamentos}
        });
	}

	this.excluir = function(event, equipamento, index){
		var confirm = $mdDialog.confirm()
			.title("Tem certeza que deseja excluir esse tipo de equipamento?")
			.htmlContent("Ela não estará mais disponível depois de excluida.<p>Essa ação não poderá ser desfeita!</p>")
			.ariaLabel("Excluir Tipo de Equipamento")
			.ok("Excluir")
			.cancel("Cancelar");

		$mdDialog.show(confirm).then(function(){
			send.delete("/tipodeequipamento", equipamento)
			.then(function(data){
				TipoDeEquipamentoCtrl.tiposDeEquipamento.splice(index, 1);
			}, function(response){
				if(response.status == 403){
					$scope.toast403();
				}
			});
		}, function(){
			$mdDialog.hide();
		});
		
	}

	this.openModal = function(event, _equipamento){
		$mdDialog.show({
            controller: "TipoDeEquipamentoFormController as TipoDeEquipamentoFormCtrl",
            templateUrl: "./views/tipodeequipamento/tipodeequipamento-form.html",
            parent: angular.element(document.body),
            targetEvent: event,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen,
            locals:{
                data: {tiposDeEquipamento: TipoDeEquipamentoCtrl.tiposDeEquipamento, equipamento: _equipamento}
            }
        }).then(function(equipamento){
            if(equipamento){
            	TipoDeEquipamentoCtrl.tiposDeEquipamento.push(equipamento);
                $mdToast.show(
			      $mdToast.simple()
			        .textContent('Tipo de equipamento cadastrado com sucesso')
			        .position("bottom right")
			        .hideDelay(3000)
			    );
            }
        });
	}

});