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

        // 별점 클릭 시 평점 설정
        document.querySelectorAll('.star').forEach(star => {
            star.addEventListener('click', function () {
                const score = this.getAttribute('data-value');
                document.getElementById('rating-value').innerText = score;
            });
        });
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
