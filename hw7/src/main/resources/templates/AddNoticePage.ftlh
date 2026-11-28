<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Add Notice</div>
        <form method="post" action="<@spring.url '/addNotice'/>">
            <div class="field">
                <div class="name"><label for="content">Content</label></div>
                <div class="value">
                    <textarea id="content" name="content" rows="5" maxlength="65000">${noticeForm.content!}</textarea>
                </div>
                <@c.error "noticeForm.content"/>
            </div>
            <div class="button-field"><input type="submit" value="Add"></div>
        </form>
    </div>
</@c.page>
