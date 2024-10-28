document.querySelectorAll('.delete_btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
        const pSeq = e.target.value
        alertDelete(pSeq)

        function alertDelete(pSeq) {
            if(confirm('삭제하시겠습니까?')) deleteItem(pSeq)
        }

        function deleteItem(pSeq) {
            fetch(`api/purchaseList/deleteItem/${pSeq}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ pSeq: pSeq, username: username })
            })
            .then(resp => {
                if (!resp.ok) {
                    throw new Error('네트워크 응답 실패!')
                }
                return resp.json()
            })
            .then(() => {
                window.location.reload()
            })
            .catch(err => console.log(err))
        }
    })
})

