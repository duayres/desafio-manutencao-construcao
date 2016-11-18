"use strict";
app.controller('AgendamentoFormController', function($scope, $mdDialog , send, $location, $routeParams){
	var AgndFormCtrl = this;
	this.agendamento = {};
	this.agendamento.participantes = [];
	this.salasDeReuniao = [];
	this.usuarios = [];
	this.usuarioPesquisa = {};
    this.exception = "";

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

    this.carregarDados = function(){
    	send.get("/salasdereuniao")
    		.success(function(salas){
    			AgndFormCtrl.salasDeReuniao = salas;
    		});

    	send.get("/usuarios")
    		.success(function(usuarios){
    			AgndFormCtrl.usuarios = usuarios;
    		});

		if($routeParams.id){
			send.get("/getAgendamento", $routeParams.id)
				.success(function(agendamento){
					AgndFormCtrl.agendamento = agendamento;
					AgndFormCtrl.agendamento.data = new Date(agendamento.data);
					AgndFormCtrl.agendamento.hora = new Date(agendamento.hora);

					var i = 0;
					for(i = 0; i < AgndFormCtrl.salasDeReuniao.length; i++){
						if(AgndFormCtrl.agendamento.salaDeReuniao.idSala == AgndFormCtrl.salasDeReuniao[i].idSala){
							AgndFormCtrl.agendamento.salaDeReuniao = AgndFormCtrl.salasDeReuniao[i];
						}
					}
				});
		}
    }

    this.verificaParticipante = function(usuario){
    	if(AgndFormCtrl.agendamento.idAgendamento){
    		var i = 0;
    		for(i = 0; i < AgndFormCtrl.agendamento.participantes.length; i++){
    			if(AgndFormCtrl.agendamento.participantes[i].idUsuario == usuario.idUsuario){
    				return true;
    			}
    		}
    	}else{
    		if(AgndFormCtrl.agendamento.participantes.length != 0){
    			var index = AgndFormCtrl.agendamento.participantes.indexOf(usuario);

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
    	if(AgndFormCtrl.agendamento.salaDeReuniao){
	    	var index = -1;
	    	var i = 0;
    		for(i = 0; i < AgndFormCtrl.agendamento.participantes.length; i++){
    			if(AgndFormCtrl.agendamento.participantes[i].idUsuario == usuario.idUsuario){
    				index = i;
    				break;
    			}
    		}
	    	var capacidade = AgndFormCtrl.agendamento.salaDeReuniao.capacidade;

	    	if(index == -1){
	    		if(AgndFormCtrl.agendamento.participantes.length == capacidade){
	    			$mdDialog.show(
				    	$mdDialog.alert()
				        .clickOutsideToClose(true)
				        .title('Capacidade excedida')
				        .textContent('A capacidade dessa sala foi excedida, remova participantes ou escolha outra sala')
				        .ariaLabel('Alerta Sala')
				        .ok('Ok')
				    );
				}else{
	    			AgndFormCtrl.agendamento.participantes.push(usuario);
	    		}
	    	}else{
	    		AgndFormCtrl.agendamento.participantes.splice(index, 1);
	    	}
	    }else{
	    	$mdDialog.show(
		    	$mdDialog.alert()
		        .clickOutsideToClose(true)
		        .title('Selecione uma sala')
		        .textContent('Você deve selecionar uma sala antes de selecionar os participantes')
		        .ariaLabel('Alerta Sala')
		        .ok('Ok')
		    );
	    }
    }

    this.verificaCapacidade = function(){
    	if(AgndFormCtrl.agendamento.salaDeReuniao.capacidade < AgndFormCtrl.agendamento.participantes.length){
    		AgndFormCtrl.agendamento.participantes = [];
    	}
    }

    this.upload = function(){
    	angular.element($("#arquivo")).click();
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
    	angular.copy(AgndFormCtrl.agendamento, agendEnvio);

    	agendEnvio.hora = AgndFormCtrl.agendamento.hora.getTime();
    	agendEnvio.data = AgndFormCtrl.agendamento.data.getTime();

    	var participantes = {};

    	var i = 0;
    	for (i = 0; i < agendEnvio.participantes.length; i++){
    		delete agendEnvio.participantes[i].$$hashKey;
    		participantes[i] = agendEnvio.participantes[i].idUsuario;
    	}
    	agendEnvio.participantes = participantes;
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