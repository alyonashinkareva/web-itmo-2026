const pageLinks = document.querySelectorAll("nav [data-page]");
const pages = document.querySelectorAll(".middle > main");

pageLinks.forEach((link) => {
    link.addEventListener("click", (event) => {
        event.preventDefault();

        pages.forEach((page) => {
            page.hidden = page.id !== link.dataset.page;
        });

        pageLinks.forEach((pageLink) => {
            if (pageLink === link) {
                pageLink.setAttribute("aria-current", "page");
            } else {
                pageLink.removeAttribute("aria-current");
            }
        });
    });
});
