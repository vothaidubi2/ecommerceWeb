let domain = "http://localhost:8080/admin/api";
const app = angular.module("app", ['angularUtils.directives.dirPagination', 'ngDialog']);
app.directive('ngFileModel', ['$parse', function($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.ngFileModel);
			var modelSetter = model.assign;
			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files);
				});
			});
		}
	};
}])
app.controller("controller", [
	'ngDialog',
	'$scope', '$http', function(ngDialog, $scope, $http) {

		$scope.items = [];
		$scope.choices = [];
		$scope.newProduct = {}; // Đối tượng để lưu thông tin sản phẩm từ form
		$scope.searchInput = ''; // Biến để lưu giá trị nhập liệu từ ô tìm kiếm
		$scope.selectedValues = [];
		$scope.selectedLabels = [];
		$scope.categories = [];
		$scope.brands = [];
		//		//	// Set the selectedBrandId to the ID of the first item in the brands array
		$scope.slectedBrand = {};
		$scope.selectedCategory = {};
		$scope.handleSelectionBrandChange = function(selectedBrand) {
			// Assign the entire selected brand object to $scope.selectedBrand
			$scope.selectedBrand = selectedBrand;
			console.log('Selected Brand:', $scope.selectedBrand);
			// You can perform any actions based on the selected brand here
			// For example, you can access $scope.selectedBrand.id, $scope.selectedBrand.name, etc.
		};
		$scope.handleSelectionCateChange = function(selectedCategory) {
			// Assign the entire selected brand object to $scope.selectedBrand
			$scope.selectedCategory = selectedCategory;
			console.log('Selected Category:', $scope.selectedCategory);
			// You can perform any actions based on the selected brand here
			// For example, you can access $scope.selectedBrand.id, $scope.selectedBrand.name, etc.
		};

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
				$scope.categories = response.data;
				$scope.selectedCategory = response.data[0];
				console.log("component: ", $scope.selectedCategory);
			}).catch(error => {
				console.log("Error: ", error);
			})
			$http.get(url2).then(response => {
				$scope.brands = response.data;
				$scope.slectedBrand = response.data[0];
				console.log("component: ", $scope.slectedBrand);
			}).catch(error => {
				console.log("Error: ", error);
			})

		}
		$scope.loadAll();
		$scope.DataComponent();
		//reset
		$scope.reset = function() {
			$scope.newProduct = {
				name: null,
				price: null,
				quantity: null,
				category: null,
				producer: null,
				specifications: [],
				images: [],
				description: null
			};
			$scope.imgUrls = [];
			$scope.selectedValues = [];
			$scope.selectedLabels = [];
			editor.data.set('');
			$scope.imageFiles = [];
			$scope.imgLength = null;
			$scope.choices.forEach(function(item) {
				item.isSelected = false;
			});
			$scope.selectedCategory = $scope.categories[0];
			$scope.slectedBrand = $scope.brands[0];
			$scope.loadAll();
		}
		//create
		$scope.createProduct = function() {
			let error = null;
			if (editor.getData() == '') error = "Please enter Product description!";
			if ($scope.imgUrls.length < 1) error = "Please choose Product image!";
			if ($scope.selectedValues.length < 1) error = "Please choose Product specifications!";
			if ($scope.newProduct.quantity == null) error = "Please enter Product quantity!";
			if ($scope.newProduct.price == null) error = "Please enter Product price!";
			if ($scope.newProduct.name == null) error = "Please enter Product name!";
			if (error != null) {
				ngDialog.open({
					template: `\
                <p>${error}</p>\
                <div class="ngdialog-buttons">\
                    <button type="button" class="ngdialog-button ngdialog-button-primary" ng-click="closeThisDialog(0)">Yes</button>\
                </div>`,
					plain: true,
				});
				return;
			}

			$scope.newProduct.category = $scope.selectedCategory;
			$scope.newProduct.producer = $scope.slectedBrand;
			$scope.newProduct.description = editor.getData();
			$scope.newProduct.image = $scope.imgUrls.map(function(imageUrl) {
				return { url: imageUrl };
			});

			$scope.newProduct.specifications = $scope.selectedValues.map(function(specification) {
				return {
					id: specification.id,
					keys: specification.keys,
					value: specification.value
				};
			});
			$http.post(`${domain}/products`, $scope.newProduct)
				.then(function(response) {
					$scope.reset();
				})
				.catch(function(error) {
					alert('Error creating product:', error);
				});
			alert('Create successfully!')
		}
		//edit
		$scope.edit = function(id) {
			var url = `${domain}/product/${id}`;
			$http.get(url).then(respone => {
				$scope.imgUrls = [];
				$scope.selectedValues = [];
				$scope.selectedLabels = [];
				$scope.newProduct = respone.data;
				$scope.selectedCategory = respone.data.category;
				$scope.slectedBrand = respone.data.producer;
				editor.setData(respone.data.description);
				//			= $scope.categories[0];
				//		$scope.slectedBrand = $scope.brands[0];
				respone.data.image.forEach(img => {
					$scope.imgUrls.push(img.url);
				});
				$scope.imgLength = respone.data.image.length;
				$scope.selectedValues = respone.data.specifications;
				respone.data.specifications.forEach(spec => {
					$scope.selectedLabels.push(spec.key);
				});

				console.log("Success", $scope.imgLength);
			}).catch(error => {
				console.log("Error: ", error);
			})
		}
		//update product
		$scope.updateProduct = function() {
			let error = null;
			if (editor.getData() == '') error = "Please enter Product description!";
			if ($scope.imgUrls.length < 1) error = "Please choose Product image!";
			if ($scope.selectedValues.length < 1) error = "Please choose Product specifications!";
			if ($scope.newProduct.quantity == null) error = "Please enter Product quantity!";
			if ($scope.newProduct.price == null) error = "Please enter Product price!";
			if ($scope.newProduct.name == null) error = "Please enter Product name!";
			if (error != null) {
				ngDialog.open({
					template: `<p>${error}</p>\
                    <div class="ngdialog-buttons">\
                        <button type="button" class="ngdialog-button ngdialog-button-primary" ng-click="closeThisDialog(0)">Yes</button>\
                    </div>`,
					plain: true,
				});
				return;
			}

			$scope.newProduct.category = $scope.selectedCategory;
			$scope.newProduct.producer = $scope.slectedBrand;
			$scope.newProduct.description = editor.getData();
			$scope.newProduct.image = $scope.imgUrls.map(function(imageUrl) {
				return { url: imageUrl };
			});

			$scope.newProduct.specifications = $scope.selectedValues.map(function(specification) {
				return {
					id: specification.id,
					keys: specification.keys,
					value: specification.value
				};
			});

			// Use the product ID from the newProduct object
			const productId = $scope.newProduct.id;

			$http.put(`${domain}/product/update/${productId}`, $scope.newProduct)
				.then(function() {
					alert('Update successfully!');
					$scope.reset();
				})
				.catch(function(error) {
					console.error('Error updating product:', error);
				});
		};
		//delete product
		$scope.deleteProduct = function(id) {
			$http.put(`${domain}/product/delete/${id}`)
				.then(function(response) {
					console.log('Product delete successfully');
					$scope.reset();
				})
				.catch(function(error) {
					alert('Error delete product:', error);
				});
			alert('Delete successfully!');
		};


		$scope.hideModal = function() {
			$('#exampleModal').modal('hide');
		};
		$scope.showModal = function() {
			// Reset biến lưu giá trị tìm kiếm khi mở modal
			$scope.searchInput = '';
			$('#exampleModal').modal('show');
		};
		$scope.selectValue = function(choice) {
			if ($scope.selectedValues.length > 0) {
				if ("isSelected" in $scope.selectedValues[0] == false) {
					$scope.selectedValues = [];
					$scope.selectedLabels = [];
				}
			}
			if (!choice.isSelected) {
				$scope.selectedValues.push(choice);
				$scope.selectedLabels.push(choice.keys);
				choice.isSelected = true;
				console.log("clik: ", choice.id)
			}
		};
		$scope.removeValue = function(index) {
			// Xóa giá trị và nhãn từ mảng selectedValues và selectedLabels, đánh dấu isSelected là false
			var removedChoice = $scope.choices.find(choice => choice.id === $scope.selectedValues[index].id);
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
		//upload image
		$scope.imageFiles = [];
		$scope.imgUrls = [];
		$scope.imgLength = null;

		$scope.uploadImages = function() {
			if (!$scope.imageFiles || $scope.imageFiles.length === 0) {
				alert("Please select images to upload.");
				return;
			}
			
			if ($scope.imgUrls.length > 4) {
				alert("Can only select up to 4 images");
				return;
			}

			// Validate image types
			var allowedTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'];
			var invalidFiles = [];
			for (var i = 0; i < $scope.imageFiles.length; i++) {
				var file = $scope.imageFiles[i];
				if (allowedTypes.indexOf(file.type) === -1) {
					invalidFiles.push(file.name);
				}
			}

			if (invalidFiles.length > 0) {
				alert("Invalid image file(s): " + invalidFiles.join(", "));
				return;
			}

			// Proceed with image upload
			var formData = new FormData();
			for (var i = 0; i < 4; i++) {
				formData.append('files', $scope.imageFiles[i]);
			}

			$http.post(`${domain}/image/upload`, formData, {
				transformRequest: angular.identity,
				headers: { 'Content-Type': undefined }
			}).then(function(response) {
				$scope.imgUrls = response.data;
			}).catch(function(error) {
				console.error('Error uploading images:', error);
			});
		};
		//delete image
		function getFileNameFromURL(url) {
			const partsWithoutQuery = url.split('?')[0];
			const parts = partsWithoutQuery.split('/');
			const fileName = parts[parts.length - 1];
			return fileName;
		}
		const firebaseConfig = {
			apiKey: "AIzaSyAyDO6xmsDeobSfAdsRU1Qf6n1tJP0TqNE",
			authDomain: "apiimage-c42f8.firebaseapp.com",
			projectId: "apiimage-c42f8",
			storageBucket: "apiimage-c42f8.appspot.com",
			messagingSenderId: "905330924078",
			appId: "1:905330924078:web:055420b8564ccf3f4e9dbf",
			measurementId: "G-CM4JCWY5BR"
		};

		firebase.initializeApp(firebaseConfig);
		$scope.deleteImage = function(imgUrl) {
			//			const storageRef = firebase.storage().refFromURL(url);
			const storageRef = firebase.storage().ref(getFileNameFromURL(imgUrl));
			storageRef.delete()
				.then(() => {
					console.log("Image deleted successfully");
					const index = $scope.imgUrls.indexOf(imgUrl);
					if (index > -1) {
						$scope.imgUrls.splice(index, 1);
						$scope.$apply();
					}
				})
				.catch(error => {
					console.error("Error deleting image:", error);
				});
		};

		//ck editor
		let editor;
		ClassicEditor
			.create(document.querySelector('#editor'))
			.then(newEditor => {
				editor = newEditor;
			})
			.catch(error => {
				console.error(error);
			});
	}]);


