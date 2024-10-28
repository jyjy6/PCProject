const stars = document.querySelectorAll('.star')
const ratingValue = document.getElementById('rating-value')

stars.forEach(star => {
    star.addEventListener('click', () => {
        const value = star.getAttribute('data-value')

        // 선택된 별의 색상 변경
        stars.forEach(s => {
            s.classList.remove('selected')
            if (s.getAttribute('data-value') <= value) {
                s.classList.add('selected')
            }
        })
        ratingValue.textContent = value
    })
})