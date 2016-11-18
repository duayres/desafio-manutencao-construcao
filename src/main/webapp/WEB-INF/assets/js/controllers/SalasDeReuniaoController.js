"use strict";
app.controller("SalasDeReuniaoController", function($scope, $importService, send, $mdDialog, $mdToast){
	this.salasDeReuniao = [];
	var SalasCtrl = this;

	this.carregarSalasDeReuniao = function(){
		send.get("/salasdereuniao")
			.success(function(data){
				SalasCtrl.salasDeReuniao = data;
			});
	}

	this.excluir = function(event, sala, index){
		var confirm = $mdDialog.confirm()
			.title("Tem certeza que deseja excluir essa sala?")
			.htmlContent("Ela não estará mais disponível depois de excluida.<p>Essa ação não pode ser desfeita!</p>")
			.ariaLabel("Excluir Sala")
			.ok("Excluir")
			.cancel("Cancelar");

		$mdDialog.show(confirm).then(function(){
			send.delete("/salasdereuniao", sala)
			.then(function(data){
				SalasCtrl.salasDeReuniao.splice(index, 1);
			}, function(response){
				if(response.status == 403){
					$scope.toast403();
				}
			});
		}, function(){
			$mdDialog.hide();
		});
		
	}

	this.openModal = function(event, _sala){
		$mdDialog.show({
            controller: "SalasDeReuniaoFormController as SalasFormCtrl",
            templateUrl: "./views/salasdereuniao/salasdereuniao-form.html",
            parent: angular.element(document.body),
            targetEvent: event,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen,
            locals:{
                data: {salasdereuniao: SalasCtrl.salasDeReuniao, sala: _sala}
            }
        }).then(function(sala){
            if(sala){
                SalasCtrl.salasDeReuniao.push(sala);
                $mdToast.show(
			      $mdToast.simple()
			        .textContent('Sala cadastrada com sucesso')
			        .position("bottom right")
			        .hideDelay(3000)
			    );
            }
        });
	}

});