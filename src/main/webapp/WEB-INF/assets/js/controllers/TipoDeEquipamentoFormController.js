app.controller("TipoDeEquipamentoFormController", function($scope, $mdDialog, data, send, $mdToast){
	this.equipamento = data.equipamento;
	var TipoDeEquipamentoFormCtrl = this;
	
	this.submitForm = function(){
		if(TipoDeEquipamentoFormCtrl.equipamento){
			delete TipoDeEquipamentoFormCtrl.equipamento.errors;
			
			if (!TipoDeEquipamentoFormCtrl.equipamento.hasOwnProperty("idEquipamento"))
				TipoDeEquipamentoFormCtrl.equipamento.status=true;
			
			var foto=equipamentoForm.foto.files[0];
			var manual=equipamentoForm.manual.files[0];
			
			
			if(foto && !window.waitUp){
		    	var fd = new FormData();
		    	fd.append("file", foto);
		    	send.file("/uploadFoto", fd)
		    	.success(function (data){
		    		if (data.uniqueFile=="error") {
		    			$('#exception').html("Erro ao mandar a foto"+data.msg).show();
		    			return false;
		    		}
		    		TipoDeEquipamentoFormCtrl.equipamento.foto=data.uniqueFile;
		    		TipoDeEquipamentoFormCtrl.submitForm();
		    	});
		    	window.waitUp=true;
		    	return false;//esperar até carregar o arquivo
			}
			
			if(manual && !window.waitUp){
		    	var fd = new FormData();
		    	fd.append("file", foto);
		    	send.file("/uploadManual", fd)
		    	.success(function (data){
		    		if (data.uniqueFile=="error") {
		    			$('#exception').html("Erro ao mandar a manual").show();
		    			return false;
		    		}
		    		TipoDeEquipamentoFormCtrl.equipamento.manual=data.uniqueFile;
		    		TipoDeEquipamentoFormCtrl.submitForm();
		    	});
		    	window.waitUp=true;
		    	return false;//esperar até terminar o upload
			}
			
	        DWRTipoDeEquipamentoService.save(TipoDeEquipamentoFormCtrl.equipamento, {
	        	async: false,
	        	callback : function ( result ) {
	        		if (TipoDeEquipamentoFormCtrl.equipamento.idEquipamento){
	        			$mdDialog.hide();
	        		} else {
	        			$mdDialog.hide(result);
	        		}
	        		window.waitUp=false;
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