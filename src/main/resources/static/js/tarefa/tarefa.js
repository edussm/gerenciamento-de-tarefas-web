angular.module('tarefas', []).controller('tarefas', function($scope, $http) {
	$http.get('/user/').then(function(response) {
		$scope.user = response.data.name;
	});
	$http.get('/api/v1/tarefa').then(function(response) {
		  $scope.itens = response.data;
	});
	
    $scope.adicionaItem = function (isValid) {
    		if (isValid) {
    			var tarefa = { 'descricao': $scope.item.descricao,
        				'status': $scope.item.status    				
        		}
        		
        		var res = $http.post('/api/v1/tarefa', tarefa);
        		res.success(function(data, status, headers, config) {
        			$scope.itens.push(data);
                $scope.item.descricao = '';
                $scope.item.status = 'PENDENTE';
        		});
            res.error(function(data, status, headers, config) {
            		self.error = true;
            		self.errorMessage = "Falha ao salvar a tarefa: " + data.error 
    				+ "(" + data.message + ")";
            });           		
    		};    		
    };
    
    editarTarefa = function (tarefa, index) {
    		var res = $http.put('/api/v1/tarefa', tarefa);
    		res.success(function(data, status, headers, config) {
    			$scope.itens[index] = data;
    		});
        res.error(function(data, status, headers, config) {
        		self.error = true;
        		self.errorMessage = "Falha ao salvar a tarefa: " 
        			+ data.error 
				+ "(" + data.message + ")";
        });           		   		
    };
    
    $scope.iniciarTarefa = function (tarefa, index) {
		tarefa.status = 'EM_EXECUCAO';
		tarefa.dataInicio = new Date();
		tarefa.executor = $scope.user;
    		editarTarefa(tarefa, index);  		   		
    };
    
    $scope.pausarTarefa = function (tarefa, index) {
		tarefa.status = 'PENDENTE';
		tarefa.executor = '';
    		editarTarefa(tarefa, index);  		   		
    };
    
    $scope.terminarTarefa = function (tarefa, index) {
		tarefa.status = 'CONCLUIDA';
		tarefa.dataFim = new Date();
    		editarTarefa(tarefa, index);  		   		
    };
    
    $scope.apagarTarefa = function (tarefa, index) {
	    	var res = $http.delete('/api/v1/tarefa/'+tarefa.id);
		res.success(function(data, status, headers, config) {
				$scope.itens.splice(index,1);
			});
	    res.error(function(data, status, headers, config) {
	    		self.error = true;
	    		self.errorMessage = "Falha ao apagar a tarefa: " 
	    			+ data.error 
				+ "(" + data.message + ")";
	    });        		   		
    };
    
});
