var domain = "http://localhost:8080/admin/api";
const app = angular.module("app", ['angularUtils.directives.dirPagination']);
app.controller("ctrl", function($scope, $http) {
	$scope.items = []
	$scope.currentId = null;
	$scope.selectedStatus = {}
	$scope.listStatus = [
		{
			id: 4,
			name: "Pending Confirm"
		}, {
			id: 3,
			name: "Confirmed"
		}, {
			id: 2,
			name: "Transporting"
		}, {
			id: 1,
			name: "Delivered"
		}
	]
	$scope.handleChange = function(input) {
		$scope.selectedStatus = input;
		console.log("vao", $scope.selectedStatus.id)
		$scope.loadAll();
	}

	$scope.selectedStatus = $scope.listStatus[0];
	$scope.loadAll = function() {
		if ($scope.items.length > 0) {
			$scope.items = [];
		}
		var url = `${domain}/orders?status=${$scope.selectedStatus.id}`;
		$http.get(url).then(response => {
			//			$scope.items = response.data;
			var i = 1;
			for (const item of response.data) {
				item.index = i;
				i++;
				item.datecreate = new Date(item.datecreate).toLocaleDateString("en-US")
				$scope.items.push(item);
			}
			console.log("item:", $scope.items)

		}).catch(error => {
			console.log("Error: ", error);
		})
	}
	$scope.loadAll()
});