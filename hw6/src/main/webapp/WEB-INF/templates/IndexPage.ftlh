<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles"></div>

    <template class="article-template">
        <article>
            <div class="title"></div>
            <div class="information">By <span class="author"></span>, <span class="creation-time"></span></div>
            <div class="body"></div>
        </article>
    </template>

    <script>
        $(function () {
            const template = $(".article-template")[0];
            ajax({
                data: {action: "findAll"},
                success: function (response) {
                    response.articles.forEach(function (article) {
                        const $article = $(template.content.firstElementChild).clone();
                        const $body = $article.find(".body");
                        $article.find(".title").text(article.title);
                        $article.find(".author").text(response.logins[article.userId]);
                        $article.find(".creation-time").text(article.creationTime);
                        article.text.split(/\r?\n/).forEach(function (line) {
                            if (line.trim()) {
                                $body.append($("<p>").text(line));
                            }
                        });
                        $(".articles").append($article);
                    });
                }
            });
        });
    </script>
</@c.page>
