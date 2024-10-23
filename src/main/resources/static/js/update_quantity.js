function updateQty(pSeq, newQty) {
    fetch('api/cart/updateQuantity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: new URLSearchParams({ pSeq: pSeq, quantity: newQty })
    })
        .then(resp => resp.json())
        .then(data => {
            if(!data.success) {
                alert('선택 수량보다 재고가 부족합니다.')
            }
        })
        .catch(err => console.error(err));
}