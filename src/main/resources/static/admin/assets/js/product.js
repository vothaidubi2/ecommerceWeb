let domain = "http://localhost:8080/admin/api";
const app = angular.module("app", ['angularUtils.directives.dirPagination']);

app.controller("controller", function($scope, $http, $location) {
	$scope.items = [];
	$scope.choices = [];
	$scope.product = {}; // Đối tượng để lưu thông tin sản phẩm từ form
	$scope.selectedValues = []; // Mảng để lưu danh sách giá trị được chọn từ modal
	$scope.searchInput = ''; // Biến để lưu giá trị nhập liệu từ ô tìm kiếm
	$scope.selectedLabels = [];
	$scope.category = [];
	$scope.brand = [];

	$scope.loadAll = function() {
		var url = `${domain}/products`;
		$http.get(url).then(response => {
			$scope.items = response.data.data;
			console.log("data: ", response.data.data);
		}).catch(error => {
			console.log("Error: ", error);
		})
	}
	$scope.DataComponent = function() {
		var url = `${domain}/specification`;
		var url1 = `${domain}/category`;
		var url2 = `${domain}/producer`;
		$http.get(url).then(response => {
			$scope.choices = response.data;
			$scope.choices.forEach(function(item) {
				item.isSelected = false;
			});
			console.log("component: ", response.data);
		}).catch(error => {
			console.log("Error: ", error);
		})
		$http.get(url1).then(response => {
			$scope.category = response.data;
			console.log("component: ", response.data);
		}).catch(error => {
			console.log("Error: ", error);
		})
		$http.get(url2).then(response => {
			$scope.brand = response.data;
			console.log("component: ", response.data);
		}).catch(error => {
			console.log("Error: ", error);
		})
	}
	$scope.loadAll();
	$scope.DataComponent();


	$scope.hideModal = function() {
		$('#exampleModal').modal('hide');
	};
	$scope.showModal = function() {
		// Reset biến lưu giá trị tìm kiếm khi mở modal
		$scope.searchInput = '';
		$('#exampleModal').modal('show');
	};
	$scope.selectValue = function(choice) {
		// Kiểm tra nếu lựa chọn chưa được chọn thì thêm vào mảng selectedValues và đánh dấu isSelected là true
		if (!choice.isSelected) {
			$scope.selectedValues.push(choice.id);
			$scope.selectedLabels.push(choice.keys);
			choice.isSelected = true;
			console.log("clik: ", choice.id)
		}
	};
	$scope.removeValue = function(index) {
		// Xóa giá trị và nhãn từ mảng selectedValues và selectedLabels, đánh dấu isSelected là false
		var removedChoice = $scope.choices.find(choice => choice.id === $scope.selectedValues[index]);
		if (removedChoice) {
			removedChoice.isSelected = false;
		}
		$scope.selectedValues.splice(index, 1);
		$scope.selectedLabels.splice(index, 1);
	};
	// Sự kiện nhập liệu trong ô tìm kiếm
	$scope.$watch('searchInput', function(newValue, oldValue) {
		// Thực hiện lọc danh sách giá trị dựa trên giá trị nhập liệu
		$scope.filteredValues = $scope.selectedLabels.filter(function(value) {
			return value.toLowerCase().includes(newValue);
		});
	});
	//dialog
	$scope.normalDialog = function() {
		ngDialog.open({
			template: 'templateId',
			className: 'ngdialog-theme-default',
			scope: $scope,
		});
	};
});


//ck editor
ClassicEditor
	.create(document.querySelector('#editor'))
	.catch(error => {
		console.error(error);
	});


//alert
function CustomAlert() {
	this.alert = function(message, title) {
		document.body.innerHTML = document.body.innerHTML + '<div id="dialogoverlay"></div><div id="dialogbox" class="slit-in-vertical"><div><div id="dialogboxhead"></div><div id="dialogboxbody"></div><div id="dialogboxfoot"></div></div></div>';

		let dialogoverlay = document.getElementById('dialogoverlay');
		let dialogbox = document.getElementById('dialogbox');

		let winH = window.innerHeight;
		dialogoverlay.style.height = winH + "px";

		dialogbox.style.top = "100px";

		dialogoverlay.style.display = "block";
		dialogbox.style.display = "block";

		document.getElementById('dialogboxhead').style.display = 'block';

		if (typeof title === 'undefined') {
			document.getElementById('dialogboxhead').style.display = 'none';
		} else {
			document.getElementById('dialogboxhead').innerHTML = '<i class="fa fa-exclamation-circle" aria-hidden="true"></i> ' + title;
		}
		document.getElementById('dialogboxbody').innerHTML = message;
		document.getElementById('dialogboxfoot').innerHTML = '<button class="pure-material-button-contained active" onclick="customAlert.ok()">OK</button>';
	}

	this.ok = function() {
		document.getElementById('dialogbox').style.display = "none";
		document.getElementById('dialogoverlay').style.display = "none";
	}
}

let customAlert = new CustomAlert();
//
//
//const dialogElement = document.getElementById('dialog');
//
//// Get the dropdown menu button
//const dropdownButton = document.getElementById('dropdownMenuButton');
//
//// Get the display input field
//const displayInput = document.getElementById('selectedValue');
//const inputValue = document.getElementById('inputValue');
//
//// Get the search input field and search button inside the dialog
//const searchInput = document.getElementById('searchInput');
//const searchButton = document.getElementById('searchButton');
//
//
//// Function to filter dropdown items based on search input
//function filterDropdownItems() {
//	const searchTerm = searchInput.value.trim().toLowerCase();
//	const dropdownItems = document.querySelectorAll('.dropdown-item');
//
//	dropdownItems.forEach((item) => {
//		const itemText = item.textContent.toLowerCase();
//		if (itemText.includes(searchTerm)) {
//			item.style.display = 'block';
//		} else {
//			item.style.display = 'none';
//		}
//	});
//}
//
//// Add an event listener to the dropdown buttons inside the dialog
//document.querySelectorAll('.dropdown-item').forEach((button) => {
//	button.addEventListener('click', function() {
//		// Close the dialog when a button is clicked
//		$('#dialog').modal('hide');
//
//		// Get the value of the clicked button
//		const selectedValue = button.innerText;
//
//		// Display the value in the input field
//		displayInput.value = selectedValue;
//		inputValue.value = button.value;
//		console.log("input:", inputValue.value)
//	});
//});
//
//// Add an event listener to the dropdown button
//dropdownButton.addEventListener('click', function() {
//	// Show the dialog when the dropdown button is clicked
//	$('#dialog').modal('show');
//});
//
//// Add an event listener to the Remove button (icon)
//document.getElementById('removeButton').addEventListener('click', function() {
//	// Clear the input field when the Remove button is clicked
//	displayInput.value = '';
//	inputValue.value = null;
//	// Reset the width of the input field
//	displayInput.style.width = 'auto';
//});
//
//// Add an event listener to the search button inside the dialog
//let timeoutId;
//searchInput.addEventListener('input', function() {
//	// Clear the previous timeout (if any)
//	clearTimeout(timeoutId);
//
//	// Set a new timeout to filter the dropdown items after 1000ms (1 second)
//	timeoutId = setTimeout(filterDropdownItems, 500);
//});