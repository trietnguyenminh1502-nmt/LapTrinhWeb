$(document).ready(function() {
    // --- 1. XỬ LÝ ĐĂNG NHẬP (LOGIN) ---
    $('#Login').click(function() {
        // Lấy thông tin từ form 
        var email = $('#email').val();
        var password = $('#password').val();
        
        var loginRequest = JSON.stringify({
            email: email,
            password: password
        });

        $.ajax({
            type: "POST",
            url: "/auth/login", 
            contentType: "application/json; charset=utf-8",
            data: loginRequest,
            dataType: 'json',
            success: function(data) {
                // Lưu mã token vào bộ nhớ trình duyệt (localStorage) [cite: 118, 1101]
                localStorage.token = data.token;
                // Chuyển hướng sang trang cá nhân 
                window.location.href = "/user/profile";
            },
            error: function(e) {
                // Hiển thị thông báo lỗi lên giao diện 
                var json = e.responseText;
                $('#feedback').html("Đăng nhập thất bại! Vui lòng kiểm tra lại.");
                console.log("ERROR: ", e);
            }
        });
    });

    // --- 2. TỰ ĐỘNG LẤY THÔNG TIN NGƯỜI DÙNG KHI VÀO TRANG PROFILE ---
    if (window.location.pathname === "/user/profile") {
        $.ajax({
            type: 'GET',
            url: '/users/me', 
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function(xhr) {
                // Đính kèm Token vào Header Authorization trước khi gửi request 
                if (localStorage.token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                }
            },
            success: function(data) {
                // Hiển thị thông tin lên các thẻ HTML tương ứng 
                $('#profile').html("Xin chào: " + data.fullName);
                if (data.images) {
                    $('#images').attr('src', data.images);
                }
                console.log("SUCCESS: ", data);
            },
            error: function(e) {
                alert("Bạn chưa đăng nhập hoặc Token đã hết hạn!");
                window.location.href = "/login"; 
            }
        });
    }

    // --- 3. XỬ LÝ ĐĂNG XUẤT (LOGOUT) ---
    $('#Logout').click(function() {
        // Xóa sạch bộ nhớ LocalStorage 
        localStorage.clear();
        // Quay về trang đăng nhập 
        window.location.href = "/login";
    });
});