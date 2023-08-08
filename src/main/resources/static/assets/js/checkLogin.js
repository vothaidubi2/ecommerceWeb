var host = "http://localhost:8080/api/";
 var app = angular.module('myApp', []);

  app.controller('myCtrl', function($scope, $http) {
    // Hàm kiểm tra trạng thái đăng nhập
    $scope.checkLoginStatus = function() {
      $http.get('admin/checkuser')
        .then(function(response) {
          // Nếu người dùng đã đăng nhập
          if (response.data.loggedIn) {
            $scope.loggedIn = true;
            $scope.username = response.data.username;
          } else {
            // Nếu người dùng chưa đăng nhập
            $scope.loggedIn = false;
          }
        })
        .catch(function(error) {
          console.error('Error:', error);
        });
    };

    // Gọi hàm kiểm tra trạng thái đăng nhập khi trang được tải
    $scope.checkLoginStatus();
  });