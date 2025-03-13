function loginWithFacebook() {
    // Xử lý đăng nhập Facebook
    window.location.href = 'facebook-login.php';
}

function loginWithGoogle() {
    // Xử lý đăng nhập Google
    window.location.href = 'google-login.php';
}

// Xử lý form đăng nhập
document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    // Thêm logic xử lý đăng nhập ở đây
    console.log('Form submitted');
});