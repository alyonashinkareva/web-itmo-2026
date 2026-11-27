<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">New article</div>
        <form class="ajax-form" method="post" action="/article">
            <input type="hidden" name="action" value="create">
            <div class="field">
                <div class="name"><label for="title">Title</label></div>
                <div class="value"><input id="title" name="title" type="text"></div>
            </div>
            <div class="field">
                <div class="name"><label for="text">Text</label></div>
                <div class="value"><textarea id="text" name="text" rows="6"></textarea></div>
            </div>
            <div class="button-field"><input type="submit" value="Create"></div>
            <div class="error" role="alert"></div>
        </form>
    </div>
</@c.page>
