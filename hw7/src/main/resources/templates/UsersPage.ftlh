<#import "commons.ftlh" as c>

<@c.page>
    <#assign isAdmin = user?? && user.admin>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if isAdmin>
                    <th>Actions</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                    <tr>
                        <td class="id">${user.id}</td>
                        <td class="login">${user.login}</td>
                        <td class="creationTime">${user.creationTime}</td>
                        <#if isAdmin>
                            <td>
                                <form method="post" action="">
                                    <input type="hidden" name="id" value="${user.id?c}"/>
                                    <input type="hidden" name="disabled" value="${(!user.disabled)?c}"/>
                                    <input type="submit" value="${user.disabled?then('Enable', 'Disable')}"/>
                                </form>
                            </td>
                        </#if>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
