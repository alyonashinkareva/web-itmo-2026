<#import "commons.ftlh" as c>

<@c.page>
    <div class="my-articles datatable">
        <div class="caption">My articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Visibility</th>
            </tr>
            </thead>
            <tbody>
            <#list articles as a>
                <tr>
                    <td>${a.id?c}</td>
                    <td>${a.title}</td>
                    <td>
                        <button data-article-id="${a.id?c}" data-hidden="${a.hidden?c}">${a.hidden?then("Show", "Hide")}</button>
                    </td>
                </tr>
            <#else>
                <tr class="noData">
                    <td colspan="3">No data</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            $(".my-articles button").click(function () {
                const $button = $(this);
                const hidden = !$button.data("hidden");
                ajax({
                    data: {action: "setHidden", articleId: $button.data("article-id"), hidden: hidden},
                    success: function (response) {
                        if (response.error) {
                            notify(response.error, true);
                        } else {
                            $button.data("hidden", hidden);
                            $button.text(hidden ? "Show" : "Hide");
                        }
                    }
                });
            });
        });
    </script>
</@c.page>
