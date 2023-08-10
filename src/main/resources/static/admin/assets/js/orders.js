var domain = "http://localhost:8080/admin/api";
const app = angular.module("app", ['angularUtils.directives.dirPagination']);
app.controller("ctrl", function($scope, $http) {
	$scope.items = []
	$scope.currentId = null;

	$scope.loadAll = function() {
		var url = `${domain}/orders`;
		$http.get(url).then(response => {
			//			$scope.items = response.data;
			var i = 1;
			for (const item of response.data) {
				item.index = i;
				i++;
				item.datecreate = new Date(item.datecreate).toLocaleDateString("en-US")
				console.log("data: ", item);
				$scope.items.push(item);
			}

		}).catch(error => {
			console.log("Error: ", error);
		})
	}

	$scope.loadAll()
});