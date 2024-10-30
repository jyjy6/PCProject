function review() {
    const options = { method: 'GET' };

    fetch(`../api/comment/list/${productSeq}`, options)
        .then(response => {
            if (!response.ok) throw new Error('조회된 상품평이 없습니다.' + response.status);
            return response.json();
        })
        .then((data) => {
            console.log(productSeq);
            console.log(data);
            detailArea.innerHTML = ''
            printReviewList(data);
        })
        .catch(err => console.error('error: ', err));

    function printReviewList(list) {
        let str = '';
        if (list && list.length > 0) {
            list.forEach(dto => {
                str += `
                    <li>
                        <span class="username">${dto.username}</span>
                        <textarea class="content">${dto.content}</textarea>
                        <span class="regDate">${dto.regDate}</span>
                        <span class="score">${dto.score}</span>
                        <button onclick="deleteReview(${dto.seq})">삭제</button>
                    </li>
                `;
            });
        } else {
            str = '<p class="no-reviews">상품평이 없습니다.</p>';
        }

        let btnStr = '<button class="write_review_btn">리뷰작성</button>';
        detailArea.innerHTML = '<ul class="review_list">' + str + '</ul>' + btnStr;

        document.querySelector('.write_review_btn').addEventListener('click', () => {
            if (!username) {
                alert('상품평을 작성하려면 로그인이 필요합니다.');
            } else {
                renderReviewForm();
            }
        });
    }

    // 리뷰 작성 폼 렌더링 함수
    function renderReviewForm() {
        const today = new Date();
        let str = `
        <li>
            <span class="username">${username}</span>
            <textarea class="content" placeholder="댓글을 입력하세요"></textarea>
            <span class="regDate">${today.getFullYear()}-${today.getMonth() + 1}-${today.getDate()}</span>
            <span class="score"></span>
            <div class="rating">
                <span class="star" data-value="1">★</span>
                <span class="star" data-value="2">★</span>
                <span class="star" data-value="3">★</span>
                <span class="star" data-value="4">★</span>
                <span class="star" data-value="5">★</span>
            </div>
            <div class="rating-info">
                <p>평점: <span id="rating-value">0</span>/5</p>
            </div>
        </li>
    `;
        const btnStr = `
        <button type="submit" class="submit_review_btn">완료</button>
        <button class="cancel_review_btn">취소</button>
    `;

        detailArea.innerHTML = '<ul>' + str + '</ul>' + btnStr;

        document.querySelector('.submit_review_btn').addEventListener('click', submitReview);
        document.querySelector('.cancel_review_btn').addEventListener('click', review);

        let selectedScore = 0; // 선택된 별점 초기화

        // 별점 클릭 시 평점 설정 및 선택 상태 관리
        document.querySelectorAll('.star').forEach(star => {
            star.addEventListener('click', function () {
                selectedScore = this.getAttribute('data-value');
                document.getElementById('rating-value').innerText = selectedScore;

                // 선택된 별점 시 색상 변경
                updateStarSelection(selectedScore);
            });

            // 호버 이벤트 추가
            star.addEventListener('mouseover', function () {
                const score = this.getAttribute('data-value');
                updateStarSelection(score); // 호버 중인 별 이하 선택
            });

            // 마우스 아웃 이벤트 추가
            star.addEventListener('mouseout', function () {
                updateStarSelection(selectedScore); // 선택된 별점으로 되돌리기
            });
        });

        // 별점 선택 상태 업데이트 함수
        function updateStarSelection(score) {
            document.querySelectorAll('.star').forEach(s => {
                s.classList.remove('selected');
                if (s.getAttribute('data-value') <= score) {
                    s.classList.add('selected'); // 선택된 별점 이하 선택
                }
            });
        }
    }



    // 리뷰 제출 함수
    function submitReview() {
        const content = document.querySelector('.content').value;
        const score = document.getElementById('rating-value').innerText;

        if (!content || score === '0') {
            alert('내용과 평점을 모두 입력해주세요.');
            return;
        }

        const reviewData = {
            content: content,
            score: parseInt(score)
        };

        // URL 파라미터로 pSeq 전달
        fetch(`../api/comment/add?pSeq=${productSeq}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(reviewData)
        })
            .then(response => {
                if (!response.ok) throw new Error('상품평 등록에 실패했습니다.' + response.status);
                alert('상품평이 등록되었습니다.');
                review();  // 등록 후 상품평 목록 다시 불러오기
            })
            .catch(err => console.error('error: ', err));
    }

}
function deleteReview(seq) {
    if (confirm("정말로 이 댓글을 삭제하시겠습니까?")) {
        fetch(`/api/comment/remove?seq=${seq}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => {
                if (response.ok) {
                    alert("댓글이 삭제되었습니다.");
                    // 삭제 후 댓글 목록 갱신 등 필요한 로직 추가
                    location.reload(); // 페이지 새로 고침
                } else {
                    alert("댓글 삭제에 실패했습니다.");
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("댓글 삭제 중 오류가 발생했습니다.");
            });
    }
}