document.addEventListener('DOMContentLoaded', () => {
    const links = document.querySelectorAll("nav a[data-target]");
    const pages = document.querySelectorAll(".page");

    links.forEach(link => {
        link.addEventListener('click', (ev) => {
            ev.preventDefault();

            const targetID = link.getAttribute("data-target");
            const activePage = Array.from(pages).find(page => {
                return page.classList.contains('active');
            });

            if (targetID !== activePage.getAttribute('id')) {
                const targetPage = document.getElementById(targetID);
                targetPage.classList.add('active');
                activePage.classList.remove('active');
            }
        });
    });
});