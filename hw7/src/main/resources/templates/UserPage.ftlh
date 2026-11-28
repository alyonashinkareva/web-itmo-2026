<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User profile</div>

        <#if profileUser??>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Login</th>
                    <th>Created</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="id">${profileUser.id}</td>
                    <td class="login">${profileUser.login}</td>
                    <td class="creationTime">${profileUser.creationTime}</td>
                </tr>
                </tbody>
            </table>
        <#else>
            <p>No such user</p>
        </#if>
    </div>
</@c.page>