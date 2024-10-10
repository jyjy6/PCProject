        function showPopup() {
            const popup = document.querySelector('.nav-popup');
            if (popup.classList.contains('show-popup')) {
                popup.style.opacity = '0';
                setTimeout(() => {
                    popup.classList.remove('show-popup');
                }, 300);
            } else {
                popup.classList.add('show-popup');
                setTimeout(() => {
                    popup.style.opacity = '1';
                }, 10);
            }
        }