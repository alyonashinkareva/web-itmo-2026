<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if user?? && user.admin>
                    <th>Active</th>
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
                <#list users as u>
                    <tr>
                        <td class="id">${u.id}</td>
                        <td class="login">${u.login}</td>
                        <td class="creationTime">${u.creationTime}</td>
                        <#if user?? && user.admin>
                            <td>
                                <form method="post">
                                    <input type="hidden" name="id" value="${u.id?c}">
                                    <#if u.disabled>
                                        <button name="disabled" value="false">Enable</button>
                                    <#else>
                                        <button name="disabled" value="true">Disable</button>
                                    </#if>
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
