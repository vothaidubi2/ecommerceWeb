var domain = "http://localhost:8080/api";
const app = angular.module("app", []);
app.controller("ctrl", function($scope, $http,$document) {
	$scope.items = []
	$scope.invoice = {}
	$scope.currentId = null;

	$scope.loadAll = function() {
		const urlParams = new URLSearchParams(window.location.search);
		var id = urlParams.get('id');
		var urlOrder = `${domain}/order/get?id=${id}`;
		var urlDetail = `${domain}/order?id=${id}`;
		$http.get(urlOrder).then(response => {
			const [id, type, phoneNumber, address, date, status, amount] = response.data.data.split(',');
			var dateFormat = new Date(date).toLocaleDateString("en-US");
			$scope.invoice = {
				id: id,
				user: type,
				phoneNumber: phoneNumber,
				address: address,
				dateFormat: dateFormat,
				status: status,
				amount: amount
			};
			console.log("data: ", $scope.invoice);
		}).catch(error => {
			console.log("Error: ", error);
		})
		$http.get(urlDetail).then(response => {
			const splitDataArray = response.data.map(item => {
				const [name, price, quantity] = item.split(',');
				return {
					name: name,
					price: price,
					quantity: quantity,
					total:price*quantity
				};
			});
			var i = 1;
			for (const item of splitDataArray) {
				item.index = i;
				i++;
				$scope.items.push(item)
			}
			console.log("data: ", $scope.items);

		}).catch(error => {
			console.log("Error: ", error);
		})
	}

	$scope.loadAll()
});