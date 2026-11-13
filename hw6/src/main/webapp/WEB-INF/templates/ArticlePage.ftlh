<#import "commons.ftlh" as c>

<@c.page>
    <div class="create-article form-box">
        <div class="header">Create article</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="create"/>
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" name="title" type="text"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text" rows="8"></textarea>
                    </div>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".create-article form").submit(function () {
                const $error = $(this).find(".error");
                ajax({
                    data: $(this).serialize(),
                    success: function (response) {
                        $error.text(response.error || "");
                    }
                });
                return false;
            });
        });
    </script>
</@c.page>
