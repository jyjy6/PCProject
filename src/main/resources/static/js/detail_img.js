const detailArea = document.querySelector('.detail_area')
const productSeq = document.querySelector('.seq').value

getDetailSpec()

function getDetailSpec() {
    const options = { method: 'GET' };
    fetch(`../api/detail/${productSeq}`, options)
        .then((response) => {
            if(!response.ok) throw new Error('조회된 상세 이미지가 없습니다.' + response.status)
            return response.json()
        })
        .then((data) => {
            printDetailSpec(data)
        })
        .catch(err => console.error('error: ', err))
}

function printDetailSpec(data) {
    detailArea.innerHTML = ''
    if(data && data.length > 0) {
        data.forEach(d => {
            const img = document.createElement('img');
            img.src = '/images/' + d.fileName
            img.alt = '이미지가 없습니다.'
            detailArea.appendChild(img)
        })
    }
}
