<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <#assign showActions = user?? && user.admin>
    <div class="users datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if showActions><th>Actions</th></#if>
            </tr>
            </thead>
            <tbody>
            <#list users as account>
                <tr>
                    <td class="id">${account.id?c}</td>
                    <td class="login"><a href="<@spring.url ('/user/' + account.id?c)/>">${account.login}</a></td>
                    <td class="creationTime">${account.creationTime?datetime}</td>
                    <#if showActions>
                        <td>
                            <form method="post" action="<@spring.url '/users'/>">
                                <input type="hidden" name="id" value="${account.id?c}">
                                <input type="hidden" name="disabled" value="${(!account.disabled)?c}">
                                <button type="submit">${account.disabled?string("Enable", "Disable")}</button>
                            </form>
                        </td>
                    </#if>
                </tr>
            <#else>
                <tr class="noData">
                    <td colspan="${showActions?string('4', '3')}">No data</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
