<#import "commons.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">My articles</div>
        <table>
            <thead>
            <tr><th>Id</th><th>Title</th><th>Visibility</th></tr>
            </thead>
            <tbody>
            <#list articles as article>
                <tr>
                    <td>${article.id?c}</td>
                    <td>${article.title}</td>
                    <td>
                        <button type="button" class="change-hidden" data-id="${article.id?c}"
                                data-hidden="${(!article.hidden)?c}">${article.hidden?string("Show", "Hide")}</button>
                    </td>
                </tr>
            <#else>
                <tr><td colspan="3">No articles</td></tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            $(".change-hidden").on("click", function () {
                var button = $(this);
                button.prop("disabled", true);

                ajax({
                    url: "/myArticles",
                    data: {
                        action: "updateHidden",
                        id: button.attr("data-id"),
                        hidden: button.attr("data-hidden")
                    },
                    success: function (response) {
                        if (response.error) {
                            notify(response.error, true);
                            return;
                        }
                        button.text(response.hidden ? "Show" : "Hide");
                        button.attr("data-hidden", String(!response.hidden));
                    }
                }).always(function () {
                    button.prop("disabled", false);
                });
            });
        });
    </script>
</@c.page>
