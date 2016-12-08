"use strict";
app.controller('AgendamentoFormController', function($scope, $mdDialog , send, $location, $routeParams, $importService){
	$importService("DWRAgendamentoService");
	$importService("DWRUsuarioService");
	$importService("DWRTipoDeEquipamentoService");
	$importService("DWRMembroService");
	
	var AgndFormCtrl = this;
	this.agendamento = {};
	this.agendamento.membros = [];
	this.tiposDeEquipamento = [];
	this.usuarios = [];
	this.usuarioPesquisa = {};
    this.exception = "";
    
    $scope.$watch('AgndFormCtrl.agendamento.dataInicial',function (newValue, oldValue) {
    	if (newValue==oldValue) return;
    	AgndFormCtrl.atualizaMembros();
    });
    $scope.$watch('AgndFormCtrl.agendamento.dataFinal',function (newValue, oldValue) {
    	if (newValue==oldValue) return;
    	AgndFormCtrl.atualizaMembros();
    });
    
    
    this.atualizaMembros = function(){
    	if (AgndFormCtrl.agendamento.dataInicial == undefined || AgndFormCtrl.agendamento.dataFinal == undefined) return;
        DWRUsuarioService.listUsuariosLivresNoPeriodo(AgndFormCtrl.agendamento.dataInicial,AgndFormCtrl.agendamento.dataFinal,{
        	async: false,
        	callback: function(usuarios){
        		AgndFormCtrl.usuarios=usuarios;
        		}
        });
    }
    
   	this.cancelar = function(){
   		 var confirm = $mdDialog.confirm()
                .title("Tem certeza que deseja cancelar?")
                .textContent("As alterações não serão salvas")
                .ariaLabel("Cancelar")
                .ok("Sim")
                .cancel("Não");

            $mdDialog.show(confirm).then(function(){
                return $location.path("/agendamento");
            }, function(){
                $mdDialog.hide();
            });
   	}
   	
   	this.buscaLocalizacao = function (query) {
      send.get('localizacao/busca/' + escape(query))
        .then(function(result) {
          AgndFormCtrl.localizacoes= result.data;
          return result.data;
       });
      
      //return AgndFormCtrl.localizacoes.data;
   	}

    this.carregarDados = function(){
        DWRTipoDeEquipamentoService.listAll({
        	async: false,
        	callback: function(equipamentos){AgndFormCtrl.tiposDeEquipamento=equipamentos}
        });

        
        DWRUsuarioService.listAll({
        	async: false,
        	callback: function(usuarios){
        		AgndFormCtrl.usuarios=usuarios;
        		}
        });
        //AgndFormCtrl.usuarios={nome: "Complete as datas acima para liberar usuários"};

        if($routeParams.id){
            DWRAgendamentoService.findByIdAgendamento($routeParams.id,{
            	async: false,
            	callback: function(agendamento){
            		
            		
            		AgndFormCtrl.agendamento=agendamento;
					AgndFormCtrl.agendamento.dataInicial = new Date(agendamento.dataInicial);
					AgndFormCtrl.agendamento.dataFinal = new Date(agendamento.dataFinal);

					var i = 0;
					for(i = 0; i < AgndFormCtrl.tiposDeEquipamento.length; i++){
						if(AgndFormCtrl.agendamento.tipoDeEquipamento.idEquipamento == AgndFormCtrl.tiposDeEquipamento[i].idEquipamento){
							AgndFormCtrl.agendamento.tipoDeEquipamento = AgndFormCtrl.tiposDeEquipamento[i];
						}
					}
            	
            	}
            });
            
            DWRMembroService.findByAgendamentoId($routeParams.id,{
            	async: false,
            	callback: function(usuarios){
            		AgndFormCtrl.agendamento.membros=usuarios;
            	}
            });
            
        }
		/*if($routeParams.id){
			send.get("/getAgendamento", $routeParams.id)
				.success(function(agendamento){
					AgndFormCtrl.agendamento = agendamento;
					AgndFormCtrl.agendamento.data = new Date(agendamento.data);
					AgndFormCtrl.agendamento.hora = new Date(agendamento.hora);

					var i = 0;
					for(i = 0; i < AgndFormCtrl.tiposDeEquipamento.length; i++){
						if(AgndFormCtrl.agendamento.salaDeReuniao.idSala == AgndFormCtrl.tiposDeEquipamento[i].idSala){
							AgndFormCtrl.agendamento.salaDeReuniao = AgndFormCtrl.tiposDeEquipamento[i];
						}
					}
				});
		}*/
    
		//$scope.$apply();
    }

    this.verificaParticipante = function(usuario){
    	if(AgndFormCtrl.agendamento.idAgendamento){
    		var i = 0;
    		if (AgndFormCtrl.agendamento.membros.length==0) return;
    		for(i = 0; i < AgndFormCtrl.agendamento.membros.length; i++){
    			if(AgndFormCtrl.agendamento.membros[i].idUsuario == usuario.idUsuario){
    				return true;
    			}
    		}
    	}else{
    		if(AgndFormCtrl.agendamento.membros.length != 0){
    			var index = AgndFormCtrl.agendamento.membros.indexOf(usuario);

    			if(index != -1){
    				return true;
    			}else{
    				return false;
    			}
    		}else{
    			return false;
    		}
    	}
    }

    this.select = function(usuario){
    	if(AgndFormCtrl.agendamento.tipoDeEquipamento){
	    	var index = -1;
	    	var i = 0;
    		for(i = 0; i < AgndFormCtrl.agendamento.membros.length; i++){
    			if(AgndFormCtrl.agendamento.membros[i].idUsuario == usuario.idUsuario){
    				index = i;
    				break;
    			}
    		}
	    	/*var capacidade = AgndFormCtrl.agendamento.salaDeReuniao.capacidade;*/

	    	if(index == -1){
	    			AgndFormCtrl.agendamento.membros.push(usuario);
	    	}else{
	    		AgndFormCtrl.agendamento.membros.splice(index, 1);
	    	}
	    }else{
	    	$mdDialog.show(
		    	$mdDialog.alert()
		        .clickOutsideToClose(true)
		        .title('Selecione um tipo de equipamento')
		        .textContent('Você deve selecionar o tipo de equipamento antes de selecionar os membros')
		        .ariaLabel('Aviso')
		        .ok('Ok')
		    );
	    }
    }

    this.pesquisaUsuario = function(){
    	if((!AgndFormCtrl.usuarioPesquisa.nome || AgndFormCtrl.usuarioPesquisa.nome == '') && (!AgndFormCtrl.usuarioPesquisa.email || AgndFormCtrl.usuarioPesquisa.email == '')){
    		$(".usuario").each(function(){
    			$(this).removeClass("hide");
	    	});
    	}

    	$(".usuario").each(function(){
    		var nome = $(this).children().next().html().toLowerCase();
    		var email = $(this).children().next().next().html().toLowerCase();

    		if(AgndFormCtrl.usuarioPesquisa.nomeUsuario && AgndFormCtrl.usuarioPesquisa.nomeUsuario != ''){
	    		if(nome.indexOf(AgndFormCtrl.usuarioPesquisa.nomeUsuario) == -1){
	    			$(this).addClass("hide");
	    		}
	    	}

			if(AgndFormCtrl.usuarioPesquisa.email && AgndFormCtrl.usuarioPesquisa.email != ''){
	    		if(email.indexOf(AgndFormCtrl.usuarioPesquisa.email) == -1){
	    			$(this).addClass("hide");
	    		}
	    	}
    	});
    }

    this.submitForm = function(){
    	var agendEnvio = {};
    	var membros = {};
    	var localizacao = {};
    	angular.copy(AgndFormCtrl.agendamento, agendEnvio);
    	angular.copy(AgndFormCtrl.agendamento.localizacao, localizacao);
    	//angular.copy(AgndFormCtrl.agendamento.membros, membros);
    	delete agendEnvio.localizacao;
    	delete agendEnvio.usuarios;
    	delete agendEnvio.membros;
    	
    	membros=AgndFormCtrl.agendamento.membros;
    	
    	if (localizacao==null || $.isEmptyObject(localizacao)){
    		localizacao={idLocalizacao: null, nome: AgndFormCtrl.buscaTermo};
    	}
    	
    	DWRAgendamentoService.save(agendEnvio, localizacao, membros, {
        	async: false,
        	callback : function ( result ) {
        		if(result.idAgendamento){
        			return $location.path("/agendamento");
        		} else {
        			alert("bugou");
        		}
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
    	
    	
    	return;

    	agendEnvio.hora = AgndFormCtrl.agendamento.hora.getTime();
    	agendEnvio.data = AgndFormCtrl.agendamento.data.getTime();

    	var membros = {};

    	var i = 0;
    	for (i = 0; i < agendEnvio.membros.length; i++){
    		delete agendEnvio.membros[i].$$hashKey;
    		membros[i] = agendEnvio.membros[i].idUsuario;
    	}
    	agendEnvio.membros = membros;
    	agendEnvio.salaDeReuniao = agendEnvio.salaDeReuniao.idSala;
        delete agendEnvio.errors;

		var arq = agendForm.arquivo;
    	var arquivo = arq.files[0];

    	if(arquivo){
	    	var fd = new FormData();
	    	fd.append("arquivo", arquivo);
	    	send.file("/arquivoAgendamento", fd)
	    		.success(function(data){
	    			agendEnvio.urlArquivo = data.link;
	    			send.post("/agendamentos", agendEnvio)
						.then(function(response){
							return $location.path("/agendamento");
						}, function(response){
                            if(response.data.exception){
                                $("#exception").html(response.data.exception);
                            }

                            if(response.data.errors){
                                console.log(response);
                            }

                            setTimeout(function(){
                                $("#exception").html("");
                            }, 3000);
                        });
	    		});
    	}else{
    		send.post("/agendamentos", agendEnvio)
			.then(function(data){
				$mdDialog.show(
				    	$mdDialog.alert()
				        .clickOutsideToClose(true)
				        .title('Sucesso')
				        .textContent('Agendamento cadastrado com sucesso!')
				        .ariaLabel('Sucesso')
				        .ok('Ok')
				    );
				return $location.path("/agendamento");
			}, function(response){
                if(response.data.exception){
                    $("#exception").html(response.data.exception);
                }

                if(response.data.errors){
                    console.log(response);
                }

                setTimeout(function(){
                    $("#exception").html("");
                }, 3000);
            });
    	}
    	
		
    }
});