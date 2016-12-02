app.controller("TipoDeEquipamentoFormController", function($scope, $mdDialog, data, send, $mdToast){
	this.equipamento = data.equipamento;
	var TipoDeEquipamentoFormCtrl = this;
	
	this.submitForm = function(){
		if(TipoDeEquipamentoFormCtrl.equipamento){
			delete TipoDeEquipamentoFormCtrl.equipamento.errors;
			
			if (!TipoDeEquipamentoFormCtrl.equipamento.hasOwnProperty("idEquipamento"))
				TipoDeEquipamentoFormCtrl.equipamento.status=true;
			
	        DWRTipoDeEquipamentoService.save(TipoDeEquipamentoFormCtrl.equipamento, {
	        	async: false,
	        	callback : function ( result ) {
	        		alert(result);
	        	}
	        });
			
		}
			/*
			send.post("/tipodeequipamento", TipoDeEquipamentoFormCtrl.equipamento)
			.then(function(response){
				if(TipoDeEquipamentoFormCtrl.equipamento.idEquipamento){
	                    $mdDialog.hide();
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
				}else{
					var error = "";
					for(i = 0; i < response.data.errors.length; i++){
			            error += response.data.errors[i].defaultMessage + "<br>";
			        }

			        $("#exception").html(error);

			        setTimeout(function(){
		                $("#exception").html("");
		            }, 3000);
			    }
	        });
		}else{
			$mdToast.show(
				$mdToast.simple()
					.textContent('Os dois campos são obrigatórios')
					.position("bottom right")
					.hideDelay(3000)
				);
		}*/
		
		
	}

	this.closeModal = function(){
	    $mdDialog.hide();
    }

});