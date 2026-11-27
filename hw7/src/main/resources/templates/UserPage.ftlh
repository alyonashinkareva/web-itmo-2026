<#import "commons.ftlh" as c>

<@c.page>
    <#if profile??>
        <div class="user-profile datatable">
            <div class="caption">User</div>
            <table>
                <tbody>
                <tr>
                    <th>Id</th>
                    <td>${profile.id?c}</td>
                </tr>
                <tr>
                    <th>Login</th>
                    <td>${profile.login}</td>
                </tr>
                <tr>
                    <th>Created</th>
                    <td>${profile.creationTime}</td>
                </tr>
                </tbody>
            </table>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
