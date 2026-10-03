const pages = document.querySelectorAll("main");

const pageMap = {
    home: {
        link: document.querySelector("nav li:nth-child(1) a"),
        index: 0
    },
    submissions: {
        link: document.querySelector("nav li:nth-child(3) a"),
        index: 1
    },
    login: {
        link: document.querySelector(".enter-or-register-box a"),
        index: 2
    }
};

function showPage(index) {
    pages.forEach(page => {
        page.style.display = "none";
    });

    pages[index].style.display = "block";
}

Object.values(pageMap).forEach(page => {
    page.link.addEventListener("click", event => {
        event.preventDefault();
        showPage(page.index);
    });
});

showPage(pageMap.home.index);