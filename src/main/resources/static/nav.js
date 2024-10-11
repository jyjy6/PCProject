function showPopup() {
    const popup = document.querySelector('.nav-popup');
    if (popup.classList.contains('show-popup')) {
        popup.style.opacity = '0';
        setTimeout(() => {
            popup.classList.remove('show-popup');
        }, 300);
    } else {
        popup.classList.add('show-popup');
        setTimeout(() => {
            popup.style.opacity = '1';
        }, 10);
    }
}

function logout() {
    fetch('/logout', {
        method: 'POST',
        headers: {

        },
        credentials: 'include' // 쿠키를 포함
    }).then(response => {
        if (response.ok) {
            window.location.href = '/login'; // 로그인 페이지로 리디렉션
        } else {
            alert('로그아웃 실패');
        }
    });
}
