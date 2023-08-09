var domain = "http://localhost:8080/admin/api";
const app = angular.module("app", []);
app.controller("ctrl", function($scope, $http) {
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
			let active1 = '';
			let success1 = '';
			let active2 = '';
			let success2 = '';
			let active3 = '';
			let success3 = '';
			let active4 = '';
			let success4 = '';
			console.log("vao", $scope.invoice.status)
			if ($scope.invoice.status == '4') {
				active4 = "active"
				success4 = "btn-success"
			} else if ($scope.invoice.status == '3') {
				active3 = "active"
				success3 = "btn-success"
			} else if ($scope.invoice.status == '2') {
				active2 = "active"
				success2 = "btn-success"
			} else if ($scope.invoice.status == '1') {
				active1 = "active"
				success1 = "btn-success"
			}
			// display status
			document.getElementById("invoiceStatus").innerHTML = `
				<li class="nav-item"><a
					class="nav-link mb-0 px-0 py-1 d-flex align-items-center ${active4} justify-content-center ${success1}"
					data-bs-toggle="tab" href="javascript:;" role="tab"
					aria-selected="true"> <i class="ni ni-app"></i> <span
						class="ms-2">Pending Confirm</span>
				</a></li>
				<li class="nav-item"><a
					class=" nav-link mb-0 px-0 py-1 d-flex align-items-center ${active3} justify-content-center ${success3}"
					data-bs-toggle="tab" href="javascript:;" role="tab"
					aria-selected="false"> <i class="ni ni-email-83"></i> <span
						class="ms-2">Messages</span>
				</a></li>
				<li class="nav-item"><a
					class=" nav-link mb-0 px-0 py-1 d-flex align-items-center ${active2} justify-content-center ${success2}"
					data-bs-toggle="tab" href="javascript:;" role="tab"
					aria-selected="false"> <i class="ni ni-email-83"></i> <span
						class="ms-2">Messages</span>
				</a></li>
				<li class="nav-item"><a
					class=" nav-link mb-0 px-0 py-1 d-flex align-items-center ${active1} justify-content-center ${success1}"
					data-bs-toggle="tab" href="javascript:;" role="tab"
					aria-selected="false"> <i class="ni ni-settings-gear-65"></i>
						<span class="ms-2">Settings</span>
				</a></li>
			`
		}).catch(error => {
			console.log("Error: ", error);
		})
		$http.get(urlDetail).then(response => {
			const splitDataArray = response.data.map(item => {
				const [name, price, quantity] = item.split(',');
				return {
					name: name,
					price: price,
					quantity: quantity
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