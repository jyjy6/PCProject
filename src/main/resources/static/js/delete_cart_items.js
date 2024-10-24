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
        return resp.json()
    })
     .then(() => {
         chkValues.forEach(value => {
             const removeItem = document.querySelector(`input[type="checkbox"][value="${value}"]`).closest('tr')
             if(removeItem) removeItem.parentNode.removeChild(removeItem)
         })
         calcAllPricesSum()
     })
    .catch(err => console.log(err))
}