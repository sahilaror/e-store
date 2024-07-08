let index = 0;

function moveLeft() {
    const slides = document.querySelector('.slider-content');
    const items = document.querySelectorAll('.item');
    index = (index > 0) ? index - 1 : items.length - 1;
    updateSlider();
}

function moveRight() {
    const slides = document.querySelector('.slider-content');
    const items = document.querySelectorAll('.item');
    index = (index < items.length - 1) ? index + 1 : 0;
    updateSlider();
}

function updateSlider() {
    const slides = document.querySelector('.slider-content');
    slides.style.transform = 'translateX(' + (-index * 25) + '%)';
}
function startSlider() {
    slideInterval = setInterval(moveRight, 2000); // Change photo every 2 seconds
}
function resetSliderInterval() {
    clearInterval(slideInterval);
    startSlider();
}





