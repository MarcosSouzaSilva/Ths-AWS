$(document).ready(function() {
    // Smooth scrolling for navigation links
    $('nav ul li a').click(function(event) {
        if (this.hash !== "") {
            event.preventDefault();
            var hash = this.hash;

            $('html, body').animate({
                scrollTop: $(hash).offset().top
            }, 800, function() {
                window.location.hash = hash;
            });
        }
    });

    // Animate sections on scroll
    $('.banner, .about, .services, .cases, .blog, .contact').waypoint(function(direction) {
        if (direction === 'down') {
            $(this.element).addClass('animate__animated animate__fadeInUp');
        }
    }, {
        offset: '70%'
    });
});
