function registerWithFacebook() {
    // Xử lý đăng ký Facebook
    window.location.href = 'facebook-register.php';
}

function registerWithGoogle() {
    // Xử lý đăng ký Google
    window.location.href = 'google-register.php';
}

// Xử lý form đăng ký
document.getElementById('registerForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    // Kiểm tra mật khẩu trùng khớp
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    
    if (password !== confirmPassword) {
        alert('Mật khẩu xác nhận không khớp!');
        return;
    }

    // Kiểm tra định dạng mật khẩu
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    if (!passwordRegex.test(password)) {
        alert('Mật khẩu không đáp ứng yêu cầu!');
        return;
    }




    // Tiếp tục xử lý đăng ký


    console.log('Form submitted');

});