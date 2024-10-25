function deleteCartItems(chkValues) {    fetch('api/cart/deleteItems', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ pSeqs: chkValues, username: username })
    })
    .then(resp => {
        if (!resp.ok) {
            throw new Error('네트워크 응답 실패!')
        }
        return resp.text().then(text => {
            console.log('Response Text:', text)
            return JSON.parse(text)
        })
    })
    .then(() => {
        items = items.filter(item => item && !chkValues.includes(item.pseq))

        chkValues.forEach((value, i) => {
            const removeItem = document.querySelector(`input[type="checkbox"][value="${value}"]`)
            if(removeItem) {
                const row = removeItem.closest('tr')
                if(row) {
                    row.remove()
                }
            }
        })

        qtyInputs = document.querySelectorAll('.qty_number')
        calcAllPricesSum()
    })
    .catch(err => console.log(err))
}