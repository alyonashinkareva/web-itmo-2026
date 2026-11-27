<#import "commons.ftlh" as c>

<@c.page>
    <div id="articles"></div>

    <template id="article-template">
        <article class="post">
            <h2 class="post-title"></h2>
            <div class="post-info"><span class="post-author"></span> · <time class="post-date"></time></div>
            <div class="post-text"></div>
        </article>
    </template>

    <script>
        $(function () {
            var articles = $("#articles");
            var template = document.getElementById("article-template");

            ajax({
                url: "/index",
                type: "GET",
                data: {action: "articles"},
                success: function (response) {
                    if (response.articles.length === 0) {
                        articles.text("No articles");
                    }

                    for (var article of response.articles) {
                        var post = $(template.content.firstElementChild.cloneNode(true));
                        post.find(".post-title").text(article.title);
                        post.find(".post-author").text(article.authorLogin);
                        post.find(".post-date").text(article.creationTime);
                        post.find(".post-text").text(article.text);
                        articles.append(post);
                    }
                }
            });
        });
    </script>
</@c.page>
