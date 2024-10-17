let idCheck = false;
let nickCheck = false;

function checkUsername() {
    const username = document.getElementById('username').value;
    fetch(`/api/member/check-username?username=${username}`)
        .then(response => response.json())
        .then(data => {
            const feedback = document.getElementById('usernameFeedback');
            if (data || username=='') {
                feedback.textContent = "아이디가 이미 존재합니다.";
                feedback.style.color = 'red';
                idCheck= false;
            } else {
                feedback.textContent = "아이디를 사용할 수 있습니다.";
                feedback.style.color = 'green';
                idCheck = true;
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

function checkDisplayName() {
    const displayName = document.getElementById('displayName').value;

    fetch(`/api/member/check-displayName?displayName=${displayName}`)
        .then(response => response.json())
        .then(data => {
            const feedback = document.getElementById('displayNameFeedback');
            if (data || displayName=='') {
                feedback.textContent = "닉네임이 이미 존재합니다.";
                feedback.style.color = 'red';
                nickCheck= false;
            } else {
                feedback.textContent = "닉네임을 사용할 수 있습니다.";
                feedback.style.color = 'green';
                nickCheck= true;
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

document.getElementById('addressButton').onclick = function() {
    new daum.Postcode({
        oncomplete: function(data) {
            const roadAddress = data.roadAddress;
            const jibunAddress = data.jibunAddress;
            document.getElementById('address').value = roadAddress ? roadAddress : jibunAddress;
        }
    }).open();
}



// 폼 제출 이벤트 처리
function handleSubmit(event) {
    if(!idCheck||!nickCheck){
        alert('회원작성폼을 다시 확인하세요.')
        return;
    }
    event.preventDefault(); // 기본 폼 제출 방지

    // AJAX 요청
    const formData = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value,
        email: document.getElementById('email').value,
        displayName: document.getElementById('displayName').value,
        phone: document.getElementById('phone').value,
        address: document.getElementById('address').value
        address2: document.getElementById('address2').value
    };

    fetch('http://localhost:8080/api/member/sign-up', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if (response.ok) {
            alert("회원가입이 성공적으로 완료되었습니다!");
            window.location.href = "/login"; // 로그인 페이지로 리다이렉트
        } else {
            return response.text().then(text => { throw new Error(text); });
        }
    })
    .catch(error => {
        alert("회원가입에 실패했습니다: " + error.message);
    });
}

    document.getElementById('phone').addEventListener('input', function (event) {
    const input = event.target;
    let value = input.value;

    // 숫자 이외의 문자는 제거
    value = value.replace(/[^0-9]/g, '');

    // 숫자 11자리까지만 입력 허용
    if (value.length > 11) {
        value = value.slice(0, 11);  // 11자 초과 시 잘라내기
    }

    input.value = value;
});