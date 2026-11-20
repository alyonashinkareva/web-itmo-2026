<#-- @ftlvariable name="login" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="enter form-box">
        <div class="header">Enter</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="enter"/>
                <div class="field">
                    <div class="name">
                        <label for="loginOrEmail">Login or email</label>
                    </div>
                    <div class="value">
                        <input id="loginOrEmail" name="loginOrEmail"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="password">Password</label>
                    </div>
                    <div class="value">
                        <input id="password" name="password" type="password"/>
                    </div>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="Enter">
                </div>
            </form>
        </div>
    </div>
</@c.page>
<script>
    $(function () {
        $(".enter.form-box form").submit(function () {
            let $form = $(this);

            $form.find(".error").text("");

            ajax({
                action: "enter",
                loginOrEmail: $form.find('[name="loginOrEmail"]').val(),
                password: $form.find('[name="password"]').val(),
            }, function (response) {
                $form.find(".error").text(response["error"]);
            });

            return false;
        });
    });
</script>