function updateQty(pSeq, newQty) {
    fetch('api/cart/updateQty', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ pSeq: pSeq, qty: newQty, username: username })
    })
        .then(resp => {
            if(!resp.ok) throw new Error('네트워크 응답 실패!')
            return resp.json()
        })
        .then(data => {
            if(!data.success) alert('선택 수량보다 재고가 부족합니다.')
        })
        .catch(err => console.log(err))
}