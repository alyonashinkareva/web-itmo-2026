<#import "./common.ftlh" as c>

<@c.page>
    <h1>Users</h1>

    <table class="users">
        <thead>
        <tr>
            <th>Id</th>
            <th>User</th>
            <th>Name</th>
        </tr>
        </thead>

        <tbody>
        <#list users as u>
            <tr>
                <td>${u.id}</td>
                <td><@c.userlink u/></td>
                <td>${u.name}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>