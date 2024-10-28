function review() {
    const options = { method: 'GET'}

    fetch(`../api/review/${productSeq}`, options)
        .then(response => {
            if(!response.ok) throw new Error('조회된 상품평이 없습니다.' + response.status)
            return response.json()
        })
        .then((data) => {
            console.log(productSeq)
            console.log(data[0])
            printReviewList(data)
        })
        .catch(err => console.error('error: ', err))

    function printReviewList(list) {
        let str = '';
        if(list && list.length > 0) {
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
        }

        let btnStr = '<button class="write_review_btn">리뷰작성</button>';
        detailArea.innerHTML = '<ul class="review_list">' + str + '</ul>' + btnStr;

        document.querySelector('.write_review_btn').addEventListener('click', () => {
            if(!username) {
                alert('상품평을 작성하려면 로그인이 필요합니다.')
            } else {
                const today = new Date()
                detailArea.innerHTML = ''

                str = ''
                str = `
                <li>
                    <span class="username">${username}</span>
                    <textarea class="content"></textarea>
                    <span class="regDate">${today.getFullYear()}-${today.getMonth()+1}-${today.getDate()}</span>
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
            `
                let btnStr = '<button type="submit" class="write_review_btn">완료</button>' +
                    '<button class="cancel_review_btn">취소</button>'

                detailArea.innerHTML = '<ul>' + str + '</ul>' + btnStr
            }
        })
    }
}