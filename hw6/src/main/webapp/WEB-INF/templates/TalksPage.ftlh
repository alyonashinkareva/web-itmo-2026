<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">New message</div>
        <form method="post" action="/talks">
            <input type="hidden" name="action" value="send">
            <div class="field">
                <div class="name"><label for="targetUserId">To</label></div>
                <div class="value">
                    <select id="targetUserId" name="targetUserId">
                        <option value="">Choose a recipient</option>
                        <#list users as recipient>
                            <option value="${recipient.id?c}" <#if (targetUserId!"") == recipient.id?c>selected</#if>>
                                ${recipient.login}
                            </option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="field">
                <div class="name"><label for="text">Message</label></div>
                <div class="value">
                    <textarea id="text" name="text" rows="4">${text!}</textarea>
                </div>
            </div>
            <div class="button-field"><input type="submit" value="Send"></div>
            <#if error??>
                <div class="error" role="alert">${error}</div>
            </#if>
        </form>
    </div>

    <div class="datatable">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>Id</th><th>From</th><th>To</th><th>Text</th><th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as talk>
                <tr>
                    <td>${talk.id?c}</td>
                    <td>${talk.sourceLogin}</td>
                    <td>${talk.targetLogin}</td>
                    <td class="talk-text">${talk.text}</td>
                    <td>${talk.creationTime?datetime}</td>
                </tr>
            <#else>
                <tr><td colspan="5">No messages</td></tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
