app.service("send", function($http){
    return{
        post: function(url, data){
            return $http({
                method: 'POST',
                url: base_url+url,
                data: $.param(data),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            });
        },
        get: function(url, data=""){
            return $http({
                method: 'GET',
                url: base_url+url+"/"+data,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            });
        },
        put: function(url, data){
            console.log(data);
            return $http.put(base_url+url, data);
        },
        delete: function(url, data){
            return $http({
                method: 'DELETE',
                url: base_url+url+"/"+ data,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            });
        },
        file: function(url, data){
            return $http({
                method: 'POST',
                url: base_url + url,
                headers: {"Content-Type": undefined},
                async: false,
                data: data,
                transformRequest : angular.identity,
            });
        }
    }
});