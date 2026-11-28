<#import "commons.ftlh" as c>
<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.NoticeForm" -->

<@c.page>
    <div class="form-box">
        <div class="header">Create Notice</div>
        <div class="body">
            <form method="post" action="">
                <div class="field">
                    <div class="value">
                        <label for="content"> Content
                            <textarea name="content">${noticeForm.content!}</textarea>
                        </label>
                    </div>
                    <@c.error "noticeForm.content"/>
                </div>
                <div class="button-field">
                    <button type="submit">Create</button>
                </div>
            </form>
        </div>
    </div>
</@c.page>