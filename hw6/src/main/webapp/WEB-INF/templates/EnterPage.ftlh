<#import "commons.ftlh" as c>

<@c.page>
    <div class="enter form-box">
        <div class="header">Enter</div>
        <div class="body">
            <form class="ajax-form" method="post" action="/enter">
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
                <div class="button-field">
                    <input type="submit" value="Enter">
                </div>
                <div class="error" role="alert"></div>
            </form>
        </div>
    </div>
</@c.page>
